package edu.mum.mumsched.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * The persistent class for the course database table.
 * 
 */
@Entity
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty
	@Column(name="course_code")
	private String courseCode;

	@NotEmpty
	@Column(name="course_name")
	private String courseName;

	@Column(name="target_block")
	private String targetBlock;

	//bi-directional many-to-many association to Course
	@ManyToMany
	@JoinTable(
		name="course_prerequisite"
		, joinColumns={
			@JoinColumn(name="course_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="prerequisite_id")
			}
		)
	private List<Course> coursesPre;

	//bi-directional many-to-many association to Course
	@ManyToMany(mappedBy="coursesPre")
	private List<Course> coursesMain;

	//bi-directional many-to-many association to Faculty
	//@ManyToMany(mappedBy="courses")
	@ManyToMany
	@JoinTable(
		name="faculty_course"
		, joinColumns={
			@JoinColumn(name="course_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="faculty_id")
			}
		)
	private List<Faculty> faculties;

	//bi-directional many-to-one association to Section
	@OneToMany(mappedBy="course")
	private List<Section> sections;

	public Course() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseCode() {
		return this.courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTargetBlock() {
		return this.targetBlock;
	}

	public void setTargetBlock(String targetBlock) {
		this.targetBlock = targetBlock;
	}

	public List<Course> getCoursesMain() {
		return this.coursesMain;
	}

	public void setCoursesMain(List<Course> coursesMain) {
		this.coursesMain = coursesMain;
	}

	public List<Course> getCoursesPre() {
		return this.coursesPre;
	}

	public void setCoursesPre(List<Course> coursesPre) {
		this.coursesPre = coursesPre;
	}

	public List<Faculty> getFaculties() {
		return this.faculties;
	}

	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}

	public List<Section> getSections() {
		return this.sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Section addSection(Section section) {
		getSections().add(section);
		section.setCourse(this);

		return section;
	}

	public Section removeSection(Section section) {
		getSections().remove(section);
		section.setCourse(null);

		return section;
	}

}