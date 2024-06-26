package com.paintingscollectors.repository;

import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
    User getByUsername(String username);
    User getById(long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);




}
