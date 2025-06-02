package com.system.watchCar.repository;

import com.system.watchCar.entity.OcorrenciaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaTipoRepository extends JpaRepository<OcorrenciaTipo, Long> {
}
