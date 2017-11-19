package edu.mum.mumsched.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlockInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String blockName;
	
	private String blockPeriod;
	
	private List<SectionInfo> sectionInfos = new ArrayList<>();

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getBlockPeriod() {
		return blockPeriod;
	}

	public void setBlockPeriod(String blockPeriod) {
		this.blockPeriod = blockPeriod;
	}

	public List<SectionInfo> getSectionInfos() {
		return sectionInfos;
	}

	public void setSectionInfos(List<SectionInfo> sectionInfos) {
		this.sectionInfos = sectionInfos;
	}

}
