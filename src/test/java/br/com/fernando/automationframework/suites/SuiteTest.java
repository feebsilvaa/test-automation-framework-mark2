package br.com.fernando.automationframework.suites;

import br.com.fernando.automationframework.tests.ModelTest;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        ModelTest.class
)
public class SuiteTest {

    @BeforeClass
    public static void resetDados() {
        // L�gica para limpar a base de dados
        // Finalizar inst�ncia do driver
        // killDriver();
    }

}

