package com.hcs.cloud.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcs.cloud.tutorial.model.User;

public interface UserRepository extends JpaRepository<User, Long>{    

}
