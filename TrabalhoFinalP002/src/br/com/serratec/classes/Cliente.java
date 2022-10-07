package br.com.serratec.classes;

public class Cliente {
	
	private int idcliente;
	private String nome;
	private String cpf;
	private String logradouro;
	private String numeroEndereco;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String telefone;
	
	public Cliente() {}
	
	public Cliente( String nome, String cpf, String logradouro, String numeroEndereco,
			String bairro, String cep, String cidade, String telefone) {
		
		this.nome = nome;
		this.cpf = cpf;
		this.logradouro = logradouro;
		this.numeroEndereco = numeroEndereco;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.telefone = telefone;
	}
	
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}
	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
