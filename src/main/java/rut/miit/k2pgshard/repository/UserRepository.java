package rut.miit.k2pgshard.repository;

import rut.miit.k2pgshard.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    User update(User user);
}
