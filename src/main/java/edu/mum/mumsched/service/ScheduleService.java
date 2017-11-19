package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.Schedule;
import edu.mum.mumsched.dto.ScheduleInfo;

public interface ScheduleService {
	
	public void save(Schedule schedule);
	
	public List<Schedule> findAll();
	
	public Schedule getScheduleById(Integer id);
	
	public void deleteSchedule(Integer scheduleId);
	
	public Schedule generateSchedule(Integer entryId);
	
	public void updateSchedule(ScheduleInfo scheduleInfo);
	
}
