package com.system.watchCar.repository;

import com.system.watchCar.entity.TipoOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoOcorrenciaRepository extends JpaRepository<TipoOcorrencia, Long> {
}
