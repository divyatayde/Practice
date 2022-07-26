package dao;

import java.sql.SQLException;

public interface IBankAccountDao {
//add a method to invoke stored proc.
	String transferFunds(int srcId, int destId, double amount) throws SQLException;
}
