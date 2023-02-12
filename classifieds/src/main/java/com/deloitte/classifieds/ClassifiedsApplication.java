package com.deloitte.classifieds;

import com.deloitte.classifieds.controllers.models.Classified;
import com.deloitte.classifieds.service.ClassifiedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@EnableFeignClients
@SpringBootApplication
public class ClassifiedsApplication implements CommandLineRunner {
    private static final DecimalFormat df = new DecimalFormat("0.00");
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
                .limit(203).map(i -> {
                    int absI = Math.abs(i);
                    String sellerId = users.get(absI % users.size());
                    String category = names.get(absI % names.size());
                    String name = category + " " + absI;
                    LocalDate purchaseDate = LocalDate.now().minus(absI % 11, ChronoUnit.YEARS);
                    Double sellingPrice = Double.valueOf(absI) / 100000.0;
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    sellingPrice = Double.parseDouble(df.format(sellingPrice));
                    return new Classified(null, sellerId, name, category, purchaseDate, sellingPrice, null);
                }).toList();
        classifiedsService.saveAllClassifieds(classifieds);*/
    }
}
