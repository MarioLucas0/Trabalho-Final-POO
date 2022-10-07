package br.com.serratec.classes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Produto extends Empresa {	
	
	private int idproduto;
	private Date dtfabricacao;
	private String nomeProduto;
	private double custo;
	private double valorUnitario;
	
	SimpleDateFormat sdf = new  SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public Produto(String dtfabricacao, String nomeProduto, double custo,
			double valorUnitario) {
			
		try {
			this.setDtfabricacao(sdf.parse(dtfabricacao));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.setNomeProduto(nomeProduto);
		this.setCusto(custo);
		this.setValorUnitario(valorUnitario);
	}

	public Produto() {
	}

	public int getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}

	public Date getDtfabricacao() {
		return dtfabricacao;
	}

	public void setDtfabricacao(Date dtfabricacao) {
		this.dtfabricacao = dtfabricacao;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}


}
