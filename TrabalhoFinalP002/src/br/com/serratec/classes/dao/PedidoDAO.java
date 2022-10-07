package br.com.serratec.classes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.serratec.classes.Cliente;
import br.com.serratec.classes.PedItem;
import br.com.serratec.classes.Pedido;
import br.com.serratec.conexao.Conexao;

public class PedidoDAO {
	private Conexao conexao;

	PreparedStatement pInclusaoPedido = null;
	PreparedStatement pInclusaoPedItem = null;

	public PedidoDAO(Conexao conexao) {
		this.conexao = conexao;
		prepararSqlInclusaoPedItem();
		prepararSqlInclusaoPedido();
		
	}
	public void prepararSqlInclusaoPedido() {

		String sql = "insert into loja.pedido";
		sql = sql + " (numero, valortotal, idcliente,dataemissao)";
		sql = sql + " values ";
		sql = sql + " (?, ?, ?, ?)";

		try {
			pInclusaoPedido = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private void prepararSqlInclusaoPedItem() {
		String sql = "insert into loja.pedItem";
		sql = sql + " (idpedido, idproduto, valorunitario, quantidade)";
		sql = sql + " values ";
		sql = sql + " (?, ?, ?, ?)";

		try {
			pInclusaoPedItem = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	public int incluirPedido(Pedido pedido) {
		try {

			pInclusaoPedido.setInt(1, pedido.getNumero());
			pInclusaoPedido.setDouble(2, pedido.getValorTotal());
			pInclusaoPedido.setInt(3, pedido.getCliente().getIdcliente());
			pInclusaoPedido.setString(4, pedido.getDataPedido());

			pInclusaoPedido.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nPedido n�o inclu�do.\nVerifique se foi chamado o conect:\n" + e);
			} else {
				System.err.println(e);
				e.printStackTrace();
			}
		}
		return 0;
	}

	@SuppressWarnings("unused")
	public int incluirPedItem(Pedido pedido) {
		int qtdProd = pedido.getProduto().size();
		double valor_total = 0;

		try {
			for (int i = 0; i <= qtdProd - 1; i++) {
				pInclusaoPedItem.setInt(1, pedido.getIdpedido());
				pInclusaoPedItem.setInt(2, pedido.getProduto().get(i).getIdproduto());
				pInclusaoPedItem.setDouble(3, pedido.getProduto().get(i).getValorUnitario());
				pInclusaoPedItem.setDouble(4, pedido.getProduto().get(i).getQuantidade());
				valor_total += pedido.getProduto().get(i).getTotal();
				pInclusaoPedItem.executeUpdate();
			}
			String sqlFinal = "update loja.pedido set ";
			sqlFinal += "valortotal = '" + valor_total + "' ";
			sqlFinal += "where idpedido = " + pedido.getIdpedido();

			conexao.queryDatabase(sqlFinal);
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nPedItem n�o inclu�do.\nVerifique se foi chamado o conect:\n" + e);
			} else {
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
		return qtdProd;
	}

	public int getLastIdPedido() {
		ResultSet last_id;
		String sql = "SELECT idpedido FROM loja.pedido ORDER BY idpedido DESC LIMIT 1";
		last_id = conexao.query(sql);
		try {
			while (last_id.next()) {
				return last_id.getInt("idpedido");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void alterarPedido(Pedido pedido) {
		String sql = "update loja.pedido set " + "numero = '" + pedido.getNumero() + "'" + ", idcliente = '"
				+ pedido.getCliente().getIdcliente() + "'" + ", valorpedido = '" + pedido.getValorPedido() + "'"
				+ "where idpedido = " + pedido.getIdpedido();
		conexao.query(sql);
	}

	public void alterarPedidoItem(Pedido pedido, PedItem pedItem) {
		String sql = "update loja.pedItem set " + "quantidade = '" + pedItem.getQuantidade() + "'" + "where idpedido = "
				+ pedido.getIdpedido();
		conexao.query(sql);
	}

	public static int selecionarIdPedido(int idpedido, Conexao con) {
		ResultSet getIdcliente;
		int idCliente = 0;
		String sqlIdCliente = "select idcliente from loja.pedido where idpedido = " + idpedido;
		getIdcliente = con.query(sqlIdCliente);
		try {
			if (getIdcliente.next()) {
				idCliente = getIdcliente.getInt("idcliente");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return idCliente;
	}
	public static int selecionarPorDataPedido(String data, Conexao con) {
        ResultSet getIdcliente;
        int idCliente = 0;
        String sqlIdCliente = "select idcliente from loja.pedido where dataemissao = '" + data + "'";//2022/09/18 01:19:15//18941056742
        getIdcliente = con.query(sqlIdCliente);
        try {
            while(getIdcliente.next()) {
                idCliente = getIdcliente.getInt("idcliente");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return idCliente;
    }

	public Pedido selecionarPedido(String data, int idpedido, int idCliente, Conexao con) {
		Pedido pedido = new Pedido();
		Cliente cliente = new Cliente();
		ResultSet tbPedido, tbItens, tbCliente = null;
		if(data == null) {
	          idCliente = selecionarIdPedido(idpedido, con);
	      } else {
	          idCliente = selecionarPorDataPedido(data, con);
	      }
	
		String sql;
		if (data == null) {
			sql = "select * from loja.pedido where idpedido = " + idpedido;
		} else {
			sql = "select * from loja.pedido where dataemissao = '" + data + "'";
		}
		tbPedido = conexao.query(sql);

		try {

			if (tbPedido.next()) {
				sql = "select * from loja.cliente where idcliente = " + idCliente;
				tbCliente = conexao.query(sql);

				if (tbCliente.next()) {
					cliente.setCpf(tbCliente.getString("cpf"));
					cliente.setIdcliente(tbCliente.getInt("idcliente"));
					cliente.setLogradouro(tbCliente.getString("logradouro"));
					cliente.setNome(tbCliente.getString("nome"));
				}

				String sqlConsulta = "select cli.nome, pe.idpedido, pe.valortotal, pe.numero, ";
				sqlConsulta += "pe.dataemissao, pe.idcliente, pi.idproduto, pi.valorunitario, ";
				sqlConsulta += "pi.quantidade from loja.pedido pe ";
				sqlConsulta += "left join loja.peditem pi on pi.idpedido = pe.idpedido ";
				sqlConsulta += "left join loja.cliente cli on cli.idcliente = pe.idcliente ";
				sqlConsulta += "where pe.idcliente = " + idCliente;

				try {
					tbItens = conexao.query(sqlConsulta);
					PedItem itens = new PedItem();
					Pedido itens2 = new Pedido();
					while (tbItens.next()) {
						
						if(tbItens.getInt("idproduto") != 0) {	
							itens.setNomeCliente(tbItens.getString("nome"));
							itens.setIdproduto(tbItens.getInt("idproduto"));
							itens.setIdpedido(tbItens.getInt("idpedido"));
							itens.setQuantidade(tbItens.getDouble("quantidade"));
							itens.setValorUnitario(tbItens.getDouble("valorunitario"));
							itens.setTotal(tbItens.getDouble("valortotal"));
							itens2.adicionarProduto(itens);
						} else {
							continue;
						}
						
					}
					itens2.imprimirItensCliente();
				
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if(data != null) {
				System.out.println("IdPedido " + idpedido + " n�o localizado.");
			}
			tbPedido.close();

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}

		return pedido;
	}

	public void apagarPedido(int idpedido) {

		String sql = "delete from loja.peditem" + " where idpedido = " + idpedido;

		conexao.query(sql);

		sql = "delete from loja.pedido" + " where idpedido = " + idpedido;

		conexao.query(sql);
	}

	public void localizarPedidoPor(int idpedido) {
	}

	public Pedido listarPedidos(int idpedido) {

		Pedido pedido = new Pedido();
		ResultSet tabela;

		String sql = "select p.*, c.cpf, c.logradouro, c.nome " + "from loja.pedido p "
				+ "left join loja.cliente c on c.idcliente = p.idcliente " + "where idpedido = " + idpedido;

		tabela = conexao.query(sql);

		try {
			tabela.last();
			int rowCount = tabela.getRow();
			System.out.println("Quantidade de pedidos: " + rowCount);

			if (rowCount > 0) {
				System.out.println("\nDADOS DO PEDIDO");
				System.out.println("---------------------------------------------------------------------");
				System.out.println("N�mero\t\tData\t\tCliente\tCPF\t\tValor Total");
			} else {
				System.out.println("\nN�o possui dados.");
				return null;
			}

			tabela.beforeFirst();

			while (tabela.next()) {
				System.out.printf("%s\t%s\t%-20s\t%s\t\t%s\t\n", tabela.getString("numero"),
						tabela.getString("valortotal"), tabela.getString("nome"), tabela.getString("cpf"),
						tabela.getString("valortotal"));
			}

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return pedido;
	}

	public Pedido localizarPedido() {

		Pedido pedido = new Pedido();

		ResultSet tabela;

		String sql = "select idpedido from loja.pedido";

		tabela = conexao.query(sql);

		try {
			if (tabela.last()) {
				pedido.setIdpedido(tabela.getInt("idpedido"));
			} else {
			}
			tabela.close();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return pedido;
	}

}