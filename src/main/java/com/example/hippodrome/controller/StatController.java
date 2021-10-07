package com.example.hippodrome.controller;

import com.example.hippodrome.controller.utils.HorseTimeContainer;
import com.example.hippodrome.controller.utils.Container;
import com.example.hippodrome.repository.model.HorseEntity;
import com.example.hippodrome.repository.model.RaceEntity;
import com.example.hippodrome.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("")
public class StatController {

    @Autowired
    RaceService raceService;
    private final HorseTimeContainer horseTimeContainer = new HorseTimeContainer();
    private final List<Container> result = new ArrayList<>();

    public StatController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping("/stats")
    public ModelAndView amountOfHorses(@RequestParam String amount, @RequestParam String horseNum, Model model) {

        int amountInt;
        int horseInt;
        RaceEntity race = null;
        if (!(amount.equals("")) && !(horseNum.equals(""))) {
            amountInt = Integer.parseInt(amount);
            horseInt = Integer.parseInt(horseNum);
            model.addAttribute("amount", amountInt);
            model.addAttribute("horseNum", horseInt);
            race = new RaceEntity(0, LocalDate.now().toString());
            if ((amountInt >= horseInt) && amountInt != 0 && horseInt != 0) {
                insertToDb(amountInt, horseInt, race);
            } else {
                model.addAttribute("amount", 0);
                model.addAttribute("horseNum", 0);
            }
        } else {
            model.addAttribute("amount", 0);
            model.addAttribute("horseNum", 0);
        }
        int size = raceService.getAllRaces().size();
        model.addAttribute("size", size);
        model.addAttribute("races", raceService.getAllRaces());
        raceService.getRace(race.getId()).stream().forEach(race1 -> {
                    HorseEntity betHorse = race1.getHorses().stream()
                            .filter(HorseEntity::isBet).findAny().get();
                    Container horseWithRace = new Container(race1, betHorse);
                    result.add(horseWithRace);
                });
        model.addAttribute("betHorse", result);
                return new ModelAndView("stats");
    }


    /*@GetMapping("/stats/stat")
    public ModelAndView getRaces(Model model) {
        model.addAttribute("race", raceService.getAllRaces());
        return new ModelAndView("race");
    }*/


    private void insertToDb(int amount, int horseInt, RaceEntity race) {
        int prevDif = 0;
        horseTimeContainer.setHorseTimeArray(new int[amount]);
        for (int i = 0; i < amount; i++) {
            HorseEntity horse = new HorseEntity(i + 1, false, prevDif, 0, race);
            race.getHorses().add(horse);
            long before = System.currentTimeMillis();
            horseTime(amount);
            long after = System.currentTimeMillis();
            int dif = (int) (after - before);
            horseTimeContainer.getHorseTimeArray()[i] = dif;
            race.getHorses().get(i).setTime(dif);
            if (horse.getHorseId() == horseInt) {
                race.getHorses().get(i).setBet(true);
            }
            if (dif > prevDif) {
                prevDif = dif;
            }
        }
        sortHorseTime(race);
        raceService.saveRace(race);
    }

    private void sortHorseTime(RaceEntity race) {
        Arrays.sort(horseTimeContainer.getHorseTimeArray());
        for (int i = 0; i < horseTimeContainer.getHorseTimeArray().length; i++) {
            for (int j = 0; j < horseTimeContainer.getHorseTimeArray().length; j++) {
                if (horseTimeContainer.getHorseTimeArray()[i] == race.getHorses().get(j).getTime()) {
                    race.getHorses().get(j).setPlace(i + 1);
                }
            }
        }
    }

    private static void horseTime(int amount) {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(amount);
        AtomicInteger raceDistance = new AtomicInteger();

        Runnable r = () -> {
            try {
                Thread.sleep(new Random().nextInt(100) + 400);
                while (raceDistance.get() <= 1000) {
                    raceDistance.addAndGet(new Random().nextInt(100) + 100);
                }
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < amount; i++) {
            executor.execute(r);
        }
        try {
            latch.await(2, TimeUnit.SECONDS);
            System.out.println("All services up and running!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
