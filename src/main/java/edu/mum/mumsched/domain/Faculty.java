package edu.mum.mumsched.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * The persistent class for the faculty database table.
 * 
 */
@Entity
@NamedQuery(name="Faculty.findAll", query="SELECT f FROM Faculty f")
public class Faculty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@NotEmpty
	private String email;

	@NotEmpty
	@Column(name="first_name", nullable=false)
	private String firstName;

	@NotEmpty
	@Column(name="last_name", nullable=false)
	private String lastName;

	@Column(name="target_block")
	private String targetBlock;

	//bi-directional many-to-many association to Course
	@ManyToMany
	@JoinTable(
		name="faculty_course"
		, joinColumns={
			@JoinColumn(name="faculty_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="course_id")
			}
		)
	private List<Course> courses;

	//bi-directional one-to-one association to User
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private User user;

	
	@OneToMany(mappedBy="faculty")
	private List<Section> sections;
	
	public Faculty() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTargetBlock() {
		return this.targetBlock;
	}

	public void setTargetBlock(String targetBlock) {
		this.targetBlock = targetBlock;
	}

	public List<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

}