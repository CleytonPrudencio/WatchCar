package com.system.watchCar.repository;

import com.system.watchCar.entity.UserAgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAgenteRepository extends JpaRepository<UserAgente, Long> {
}

