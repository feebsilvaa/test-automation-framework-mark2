package br.com.fernando.automationframework.suites;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.fernando.automationframework.tests.ModelTest;
import br.com.fernando.automationframework.tests.SoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	ModelTest.class,
	SoTest.class })
public class SuiteTest {

	@BeforeClass
	public static void resetDados() {
	}

}
