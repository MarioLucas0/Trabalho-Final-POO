package br.com.serratec.classes;

public class Empresa {
	
	     private int idempresa;
		private String razaoSocial;
         private String cnpj;
         private int CEP;
         private String Cidade;
         
         public int getIdempresa() {
 			return idempresa;
 		}
 		public void setIdempresa(int idempresa) {
 			this.idempresa = idempresa;
 		}
         
		public String getRazaoSocial() {
			return razaoSocial;
		}
		public void setRazaoSocial(String razaoSocial) {
			this.razaoSocial = razaoSocial;
		}
		public String getCnpj() {
			return cnpj;
		}
		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
		}
		public int getCEP() {
			return CEP;
		}
		public void setCEP(int cEP) {
			CEP = cEP;
		}
		public String getCidade() {
			return Cidade;
		}
		public void setCidade(String cidade) {
			Cidade = cidade;
		}
         
         
                
}
