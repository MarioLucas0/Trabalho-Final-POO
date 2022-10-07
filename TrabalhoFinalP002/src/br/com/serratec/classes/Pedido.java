package br.com.serratec.classes;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Pedido {
	private int idpedido;
	private int numero;
	private double valorPedido;
	private double valorTotal;
	private String dataPedido;
	private Cliente cliente = new Cliente();
	private ArrayList<PedItem> produto = new ArrayList<>();	
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public ArrayList<PedItem> getProduto() {
		return produto;
	}

	public void setProduto(ArrayList<PedItem> produto) {
		this.produto = produto;
	}

	public ArrayList<PedItem> getPedItem() {
		return this.produto;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public void imprimirItensPedido() {
		System.out.println("Produtos do pedido: " + getNumero());
		System.out.println("-----------------------------------");
		for (PedItem pi:produto) {
			System.out.println("C�digo: "+ pi.getIdproduto());
			System.out.printf("Quantidade: %,2.3f", pi.getQuantidade());
			System.out.printf("%nPre�o: R$ %,2.2f", pi.getValorUnitario());
			System.out.printf("%nTotal: R$ %,2.2f", pi.getQuantidade() * pi.getValorUnitario());	
			System.out.println("\n-----------------------------------");
		}
	}
	
	public void imprimirItensCliente() {
		System.out.println("Produtos do pedido: " + getNumero());
		System.out.println("-----------------------------------");
		for (PedItem pi:produto) {
			System.out.println("Nome do cliente: " + pi.getNomeCliente());
			System.out.println("Id pedido: " + pi.getIdpedido());
			System.out.println("C�digo: "+ pi.getIdproduto());
			System.out.printf("Quantidade: %,2.3f", pi.getQuantidade());
			System.out.printf("%nPre�o: R$ %,2.2f", pi.getValorUnitario());
			System.out.printf("%nTotal: R$ %,2.2f", pi.getQuantidade() * pi.getValorUnitario());	
			System.out.println("\n-----------------------------------");
		}
	}
	
	public void adicionarProduto(PedItem itens) {
		PedItem peditem = new PedItem();
		
		peditem.setIdpedido(itens.getIdpedido());
		peditem.setNomeCliente(itens.getNomeCliente());
		peditem.setIdproduto (itens.getIdproduto());
		peditem.setCusto(itens.getCusto());
		peditem.setValorUnitario(itens.getValorUnitario());
		peditem.setQuantidade(itens.getQuantidade());
		
		produto.add(peditem);
	}
	
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public double getValorPedido() {
		return valorPedido;
	}
	public void setValorPedido(double valorPedido) {
		this.valorPedido = valorPedido;
	}
	public String getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(String string) {
		this.dataPedido = string;
		
	}
	public String getStringDataPedido() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data = sdf.format(this.dataPedido);
		
		return data;		
	}
	
}

