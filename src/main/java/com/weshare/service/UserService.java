package com.weshare.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weshare.repository.UserRepository;
import com.weshare.entity.User;
import com.weshare.exception.UserNotFoundException;
import java.util.Optional;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userRepository;

	public User getUser(String userName, String password)
	{
		User user;
		
		user = userRepository.findByUserNameAndPassword(userName, password);
		return user;
	}
	
	public User getUserById(int id)
	{
		User user;
		Optional<User> userOpt;
		
		userOpt = userRepository.findById(id);
		userOpt.orElseThrow(()->new UserNotFoundException());
		user = userOpt.get();

		return user;
	}
	
	public boolean isValiduser(int id)
	{
		return userRepository.existsById(id);
	}
}
