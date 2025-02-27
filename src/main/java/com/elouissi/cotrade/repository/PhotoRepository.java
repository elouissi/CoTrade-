package com.elouissi.cotrade.repository;

import com.elouissi.cotrade.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    List<Photo> findByPostId(UUID postId);
    @Modifying
    @Query("DELETE FROM Photo p WHERE p.post.id = :postId")
    void deleteByPostId(@Param("postId") UUID postId);

}