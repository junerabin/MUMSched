package edu.mum.mumsched.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the student_section database table.
 * 
 */
@Entity
@Table(name="student_section")
@NamedQuery(name="StudentSection.findAll", query="SELECT s FROM StudentSection s")
public class StudentSection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private byte approved;

	private int grade;

	//bi-directional many-to-one association to Section
	@ManyToOne(fetch=FetchType.LAZY)
	private Section section;

	//bi-directional many-to-one association to Student
	@ManyToOne(fetch=FetchType.LAZY)
	private Student student;

	public StudentSection() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getApproved() {
		return this.approved;
	}

	public void setApproved(byte approved) {
		this.approved = approved;
	}

	public int getGrade() {
		return this.grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}