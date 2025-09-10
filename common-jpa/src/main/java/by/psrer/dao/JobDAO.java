package by.psrer.dao;

import by.psrer.entity.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobDAO extends JpaRepository<Job, Long> {
    List<Job> findAllByOrderByJobIdAsc();
    Job findByJobName(final String jobName);
    long count();

    @Query(value = "SELECT * FROM job ORDER BY job_id LIMIT 1 OFFSET :offset", nativeQuery = true)
    Job findNth(@Param("offset") final int offset);

    default Optional<Job> findNthSafely(final int n) {
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
    @Query("DELETE FROM Job j WHERE j.jobId = :jobId")
    void deleteJobByJobId(@Param("jobId") final Long jobId);
}