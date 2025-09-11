package by.psrer.dao;

import by.psrer.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment_DepartmentIdOrderByEmployeeIdAsc(final Long departmentId);
    List<Employee> findByEmployeeNameContainingIgnoreCaseOrderByEmployeeNameAsc(final String namePart);
    long count();

    @Query(value = "SELECT * FROM employee WHERE department_id = :department_id ORDER BY employee_id LIMIT 1 OFFSET :offset",
            nativeQuery = true)
    Employee findNth(@Param("offset") final int offset, @Param("department_id") int departmentId);

    default Optional<Employee> findNthSafely(int n, final int departmentId) {
        if (n <= 0) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(findNth(n - 1, departmentId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM Employee e WHERE e.employeeId = :employeeId")
    void deleteEmployeeByEmployeeId(@Param("employeeId") final Long employeeId);
}