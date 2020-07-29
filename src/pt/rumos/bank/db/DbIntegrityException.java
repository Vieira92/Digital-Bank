package pt.rumos.bank.db;

public class DbIntegrityException extends RuntimeException {

	private static final long serialVersionUID = -7429029529286951665L;

	public DbIntegrityException(String msg) {
		super(msg);
	}
}

