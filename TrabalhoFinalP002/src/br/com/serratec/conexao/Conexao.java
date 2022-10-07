package br.com.serratec.conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	private String local;
	private String user;
	private String senha;
	private Connection c;
	private Statement statment;
	private Statement stm_database;
	private String str_conexao;
	private String driverjdbc;
	
//	public Conexao(String str) throws SQLException {
//		try {
//			Class.forName("org.postgresql.Driver");
//			setC(DriverManager.getConnection(str));
//			if(getC() != null) {
//				System.out.println("Connection OK");
//			} else {
//				System.out.println(getC());
//				System.out.println("Connection Failed!");
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	public Conexao(String bd, String local, String porta,
			String banco, String user, String senha) {
		if (bd.equals("PostgreSql")){
			setStr_conexao("jdbc:postgresql://"+ local +":" + porta +"/"+ banco);
  			setLocal(local);
  			setSenha(senha);
  			setUser(user);
  			setDriverjdbc("org.postgresql.Driver");
		}
	}
	
	public void configUser(String user, String senha) {
		setUser(user);
		setSenha(senha);
	}
	
	public void configLocal(String banco) {
		setLocal(banco);
	}
	
	public boolean conect() {
		System.out.println("Conectando...");
		try {
			Class.forName(getDriverjdbc());
			setC(DriverManager.getConnection(getStr_conexao(), getUser(), getSenha()));
			//setStatment(getC().createStatement());
			setStatment(getC().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE));
			System.out.println("Conexão estabelecida!");
			return true;
		}catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println("Falha de conexão. Tente novamente!");
			return false;
		}

	}
	
	public void disconect(){
		try {
			getC().close();
		}catch (SQLException ex) {
			System.err.println(ex);
			ex.printStackTrace();
		}
	}
	
	public ResultSet query(String query){
		try {
			return getStatment().executeQuery(query);
		}catch (SQLException ex) {
			if (!ex.getLocalizedMessage().contentEquals("Nenhum resultado foi retornado pela consulta.")) {
				//System.out.println(ex.getMessage());
				//System.out.println(ex.getErrorCode());
				ex.printStackTrace();
			}
			return null;
		}
	}
	
	public int queryDatabase(String query) {
		try {
			stm_database = c.createStatement();
			return getStm_database().executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Didn't work");
		}
		return 0;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Connection getC() {
		return c;
	}

	public void setC(Connection c) {
		this.c = c;
	}

	public Statement getStatment() {
		return statment;
	}

	public void setStatment(Statement statment) {
		this.statment = statment;
	}

	public String getStr_conexao() {
		return str_conexao;
	}

	public void setStr_conexao(String str_conexao) {
		this.str_conexao = str_conexao;
	}

	public String getDriverjdbc() {
		return driverjdbc;
	}

	public void setDriverjdbc(String driverjdbc) {
		this.driverjdbc = driverjdbc;
	}

	public Statement getStm_database() {
		return stm_database;
	}

	public void setStm_database(Statement stm_database) {
		this.stm_database = stm_database;
	}
}
