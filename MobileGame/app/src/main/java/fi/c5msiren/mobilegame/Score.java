package fi.c5msiren.mobilegame;

/**
 * Created by Miika on 19.4.2017.
 */

public class Score {

    private String name;
    private int amount;

    public Score(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void addScore(int amount) {
        this.amount += amount;
    }
}
