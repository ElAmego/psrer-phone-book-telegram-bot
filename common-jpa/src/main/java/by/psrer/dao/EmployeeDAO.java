package by.psrer.dao;

import by.psrer.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDAO extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment_DepartmentId(final Long departmentId);
    List<Employee> findByEmployeeNameContainingIgnoreCaseOrderByEmployeeNameAsc(final String namePart);
}