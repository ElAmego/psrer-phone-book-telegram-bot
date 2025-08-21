package by.psrer.dao;

import by.psrer.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AreaDAO extends JpaRepository<Area, Long> {
    List<Area> findAllByOrderByAreaIdAsc();

    @Query(value = "SELECT * FROM area ORDER BY area_id LIMIT 1 OFFSET :offset", nativeQuery = true)
    Area findNth(@Param("offset") final int offset);

    default Optional<Area> findNthSafely(final int n) {
        if (n <= 0) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(findNth(n));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}