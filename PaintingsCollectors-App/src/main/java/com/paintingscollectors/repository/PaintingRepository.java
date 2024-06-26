package com.paintingscollectors.repository;

import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Integer> {
    List<Painting> findByOwner(Optional<User> byId);
    Optional<Painting> getById(long id);
    Optional<Painting> findById(long id);
    @Query("SELECT p FROM Painting p WHERE p.owner.id <> :userId")
    List<Painting> findAllExceptUserPaintings(@Param("userId") Long userId);

    @Query("SELECT p FROM Painting p ORDER BY p.votes DESC")
    List<Painting> findAllOrderByVoteDesc();
}
