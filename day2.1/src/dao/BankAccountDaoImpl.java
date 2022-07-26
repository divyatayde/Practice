package dao;

import java.sql.*;
import static utils.DBUtils.getCn;;

public class BankAccountDaoImpl implements IBankAccountDao {
	//state
	private Connection cn;
	private CallableStatement cst1;
	
	//def ctor
	public BankAccountDaoImpl() throws SQLException{
		//get FIXED connection from DB Utils
		cn=getCn();
		cst1=cn.prepareCall("{call transfer_funds(?,?,?,?,?)}");
		//register OUT param
		//API of CST : public void registerOutParamter(int paramIndex,int sqlType)
		cst1.registerOutParameter(4, Types.DOUBLE);//Inform JVM about JDBC data type of the OUT param
		cst1.registerOutParameter(5, Types.DOUBLE);//Inform JVM about JDBC data type of the OUT param

		System.out.println("acct dao created....");
		
	}
	

	@Override
	public String transferFunds(int srcId, int destId, double amount) throws SQLException {
		// set IN params  : using inherited API from PST
		cst1.setInt(1, srcId);
		cst1.setInt(2, destId);
		cst1.setDouble(3, amount);
		//exec the proc
		cst1.execute();//ret type is unused  here.
		
		return "Updated Src Balance "+cst1.getDouble(4)+" Dest balance "+cst1.getDouble(5);
	}
	//clean up
	public void cleanUp() throws SQLException
	{
		if(cst1 != null)
			cst1.close();
		System.out.println("bank acct dao cleaned up...");
	}

}
