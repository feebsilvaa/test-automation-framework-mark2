package br.com.fernando.automationframework.core;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;

public class BasePage {

    public String tituloPagina() {
        return getDriver().getTitle();
    }

    public String capturarMensagemAlerta() {
        return obterTexto(By.className("alert"));
    }

    /********* TextField e TextArea ************/

    public void escrever(By by, String texto){
        getDriver().findElement(by).clear();
        getDriver().findElement(by).sendKeys(texto);
    }

    public void escrever(String id_campo, String texto){
        escrever(By.id(id_campo), texto);
    }

    public String obterValorCampo(String id_campo) {
        return getDriver().findElement(By.id(id_campo)).getAttribute("value");
    }

    /********* Radio e Check ************/

    public void clicarRadio(By by) {
        getDriver().findElement(by).click();
    }

    /* public void clicarRadio(String id) {
         clicarRadio(By.id(id));
     }*/
    public void clicarRadio(String opcao) {
        if ("Pago".equals(opcao)) {
            clicarRadio(By.id("status_pago"));
        } else if ("Pendente".equals(opcao)) {
            clicarRadio(By.id("status_pendente"));
        }
    }

    public boolean isRadioMarcado(String id){
        return getDriver().findElement(By.id(id)).isSelected();
    }

    public void clicarCheck(String id) {
        getDriver().findElement(By.id(id)).click();
    }

    public boolean isCheckMarcado(String id){
        return getDriver().findElement(By.id(id)).isSelected();
    }

    /********* Combo ************/

    public void selecionarCombo(By by, String valor) {
        WebElement element = getDriver().findElement(by);
        Select combo = new Select(element);
        combo.selectByVisibleText(valor);
    }

