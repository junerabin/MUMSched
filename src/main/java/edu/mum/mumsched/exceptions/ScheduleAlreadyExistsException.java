package edu.mum.mumsched.exceptions;

public class ScheduleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ScheduleAlreadyExistsException() {
		super();
	}
	
	public ScheduleAlreadyExistsException(String message) {
		super(message);
	}

}
