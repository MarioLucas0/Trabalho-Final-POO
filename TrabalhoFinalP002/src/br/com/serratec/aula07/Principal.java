package br.com.serratec.aula07;

public class Principal {
	public static void main(String[] args) {
		cabecalho();
	}
	
	public static void cabecalho( ) {
		System.out.println(Constante.LINHA_GROSSA);
		System.out.println(Constante.PROGRAMA);
		System.out.println("Empresa: " + Constante.NM_EMPRESA);
		System.out.println("Versão: " + Constante.VERSAO);
		System.out.println(Constante.LINHA_FINA);
	}
}
