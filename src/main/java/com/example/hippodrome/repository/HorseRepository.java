package com.example.hippodrome.repository;

import com.example.hippodrome.repository.model.HorseEntity;
import com.example.hippodrome.repository.model.RaceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HorseRepository extends CrudRepository<HorseEntity, Integer> {
}
