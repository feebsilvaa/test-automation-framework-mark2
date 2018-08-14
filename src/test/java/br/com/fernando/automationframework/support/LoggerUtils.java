package br.com.fernando.automationframework.support;

public class LoggerUtils {
	
	public static final String DEBUG_TAG(String className, String msg) {
		StringBuilder str = new StringBuilder();		
		str.append("[DEBUG] ")
		.append(className)
		.append(" - ")
		.append(msg);
		return str.toString();
	}
	
	public static final String INFO_TAG(String className, String msg) {
		StringBuilder str = new StringBuilder();		
		str.append("[INFO] ")
		.append(className)
		.append(" - ")
		.append(msg);
		return str.toString();
	}
	
	public static final String WARNING_TAG(String className, String msg) {
		StringBuilder str = new StringBuilder();		
		str.append("[WARNING] ")
		.append(className)
		.append(" - ")
		.append(msg);
		return str.toString();
	}
	
	public static final String ERROR_TAG(String className, String msg) {
		StringBuilder str = new StringBuilder();		
		str.append("[ERROR] ")
		.append(className)
		.append(" - ")
		.append(msg);
		return str.toString();
	}

}
