package com.deloitte.classifieds;

import com.deloitte.classifieds.service.ClassifiedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ClassifiedsApplication implements CommandLineRunner {
    @Autowired
    private ClassifiedsService classifiedsService;

    public static void main(String[] args) {
        SpringApplication.run(ClassifiedsApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
       /*List<String> users = List.of("Pradeep", "Souji", "Bhanu", "Vinodh");
        List<String> names = List.of("Car", "Phone", "Bike", "Television", "Table", "Laptop", "Desktop", "MacBook", "IPad");
        final List<Classified> classifieds = Stream.generate(new Random()::nextInt)
                .limit(105).map(i -> {
                    int absI = Math.abs(i);
                    String sellerId = users.get(absI % users.size());
                    String category = names.get(absI % names.size());
                    String name = category + " " + absI;
                    LocalDate purchaseDate = LocalDate.now().minus(absI%11, ChronoUnit.YEARS);
                    Double sellingPrice = Double.valueOf(absI);
                    return new Classified(null, sellerId, name, category, purchaseDate, sellingPrice);
                }).toList();
        classifiedsService.saveAllClassifieds(classifieds);*/
    }
}
