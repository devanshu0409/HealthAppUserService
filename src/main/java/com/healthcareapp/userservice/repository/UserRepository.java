package com.healthcareapp.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcareapp.userservice.dao.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

}
