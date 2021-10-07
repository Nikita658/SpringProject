package com.example.hippodrome.controller;

import com.example.hippodrome.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/race")
public class RaceController {

    @Autowired
    private RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/start")
    public ModelAndView startNewRace() {
        return new ModelAndView("start");
    }

    @GetMapping("/{id}")
    public ModelAndView raceDetails(@PathVariable String id, Model model) {
        model.addAttribute("race", raceService.getRace(Integer.parseInt(id)));
        return new ModelAndView("race");
    }
}
