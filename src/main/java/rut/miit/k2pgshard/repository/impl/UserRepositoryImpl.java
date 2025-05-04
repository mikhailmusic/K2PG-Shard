package rut.miit.k2pgshard.repository.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rut.miit.k2pgshard.config.ShardManager;
import rut.miit.k2pgshard.entity.User;
import rut.miit.k2pgshard.repository.UserRepository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private final ShardManager shardManager;

    @Autowired
    public UserRepositoryImpl(ShardManager shardManager) {
        this.shardManager = shardManager;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        logger.info("Finding user with ID: {}", userId);

        DataSource ds = shardManager.getShard(userId);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        try {
            User user = jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE id = ?",
                    new BeanPropertyRowMapper<>(User.class), userId
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ex) {
            logger.warn("No user found with ID: {}", userId);
            return Optional.empty();
        } catch (DataAccessException e) {
            logger.error("Error accessing database searching user by ID: {}", userId);
            return Optional.empty();
        }
    }

    @Override
    public void update(User user) {
        DataSource ds = shardManager.getShard(user.getId());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        try {
            logger.info("Updating user with ID: {}", user.getId());

            jdbcTemplate.update(
                    "UPDATE users SET first_name = ?, email = ?, phone_number = ?, country = ? WHERE id = ?",
                    user.getFirstName(), user.getEmail(), user.getPhoneNumber(), user.getCountry(), user.getId()
            );

            logger.info("User with ID: {} updated successfully", user.getId());
        } catch (DataAccessException e) {
            logger.error("Error updating user with ID: {}. Database access error: {}", user.getId(), e.getMessage());
        }
    }
    @Override
    public void save(User user) {
        DataSource ds = shardManager.getShard(user.getId());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        try {
            logger.info("Saving new user with ID: {}", user.getId());
            jdbcTemplate.update(
                    "INSERT INTO users (id, first_name, email, phone_number, birth_date, country) VALUES (?, ?, ?, ?, ?, ?)",
                    user.getId(), user.getFirstName(), user.getEmail(), user.getPhoneNumber(), user.getBirthDate(), user.getCountry()
            );

            logger.info("User with ID: {} saved successfully", user.getId());
        } catch (DataAccessException e) {
            logger.error("Failed to save user with ID: {}. Database access error: {}", user.getId(), e.getMessage());
        }
    }

}
