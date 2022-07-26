package pojos;

public class BankAccount {
//id | name | type | bal
	private int acctNo;
	private String customerName;
	private String acctType;
	private double balance;
	public BankAccount() {
		// TODO Auto-generated constructor stub
	}
	public int getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(int acctNo) {
		this.acctNo = acctNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "BankAccount [acctNo=" + acctNo + ", customerName=" + customerName + ", acctType=" + acctType
				+ ", balance=" + balance + "]";
	}
	
}
