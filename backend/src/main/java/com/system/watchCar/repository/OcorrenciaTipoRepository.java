package com.system.watchCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaTipo extends JpaRepository<OcorrenciaTipo, Long> {
}
