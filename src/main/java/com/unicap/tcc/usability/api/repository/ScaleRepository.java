package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.Scale;
import com.unicap.tcc.usability.api.models.enums.ScalesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long> {

    Optional<Scale> findByAcronym(ScalesEnum scale);

    Optional<Scale> findByUid(UUID uuid);

    Set<Scale> findByAcronymIn(List<ScalesEnum> acronyms);
}
