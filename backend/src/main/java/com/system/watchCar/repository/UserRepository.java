package com.system.watchCar.repository;

import com.system.watchCar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByCpf(String cpf);
    Optional<User> findByCpfAndAtivoTrue(String cpf);

    Optional<User> findByEmail(String emailCpf);
}

