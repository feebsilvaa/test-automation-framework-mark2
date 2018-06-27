package br.com.fernando.automationframework.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;


public class TesteSo {
	@Test
	public  void main () {
		System.out.println(System.getProperty("os.name"));
		String sysName = System.getProperty("os.name").toLowerCase();
		
		Assert.assertTrue(sysName.contains("windows"));
		
		
	}
}