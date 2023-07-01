package db;

//classe personalizada para representar exceções relacionadas as erros no acesso ao banco de dados e facilitar o tratamento dessas exceções.
public class DbException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DbException(String msg) {
		super(msg);
	}

}
