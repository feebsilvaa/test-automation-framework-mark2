package br.com.fernando.automationframework.support;

public class ErrMsgGen {
	public static String comparisonErrorMessage(String expected, String actual) {
		StringBuilder str = new StringBuilder();
		
		str.append("[ERRO] - Erro ao realizar comparação. Esperava [")
		.append(expected)
		.append("] mas encontrei [")
		.append(actual)
		.append("].");

		return str.toString();
	}
}
