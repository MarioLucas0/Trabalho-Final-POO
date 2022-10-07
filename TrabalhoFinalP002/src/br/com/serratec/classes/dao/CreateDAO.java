package br.com.serratec.classes.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.serratec.conexao.Conexao;

public class CreateDAO {
	
	// Conecta no database criado
	private static Conexao conectar(String bd) {
		Conexao conexao = new Conexao("PostgreSql", "localhost", "5432", bd, "postgres", "123456");
		conexao.conect();
		return conexao;
	}
	
	private static void desconectar(Conexao con) {
		con.disconect();
	}
	
	public static boolean criarDatabase(Conexao con, String bd) {		
		boolean bdExiste;
		int tentativas = 1;
		String sql;

		do {
			try {
				bdExiste = DatabaseDAO.Exists(con, bd).next();
				if (!bdExiste) {
					sql = "create database "+ bd;
					con.queryDatabase(sql);
					tentativas++;
				}
			} catch (Exception e) {
				System.err.printf("Não foi possível criar o database %s: %s", bd, e);
				e.printStackTrace();
				return false;
			}
		} while (!bdExiste && (tentativas<=3));
		
		System.out.println(bdExiste);
		return bdExiste;
	}
	
	public static boolean criarSchema(Conexao con, String schema, String bd) {
		con = conectar(bd);
		boolean schemaExiste;
		int tentativas = 1;
		String sql;
				
		do {
			try {
				schemaExiste = SchemaDAO.Exists(con, schema).next(); 
				
				if (!schemaExiste) {
					sql = "create schema "+ schema;		
					con.query(sql);
					tentativas++;
				}
			} catch (Exception e) {
				System.err.printf("Não foi possível criar o schema %s: %s", schema, e);
				e.printStackTrace();
				return false;
			}
		} while (!schemaExiste && (tentativas<=3));
		return schemaExiste;
	}
	
	public static void criarTabela(Conexao con, String entidade, String schema, String bd) {
		con = conectar(bd);
		String sql = "create table " + schema + "." + entidade + " ()";		
		con.query(sql);		
	}

	public static boolean databaseExists(Conexao con, String bd) {
		ResultSet entidade;
		boolean dbExists = false;
		
		String sql = "select datname from pg_database where datname = '" + bd + "'";		
		entidade = con.query(sql);
		
		try {
			dbExists = entidade.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dbExists;
	}
}
	