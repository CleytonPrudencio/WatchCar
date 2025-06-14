package com.system.watchCar.repository;

import com.system.watchCar.dto.response.UserProjection;
import com.system.watchCar.dto.response.UserResponse;
import com.system.watchCar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    Optional<User> findByCpf(String cpf);

    @Query(nativeQuery = true, value = """
            SELECT * FROM TB_USER U
            INNER JOIN TB_USER_ROLE UR ON UR.USER_ID = U.ID_USER
            INNER JOIN TB_ROLE R ON R.ROLE_ID = UR.ROLE_ID
            LEFT JOIN TB_USER_AGENTE A ON A.USER_ID = U.ID_USER
            LEFT JOIN TB_USER_GESTOR G ON G.USER_ID = U.ID_USER
            WHERE U.ID_USER = :id
            """)
    List<UserProjection> searchById(@Param("id") Long id);

}

