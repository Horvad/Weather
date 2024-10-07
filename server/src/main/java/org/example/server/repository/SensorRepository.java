package org.example.server.repository;

import org.example.server.entity.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<SensorEntity, UUID> {
    SensorEntity findByName(String sensorName);

    @Query("""
                    SELECT s 
                    FROM SensorEntity s
                    WHERE s.isActive = ?1
            """)
    List<SensorEntity> findAllByActive(Boolean active);
}
