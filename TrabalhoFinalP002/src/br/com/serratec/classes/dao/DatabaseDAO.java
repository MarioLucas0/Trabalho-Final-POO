package br.com.serratec.classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.serratec.conexao.Conexao;

public class DatabaseDAO {
	public static ResultSet Exists(Conexao con, String bd) {
		ResultSet entidade;
		String sql = "select datname from pg_database where datname = '" + bd + "'";		
		entidade = con.query(sql);
		return entidade;
	}
	
	public static void ListaDatabase(Conexao con) {
		ResultSet entidade;
		String sql = "select datname from pg_database where datistemplate = false";		
		entidade = con.query(sql);
		int counter = 1;
		try {
			while(entidade.next()) {
				System.out.println(counter + " - " + entidade.getString(1));
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
