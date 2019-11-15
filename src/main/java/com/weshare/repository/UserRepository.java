package com.weshare.repository;
import com.weshare.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
	User findByUserNameAndPassword(String userName, String password);
}
