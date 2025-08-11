package sn.zeitune.oliveinsurancesettings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OliveInsuranceSettingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OliveInsuranceSettingsApplication.class, args);
    }

}
