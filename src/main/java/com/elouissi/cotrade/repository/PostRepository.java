package com.elouissi.cotrade.repository;


import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> getPostsByUser(AppUser user);
    void deleteAllByUser(AppUser user);


}
