package by.psrer.dao;

import by.psrer.entity.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentDAO extends JpaRepository<Department, Long> {
    List<Department> findByArea_AreaId(final Long areaId);
    List<Department> findAllByOrderByDepartmentIdAsc();
    Department findByDepartmentName(final String departmentName);

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

    @Query(value = "SELECT * FROM department ORDER BY department_id LIMIT 1 OFFSET :offset", nativeQuery = true)
    Department findNth(@Param("offset") final int offset);

    default Optional<Department> findNthSafely(final int n) {
        if (n <= 0) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(findNth(n - 1));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM Department d WHERE d.departmentId = :departmentId")
    void deleteDepartmentByDepartmentId(@Param("departmentId") final Long departmentId);
}