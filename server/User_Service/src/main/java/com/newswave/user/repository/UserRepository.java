package com.newswave.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newswave.user.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer>{

	UserModel getUserByUserName(String userName);

}
