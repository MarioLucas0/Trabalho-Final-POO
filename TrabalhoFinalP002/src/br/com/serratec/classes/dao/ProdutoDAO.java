package br.com.serratec.classes.dao;


import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import br.com.serratec.classes.Produto;
import br.com.serratec.conexao.Conexao;

public class ProdutoDAO {
	
	private Conexao conexao;
	SimpleDateFormat sdf = new  SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
	public ProdutoDAO(Conexao conexao) { 
			this.conexao = conexao;
		}
	
	public void incluirProduto(Produto produto) {
		String sql =
				"insert into loja.produto";
		sql = sql + " (datafabricacao,nomeproduto,custo,valorunitario,idempresa)";
		sql = sql + " values ";
		sql = sql + " (";
		sql = sql + "'" + produto.getDtfabricacao() + "',";
		sql = sql + "'" + produto.getNomeProduto() + "',";
		sql = sql + "'" + produto.getCusto() + "',";
		sql = sql + "'" + produto.getValorUnitario() + "',";
		sql = sql + "'" + produto.getIdempresa() + "'";
		sql = sql + ")";
				
		conexao.query(sql);
	}
	
	public void alterarProduto(Produto produto) {
		String sql = "update  loja.produto set " +
						"datafabricacao = '" + produto.getDtfabricacao() + "'" +
						", nomeproduto = '" + produto.getNomeProduto() + "'" +
						", custo = '" + produto.getCusto() + "'" +
						", valorunitario = '" + produto.getValorUnitario() + "' " +
						"where idproduto = " + produto.getIdproduto() ;
		conexao.query(sql);
	}
	
	public Produto selecionarProduto(int idproduto) {
		Produto produto = new Produto();
		ResultSet tabela;
		
		String sql = "select * from loja.produto where idproduto = " + idproduto;
		
		tabela = conexao.query(sql);
		
		try {
		
			if (tabela.next()) {
				produto.setIdproduto(tabela.getInt("idproduto"));
				produto.setDtfabricacao(tabela.getDate("datafabricacao"));
				produto.setNomeProduto(tabela.getString("nomeproduto"));
				produto.setCusto(tabela.getDouble("custo"));
				produto.setValorUnitario(tabela.getDouble("valorunitario"));
				
			} else
				System.out.println("IdProduto " + idproduto + " n�o localizado.");
			
			tabela.close();	
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return produto;
	}
	
	public void apagarProduto(int idproduto) {
		String sql = "delete from loja.produto" +
						" where idproduto = " + idproduto;
		
		conexao.query(sql);		
	}
	
	public void listarProduto() {
		ResultSet tabela;		
		
		String sql = "select * from loja.produto order by idproduto";
		
		tabela = conexao.query(sql);
		
		try {
			tabela.last(); 
			int rowCount = tabela.getRow(); 
			System.out.println("Produtos disponíveis: " +rowCount);
			
			if (rowCount > 0) {
				System.out.println("\nC�digo\tdatafabricacao\tnomeproduto\tcusto\tvalorunitario");
			} else {
				System.out.println("\nN�o possui dados.");
				return; 
						
			}
			
			tabela.beforeFirst();
			
			while (tabela.next()) {
				System.out.printf("%s\t%-10s\t%s\t%s\t%s\n",
						tabela.getInt("idproduto"),
						tabela.getDate("datafabricacao"),
						tabela.getString("nomeproduto"),
						tabela.getDouble("custo"),
						tabela.getDouble("valorunitario")
					);		
			}	
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		
		
	}
	
	public Produto localizarProduto(String nomeproduto, int idproduto) {
		Produto produto = new Produto();
		
		String sql;
		ResultSet tabela;
		
		if (nomeproduto == null) {
			sql = "select * from loja.produto where idproduto = " + idproduto;
		} else {
			sql = "select * from loja.produto where nomeproduto = '" + nomeproduto + "'";
		}
		
		tabela = conexao.query(sql);
		
		try {
			if (tabela.next()) {
				produto.setIdproduto (tabela.getInt("idproduto"));
				produto.setDtfabricacao(tabela.getDate("datafabricacao"));
				produto.setNomeProduto(tabela.getString("nomeproduto"));
				produto.setCusto(tabela.getDouble("custo"));
				produto.setValorUnitario(tabela.getDouble("valorunitario"));
			} else {
				if (nomeproduto== null) {
					System.out.println("Produto com o nome " + nomeproduto + " nao localizado.");
				} else {
					System.out.println("idproduto '" + idproduto + "' nao localizado.");
				}
				produto = null;
			}
			
			tabela.close();	
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return produto;
	}
	
	public Produto localizarProdutoPedidoItem(String nomeproduto, int idproduto) {
	       Produto produto = new Produto();

	        String sql;
	        ResultSet tabela;

	        if (nomeproduto == null) {
	            sql = "select * from loja.produto where idproduto = " + idproduto;
	        } else  {
	            sql = "select idproduto from loja.produto where idproduto = '" + idproduto + "'";
	        }

	        tabela = conexao.query(sql);

	        try {

	            if (tabela.next()) {
	                produto.setIdproduto(tabela.getInt("idproduto"));
	                produto.setDtfabricacao(tabela.getDate("datafabricacao"));
	                produto.setNomeProduto (tabela.getString("nomeproduto"));
	                produto.setCusto(tabela.getDouble("custo"));
	                produto.setValorUnitario(tabela.getDouble("valorunitario"));
	            } else {
	                if (nomeproduto == null) {
	                	System.out.println("Produto com o nome " + nomeproduto + " nao localizado.");
	                } else {
	                	System.out.println("idproduto '" + idproduto + "' nao localizado.");
	                }
	                produto = null;
	            }

	            tabela.close();
	        } catch (Exception e) {
	            System.err.println(e);
	            e.printStackTrace();
	        }

	        return produto;
	    }
	
}
