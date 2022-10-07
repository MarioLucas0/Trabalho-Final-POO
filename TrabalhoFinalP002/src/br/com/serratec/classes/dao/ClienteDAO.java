package br.com.serratec.classes.dao;
import br.com.serratec.conexao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.serratec.classes.Cliente;

public class ClienteDAO {
	private Conexao conexao;
	
	public ClienteDAO(Conexao conexao) { 
		this.conexao = conexao;
	}
	
	public void incluirCliente(Cliente cliente) {
		String sql = "insert into loja.cliente";
		sql = sql + " (nome,logradouro,numeroendereco,bairro,cidade,telefonecliente,cep,cpf)";
		sql = sql + " values ";
		sql = sql + " (";
		sql = sql + "'" + cliente.getNome() + "',";
		sql = sql + "'" + cliente.getLogradouro() + "',";
		sql = sql + "'" + cliente.getNumeroEndereco() + "',";
		sql = sql + "'" + cliente.getBairro() + "',";
		sql = sql + "'" + cliente.getCidade() + "',";
		sql = sql + "'" + cliente.getTelefone() + "',";
		sql = sql + "'" + cliente.getCep() + "',";
		sql = sql + "'" + cliente.getCpf() + "'";
		sql = sql + ")";
		
		conexao.queryDatabase(sql);
	}
	
	public void alterarCliente(Cliente cliente) {
		String sql = "update loja.cliente set " +
						"nome = '" + cliente.getNome() + "'" +
						", logradouro = '" + cliente.getLogradouro() + "'" +
						", numeroendereco = '" + cliente.getNumeroEndereco() + "'" +
						", bairro = '" + cliente.getBairro() + "' " +
						", cidade = '" + cliente.getCidade() + "' " +
						", telefonecliente = '" + cliente.getTelefone() + "' " +
						", cep = '" + cliente.getCep() + "' " +
						", cpf = '" + cliente.getCpf() + "' " +
						"where idcliente = " + cliente.getIdcliente();
		conexao.query(sql);
		
	}
		
	public Cliente selecionarCliente(int idCliente) {
		Cliente cliente = new Cliente();
		ResultSet tabela;
		
		String sql = "select * from loja.cliente where idcliente = " + idCliente;
		tabela = conexao.query(sql);
		
		try {
			if (tabela.next()) {
				cliente.setIdcliente(tabela.getInt("idcliente"));
				cliente.setNome(tabela.getString("nome"));
				cliente.setLogradouro (tabela.getString("logradouro"));
				cliente.setNumeroEndereco(tabela.getString("numeroendereco"));
				cliente.setComplemento(tabela.getString("complemento"));
				cliente.setBairro(tabela.getString("bairro"));
				cliente.setCidade(tabela.getString("cidade"));
				cliente.setTelefone(tabela.getString("telefonecliente"));
				cliente.setCep(tabela.getString("cep"));
				cliente.setCpf(tabela.getString("cpf"));
			} else
				System.out.println("IdCliente " + idCliente + " n�o localizado.");
			
			tabela.close();	
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return cliente;
	}
	
	public void apagarCliente(int idCliente) {
		String sql = "delete from loja.cliente" +
						" where idcliente = " + idCliente;
		
		conexao.query(sql);		
	}
	
	public Cliente localizarCliente(String nome, int idCliente) {
		
		Cliente cliente = new Cliente();
		String sql;
		ResultSet tabela;
		
		if (nome == null) {
			sql = "select * from loja.cliente where idcliente = " + idCliente;
		} else {
			sql = "select * from loja.cliente where nome = '" + nome + "'";
		}
		tabela = conexao.query(sql);
		
		try {
		
			if (tabela.next()) {
				cliente.setIdcliente(tabela.getInt("idcliente"));
				cliente.setNome(tabela.getString("nome"));
				cliente.setLogradouro (tabela.getString("logradouro"));
				cliente.setNumeroEndereco(tabela.getString("numeroendereco"));
				cliente.setBairro(tabela.getString("bairro"));
				cliente.setCidade(tabela.getString("cidade"));
				cliente.setTelefone(tabela.getString("telefonecliente"));
				cliente.setCep(tabela.getString("cep"));
				cliente.setCpf(tabela.getString("cpf"));
			} else {
				if (nome == null) {
					System.out.println("IdCliente " + idCliente + " n�o localizado.");
				} else {
					System.out.println("Cliente '" + nome + "' n�o localizado.");
				}
				cliente = null;
			}
			
			tabela.close();	
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return cliente;
	}
	
	public  Cliente localizarClienteCpf(String cpf) {
		Cliente cliente = new Cliente();
		
		ResultSet tabela;
		
		String sql = "select * from loja.cliente where cpf = '" + cpf + "'";
		
		tabela = conexao.query(sql);
		
		try {
			
			while (tabela.next()) {
				cliente.setIdcliente(tabela.getInt("idcliente"));
				cliente.setNome(tabela.getString("nome"));
				cliente.setLogradouro (tabela.getString("logradouro"));
				cliente.setNumeroEndereco(tabela.getString("numeroendereco"));
				cliente.setBairro(tabela.getString("bairro"));
				cliente.setCidade(tabela.getString("cidade"));
				cliente.setTelefone(tabela.getString("telefonecliente"));
				cliente.setCep(tabela.getString("cep"));
				cliente.setCpf(tabela.getString("cpf"));
			}
			
			tabela.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cliente;
	}
	
	public void listarClientes() {
		ResultSet tabela;		
		
		String sql = "select * from loja.cliente order by idcliente";
		
		tabela = conexao.query(sql);
		
		try {
			tabela.last(); 
			int rowCount = tabela.getRow(); 
			System.out.println("Quantidade de clientes: " +rowCount);
			System.out.println("-------------------------------------------------------");
			
			if (!(rowCount > 0)) { 
				
				System.out.println("\nNao possui dados.");
				return; 
			}
			
			tabela.beforeFirst();
			
			while (tabela.next()) {
		
				System.out.println("Codigo: " + tabela.getInt("idcliente"));
				System.out.println("Nome: " + tabela.getString("nome"));
				System.out.println("Logradouro: " + tabela.getString("logradouro"));
				System.out.println("Numero do Endereco: " + tabela.getString("numeroendereco"));
				System.out.println("Bairro: " + tabela.getString("bairro"));
				System.out.println("Cidade: " + tabela.getString("cidade"));
				System.out.println("Cep: " + tabela.getString("cep"));
				System.out.println("Cpf: " + tabela.getString("cpf"));
				System.out.println("-------------------------------------------------------");
									
			}
			
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
