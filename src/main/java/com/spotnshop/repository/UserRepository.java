package com.spotnshop.repository;

import com.spotnshop.model.User;
import com.spotnshop.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findByCity(String city);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}