package edu.mum.mumsched.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mum.mumsched.domain.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {

	@Query("from User where username = :username and password = :password")
	User findUser(@Param("username") String username, @Param("password") String password);
	
	@Query("from User")
	List<User> findAllUsers();
}
