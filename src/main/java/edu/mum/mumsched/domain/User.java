package edu.mum.mumsched.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private byte enabled;

	private String password;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="user")
	private List<UserRole> userRoles;

	//bi-directional one-to-one association to Faculty
//	@OneToOne(mappedBy="user", fetch=FetchType.LAZY)
//	private Faculty faculty;

	//bi-directional one-to-one association to Student
//	@OneToOne(mappedBy="user", fetch=FetchType.LAZY)
//	private Student student;

	public User() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte getEnabled() {
		return this.enabled;
	}

	public void setEnabled(byte enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setUser(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setUser(null);

		return userRole;
	}

//	public Faculty getFaculty() {
//		return this.faculty;
//	}
//
//	public void setFaculty(Faculty faculty) {
//		this.faculty = faculty;
//	}

//	public Student getStudent() {
//		return this.student;
//	}
//
//	public void setStudent(Student student) {
//		this.student = student;
//	}

}