package com.system.watchCar.repository;

import com.system.watchCar.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}
