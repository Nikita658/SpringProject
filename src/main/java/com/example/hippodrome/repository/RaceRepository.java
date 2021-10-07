package com.example.hippodrome.repository;

import com.example.hippodrome.repository.model.RaceEntity;
import org.springframework.data.repository.CrudRepository;

public interface RaceRepository extends CrudRepository<RaceEntity, Integer> {
}
