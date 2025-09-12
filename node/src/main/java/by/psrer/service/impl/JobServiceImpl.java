package by.psrer.service.impl;

import by.psrer.dao.EmployeeDAO;
import by.psrer.dao.JobDAO;
import by.psrer.service.JobService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
@Transactional
public class JobServiceImpl implements JobService {
    private final JobDAO jobDAO;
    private final EmployeeDAO employeeDAO;

    public JobServiceImpl(final JobDAO jobDAO, final EmployeeDAO employeeDAO) {
        this.jobDAO = jobDAO;
        this.employeeDAO = employeeDAO;
    }

    @Transactional
    @Override
    public void deleteJobWithEmployees(final Long jobId) {
        employeeDAO.deleteByJobJobId(jobId);
        jobDAO.deleteById(jobId);
    }
}