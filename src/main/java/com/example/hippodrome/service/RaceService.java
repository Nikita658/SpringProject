package com.example.hippodrome.service;

import com.example.hippodrome.repository.RaceRepository;
import com.example.hippodrome.repository.model.RaceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RaceService {
    private final RaceRepository raceRepository;

    @Autowired
    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public List<RaceEntity> getAllRaces() {
        List<RaceEntity> races = new ArrayList<>();
        raceRepository.findAll().forEach(it -> races.add(new RaceEntity(it.getId(), it.getCurrentDate())));
        return races;
    }

    public RaceEntity saveRace(RaceEntity race) {
        return raceRepository.save(race);
    }

    public void deleteRace(int id) {
        raceRepository.deleteById(id);
    }

    public Optional<RaceEntity> getRace(int id) {
        return raceRepository.findById(id).map(entity -> new RaceEntity(entity.getId(), entity.getHorses(), entity.getCurrentDate()));
    }
}
