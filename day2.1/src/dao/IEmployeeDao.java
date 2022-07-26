package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import pojos.Employee;

public interface IEmployeeDao {
	/*
	 * Display emp details(id name salary joindate) from a specific department ,
	 * joined after specific date , sorted details as per salary asc
	 */
	List<Employee> getEmpDetailsByDeptAndDate(String dept, Date joinDate) throws SQLException;
	//add a method declaration to insert new emp details
	String insertEmpDetails(Employee e) throws SQLException;
	//add a method to update emp details
	String updateEmpDetails(double salIncr,String newDept,int empId) throws SQLException;
	//add a method to delete emp details
	String deleteEmpDetails(int empId) throws SQLException;
}
