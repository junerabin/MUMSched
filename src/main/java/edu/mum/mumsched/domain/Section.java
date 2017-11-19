package edu.mum.mumsched.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import edu.mum.mumsched.ApplicationConstants;

/**
 * The persistent class for the section database table.
 * 
 */
@Entity
@NamedQuery(name = "Section.findAll", query = "SELECT s FROM Section s")
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull
	@Min(5)
	@Max(ApplicationConstants.STUDENT_CAPACITY_LIMIT)
	private int capacity;

	// @NotEmpty: No need, check on Generate Schedule
	private String name;

	// bi-directional many-to-one association to Block
	@ManyToOne(fetch = FetchType.LAZY)
	private Block block;

	// bi-directional many-to-one association to Course
	@ManyToOne(fetch = FetchType.LAZY)
	private Course course;

	@ManyToOne(fetch = FetchType.LAZY)
	private Faculty faculty;

	// bi-directional many-to-many association to Entry
	@ManyToMany
	@JoinTable(name = "section_entry", joinColumns = { @JoinColumn(name = "section_id") }, inverseJoinColumns = {
			@JoinColumn(name = "entry_id") })
	private List<Entry> entries;

	// bi-directional many-to-one association to StudentSection
	@OneToMany(mappedBy = "section")
	private List<StudentSection> studentSections;

	public Section() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public List<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public List<StudentSection> getStudentSections() {
		return this.studentSections;
	}

	public void setStudentSections(List<StudentSection> studentSections) {
		this.studentSections = studentSections;
	}

	public StudentSection addStudentSection(StudentSection studentSection) {
		getStudentSections().add(studentSection);
		studentSection.setSection(this);

		return studentSection;
	}

	public StudentSection removeStudentSection(StudentSection studentSection) {
		getStudentSections().remove(studentSection);
		studentSection.setSection(null);

		return studentSection;
	}

}