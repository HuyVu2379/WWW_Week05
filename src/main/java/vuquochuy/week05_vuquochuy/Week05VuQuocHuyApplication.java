package vuquochuy.week05_vuquochuy;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vuquochuy.week05_vuquochuy.backend.models.Address;
import vuquochuy.week05_vuquochuy.backend.models.Candidate;
import vuquochuy.week05_vuquochuy.backend.repositories.AddressRepository;
import vuquochuy.week05_vuquochuy.backend.repositories.CandidateRepository;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
public class Week05VuQuocHuyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week05VuQuocHuyApplication.class, args);
    }
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private AddressRepository addressRepository;
//    @Bean
//    CommandLineRunner initData() {
//        return args -> {
//            Random rnd = new Random();
//            for (long i = 1; i < 1000; i++) {
//                Address add = new Address(i, "Quang Trung", "HCM",
//                        rnd.nextInt(70000, 80000), CountryCode.VN.toString(), "70000");
//                addressRepository.save(add); // Save the address first
//                Candidate can = new Candidate("Name #" + i,
//                        LocalDate.of(1998, rnd.nextInt(1, 13), rnd.nextInt(1, 29)),
//                        add, // Set the saved address
//                        rnd.nextLong(1111111111L, 9999999999L) + "",
//                        "email_" + i + "@gmail.com");
//                candidateRepository.save(can); // Save the candidate
//                System.out.println("Added: " + can);
//            }
//        };
//    }


}
