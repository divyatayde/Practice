package tester;

import java.util.Scanner;

import dao.BankAccountDaoImpl;

import static utils.DBUtils.*;

public class TestStoredProcedure {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			// open cn
			openConnection();
			// create bank acct dao
			BankAccountDaoImpl acctDao = new BankAccountDaoImpl();
			boolean exit = false;
			while (!exit) {
				System.out.println("Options 1. Transfer Funds 10. Exit");
				try {
					switch (sc.nextInt()) {
					case 1: System.out.println("Enter src a/c id , dest a/c id n amount to transfer");
						System.out.println(acctDao.transferFunds(sc.nextInt(),sc.nextInt(),sc.nextDouble()));
						break;
					case 10:
						exit = true;
						acctDao.cleanUp(); // closing CST
						closeConnection(); // closing db cn
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					sc.nextLine();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
