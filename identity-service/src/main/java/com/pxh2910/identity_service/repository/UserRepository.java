package com.pxh2910.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pxh2910.identity_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
