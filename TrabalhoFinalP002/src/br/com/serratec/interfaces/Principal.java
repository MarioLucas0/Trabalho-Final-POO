package br.com.serratec.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import br.com.serratec.classes.Cliente;
import br.com.serratec.classes.Empresa;
import br.com.serratec.classes.PedItem;
import br.com.serratec.classes.Pedido;
//import br.com.serratec.classes.Pedido;
import br.com.serratec.classes.Produto;
import br.com.serratec.classes.dao.ClienteDAO;
import br.com.serratec.classes.dao.CreateDAO;
import br.com.serratec.classes.dao.DatabaseDAO;
import br.com.serratec.classes.dao.EmpresaDAO;
import br.com.serratec.classes.dao.PedidoDAO;
//import br.com.serratec.classes.dao.PedidoDAO;
import br.com.serratec.classes.dao.ProdutoDAO;
import br.com.serratec.classes.dao.SchemaDAO;
import br.com.serratec.conexao.Conexao;
import br.com.serratec.enums.OpcoesCadastro;
import br.com.serratec.enums.OpcoesConexao;
import br.com.serratec.enums.OpcoesCreateDAO;
import br.com.serratec.enums.OpcoesCrud;
import br.com.serratec.enums.OpcoesDadosCliente;

public class Principal {
	public static Scanner input = new Scanner(System.in);
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static void main(String[] args) throws Exception {

		menuPrincipal(selecionarConexao());
		selecionarConexao().disconect();
	}

	@SuppressWarnings("null")
	public static void menuPrincipal(Conexao con) {
		int opcao;

		do {
			System.out.println("\nMENU PRINCIPAL\n---------------------");
			for (OpcoesCadastro op : OpcoesCadastro.values()) {
				System.out.println((op.ordinal() + 1) + " - " + op);
			}

			opcao = informeOpcao("Informe uma opção: ");

			switch (opcao) {

			case 1:
				menuCliente(con);
				break;
			case 2:
				menuProduto(con);
				break;
			case 3:
				menuPedido(con);
				break;
			case 4:
				menuCriar(con);
				break;
			case 5:
				System.out.println("\nSistema encerrado!");
				System.exit(0);
				break;
			case 6:
				opcao = informeOpcao("Informe uma opção: ");
				break;
			default:
				System.out.println("Opcaoo invalida.");
				break;
			}

		} while (opcao != 5);
	}

	private static void menuProduto(Conexao con) {

		ProdutoDAO prodDAO = new ProdutoDAO(con);
		Produto produto = null;

		int opcao;

		menuCRUD();
		opcao = informeOpcao("Informe uma opcao: ");

		do {
			switch (opcao) {
			case 1:
				dadosInclusaoProduto(con);
				break;
			case 2:
				dadosAlteradosProduto(con);
				break;
			case 3:
				produto = localizarProduto(produto, prodDAO);

				if (produto != null) {
					prodDAO.apagarProduto(produto.getIdproduto());
					System.out.println("Dado apagado com sucesso!");
				}
				menuProduto(con);
				break;
			case 4:

				System.out.println("Escolha como localizar");
				System.out.println("1- Nome");
				System.out.println("2- C�digo");
				System.out.println("3- Voltar");
				System.out.print("=> ");
				int opcaoL = input.nextInt();
				input.nextLine();

				do {
					switch (opcaoL) {
					case 1:
						System.out.println("Informe o nome: ");
						String nome2 = input.nextLine();
						produto = prodDAO.localizarProduto(nome2, 0);
						if (produto == null) {
							System.out.println("Produto nao encontrado");
						} else {
							System.out.println("\n-------------------------------------------------------");
							System.out.println("Dados do Produto: ");
							System.out.println("-------------------------");
							System.out.println("Codigo: " + produto.getIdproduto());
							System.out.println("Nome: " + produto.getNomeProduto());
							System.out.println("Valor Unitario: " + produto.getValorUnitario());
							System.out.println("Custo Produto: " + produto.getCusto());
							System.out.println("Data de Fabricaçao: " + produto.getDtfabricacao());
							System.out.println("\n-------------------------------------------------------");
							opcaoL = 3;
						}
						break;
					case 2:
						System.out.println("Informe o codigo: ");
						System.out.println("=> ");
						int codigo2 = informeOpcao("");
						produto = prodDAO.localizarProduto(null, codigo2);
						if (produto == null) {
							System.out.println("Produto n�o encontrado");
						} else {
							System.out.println("\n-------------------------------------------------------");
							System.out.println("Dados do Produto: ");
							System.out.println("-------------------------");
							System.out.println("C�digo: " + produto.getIdproduto());
							System.out.println("Nome: " + produto.getNomeProduto());
							System.out.println("Valor Unitario: " + produto.getValorUnitario());
							System.out.println("Custo Produto: " + produto.getCusto());
							System.out.println("Data de Fabricaçao: " + produto.getDtfabricacao());
							System.out.println("\n-------------------------------------------------------");
							opcaoL = 3;

						}
						break;
					default:
						System.out.println("Opcao invalida!");
					}
				} while (opcaoL != 3);

				menuPrincipal(con);
				break;
			case 5:

				prodDAO.listarProduto();
				return;
			}

		} while (opcao != 6);
	}