    public void selecionarCombo(String id, String valor) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        combo.selectByVisibleText(valor);
    }

    public void deselecionarCombo(By by, String valor) {
        WebElement element = getDriver().findElement(by);
        Select combo = new Select(element);
        combo.deselectByVisibleText(valor);
    }

    public void deselecionarCombo(String id, String valor) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        combo.deselectByVisibleText(valor);
    }

    public String obterValorCombo(By by) {
        WebElement element = getDriver().findElement(by);
        Select combo = new Select(element);
        return combo.getFirstSelectedOption().getText();
    }

    public String obterValorCombo(String id) {
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        return combo.getFirstSelectedOption().getText();
    }

    public List<String> obterValoresCombo(String id) {
        WebElement element = getDriver().findElement(By.id("elementosForm:esportes"));
        Select combo = new Select(element);
        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
        List<String> valores = new ArrayList<String>();
        for(WebElement opcao: allSelectedOptions) {
            valores.add(opcao.getText());
        }
        return valores;
    }

    public int obterQuantidadeOpcoesCombo(String id){
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        return options.size();
    }

    public boolean verificarOpcaoCombo(String id, String opcao){
        WebElement element = getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        for(WebElement option: options) {
            if(option.getText().equals(opcao)){
                return true;
            }
        }
        return false;
    }

    /********* Botao ************/

    public void clicarBotao(By by) {
        getDriver().findElement(by).click();
    }

    public void clicarBotao(String id) {
        getDriver().findElement(By.id(id)).click();
    }

    public String obterValueElemento(String id) {
        return getDriver().findElement(By.id(id)).getAttribute("value");
    }

    /********* Link ************/

    public void clicarLink(By by) {
        getDriver().findElement(by).click();
    }

    public void clicarLink(String link) {
        getDriver().findElement(By.linkText(link)).click();
    }

    /********* Textos ************/

    public String obterTexto(By by) {
        return getDriver().findElement(by).getText();
    }

    public String obterTexto(String xpath) {
        return obterTexto(By.xpath(xpath));
    }

    /********* Alerts ************/

    public String alertaObterTexto(){
        Alert alert = getDriver().switchTo().alert();
        return alert.getText();
    }

    public String alertaObterTextoEAceita(){
        Alert alert = getDriver().switchTo().alert();
        String valor = alert.getText();
        alert.accept();
        return valor;

    }

    public String alertaObterTextoENega(){
        Alert alert = getDriver().switchTo().alert();
        String valor = alert.getText();
        alert.dismiss();
        return valor;

    }

    public void alertaEscrever(String valor) {
        Alert alert = getDriver().switchTo().alert();
        alert.sendKeys(valor);
        alert.accept();
    }

    /********* Frames e Janelas ************/

    public void entrarFrame(String id) {
        getDriver().switchTo().frame(id);
    }

    public void sairFrame(){
        getDriver().switchTo().defaultContent();
    }

    public void trocarJanela(String id) {
        getDriver().switchTo().window(id);
    }

    /********** JS **********************/

    public Object executarJS (String cmd, Object... param) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        //js.executeScript("alert('Testando js via sleenium')");

        return js.executeScript(cmd, param);
    }

    /********************  tabelas  *****************************/

    public String obterValorCelula(String colunaBusca, String valor, String idTabela) {
        // procurar coluna do registro
        WebElement tabela = getDriver().findElement(By.id(idTabela));
        int idColuna = obterIndiceColuna(colunaBusca, tabela);

        // encontrar a linha do registro
        int idLinha = obterIndiceLinha(valor, tabela, idColuna);

        // clicar no botão da celula encontrada
        WebElement celula = getDriver().findElement(By.xpath("//tr["+idLinha+"]/td[1]"));

        return celula.getText();
    }

    public void clicarBotaoTabela(String colunaBusca, String valor, String colunaBotao, String idTabela) {
        // procurar coluna do registro
        WebElement tabela = getDriver().findElement(By.id(idTabela));
        int idColuna = obterIndiceColuna(colunaBusca, tabela);

        // encontrar a linha do registro
        int idLinha = obterIndiceLinha(valor, tabela, idColuna);

        // procurar coluna do bota
        int idColunaBotao = obterIndiceColuna(colunaBotao, tabela);

        // clicar no botão da celula encontrada
        WebElement celula = getDriver().findElement(By.xpath("//tr["+idLinha+"]/td["+idColunaBotao+"]"));
        celula.findElement(By.xpath(".//input")).click();

    }
    public void clicarBotaoTabela(String colunaBusca, String valor, String colunaBotao, String idTabela, By by) {
        // procurar coluna do registro
        WebElement tabela = getDriver().findElement(By.id(idTabela));
        int idColuna = obterIndiceColuna(colunaBusca, tabela);

        // encontrar a linha do registro
        int idLinha = obterIndiceLinha(valor, tabela, idColuna);

        // procurar coluna do bota
        int idColunaBotao = obterIndiceColuna(colunaBotao, tabela);

        // clicar no botão da celula encontrada
        WebElement celula = getDriver().findElement(By.xpath("//tr["+idLinha+"]/td["+idColunaBotao+"]"));
        celula.findElement(by).click();
    }

    protected int obterIndiceColuna(String colunaBusca, WebElement tabela) {
        List<WebElement> colunas = tabela.findElements(By.xpath(".//th"));
        int idColuna = -1;
        for (int i = 0 ; i< colunas.size(); i++ ) {
            if (colunas.get(i).getText().equals(colunaBusca)) {
                idColuna = i + 1;
                break;
            }
        }
        return idColuna;
    }

    protected int obterIndiceLinha(String valor, WebElement tabela, int idColuna) {
        List<WebElement> linhas = tabela.findElements(By.xpath("./tbody/tr/td["+idColuna+"]"));
        int idLinha = -1;
        for (int i = 0 ; i< linhas.size(); i++ ) {
            if (linhas.get(i).getText().equals(valor)) {
                idLinha = i + 1;
                break;
            }
        }
        return idLinha;
    }
}
