package MoneyService;

public class Customer {

private final long id;
private final String Name;
private String email;
private int mobileNo;


public void setEmail(String email) {
	this.email = email;
}


public void setMobileNo(int mobileNo) {
	this.mobileNo = mobileNo;
}


public Customer(long id, String name, String email, int mobileNo) {
	super();
	this.id = id;
	Name = name;
	this.email = email;
	this.mobileNo = mobileNo;
}


public long getId() {
	return id;
}


public String getName() {
	return Name;
}


public String getEmail() {
	return email;
}


public int getMobileNo() {
	return mobileNo;
}

@Override
public String toString() {
	return String.format("Customer [id=%s, Name=%s, email=%s, mobileNo=%s]", id, Name, email, mobileNo);
}


}
