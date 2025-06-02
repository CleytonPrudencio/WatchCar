package com.system.watchCar.repository;

import com.system.watchCar.entity.UserGestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGestorRepository extends JpaRepository<UserGestor, Long> {
}

