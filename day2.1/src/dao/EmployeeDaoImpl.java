package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pojos.Employee;
import static utils.DBUtils.getCn;

public class EmployeeDaoImpl implements IEmployeeDao {
	// state
	private Connection connection;
	private PreparedStatement pst1, pst2, pst3, pst4;

	public EmployeeDaoImpl() throws SQLException {
		connection = getCn();
		String sql = "select empid,name,salary,join_date from my_emp where deptid=? and join_date>? order by salary";
		pst1 = connection.prepareStatement(sql);
		pst2 = connection.prepareStatement("insert into my_emp values(default,?,?,?,?,?)");
		pst3 = connection.prepareStatement("update my_emp set salary=salary+?,deptid=? where empid=?");
		pst4 = connection.prepareStatement("delete from my_emp where empid=?");
		System.out.println("emp dao created....");
	}

	@Override
	public List<Employee> getEmpDetailsByDeptAndDate(String dept, Date joinDate) throws SQLException {
		List<Employee> emps = new ArrayList<>();
		// set IN params
		pst1.setString(1, dept);
		pst1.setDate(2, joinDate);
		try (ResultSet rst = pst1.executeQuery()) {
			// rst cursor --before 1st row
			while (rst.next())
				emps.add(new Employee(rst.getInt(1), rst.getString(2), rst.getDouble(3), rst.getDate(4)));

		}
		return emps;
	}

	@Override
	public String insertEmpDetails(Employee e) throws SQLException {
		// set IN params
		// name
		// API of PST : public void setType(int paramIndex,Type value) throws SQLExc.
		// paramIndex : 1....n from left to right !
		pst2.setString(1, e.getName());
		// adr
		pst2.setString(2, e.getAddress());
		// salry
		pst2.setDouble(3, e.getSalary());
		// dept id
		pst2.setString(4, e.getDeptId());
		// join date
		pst2.setDate(5, e.getJoinDate());
		// exec DML
		int updateCount = pst2.executeUpdate();
		if (updateCount == 1)
			return "Emp details inserted ....";
		return "Emp details insertion failed!!!!!!!!!!!!!!!";

	}

	@Override
	public String updateEmpDetails(double salIncr, String newDept, int empId) throws SQLException {
		// set IN params
		pst3.setDouble(1, salIncr); // sal incr
		pst3.setString(2, newDept);// new dept
		pst3.setInt(3, empId);// emp id
		int updateCount = pst3.executeUpdate();
		if (updateCount == 1)
			return "Emp details updated ....";
		return "Emp details updation failed!!!!!!!!!!!!!!!";

	}

	@Override
	public String deleteEmpDetails(int empId) throws SQLException {
		// set IN param : emp id
		pst4.setInt(1, empId);
		int updateCount = pst4.executeUpdate();
		if (updateCount == 1)
			return "Emp details deleted ....";
		return "Emp details deletion failed!!!!!!!!!!!!!!!";

	}

	// add clean up method for closing DB related resources
	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		if (pst3 != null)
			pst3.close();
		if (pst4 != null)
			pst4.close();
		System.out.println("emp dao cleaned up.....");

	}

}
