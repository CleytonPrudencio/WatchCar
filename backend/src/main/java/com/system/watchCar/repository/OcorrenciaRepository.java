package com.system.watchCar.repository;

import com.system.watchCar.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long>, JpaSpecificationExecutor<Ocorrencia> {
}


