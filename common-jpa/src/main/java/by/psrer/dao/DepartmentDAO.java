package by.psrer.dao;

import by.psrer.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentDAO extends JpaRepository<Department, Long> {
    List<Department> findByArea_AreaId(final Long areaId);

    @Query(value = "SELECT * FROM department WHERE area_id = :area_id ORDER BY department_id LIMIT 1 OFFSET :offset", nativeQuery = true)
    Department findNth(@Param("offset") final int offset, @Param("area_id") final Long areaId);

    default Optional<Department> findNthSafely(final int n, final Long areaId) {
        if (n <= 0) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(findNth(n - 1, areaId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}