package db;


//classe que lança uma exceção personalizada relacionada a violação de integridade, quando ocorre algum erros ou restrição de integridade.
public class DbIntegrityException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DbIntegrityException(String msg) {
		super(msg);
	}

}
