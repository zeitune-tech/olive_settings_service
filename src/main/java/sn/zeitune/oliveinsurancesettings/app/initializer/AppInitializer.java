package sn.zeitune.oliveinsurancesettings.app.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sn.zeitune.oliveinsurancesettings.app.services.BranchService;

@Component
@RequiredArgsConstructor
public class AppInitializer implements CommandLineRunner {

    private final BranchService branchService;

    @Override
    public void run(String... args) throws Exception {

        branchService.init();
    }
}
