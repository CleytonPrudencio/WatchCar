package com.system.watchCar.repository;

import com.system.watchCar.entity.AcaoInvestigacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcaoInvestigacaoRepository extends JpaRepository<AcaoInvestigacao, Long> {
}


