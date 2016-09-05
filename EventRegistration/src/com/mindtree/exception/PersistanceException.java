package com.mindtree.exception;

public class PersistanceException extends Exception
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9194311514899873368L;

	public PersistanceException() {
		super();
	}

	public PersistanceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PersistanceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PersistanceException(String arg0) {
		super(arg0);
	}

	public PersistanceException(Throwable arg0) {
		super(arg0);
	}

}
