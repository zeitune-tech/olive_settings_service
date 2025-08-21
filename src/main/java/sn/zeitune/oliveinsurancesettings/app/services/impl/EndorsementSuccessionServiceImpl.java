// sn/zeitune/oliveinsurancesettings/app/services/impl/EndorsementSuccessionServiceImpl.java
package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.dtos.requests.SuccessionConfigRequest;
import sn.zeitune.oliveinsurancesettings.app.dtos.responses.SuccessionConfigResponse;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.EndorsementNatureRank;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.EndorsementSuccession;
import sn.zeitune.oliveinsurancesettings.app.repositories.endorsement.EndorsementNatureRankRepository;
import sn.zeitune.oliveinsurancesettings.app.repositories.endorsement.EndorsementSuccessionRepository;
import sn.zeitune.oliveinsurancesettings.app.services.EndorsementSuccessionService;
import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;
import sn.zeitune.oliveinsurancesettings.security.Employee;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EndorsementSuccessionServiceImpl implements EndorsementSuccessionService {

    private final EndorsementSuccessionRepository successionRepo;
    private final EndorsementNatureRankRepository rankRepo;

    /* -------- Defaults (règles + rangs) -------- */
    private static final Map<NatureEndorsement, List<NatureEndorsement>> DEFAULT_RULES;
    private static final Map<NatureEndorsement, Integer> DEFAULT_RANKS;
    static {
        Map<NatureEndorsement, List<NatureEndorsement>> r = new EnumMap<>(NatureEndorsement.class);

        put(r, NatureEndorsement.NEW_BUSINESS, List.of(
                NatureEndorsement.MODIFICATION,
                NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT,
                NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.MODIFICATION, List.of(
                NatureEndorsement.MODIFICATION,
                NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT,
                NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.INCORPORATION, List.of(
                NatureEndorsement.MODIFICATION,
                NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT,
                NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.RETRACT, List.of(
                NatureEndorsement.MODIFICATION,
                NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT,
                NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.SUSPENSION, List.of(
                NatureEndorsement.REINSTATEMENT_WITH_DISCOUNT,
                NatureEndorsement.REINSTATEMENT,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.REINSTATEMENT_WITH_DISCOUNT, List.of(
                NatureEndorsement.MODIFICATION,
                NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT,
                NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.REINSTATEMENT, List.of(
                NatureEndorsement.MODIFICATION,
                NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT,
                NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.RENEWAL, List.of(
                NatureEndorsement.MODIFICATION,
                NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT,
                NatureEndorsement.SUSPENSION,
                NatureEndorsement.CANCELLATION
        ));
        put(r, NatureEndorsement.CANCELLATION, List.of());

        for (NatureEndorsement n : NatureEndorsement.values()) {
            r.putIfAbsent(n, List.of());
        }
        DEFAULT_RULES = Collections.unmodifiableMap(r);

        Map<NatureEndorsement, Integer> ranks = new EnumMap<>(NatureEndorsement.class);
        // 1 = avant tout
        ranks.put(NatureEndorsement.NEW_BUSINESS, 1);
        ranks.put(NatureEndorsement.MODIFICATION, 2);
        ranks.put(NatureEndorsement.INCORPORATION, 3);
        ranks.put(NatureEndorsement.RETRACT, 4);
        ranks.put(NatureEndorsement.SUSPENSION, 5);
        ranks.put(NatureEndorsement.REINSTATEMENT_WITH_DISCOUNT, 6);
        ranks.put(NatureEndorsement.REINSTATEMENT, 7);
        ranks.put(NatureEndorsement.RENEWAL, 8);
        ranks.put(NatureEndorsement.CANCELLATION, 9);
        DEFAULT_RANKS = Collections.unmodifiableMap(ranks);
    }

    private static void put(Map<NatureEndorsement, List<NatureEndorsement>> m,
                            NatureEndorsement key, List<NatureEndorsement> values) {
        m.put(key, List.copyOf(values));
    }

    /* ---------- API ---------- */

    @Override
    @Transactional(readOnly = true)
    public SuccessionConfigResponse getConfig() {
        UUID tenant = currentTenant();

        // rules
        Map<NatureEndorsement, List<NatureEndorsement>> rules = new EnumMap<>(NatureEndorsement.class);
        for (NatureEndorsement n : NatureEndorsement.values()) rules.put(n, new ArrayList<>());
        for (EndorsementSuccession row : successionRepo.findAllByManagementEntity(tenant)) {
            rules.get(row.getFromNature()).add(row.getToNature());
        }
        rules.replaceAll((k, v) -> v.stream().distinct().collect(Collectors.toList()));

        // ranks
        Map<NatureEndorsement, Integer> ranks = new EnumMap<>(NatureEndorsement.class);
        rankRepo.findAllByManagementEntity(tenant).forEach(r ->
                ranks.put(r.getNature(), r.getRank())
        );

        // si vide, renvoyer défauts (ou laisser vide si tu préfères)
        if (ranks.isEmpty()) ranks.putAll(DEFAULT_RANKS);
        for (NatureEndorsement n : NatureEndorsement.values()) rules.putIfAbsent(n, List.of());

        return new SuccessionConfigResponse(rules, ranks);
    }

    @Override
    @Transactional
    public SuccessionConfigResponse saveConfig(SuccessionConfigRequest request) {
        UUID tenant = currentTenant();

        Map<NatureEndorsement, List<NatureEndorsement>> rules = normalizeRules(request.rules());
        Map<NatureEndorsement, Integer> ranks = normalizeRanks(request.ranks());

        // purge
        successionRepo.hardDeleteByManagementEntity(tenant);
        rankRepo.deleteByManagementEntity(tenant);

        // --- Déduplication stricte des paires avant insert ---
        Set<String> seenPairs = new HashSet<>();
        List<EndorsementSuccession> toSaveRules = new ArrayList<>();

        rules.forEach((from, tos) -> {
            for (NatureEndorsement to : tos) {
                if (to == null) continue;
                String key = from.name() + "→" + to.name(); // clé unique en mémoire
                if (seenPairs.add(key)) {
                    toSaveRules.add(EndorsementSuccession.builder()
                            .fromNature(from)
                            .toNature(to)
                            .managementEntity(tenant)
                            .build());
                }
                // sinon: doublon ignoré
            }
        });

        successionRepo.saveAll(toSaveRules);

        // ranks (déjà normalisés, aucun null/dup)
        List<EndorsementNatureRank> toSaveRanks = ranks.entrySet().stream()
                .map(e -> EndorsementNatureRank.builder()
                        .managementEntity(tenant)
                        .nature(e.getKey())
                        .rank(e.getValue())
                        .build())
                .toList();

        rankRepo.saveAll(toSaveRanks);

        return new SuccessionConfigResponse(rules, ranks);
    }


    @Override
    public SuccessionConfigResponse defaultConfig() {
        return new SuccessionConfigResponse(DEFAULT_RULES, DEFAULT_RANKS);
    }

    /* ---------- Helpers ---------- */

    private Map<NatureEndorsement, List<NatureEndorsement>> normalizeRules(
            Map<NatureEndorsement, List<NatureEndorsement>> in) {

        Map<NatureEndorsement, List<NatureEndorsement>> out = new EnumMap<>(NatureEndorsement.class);

        for (NatureEndorsement n : NatureEndorsement.values()) {
            List<NatureEndorsement> raw = Optional.ofNullable(in)
                    .map(m -> m.get(n))
                    .orElseGet(List::of);

            // unicité par FROM avec conservation d'ordre
            LinkedHashSet<NatureEndorsement> uniq = new LinkedHashSet<>();
            for (NatureEndorsement to : raw) {
                if (to != null) uniq.add(to);
            }
            out.put(n, new ArrayList<>(uniq));
        }
        return out;
    }


    private Map<NatureEndorsement, Integer> normalizeRanks(Map<NatureEndorsement, Integer> in) {
        Map<NatureEndorsement, Integer> out = new EnumMap<>(NatureEndorsement.class);
        Set<Integer> used = new HashSet<>();

        // 1) NEW_BUSINESS toujours 1 (si tu veux ce verrou)
        out.put(NatureEndorsement.NEW_BUSINESS, 1);
        used.add(1);

        // 2) Pré-récup des valeurs proposées côté client
        Map<NatureEndorsement, Integer> proposed =
                Optional.ofNullable(in).orElseGet(() -> new EnumMap<>(NatureEndorsement.class));

        // 3) Pour chaque nature (sauf NEW_BUSINESS déjà fixée), on normalise
        for (NatureEndorsement n : NatureEndorsement.values()) {
            if (n == NatureEndorsement.NEW_BUSINESS) continue;

            Integer r = proposed.get(n);
            // par défaut -> 2,3,4,... (en partant de 2 pour laisser 1 à NEW_BUSINESS)
            int base = 2 + out.size() - 1; // -1 car NEW_BUSINESS déjà mis
            int rank = (r != null && r > 0) ? r : base;

            // résout les collisions naïvement en incrémentant
            while (used.contains(rank)) rank++;

            out.put(n, rank);
            used.add(rank);
        }

        return out;
    }

    private UUID currentTenant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof sn.zeitune.oliveinsurancesettings.security.Employee emp)) {
            throw new IllegalStateException("Authenticated Employee required");
        }
        return emp.getManagementEntity();
    }
}
