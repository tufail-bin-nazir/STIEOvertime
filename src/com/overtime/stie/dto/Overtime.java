package com.overtime.stie.dto;

import java.util.ArrayList;
import java.util.List;

public class Overtime {
	
	private int id;
	
	private String technicianName;
	
	private String department;
	
	private String startDate;
	
	private String startTime;
	
	private String endDate;
	
	private String endTime;
	
	private String purpose;
	
	private String breakDown;
	
	private String workorderNumber;
	
	private String site;
	
	private String userfrom;
	
	private String userto;
	
	private String status;
	
	private String numStatus;
	
	private String breakdowntype;
	
	private List<OverTimeRemarks> remarks = new ArrayList<OverTimeRemarks>();

	public List<OverTimeRemarks> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<OverTimeRemarks> remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTechnicianName() {
		return technicianName;
	}

	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBreakDown() {
		return breakDown;
	}

	public void setBreakDown(String breakDown) {
		this.breakDown = breakDown;
	}

	public String getWorkorderNumber() {
		return workorderNumber;
	}

	public void setWorkorderNumber(String workorderNumber) {
		this.workorderNumber = workorderNumber;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getUserfrom() {
		return userfrom;
	}

	public void setUserfrom(String userfrom) {
		this.userfrom = userfrom;
	}

	public String getUserto() {
		return userto;
	}

	public void setUserto(String userto) {
		this.userto = userto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBreakdowntype() {
		return breakdowntype;
	}

	public void setBreakdowntype(String breakdowntype) {
		this.breakdowntype = breakdowntype;
	}

	public String getNumStatus() {
		return numStatus;
	}

	public void setNumStatus(String numStatus) {
		this.numStatus = numStatus;
	}
	
	
	
	
	
	
	
	
	

}
