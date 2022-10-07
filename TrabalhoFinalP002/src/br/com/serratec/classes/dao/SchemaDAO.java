package br.com.serratec.classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.serratec.conexao.Conexao;

public class SchemaDAO {
	public static ResultSet Exists(Conexao con, String schema) {
		ResultSet entidade;
		String sql = "select * from pg_namespace where nspname = '" + schema + "'";		
		entidade = con.query(sql);
		return entidade;
	}
	
	public static void ListaSchema(Conexao con) {
		ResultSet entidade;
		String sql = "SELECT schema_name FROM information_schema.schemata";	
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
