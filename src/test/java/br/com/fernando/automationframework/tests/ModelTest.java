package br.com.fernando.automationframework.tests;

import br.com.fernando.automationframework.core.BaseTest;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "ModelTest.csv")
public class ModelTest extends BaseTest {

    @Test
    public void acessarPaginaGoogle(
            @Param(name="tituloPagina")String tituloPagina
    ) throws Exception {
        Assert.assertEquals(tituloPagina, getDriver().getTitle());
    }

}
