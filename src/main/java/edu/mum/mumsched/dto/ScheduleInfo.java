package edu.mum.mumsched.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.mum.mumsched.domain.Entry;
import edu.mum.mumsched.domain.Section;

public class ScheduleInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// this is ugly but it's ok for this short period of time
	// #### entry list for selection ####
	private List<Entry> entries = new ArrayList<>();
	private Integer entryId;
	
	
	// ### entry for displaying schedule detail ###
	// entry -> many blocks -> many sections
	private Integer scheduleId;
	private String entryName;
	private List<BlockInfo> blockInfos = new ArrayList<>();
	private String dateRanges;
	private String status;

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public List<BlockInfo> getBlockInfos() {
		return blockInfos;
	}

	public void setBlockInfos(List<BlockInfo> blockInfos) {
		this.blockInfos = blockInfos;
	}

	public String getDateRanges() {
		return dateRanges;
	}

	public void setDateRanges(String dateRanges) {
		this.dateRanges = dateRanges;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
