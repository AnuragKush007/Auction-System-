package sdm.engine.CutomSDMClasses;

import java.util.Date;

public abstract class User {

    private static int IDs = 1;

    private int ID;
    private String name;
    private Account account;

    public User(String name) {
        this.ID = IDs;
        IDs++;
        this.name = name;
        this.account = new Account();
    }

    public int getID() { return ID ; }
    public String getName() { return name; }
    public void setID(int id) { this.ID = id ; }
    public void setName(String name) { this.name = name; }

    public Account getAccount() {
        return account;
    }

    public void addTransaction(String type, Date date, double amount){
        this.account.addTransaction(type, date, amount);
    }

    public double getAccountBalance(){
        return this.account.getBalance();
    }

}
