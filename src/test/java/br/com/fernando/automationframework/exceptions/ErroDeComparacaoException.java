package br.com.fernando.automationframework.exceptions;

import org.junit.ComparisonFailure;

public class ErroDeComparacaoException extends ComparisonFailure{

	public ErroDeComparacaoException(String message, String expected, String actual) {
		super(message, expected, actual);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
