package edu.mum.mumsched.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the entry database table.
 * 
 */
@Entity
@NamedQuery(name="Entry.findAll", query="SELECT e FROM Entry e")
public class Entry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="end_date")
	private Date endDate;

	@NotEmpty
	private String name;

	@NotNull
	@Column(name="percent_opt")
	private int percentOpt;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="start_date")
	private Date startDate;

	@NotNull
	@Column(name="total_fpp")
	private int totalFpp;

	@NotNull
	@Column(name="total_mpp")
	private int totalMpp;

	//bi-directional many-to-many association to Block
	@ManyToMany
	@JoinTable(
		name="entry_block"
		, joinColumns={
			@JoinColumn(name="entry_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="block_id")
			}
		)
	@OrderBy("startDate ASC")
	private List<Block> blocks;

	//bi-directional many-to-one association to Schedule
	@OneToMany(mappedBy="entry")
	private List<Schedule> schedules;

	//bi-directional many-to-many association to Section
	@ManyToMany(mappedBy="entries")
	private List<Section> sections;

	//bi-directional many-to-one association to Student
	@OneToMany(mappedBy="entry")
	private List<Student> students;

	public Entry() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPercentOpt() {
		return this.percentOpt;
	}

	public void setPercentOpt(int percentOpt) {
		this.percentOpt = percentOpt;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getTotalFpp() {
		return this.totalFpp;
	}

	public void setTotalFpp(int totalFpp) {
		this.totalFpp = totalFpp;
	}

	public int getTotalMpp() {
		return this.totalMpp;
	}

	public void setTotalMpp(int totalMpp) {
		this.totalMpp = totalMpp;
	}

	public List<Block> getBlocks() {
		return this.blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public List<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Schedule addSchedule(Schedule schedule) {
		getSchedules().add(schedule);
		schedule.setEntry(this);

		return schedule;
	}

	public Schedule removeSchedule(Schedule schedule) {
		getSchedules().remove(schedule);
		schedule.setEntry(null);

		return schedule;
	}

	public List<Section> getSections() {
		return this.sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setEntry(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setEntry(null);

		return student;
	}

}