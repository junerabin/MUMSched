package edu.mum.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.mumsched.dao.UserDao;
import edu.mum.mumsched.domain.User;
import edu.mum.mumsched.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDAO;
	
	public void registerUser(User user) {
		userDAO.save(user);
	}
	
	public User findUser(String username, String password) {
		return userDAO.findUser(username, password);
	}
	
	public List<User> findAllUsers() {
		return userDAO.findAllUsers();
	}
}
