package com.example.hippodrome.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "race")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "race_horses",
            joinColumns = {@JoinColumn(name = "race_id")},
            inverseJoinColumns = {@JoinColumn(name = "horse_id")})
    private List<HorseEntity> horses = new ArrayList<>();

    @Column(name = "date")
    private String currentDate;

    public RaceEntity(int id, String currentDate) {
        this.id = id;
        this.currentDate = currentDate;
    }
}