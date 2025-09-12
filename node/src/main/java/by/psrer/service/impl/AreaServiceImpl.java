package by.psrer.service.impl;

import by.psrer.dao.AreaDAO;
import by.psrer.dao.DepartmentDAO;
import by.psrer.service.AreaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
@Transactional
public class AreaServiceImpl implements AreaService {
    private final AreaDAO areaDAO;
    private final DepartmentDAO departmentDAO;

    public AreaServiceImpl(final AreaDAO areaDAO, final DepartmentDAO departmentDAO) {
        this.areaDAO = areaDAO;
        this.departmentDAO = departmentDAO;
    }

    @Transactional
    @Override
    public void deleteAreaWithDepartments(final Long areaId) {
        departmentDAO.deleteByAreaAreaId(areaId);
        areaDAO.deleteById(areaId);
    }
}
