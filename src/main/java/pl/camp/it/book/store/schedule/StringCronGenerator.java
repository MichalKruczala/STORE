package pl.camp.it.book.store.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


public class StringCronGenerator {

    @Scheduled(cron = "4,11,16,23,35,43,51,56 * * ? * *")
    public void generateString() {
        System.out.println("zadanie cyklicze !!!");
    }
}
