package GUI.Practice;

public class Ranking {
    private String account = "";
    private int cash = 0;

    public Ranking(){}

    public Ranking(String account, int cash){
        this.account = account;
        this.cash = cash;
    }

    public String getAccount() {
        return account;
    }

    public int getCash() {
        return cash;
    }
}
