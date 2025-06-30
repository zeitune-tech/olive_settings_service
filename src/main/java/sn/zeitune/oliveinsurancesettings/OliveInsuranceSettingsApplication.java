package sn.zeitune.oliveinsurancesettings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class OliveInsuranceSettingsApplication {

    @GetMapping
    public String home() {
        return "Welcome to Olive Insurance Settings Service!";
    }

    public static void main(String[] args) {
        SpringApplication.run(OliveInsuranceSettingsApplication.class, args);
    }

}
