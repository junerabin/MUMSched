package edu.mum.mumsched.exceptions;

public class ScheduleCanNotBeDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ScheduleCanNotBeDeletedException() {
		super();
	}
	
	public ScheduleCanNotBeDeletedException(String message) {
		super(message);
	}

}
