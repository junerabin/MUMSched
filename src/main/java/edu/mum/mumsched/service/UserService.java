package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.User;

public interface UserService {
	
	void registerUser(User user);
		
	User findUser(String userName, String password);

	List<User> findAllUsers();
}
