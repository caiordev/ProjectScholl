package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


//classe responsável por fornecer métodos para manipulação de um banco de dados.
public class DB {
	private static Connection conn = null;  
	//conexão com o banco de dados.
	public static Connection getConnection() {
		//verifica se a conexão já foi estabalecida.
		if(conn == null) {
			try {
			Properties props = LoadProperties(); //pega as infos do bd.properties.
			String url = props.getProperty("dburl");
			conn = DriverManager.getConnection(url, props); //cria uma nova conexão.
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage()); //exceção personalizada.
			}
			
		}
		return conn;
	}
	
	//método responsável por fechar a conexão.
	public static void closeConnection() {
		if(conn != null) {
			try {
			conn.close();
			}                       //lança o erro personalizado caso ocorra um erro.
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
	//método responsável por carregar as propriedades do arquivo db.prorpeties.
	private static Properties LoadProperties() {
		//instancia para ler o arquivo.
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs); //carraega as propriedades.
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	
	//esses dois métodos são responsáveis por fechar o statement e resultset, 
	//utilizados para execução de consultas.
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			}catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
}
