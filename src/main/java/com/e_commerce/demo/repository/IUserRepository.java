package com.e_commerce.demo.repository;


import com.e_commerce.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);

    Optional<User> existsByEmail(String email);
}
