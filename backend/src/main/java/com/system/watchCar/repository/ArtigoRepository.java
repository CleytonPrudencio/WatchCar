package com.system.watchCar.repository;

import com.system.watchCar.entity.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArtigoRepository extends JpaRepository<Artigo, Long> {

    // Busca se rubrica já está como código ou descrição
    @Query(nativeQuery = true, value = """
        SELECT * FROM TB_ARTIGO_CRIMINAL WHERE cod_artigo = :rubrica;
            """)
    List<Artigo> findByRubricaInCodigoOuDescricao(@Param("rubrica") String rubrica);

}
