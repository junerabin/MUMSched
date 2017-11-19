package edu.mum.mumsched.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.mum.mumsched.domain.Schedule;

@Repository
public interface ScheduleDao extends CrudRepository<Schedule, Integer>{
	
}
