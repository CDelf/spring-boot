package fr.diginamic.hello;

import fr.diginamic.hello.dtos.PaysDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Profile("rest")
public class RestTemplateApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "rest");
        SpringApplication application = new SpringApplication(RestTemplateApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        PaysDto[] response = restTemplate.getForObject("https://restcountries.com/v3.1/all", PaysDto[].class);
        if(response != null) {
            for(PaysDto p : response) {
                System.out.println("Pays : " + (p.getNomCommun() != null ? p.getNomCommun() : "N/A"));
                if (p.getCapital() != null && !p.getCapital().isEmpty()) {
                    System.out.println("Capitale : " + p.getCapital().getFirst());
                } else {
                    System.out.println("Capitale : N/A");
                }
                if(p.getLanguages() != null && !p.getLanguages().isEmpty()) {
                    System.out.println("Langues : " + String.join(", ", p.getLanguages().values()));
                } else {
                    System.out.println("Langues : N/A");
                }
                System.out.println("Région : " + (p.getRegion() != null ? p.getRegion() : "N/A"));
                System.out.println("Sous-région : " + (p.getSubregion() != null ? p.getSubregion() : "N/A"));
                System.out.println("Population : " + p.getPopulation());
                System.out.println("---------------------------------------------------");
            }
        } else {
            throw new NullPointerException("response null");
        }
    }
}
