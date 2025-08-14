package sn.zeitune.oliveinsurancesettings.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.zeitune.oliveinsurancesettings.app.entities.endorsement.EndorsementSuccession;
import sn.zeitune.oliveinsurancesettings.app.repositories.EndorsementSuccessionRepository;
import sn.zeitune.oliveinsurancesettings.app.services.EndorsementSuccessionService;
import sn.zeitune.oliveinsurancesettings.enums.NatureEndorsement;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EndorsementSuccessionServiceImpl implements EndorsementSuccessionService {

    private final EndorsementSuccessionRepository repository;
    private static final List<NatureEndorsement> ALL = Arrays.asList(NatureEndorsement.values());

    @Override
    @Transactional(readOnly = true)
    public Map<NatureEndorsement, List<NatureEndorsement>> getRules() {
        List<EndorsementSuccession> all = repository.findAll();
        Map<NatureEndorsement, List<NatureEndorsement>> map = new EnumMap<>(NatureEndorsement.class);
        for (NatureEndorsement n : ALL) map.put(n, new ArrayList<>());
        for (EndorsementSuccession row : all) {
            map.get(row.getFromNature()).add(row.getToNature());
        }
        map.replaceAll((k,v) -> v.stream().sorted(Comparator.comparing(Enum::name)).toList());
        return map;
    }

    @Override
    public Map<NatureEndorsement, List<NatureEndorsement>> saveRules(Map<NatureEndorsement, List<NatureEndorsement>> input) {
        // normalisation & filtrage
        Map<NatureEndorsement, List<NatureEndorsement>> cleaned = new EnumMap<>(NatureEndorsement.class);
        for (NatureEndorsement from : ALL) {
            List<NatureEndorsement> nexts = Optional.ofNullable(input.get(from)).orElseGet(List::of).stream()
                    .filter(ALL::contains)
                    .distinct()
                    .toList();
            cleaned.put(from, nexts);
        }

        // stratégie de remplacement: purge par from → insert nouveaux liens
        for (NatureEndorsement from : ALL) {
            repository.deleteAllByFromNature(from);
            List<EndorsementSuccession> rows = cleaned.get(from).stream()
                    .map(to -> EndorsementSuccession.builder()
                            .fromNature(from)
                            .toNature(to)
                            .build())
                    .toList();
            if (!rows.isEmpty()) repository.saveAll(rows);
        }
        return getRules();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<NatureEndorsement, List<NatureEndorsement>> defaultRules() {
        Map<NatureEndorsement, List<NatureEndorsement>> d = new EnumMap<>(NatureEndorsement.class);

        d.put(NatureEndorsement.MODIFICATION, List.of(
                NatureEndorsement.MODIFICATION, NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT, NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL, NatureEndorsement.CANCELLATION));

        d.put(NatureEndorsement.INCORPORATION, List.of(
                NatureEndorsement.MODIFICATION, NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT, NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL, NatureEndorsement.CANCELLATION));

        d.put(NatureEndorsement.RETRACT, List.of(
                NatureEndorsement.MODIFICATION, NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT, NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL, NatureEndorsement.CANCELLATION));

        d.put(NatureEndorsement.SUSPENSION, List.of(
                NatureEndorsement.REINSTATEMENT_WITH_DISCOUNT,
                NatureEndorsement.REINSTATEMENT, NatureEndorsement.CANCELLATION));

        d.put(NatureEndorsement.REINSTATEMENT_WITH_DISCOUNT, List.of(
                NatureEndorsement.MODIFICATION, NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT, NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL, NatureEndorsement.CANCELLATION));

        d.put(NatureEndorsement.REINSTATEMENT, List.of(
                NatureEndorsement.MODIFICATION, NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT, NatureEndorsement.SUSPENSION,
                NatureEndorsement.RENEWAL, NatureEndorsement.CANCELLATION));

        d.put(NatureEndorsement.RENEWAL, List.of(
                NatureEndorsement.MODIFICATION, NatureEndorsement.INCORPORATION,
                NatureEndorsement.RETRACT, NatureEndorsement.SUSPENSION,
                NatureEndorsement.CANCELLATION));

        d.put(NatureEndorsement.CANCELLATION, List.of());

        return d;
    }
}
