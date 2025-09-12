package by.psrer.dao;

import by.psrer.entity.AppUser;
import by.psrer.entity.enums.Role;
import by.psrer.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByTelegramUserId(final Long userId);
    List<AppUser> findByAppUserConfigIdRole(final Role role);
    List<AppUser> findByAppUserConfigIdStatus(final Status status);
    long countByAppUserConfigId_Status(final Status status);
    long countByAppUserConfigId_Role(final Role role);
}