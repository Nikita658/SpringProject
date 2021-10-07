package com.example.hippodrome.controller.utils;

import com.example.hippodrome.repository.model.HorseEntity;
import com.example.hippodrome.repository.model.RaceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Container {

    private RaceEntity race;
    private HorseEntity betHorse;
}
