package com.example.hippodrome.repository.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "horse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int horseId;
    private boolean bet;
    private int place;
    private int time;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RaceEntity race;

    public HorseEntity(int horseId, boolean bet, int place, int time, RaceEntity race) {
        this.horseId = horseId;
        this.bet = bet;
        this.place = place;
        this.time = time;
        this.race = race;
    }
}