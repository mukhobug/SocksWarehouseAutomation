package com.example.sockswarehouseautomation.repository;

import com.example.sockswarehouseautomation.entity.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {

    @Query(nativeQuery = true, value = "SELECT quantity FROM socks WHERE color = :color AND cotton_part = :part")
    Optional<Integer> findQuantityByColorAndCottonPart(@Param(value = "color") String color,
                                                       @Param(value = "part") int cottonPart);

    @Query(nativeQuery = true, value = "SELECT sum(quantity) FROM socks WHERE color = :color AND cotton_part < :part")
    Optional<Integer> findQuantityByColorAndCottonPartLessThan(@Param(value = "color") String color,
                                                               @Param(value = "part") int cottonPart);

    @Query(nativeQuery = true, value = "SELECT sum(quantity) FROM socks WHERE color = :color AND cotton_part > :part")
    Optional<Integer> findQuantityByColorAndCottonPartGreaterThan(@Param(value = "color") String color,
                                                                  @Param(value = "part") int cottonPart);

    Optional<Socks> findByColorAndCottonPart(String color, int cottonPart);
}
