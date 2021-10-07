package com.example.hippodrome.service;

import com.example.hippodrome.repository.HorseRepository;
import com.example.hippodrome.repository.model.HorseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HorseService {
    private final HorseRepository horseRepository;

    @Autowired
    public HorseService(HorseRepository horseRepository) {
        this.horseRepository = horseRepository;
    }

    public List<HorseEntity> getAllHorses() {
        List<HorseEntity> horses = new ArrayList<>();
        horseRepository.findAll().forEach(it -> horses.add(new HorseEntity(it.getId(), it.getHorseId(), it.isBet(), it.getTime(), it.getPlace(), it.getRace())));
        return horses;
    }

    public HorseEntity saveHorse(HorseEntity horse) {
        HorseEntity entity = horseRepository.save(new HorseEntity(horse.getHorseId(), horse.isBet(), horse.getPlace(), horse.getTime(), horse.getRace()));
        return new HorseEntity(entity.getId(), entity.getHorseId(), entity.isBet(), entity.getTime(), entity.getPlace(), entity.getRace());
    }

    public void deleteHorse(int id) {
        horseRepository.deleteById(id);
    }

    public Optional<HorseEntity> getHorse(int id) {
        return horseRepository.findById(id).map(entity -> new HorseEntity(entity.getId(), entity.getHorseId(), entity.isBet(), entity.getTime(), entity.getPlace(), entity.getRace()));
    }
}
