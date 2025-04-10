package com.e_commerce.demo.repository;


import com.e_commerce.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,String> {

}
