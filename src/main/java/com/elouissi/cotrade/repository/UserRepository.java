package com.elouissi.cotrade.repository;

import com.elouissi.cotrade.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {


    Optional<AppUser> findByEmail(String email);


    boolean existsByEmail(String email);

}