package com.spotnshop.repository;

import com.spotnshop.model.Favorite;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    List<Favorite> findByOffer(Offer offer);
    Optional<Favorite> findByUserAndOffer(User user, Offer offer);
    boolean existsByUserAndOffer(User user, Offer offer);
    void deleteByUserAndOffer(User user, Offer offer);
    long countByUser(User user);
}