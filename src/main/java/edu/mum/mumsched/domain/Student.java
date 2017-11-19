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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the student database table.
 * 
 */
@Entity
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	//bi-directional many-to-one association to Entry
	@ManyToOne(fetch=FetchType.LAZY)
	private Entry entry;

	//bi-directional many-to-one association to StudentSection
	@OneToMany(mappedBy="student")
	private List<StudentSection> studentSections;

	//bi-directional one-to-one association to User
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private User user;

	@Temporal(TemporalType.DATE)
	private Date dob;

	private String email;

	@Column(name="first_name")
	private String firstName;

	//private int kind;

	@Column(name="last_name")
	private String lastName;
	
	public Student() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Entry getEntry() {
		return this.entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public List<StudentSection> getStudentSections() {
		return this.studentSections;
	}

	public void setStudentSections(List<StudentSection> studentSections) {
		this.studentSections = studentSections;
	}

	public StudentSection addStudentSection(StudentSection studentSection) {
		getStudentSections().add(studentSection);
		studentSection.setStudent(this);

		return studentSection;
	}

	public StudentSection removeStudentSection(StudentSection studentSection) {
		getStudentSections().remove(studentSection);
		studentSection.setStudent(null);

		return studentSection;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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

//	public int getKind() {
//		return this.kind;
//	}
//
//	public void setKind(int kind) {
//		this.kind = kind;
//	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}