	private static Produto localizarProduto(Produto produto, ProdutoDAO prodDAO) {

		boolean continuar_id = true;
		int idproduto;

		do {
			idproduto = informeOpcao("Informe o c�digo do produto: ");

			if (idproduto == 0) {
				System.out.println("id inv�lido!");
			} else {
				produto = prodDAO.localizarProduto(null, idproduto);

				if (produto == null) {
					System.out.println("Cliente n�o encontrado.");
				}
			}

			continuar_id = idproduto == 0;

		} while (continuar_id);

		return produto;

	}

	@SuppressWarnings({ "null", "unused" })
	private static void dadosAlteradosProduto(Conexao con) {

		ProdutoDAO prodDAO = new ProdutoDAO(con);
		Produto produto = new Produto();

		produto = prodDAO.selecionarProduto(1);

		int resp = 0;
		do {

			System.out.println("Informe o codigo: ");
			int codigo = informeOpcao("=> ");
			produto = prodDAO.localizarProduto(null, codigo);

			if (produto != null) {
				System.out.println("Produto do cliente: ");
				System.out.println("-------------------------");
				System.out.println("C�digo: " + produto.getIdproduto());
				System.out.println("data de fabricacao: " + produto.getDtfabricacao());
				System.out.println("nome do produto: " + produto.getNomeProduto());
				System.out.println("custo do produto: " + produto.getCusto());
				System.out.println("valor unitario: " + produto.getValorUnitario());
			}

			System.out.println("\nQual dado Gostaria de alterar? ");
			System.out.print("=> ");
			menuAlterarDadosProduto();

			int res = input.nextInt();
			input.nextLine();

			switch (res) {
			case 1:
				System.out.println("Digite a nova data de fabricaçao");
				System.out.println("=> ");
				String data = input.nextLine();

				if (!data.isBlank()) {
					try {
						produto.setDtfabricacao(sdf.parse(data));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				prodDAO.alterarProduto(produto);
				System.out.println("Dados alterado com sucesso!");

				break;
			case 2:
				System.out.println("Digite o novo  nome do produto");
				System.out.println("=> ");
				String nomeProduto = input.nextLine();

				if (!nomeProduto.isBlank()) {
					produto.setNomeProduto(nomeProduto);

				} else if (nomeProduto == null) {
					System.out.println("Seu Produto nao existe!");
					menuPrincipal(con);
				}

				prodDAO.alterarProduto(produto);
				System.out.println("Dados alterado com sucesso!");

				break;
			case 3:
				System.out.println("Digite o novo  custo do  produto");
				System.out.println("=> ");
				double custoProduto = input.nextDouble();

				if (!(custoProduto != 0)) {
					produto.setCusto(custoProduto);
				}
				prodDAO.alterarProduto(produto);
				System.out.println("Dados alterado com sucesso!");
				break;
			case 4:
				System.out.println("Digite o novo valor unitario");
				System.out.println("=> ");
				double valorunitrario = input.nextDouble();

				if (!(valorunitrario != 0)) {
					produto.setValorUnitario(valorunitrario);
				}
				prodDAO.alterarProduto(produto);
				System.out.println("Dados alterado com sucesso!");
				break;
			}

			System.out.println("Dados alterado com sucesso!");

			System.out.println("Gostaria de alterar mais 1 dado? ");
			System.out.println("1 - SIM");
			System.out.println("2 - NAO");
			System.out.print("=> ");
			resp = input.nextInt();
			input.nextLine();

			if (resp == 2) {
				menuPrincipal(con);

			} else if (res == 1) {
				dadosAlteradosProduto(con);
			}
		} while (resp != 2 || resp != 1);
	}

	private static void dadosInclusaoProduto(Conexao con) {

		ProdutoDAO podDAO = new ProdutoDAO(con);
		EmpresaDAO empDAO = new EmpresaDAO(con);
		Empresa empresa = null;

		String resp;

		String result;
		do {

			System.out.println("\nInforme a data de fabricao do  produto (dd/MM/yyyy HH:mm:ss): ");
			System.out.print("=> ");
			String data = input.nextLine();
			verificaData(data);
			System.out.println("Informe o nome do produto: ");
			System.out.print("=> ");
			String nomeProduto = input.nextLine();
			System.out.println("Informe o custo do produto: ");
			System.out.print("=> ");
			double custo = input.nextDouble();
			System.out.println("Informe o valorUnitario do produto: ");
			System.out.print("=> ");
			double valorUnitario = input.nextDouble();
			System.out.println("-------------------------------------------------------");
			empDAO.listarEmpresas();
			System.out.println("Informe o Id da empresa que vende o produto: ");
			System.out.print("=> ");
			int idempresa = input.nextInt();
			input.nextLine();

			empresa = empDAO.LocalizarEmpresa(idempresa);
			empresa.setIdempresa(idempresa);

			Produto produto = new Produto(data, nomeProduto, custo, valorUnitario);
			produto.setIdempresa(empresa.getIdempresa());
			podDAO.incluirProduto(produto);

			System.out.println("-------------------------------------------------------");
			System.out.println("Dados incluidos com sucesso!");
			System.out.println("Gostaria de inclui mais 1 produto? ");
			System.out.println("1 - SIM");
			System.out.println("2 - NAO");
			System.out.print("=> ");
			resp = input.next();
			result = verificaCliente(resp);

			if (result.contentEquals("2")) {

				System.out.println("\n");
				System.out.println("\nMENU PEDIDO");
				System.out.println("-------------------------------------------------------");
				menuProduto(con);

			} else {
				do {
					System.out.println("Escolha uma opcao Valida");
					System.out.println("Gostaria de inclui mais 1 produto? ");
					System.out.println("1 - SIM");
					System.out.println("2 - NAO");
					System.out.print("=> ");
					resp = input.next();
					result = verificaCliente(resp);
				} while (result.contentEquals("2"));
			}
		} while (result.contentEquals("2"));
		menuPrincipal(con);
	}

	public static int informeOpcao(String msg) {
		System.out.print("\n" + msg);
		String resposta = input.nextLine();
		int opcao;

		try {
			opcao = Integer.parseInt(resposta);
		} catch (Exception e) {
			opcao = 0;
		}
		return opcao;
	}

	public static Cliente dadosInclusaoCliente() {

		int resp = 0;
		do {
			System.out.println("\n-------------------------------------------------------");
			System.out.println("Informe o nome: ");
			System.out.print("=> ");
			String nome = input.nextLine();
			System.out.println("Informe o CPF: ex:(18941056742)");
			System.out.print("=> ");
			String cpf = input.nextLine();
			verificaCpf(cpf);
			System.out.println("Informe o endere�o: ");
			System.out.print("=> ");
			String endereco = input.nextLine();
			System.out.println("Informe o numero do Endereço: ");
			System.out.print("=> ");
			String nmEndereco = input.nextLine();
			verificaNumeroEndereco(nmEndereco);
			System.out.println("Informe o bairro: ");
			System.out.print("=> ");
			String bairro = input.nextLine();
			System.out.println("Informe a cidade ");
			System.out.print("=> ");
			String cidade = input.nextLine();
			System.out.println("Informe o cep: ex(25610190)");
			System.out.print("=> ");
			String cep = input.nextLine();
			verificaCep(cep);
			System.out.println("Informe o telefone: ex(24992329899)");
			System.out.print("=> ");
			String telefone = input.nextLine();
			verificaTelefone(telefone);
			System.out.println("\n-------------------------------------------------------");

			Cliente cliente = new Cliente(nome, cpf, endereco, nmEndereco, bairro, cep, cidade, telefone);
			System.out.println("\n-------------------------------------------------------");
			System.out.println("Dados incluidos com sucesso!");
			System.out.println("Gostaria de inclui mais 1 cliente ? ");
			System.out.println("1 - SIM");
			System.out.println("2 - NAO");
			System.out.print("=> ");
			resp = input.nextInt();
			input.nextLine();
			System.out.println("\n-------------------------------------------------------");

			if (resp != 2 && resp != 1) {
				System.out.println("Opcao Invalida! ");
				do {
					System.out.println("---------------------------------------------------------------------");
					System.out.println("Escolha uma opcao Valida");
					System.out.println("Gostaria de inclui mais 1 cliente ? ");
					System.out.println("1 - SIM");
					System.out.println("2 - NAO");
					System.out.print("=> ");
					resp = input.nextInt();
					System.out.println("---------------------------------------------------------------------");
					input.nextLine();
				} while (resp != 2 && resp != 1);
			}

			return cliente;
		} while (resp != 2 || resp != 1);
	}

	public static void menuCRUD() {
		System.out.println("\nOPERAÇÃO\n---------------------");
		for (OpcoesCrud opc : OpcoesCrud.values()) {
			System.out.println((opc.ordinal() + 1) + " - " + opc);
		}
	}

	@SuppressWarnings("unused")
	public static void menuCliente(Conexao con) {
		ClienteDAO cliDAO = new ClienteDAO(con);
		Cliente cliente = null;

		boolean continuar = true;
		int opcao;
		String nome;

		System.out.println("\nMENU CLIENTE\n---------------------------");
		menuCRUD();
		opcao = informeOpcao("Informe uma opcao: ");

		do {
			switch (opcao) {
			case 1:
				incluir(OpcoesCadastro.CLIENTE, con);
				break;
			case 2:

				System.out.println("Digite seu cpf:");
				System.out.println("=> ");

				if (cliente != null) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println("Dados do cliente: \n-------------------------");
					System.out.println("C�digo: " + cliente.getIdcliente());
					System.out.println("Nome: " + cliente.getNome());
					System.out.println("CPF: " + cliente.getCpf());
					System.out.println("Logradouro: " + cliente.getLogradouro());
					System.out.println("numero do Endere�o: " + cliente.getNumeroEndereco());
					System.out.println("Complemento: " + cliente.getComplemento());
					System.out.println("Bairro: " + cliente.getBairro());
					System.out.println("Cidade: " + cliente.getCidade());
					System.out.println("Cep: " + cliente.getCep());
					System.out.println("Telefone: " + cliente.getTelefone());
					System.out.println("---------------------------------------------------------------------");
				}

				System.out.println("\nQual dado você deseja alterar?");
				menuAlterarDadosCliente();

				int res = input.nextInt();
				input.nextLine();
				dadosAlterados(res, con);
				System.out.println("Dado alterado com sucesso! ");

				return;
			case 3:

				System.out.println("NAO TENTAR APAGAR CLIENTES QUE JA FIZERAM PEDIDO!");

				System.out.println("Digite o id do Cliente ");
				System.out.print("=> ");
				int idcliapg = input.nextInt();
				cliente = cliDAO.localizarCliente(null, idcliapg);
				cliente.setIdcliente(idcliapg);

				if (cliente.getIdcliente() == 0) {
					System.out.println("Cliente nao existe! ");
					menuCliente(con);
				} else if (cliente != null) {

					cliDAO.apagarCliente(cliente.getIdcliente());
					System.out.println("Dado apagado com sucesso!");
					menuCliente(con);

				} else {
					System.out.println("   " + "  ");
				}

				menuCliente(con);

				break;

			case 4:
				System.out.println("---------------------------------------------------------------------");
				System.out.println("Escolha como localizar");
				System.out.println("1- Nome");
				System.out.println("2- C�digo");
				System.out.println("3- Voltar");
				System.out.print("=> ");
				int opcaoL = input.nextInt();
				input.nextLine();
				System.out.println("---------------------------------------------------------------------");

				do {
					switch (opcaoL) {
					case 1:
						System.out.println("Informe o nome: ");
						System.out.println("=>  ");
						String nome2 = input.nextLine();
						System.out.println("\n");
						cliente = cliDAO.localizarCliente(nome2, 0);
						if (cliente == null) {
							System.out.println("Cliente n�o encontrado");
						} else {
							System.out.println("\n-------------------------------------------------------");
							System.out.println("Localiza��o do cliente");
							System.out.println("Nome: %s" + cliente.getNome());
							System.out.println("Logradouro: " + cliente.getLogradouro());
							System.out.println("Bairro: " + cliente.getBairro());
							System.out.println("Cidade: " + cliente.getCidade());
							System.out.println("Cep: " + cliente.getCep());
							System.out.println("Numero do Endereco: " + cliente.getNumeroEndereco());
							System.out.println("\n-------------------------------------------------------");
							opcaoL = 3;
						}
						break;
					case 2:
						System.out.println("Informe o c�digo: ");
						System.out.println("=>  ");
						int codigo = informeOpcao("");
						System.out.println("\n");
						cliente = cliDAO.localizarCliente(null, codigo);
						if (cliente == null) {
							System.out.println("Cliente n�o encontrado");
						} else {
							System.out.println("\n-------------------------------------------------------");
							System.out.println("Localiza��o do cliente");
							System.out.println("Nome: " + cliente.getNome());
							System.out.println("Logradouro: " + cliente.getLogradouro());
							System.out.println("Bairro: " + cliente.getBairro());
							System.out.println("Cidade: " + cliente.getCidade());
							System.out.println("Cep: " + cliente.getCep());
							System.out.println("Numero do Endereco: " + cliente.getNumeroEndereco());
							System.out.println("-------------------------------------------------------");
							opcaoL = 3;

						}
						break;
					default:
						System.out.println("Opcao invalida!");
					}
				} while (opcaoL != 3);

				menuPrincipal(con);
				break;

			case 5:
				cliDAO.listarClientes();
				return;
			case 6:
				break;
			default:
				System.out.println("Opcao invalida");
				System.out.println("Sistema Encerrado! ");
				System.exit(0);
				break;
			}

		} while (opcao != 6);
	}

	public static void dadosAlterados(int res, Conexao con) {

		ClienteDAO cliDAO = new ClienteDAO(con);
		Cliente cliente = new Cliente();

		System.out.println("Informe o id do cliente!  ");
		System.out.print("=> ");
		int idcliente = input.nextInt();
		input.nextLine();

		cliente = cliDAO.localizarCliente(null, idcliente);

		switch (res) {
		case 1:
			System.out.println("Escreva o novo nome: ");
			System.out.print("=>  ");
			String nome = input.nextLine();

			if (!nome.isBlank()) {
				cliente.setNome(nome);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		case 2:
			System.out.println("Escreva um novo CPF: ex(18941056742)");
			System.out.print("=>  ");
			String cpf = input.nextLine();
			String result = verificaCpf(cpf);

			if (!result.isBlank()) {
				cliente.setCpf(result);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		case 3:
			System.out.println("Escreva um novo logradouro: ");
			String logradouro = input.nextLine();

			if (!logradouro.isBlank()) {
				cliente.setLogradouro(logradouro);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		case 4:
			System.out.println("Escreva um novo numero do endereço: ");
			System.out.print("=>  ");
			String nmendereco = input.nextLine();
			verificaNumeroEndereco(nmendereco);

			if (!nmendereco.isBlank()) {
				cliente.setNumeroEndereco(nmendereco);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		case 5:
			System.out.println("Escreva um novo bairro: ");
			System.out.print("=>  ");
			String bairro = input.nextLine();

			if (!bairro.isBlank()) {
				cliente.setBairro(bairro);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		case 6:
			System.out.println("Escreva a nova cidade: ");
			System.out.print("=>  ");
			String cidade = input.nextLine();

			if (!cidade.isBlank()) {
				cliente.setBairro(cidade);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		case 7:
			System.out.println("Escreva um novo CEP: ex(25610190)");
			System.out.print("=>  ");
			String cep = input.nextLine();
			String resultCep = verificaCep(cep);

			if (!resultCep.isBlank()) {
				cliente.setCep(resultCep);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		case 8:
			System.out.println("Escreva o novo telefone: ex:(24992329899)");
			System.out.print("=>  ");
			String tel = input.nextLine();
			String resultTel = verificaTelefone(tel);

			if (!resultTel.isBlank()) {
				cliente.setTelefone(resultTel);
			}
			cliDAO.alterarCliente(cliente);
			menuCliente(con);
			break;
		default:
			System.out.println("Opcao  Invalida! ");
			menuCliente(con);
			break;
		}
	}

	public static void incluir(OpcoesCadastro opcao, Conexao con) {

		ClienteDAO cliente = new ClienteDAO(con);
		// ProdutoDAO produto = new ProdutoDAO(con);
		// PedidoDAO pedido = new PedidoDAO(con);

		switch (opcao) {
		case CLIENTE:
			cliente.incluirCliente(dadosInclusaoCliente());
			menuPrincipal(con);
			break;
		case PRODUTO:
			// produto.incluirProduto(dadosInclusaoProduto());
			// menuPrincipal(con);
			break;
		// case PEDIDO: pedido.fazerPedido(dadosDoPedido()); break;
		default:
			break;
		}
	}

	public static void localizarCliente(Conexao con) {
		ClienteDAO clienteDAO = new ClienteDAO(con);

		int codigo = informeOpcao("\nInforme o codigo: ");
		Cliente cliente = clienteDAO.localizarCliente(null, codigo);

		if (cliente != null) {
			System.out.println("Localizacao do cliente");
			System.out.printf("Nome: ", cliente.getNome());
			System.out.printf("Logradouro: ", cliente.getLogradouro());
		}
	}

	public static void menuAlterarDadosCliente() {
		for (OpcoesDadosCliente odc : OpcoesDadosCliente.values()) {
			System.out.println(odc + " > Digite [" + (odc.ordinal() + 1 + "]"));
		}
	}

	public static void menuAlterarDadosProduto() {
		System.out.println("---------------------------------------------------------------------");
		System.out.println("1 - DATA DE FABRICAÇAO");
		System.out.println("2 - NOME");
		System.out.println("3 - CUSTO");
		System.out.println("1 - VALOR UNITARIO");
		System.out.println("---------------------------------------------------------------------");
	}

	@SuppressWarnings("null")
	private static void menuPedido(Conexao con) {

		ClienteDAO cliDAO = new ClienteDAO(con);
		Cliente cliente = null;

		System.out.println("\nMENU PEDIDO");
		System.out.println("\n-------------------------------------------------------");

		System.out.println("Cliente novo digite (1)");
		System.out.println("Cliente antigo (2)");
		System.out.print("=> ");
		String res = input.next();
		String result = verificaCliente(res);
		System.out.println("---------------------------------------------------------------------");
		if (result.contentEquals("1")) {
			incluir(OpcoesCadastro.CLIENTE, con);
		} else if (result.contentEquals("2")) {

			System.out.println("Digite seu CPF: ");
			System.out.print("=> ");
			String resp = input.next();
			String result2 = verificaCpf(resp);
			input.nextLine();

			cliente = cliDAO.localizarClienteCpf(result2);

			if (cliente.getCpf() != null) {

				menuCrudPedido();
				int opcao = informeOpcao("Informe uma op��o: ");
				opcoesPedido(opcao, result2, con, cliente);

			} else {
				System.out.println("\nCliente nao encontrado!");
			}

		} else {

			System.out.println("\nOpcao invalida!");
			System.out.println("\n-------------------------------------------------------");
			return;
		}
	}

	public static void menuCrudPedido() {
		System.out.println("---------------------------------------------------------------------");
		System.out.println("\n1- Fazer novo Pedido");
		System.out.println("2- Alterar dado do Pedido");
		System.out.println("3- Excluir pedido");
		System.out.println("4- Listar Pedidos");
		System.out.println("5- Voltar");
		System.out.println("---------------------------------------------------------------------");
	}

	@SuppressWarnings("unused")
	private static void opcoesPedido(int opcao, String resp, Conexao con, Cliente cliente) {

		PedidoDAO pedDAO = new PedidoDAO(con);
		Pedido pedido = null;
		PedItem pedItem = new PedItem();

		ClienteDAO cliDAO = new ClienteDAO(con);
		Cliente cliente2 = null;

		switch (opcao) {
		case 1:
			dadosinclusapedido(resp, con, cliente);
			break;
		case 2:

			System.out.println("---------------------------------------------------------------------");
			System.out.println("So e Possivel alterar a Quantidade de Produtos!\n");
			dadosAlteradosPedido(con);
			System.out.println("---------------------------------------------------------------------");
			menuPrincipal(con);

			break;

		case 3:

			int continuar = 0;

			do {
				pedido = pedDAO.localizarPedido();

				if (pedido != null) {

					System.out.println("Digite o id do pedido que gostaria de apagar: ");
					System.out.print("=> ");
					int idpedido = input.nextInt();
					System.out.println("---------------------------------------------------------------------");
					pedDAO.apagarPedido(idpedido);
					System.out.println("\n Pedido apagado com sucesso!");
					System.out.println("---------------------------------------------------------------------");
					System.out.println("Gostaria de voltar pro menu principal ou finalizar o programa?");
					System.out.println("1 - VOLTAR MENU");
					System.out.println("2 - FINALIZAR");
					System.out.print("=> ");
					String res = input.next();
					String result = verificaCliente(res);
					if (result.contentEquals("1")) {
						menuPrincipal(con);
					} else {
						System.out.println("\nSistema encerrado!");
						System.exit(0);
					}

				} else {

					System.out.println("Pedido nao encontrado!");
					System.out.println("Gostaria de tentar novamente? ");
					System.out.print("=> ");

					do {

						continuar = informeOpcao("1 - SIM \n 2 - NAO");

						if (continuar != 1 || continuar != 2) {
							System.out.println("Digite uma opcao valida!");
						}

					} while (continuar != 1 || continuar != 2);
				}

			} while (continuar != 2);

			break;

		case 4:

			System.out.println("Escolha como localizar");
			System.out.println("1- ID PEDIDO");
			System.out.println("2- VOLTAR");
			System.out.print("=> ");
			String res = input.nextLine();
			String result = verificaModoLocalizacao(res);

			if (result.contentEquals("1")) {
				System.out.println("ID PEDIDO");
				System.out.print("=> ");
				int idpedido = input.nextInt();

				pedido = pedDAO.selecionarPedido(null, idpedido, cliente.getIdcliente(), con);

			} else {

				System.out.println("Data");
				System.out.print("=> ");
				String data = input.next();

			}

			int opcaoL;
			do {

				switch (Integer.parseInt(result)) {
				case 1:

					System.out.println("Informe o id do pedido: ");
					System.out.print("=> ");
					int codigo = input.nextInt();

					pedido = pedDAO.selecionarPedido(null, codigo, cliente.getIdcliente(), con);

					if (pedido == null) {
						System.out.println("Cliente n�o encontrado");
					} else {

						System.out.println("\n-------------------------------------------------------");
						System.out.println("Dados do Pedido: ");
						System.out.println("Codigo/Idpedido: " + pedido.getIdpedido());
						System.out.println("Numero: " + pedido.getNumero());
						System.out.println("Valor total: " + pedido.getValorPedido());
						System.out.println("Data Pedido: " + pedido.getDataPedido());
						System.out.println("Nome do Cliente: " + pedido.getCliente().getNome());
						System.out.println("\n-------------------------------------------------------");
						System.out.println("Dados da Empresa: ");
						System.out.println("Nome da empresa: " + pedido.getDataPedido());
						System.out.println("Cnpj da empresa: " + pedido.getDataPedido());
						System.out.println("Matriz: " + pedido.getDataPedido());
						System.out.println("\n-------------------------------------------------------");

						menuPrincipal(con);

					}

					break;
				case 2:
					menuPrincipal(con);
					break;
				default:
					System.out.println("Opcao invalida!");
					menuPrincipal(con);
					break;
				}

			} while (Integer.parseInt(result) != 3);

			break;
		}

	}

	private static void dadosAlteradosPedido(Conexao con) {

		PedidoDAO pedDAO = new PedidoDAO(con);
		Pedido pedido = new Pedido();
		PedItem pedItem = new PedItem();

		System.out.println("Pra alteraçao do pedido informe o id do pedido ");
		System.out.print("=> ");
		int alt = input.nextInt();
		System.out.println("\n");

		pedido.setIdpedido(alt);

		System.out.println("Digite a nova quantidade de produtos");
		System.out.print("=> ");
		int quantidade = input.nextInt();
		System.out.println("\n-------------------------------------------------------");

		pedItem.setQuantidade(quantidade);

		double total = pedItem.getValorUnitario() * pedItem.getQuantidade();
		pedItem.setTotal(total);

		pedDAO.alterarPedidoItem(pedido, pedItem);

		System.out.println("Quantidade  alterada com sucesso! ");
		System.out.println("-------------------------------------------------------\n");

	}

	private static void dadosinclusapedido(String resp, Conexao con, Cliente cliente) {

		Random random = new Random();
		Pedido pedido = new Pedido();
		ClienteDAO cliDAO = new ClienteDAO(con);
		PedidoDAO pedDAO = new PedidoDAO(con);
		PedItem pedItem = new PedItem();
		ProdutoDAO prodDAO = new ProdutoDAO(con);
		@SuppressWarnings("unused")
		Produto produto = null;

		pedido = pedDAO.localizarPedido();
		System.out.println("idpedido:  " + pedido.getIdpedido());

		cliente = cliDAO.localizarClienteCpf(resp);
		pedido.getCliente().setIdcliente(cliente.getIdcliente());

		pedido.setDataPedido(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));

		int numero = random.nextInt(1000) + 2;

		pedido.setNumero(numero);
		pedido.setValorPedido(numero);
		pedDAO.incluirPedido(pedido);
		pedDAO.prepararSqlInclusaoPedido();

		System.out.println("\nDigite o Codigo dos produtos que voce ira comprar ");
		System.out.println("\n-------------------------------------------------------");
		prodDAO.listarProduto();
		System.out.println("\n-------------------------------------------------------");

		String res;

		String result;
		do {
			System.out.println("Codigo do Produto:");
			System.out.print("=> ");
			int codigoProduto = input.nextInt();
			pedItem.setIdproduto(codigoProduto);
			System.out.println("Digite a quantidade:");
			System.out.print("=> ");
			int quantidadeProduto = input.nextInt();
			System.out.print("\n");

			pedido.setIdpedido(pedDAO.getLastIdPedido());
			pedItem.setQuantidade(quantidadeProduto);
			produto = prodDAO.localizarProdutoPedidoItem(null, codigoProduto);
			pedItem.setIdproduto(codigoProduto);

			double vlunitario = prodDAO.localizarProduto(null, codigoProduto).getValorUnitario();
			pedItem.setValorUnitario(vlunitario);
			double total = pedItem.getValorUnitario() * pedItem.getQuantidade();
			pedItem.setTotal(total);

			pedido.getCliente().setIdcliente(cliente.getIdcliente());
			pedido.setValorPedido(pedido.getValorPedido());
			pedido.setValorTotal(total);

			pedDAO.incluirPedido(pedido);
			pedido.adicionarProduto(pedItem);

			System.out.println("Gostaria de comprar outro produto");
			System.out.println("1 - SIM");
			System.out.println("2- NAO");
			System.out.println("=> ");
			res = input.next();
			result = verificaCliente(res);

			if (result.contentEquals("2")) {
				pedDAO.incluirPedItem(pedido);
				menuPrincipal(con);
			}

		} while (result != "2");

	}

	private static void menuCriar(Conexao con) {
		int opcao;
		do {
			System.out.println("O que você deseja criar?");
			for (OpcoesCreateDAO ctd : OpcoesCreateDAO.values()) {
				System.out.println((ctd.ordinal() + 1) + " - " + ctd);
			}
			opcao = informeOpcao("Informe uma opção: ");

			switch (opcao) {
			case 1:
				try {
					System.out.println("Digite o nome da Database que deseja criar: ");
					String new_database = input.nextLine();
					CreateDAO.criarDatabase(con, new_database);
				} catch (Exception e) {
					break;
				}
				break;

			case 2:
				try {
					DatabaseDAO.ListaDatabase(con);
					System.out.println("Digite o nome da Database em que deseja criar o SCHEMA: ");
					String database_schema = input.nextLine().toLowerCase();
					System.out.print("Digite o nome do SCHEMA: ");
					String schema = input.nextLine().toLowerCase();
					CreateDAO.criarSchema(con, schema, database_schema);
				} catch (Exception e) {
					break;
				}
				break;

			case 3:
				try {
					DatabaseDAO.ListaDatabase(con);
					System.out.println("Digite o nome da Database em que deseja criar a TABELA: ");
					String database_tabela = input.nextLine().toLowerCase();
					SchemaDAO.ListaSchema(mudarDatabase(database_tabela));
					System.out.println("Digite o nome do SCHEMA: ");
					String schema_tabela = input.nextLine().toLowerCase();
					System.out.println("Digite o nome da tabela: ");
					String tabela = input.nextLine().toLowerCase();
					CreateDAO.criarTabela(con, tabela, schema_tabela, database_tabela);
				} catch (Exception e) {
					break;
				}
				break;
			case 4:
				menuPrincipal(con);
				break;
			default:
				System.out.println("Op��o inv�lida.");
			}
		} while (opcao != 4);
	}

	private static Conexao mudarDatabase(String database) throws Exception {
		Conexao conexao = new Conexao("PostgreSql", "localhost", "5432", database, "postgres", "123456");
		conexao.conect();
		return conexao;
	}

	private static Conexao selecionarConexao() throws Exception {
		String conPadrao = "PADRÃO: jdbc:postgresql://localhost:5432/postgres/" + "postgres" + "/123456\n";
		int opcao;
		Conexao con = null;
		boolean connection = false;
		do {
			System.out.println("SELECIONE O TIPO DE CONEXÃO: ");
			System.out.println(conPadrao);
			for (OpcoesConexao opc : OpcoesConexao.values()) {
				System.out.println((opc.ordinal() + 1) + " - " + opc);
			}
			opcao = informeOpcao("Informe uma opção: ");
			switch (opcao) {
			case 1:
				try {
					con = new Conexao("PostgreSql", "localhost", "5432", "postgres", "postgres", "992329899xx");
					if (con.conect() == true) {
						connection = true;
					} else {
						continue;
					}
				} catch (Exception e) {
					break;
				}
				break;
			case 2:
				try {
					System.out.println("DIGITE O IP: (modelo: 127.0.0.1)");
					String ip = input.nextLine().toLowerCase();
					verificaIp(ip);
					System.out.println("DIGITE A PORTA: ");
					String porta = input.nextLine().toLowerCase();
					System.out.println("DIGITE A DATABASE: ");
					String database = input.nextLine().toLowerCase();
					System.out.println("DIGITE O USUÁRIO: ");
					String user = input.nextLine().toLowerCase();
					System.out.println("DIGITE A SENHA: ");
					String pass = input.nextLine().toLowerCase();
					con = new Conexao("PostgreSql", ip, porta, database, user, pass);
					
					if (con.conect() == true) {
						connection = true;
					} else {
						continue;
					}
				} catch (Exception e) {
					break;
				}
				break;
			default:
				System.out.println("OPCAO INVALIDA. TENTE NOVAMENTE!");
			}
		} while (connection != true);
		return con;
	}
	
	@SuppressWarnings("unused")
	private static String verificaIp(String ip) {
		  String opcaoValida = "([\\d]{3}\\.[\\d]{1}\\.[\\d]{1}\\.[\\d]{1})|([\\d]{3}\\.[\\d]{3}\\.[\\d]{1}\\.[\\d]{1})|([\\d]{3}\\.[\\d]{3}\\.[\\d]{1}\\.[\\d]{2})|(localhost)";
		String opcaoTest;
		boolean matches = false;
		matches = Pattern.matches(opcaoValida, ip);
		if (!matches || ip.isEmpty() || ip.isBlank()) {
			do {
				System.out.println("IP INVALIDO. TENTE NOVAMENTE!\n");
				System.out.println("=> ");
				opcaoTest = input.nextLine();
				matches = Pattern.matches(opcaoValida, opcaoTest);
			} while (!matches || opcaoTest.isEmpty() || opcaoTest.isBlank());
			return opcaoTest;
		}
		return ip;
	}

	@SuppressWarnings("unused")
	private static String verificaPorta(String porta) {
		String opcaoValida = "^[\\d]{2,10}$";
		String opcaoTest;
		boolean matches = false;
		matches = Pattern.matches(opcaoValida, porta);
		if (!matches || porta.isEmpty() || porta.isBlank()) {
			do {
				System.out.println("PORTA INVALIDA. TENTE NOVAMENTE!\n");
				System.out.print("=> ");
				opcaoTest = input.nextLine();
				matches = Pattern.matches(opcaoValida, opcaoTest);
			} while (!matches || opcaoTest.isEmpty() || opcaoTest.isBlank());
			return opcaoTest;
		}
		return porta;
	}

	private static String verificaCpf(String cpf) {
		String cpfValido = "[\\d]{11}";
		String cpfTest;
		boolean matches = false;
		matches = Pattern.matches(cpfValido, cpf);
		if (!matches) {
			do {
				System.out.println("\nCPF INVALIDO. TENTE NOVAMENTE!");
				System.out.print("=> ");
				cpfTest = input.nextLine();
				matches = Pattern.matches(cpfValido, cpfTest);
			} while (!matches);
			return cpfTest;
		}
		return cpf;
	}

	private static String verificaCep(String cep) {
		String cepValido = "[\\d]{8}";
		String cepTest;
		boolean matches = false;
		matches = Pattern.matches(cepValido, cep);
		if (!matches) {
			do {
				System.out.println("CEP INVALIDO. TENTE NOVAMENTE! ");
				System.out.print("=> ");
				cepTest = input.nextLine();
				matches = Pattern.matches(cepValido, cepTest);
			} while (!matches);
			return cepTest;
		}
		return cep;
	}

	private static String verificaTelefone(String telefone) {

		String telefoneValido = "[\\d]{11}";
		String telefoneTest;
		boolean matches = false;
		matches = Pattern.matches(telefoneValido, telefone);
		if (!matches) {
			do {
				System.out.println("TELEFONE INVALIDO. TENTE NOVAMENTE! ");
				System.out.print("=> ");
				telefoneTest = input.nextLine();
				matches = Pattern.matches(telefoneValido, telefoneTest);
			} while (!matches);
			return telefoneTest;
		}
		return telefoneValido;
	}

	private static String verificaNumeroEndereco(String numero) {
		String numeroValido = "^([\\w\\d]??){1,5}$";
		String numeroTest;
		boolean matches = false;
		matches = Pattern.matches(numeroValido, numero);
		if (!matches) {
			do {
				System.out.println("NUMERO INVALIDO. TENTE NOVAMENTE!\n");
				System.out.print("=> ");
				numeroTest = input.nextLine();
				matches = Pattern.matches(numeroValido, numeroTest);
			} while (!matches);
			return numeroTest;
		}
		return numero;
	}

	private static String verificaCliente(String opcao) {
		String opcaoValida = "^[1-2]?$";
		String opcaoTest;
		boolean matches = false;
		matches = Pattern.matches(opcaoValida, opcao);
		if (!matches || opcao.isEmpty() || opcao.isBlank()) {
			do {
				System.out.println("OPCAO INVALIDA. TENTE NOVAMENTE!\n");
				System.out.println("=> ");
				opcaoTest = input.nextLine();
				matches = Pattern.matches(opcaoValida, opcaoTest);
			} while (!matches || opcaoTest.isEmpty() || opcaoTest.isBlank());
			return opcaoTest;
		}
		return opcao;
	}

	private static String verificaModoLocalizacao(String opcao) {
		String opcaoValida = "^[1-3]?$";
		String opcaoTest;
		boolean matches = false;
		matches = Pattern.matches(opcaoValida, opcao);
		if (!matches || opcao.isEmpty() || opcao.isBlank()) {
			do {
				System.out.println("OPCAO INVALIDA. TENTE NOVAMENTE!\n");
				System.out.println("=> ");
				opcaoTest = input.nextLine();
				matches = Pattern.matches(opcaoValida, opcaoTest);
			} while (!matches || opcaoTest.isEmpty() || opcaoTest.isBlank());
			return opcaoTest;
		}
		return opcao;
	}

	private static String verificaData(String data) {
		String opcaoValida = "^([\\d]{2}\\/[\\d]{2}\\/[\\d]{4})\\s([\\d]{2}:[\\d]{2}:[\\d]{2})$";
		String opcaoTest;
		boolean matches = false;
		matches = Pattern.matches(opcaoValida, data);
		if (!matches || data.isEmpty() || data.isBlank()) {
			do {
				System.out.println("DATA INVALIDA. TENTE NOVAMENTE!\n");
				System.out.println("Modelo valido: dd/MM/yyyy HH:mm:ss");
				System.out.print("=> ");
				opcaoTest = input.nextLine();
				matches = Pattern.matches(opcaoValida, opcaoTest);
			} while (!matches || opcaoTest.isEmpty() || opcaoTest.isBlank());
			return opcaoTest;
		}
		return data;
	}

}