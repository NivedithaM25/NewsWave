package com.newswave.authserver.repository;

import java.util.Optional;

import org.apache.catalina.startup.ListenerCreateRule.OptionalListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newswave.authserver.model.UserModel;

@Repository
public interface AuthRepository extends JpaRepository<UserModel,Integer>{

	Optional<UserModel> findByUserName(String userName);

}
