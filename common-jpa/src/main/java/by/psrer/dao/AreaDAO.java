package by.psrer.dao;

import by.psrer.entity.Area;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AreaDAO extends JpaRepository<Area, Long> {
    List<Area> findAllByOrderByAreaIdAsc();
    Area findByAreaName(final String areaName);
    Area findByAreaId(final Integer areaId);

    @Query(value = "SELECT * FROM area ORDER BY area_id LIMIT 1 OFFSET :offset", nativeQuery = true)
    Area findNth(@Param("offset") final int offset);

    default Optional<Area> findNthSafely(final int n) {
        if (n <= 0) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(findNth(n-1));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM Area a WHERE a.areaId = :areaId")
    void deleteAreaByAreaId(@Param("areaId") final Long areaId);
}