package br.com.serratec.classes.dao;

import java.sql.ResultSet;


import br.com.serratec.classes.Empresa;
import br.com.serratec.classes.Pedido;
import br.com.serratec.conexao.Conexao;

public class EmpresaDAO {

	private Conexao conexao;

	public EmpresaDAO(Conexao conexao) {
		this.conexao = conexao;
	}
	
	
	public void incluirEmpresa(Empresa empresa) {
		String sql = "insert into loja.empresa";
		sql = sql + " (razaosocial,cnpj,cep,cidade)";
		sql = sql + " values ";
		sql = sql + " (";
		sql = sql + "'" + empresa.getRazaoSocial() + "',";
		sql = sql + "'" + empresa.getCnpj() + "',";
		sql = sql + "'" + empresa.getCEP() + "',";
		sql = sql + "'" + empresa.getCidade() + "'";
		sql = sql + ")";
		
		conexao.query(sql);
	}
	
	public Empresa LocalizarEmpresa(int idempresa) {
		
		Empresa empresa = new Empresa();
		ResultSet tabela;
		
		String sql = "select * from loja.empresa where idempresa = " + idempresa;
		tabela = conexao.query(sql);
		
		try {
			if (tabela.next()) {
				empresa.setIdempresa(tabela.getInt("idempresa"));
				empresa.setRazaoSocial(tabela.getString("razaosocial"));
				empresa.setCnpj(tabela.getString("cnpj"));
				empresa.setCidade(tabela.getString("cidade"));
				empresa.setCEP(tabela.getInt("cep"));
			} else
				System.out.println("Idempresa " + idempresa + " nao localizado.");
			
			tabela.close();	
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return empresa;
	}
	
	public Empresa listarEmpresas() {

		Empresa empresa = new Empresa();
		ResultSet tabela;

		String sql = "select * from loja.empresa ";
				

		tabela = conexao.query(sql);

		try {
			tabela.last();
			int rowCount = tabela.getRow();
			System.out.println("Quantidade de empresas: " + rowCount);

			if (rowCount > 0) {
				System.out.println("\nDADOS DAS EMPRESAS");
				System.out.println("---------------------------------------------------------------------");
			} else {
				System.out.println("\nN�o possui dados.");
				return null;
			}

			tabela.beforeFirst();

			while (tabela.next()) {
				  System.out.println("Idpedido: "  + tabela.getInt("idempresa"));
				  System.out.println("Nome da Empresa: " + tabela.getString("razaosocial")); 
				  System.out.println("CNPJ: " + tabela.getString("cnpj")); 
				  System.out.println("CEP: " + tabela.getString("cep")); 
				  System.out.println("Cidade: " + tabela.getString("cidade"));
				  System.out.println("---------------------------------------------------------------------");
			}

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return empresa;
	}

	
	
	
}
