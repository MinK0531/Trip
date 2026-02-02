package com.mink.trip.post.repository;

import com.mink.trip.post.domain.Post;
import com.mink.trip.post.dto.CountryCityRow;
import com.mink.trip.post.dto.PostDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByUserIdOrderByIdDesc(long userId);
    public int countByUserId(Long userId);
    public List<Post> findByCountryIdOrderByIdDesc(long countryId);

    @Query("""
    SELECT p
    FROM Post p
    JOIN Country c ON p.countryId = c.id
    WHERE LOWER(p.contents) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(p.cityName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(c.countryName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    ORDER BY p.id DESC
""")
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    @Query("""
        SELECT new com.mink.trip.post.dto.CountryCityRow(c.id, c.countryName, p.cityName)
        FROM Post p
        JOIN Country c ON p.countryId = c.id
        WHERE LOWER(c.countryName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.cityName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY c.id DESC
    """)
    List<CountryCityRow> searchCountryCity(@Param("keyword") String keyword);
}
