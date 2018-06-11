package br.com.fernando.automationframework.core;

public class FrameworkProperties {

    public static boolean CLOSE_BROWSER = true;

    public static Browsers BROWSER = Browsers.GOOGLE_CHROME;

    public static TipoExecucao TIPO_EXECUCAO = TipoExecucao.LOCAL;

    public enum Browsers {
        GOOGLE_CHROME,
        MOZILLA_FIREFOX,
        MICROSOFT_EDGE,
        INTERNET_EXPLORER
    }

    public enum TipoExecucao {
        LOCAL,
        SELENIUM_GRID,
        CLOUD_SOUCE_LABS,
        CLOUD_BROWSER_STACK
    }

}
