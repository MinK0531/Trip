package com.mink.trip.wishlist.repository;


import com.mink.trip.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    public List<Wishlist> findByUserIdOrderByIdDesc(long userId);
    public int countByUserId(Long userId);


}
