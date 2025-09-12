package by.psrer.service.impl;

import by.psrer.dao.DepartmentDAO;
import by.psrer.dao.EmployeeDAO;
import by.psrer.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDAO departmentDAO;
    private final EmployeeDAO employeeDAO;

    public DepartmentServiceImpl(final DepartmentDAO departmentDAO, final EmployeeDAO employeeDAO) {
        this.departmentDAO = departmentDAO;
        this.employeeDAO = employeeDAO;
    }

    @Transactional
    @Override
    public void deleteDepartmentWithEmployees(final Long departmentId) {
        employeeDAO.deleteByDepartmentDepartmentId(departmentId);
        departmentDAO.deleteById(departmentId);
    }
}