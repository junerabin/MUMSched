package edu.mum.mumsched.domain;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the block database table.
 * 
 */
@Entity
@NamedQuery(name="Block.findAll", query="SELECT b FROM Block b")
public class Block implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="end_date")
	private Date endDate;

	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="start_date")
	private Date startDate;

	//bi-directional many-to-many association to Entry
	@ManyToMany//(mappedBy="blocks")
	@JoinTable(
			name="entry_block"
			, joinColumns={
				@JoinColumn(name="block_id")
				}
			, inverseJoinColumns={
				@JoinColumn(name="entry_id")
				}
			)
	private List<Entry> entries;

	//bi-directional many-to-one association to Section
	@OneToMany(mappedBy="block")
	private List<Section> sections = new ArrayList<Section>();

	public Block() {
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

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public List<Section> getSections() {
		return this.sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Section addSection(Section section) {
		getSections().add(section);
		section.setBlock(this);

		return section;
	}

	public Section removeSection(Section section) {
		getSections().remove(section);
		section.setBlock(null);

		return section;
	}

}