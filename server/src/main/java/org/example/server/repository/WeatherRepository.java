package org.example.server.repository;

import org.example.server.entity.WeatherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, UUID> {
    Page<WeatherEntity> findBySensorId(Pageable pageable, UUID sensorId);

    List<WeatherEntity> findByTimeAfter(LocalDateTime time);
}
