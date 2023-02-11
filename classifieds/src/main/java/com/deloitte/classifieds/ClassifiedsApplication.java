package com.deloitte.classifieds;

import com.deloitte.classifieds.controllers.models.Classified;
import com.deloitte.classifieds.service.ClassifiedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class ClassifiedsApplication implements CommandLineRunner {
    @Autowired
    private ClassifiedsService classifiedsService;

    public static void main(String[] args) {
        SpringApplication.run(ClassifiedsApplication.class, args);
    }


    @Override
    public void run(final String... args) throws Exception {
       /* List<String> users = List.of("Pradeep", "Souji", "Bhanu", "Vinodh", "Swathi");
        List<String> names = List.of("Car", "Phone", "Exercise Bike", "Television", "Table");
        final List<Classified> classifieds = Stream.generate(new Random()::nextInt)
                .limit(55).map(i -> {
                    int absI = Math.abs(i);
                    String sellerId = users.get(absI % users.size());
                    String category = names.get(absI % names.size());
                    String name = category + " " + absI;
                    LocalDate purchaseDate = LocalDate.now().minus(absI, ChronoUnit.DAYS);
                    Double sellingPrice = Double.valueOf(absI);
                    return new Classified(null, sellerId, name, category, purchaseDate, sellingPrice);
                }).toList();
        classifiedsService.saveAllClassifieds(classifieds);*/
    }
}
