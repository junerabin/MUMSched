package edu.mum.mumsched.exceptions;

public class BlockNotEnoughException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BlockNotEnoughException() {
		super();
	}
	
	public BlockNotEnoughException(String message) {
		super(message);
	}

}
