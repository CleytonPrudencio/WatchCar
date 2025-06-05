package com.system.watchCar.repository;

import com.system.watchCar.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    Optional<Local> findByCep(String cep);
}
