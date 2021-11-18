package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    List<Characteristic> findAllByCategory(String category);
}
