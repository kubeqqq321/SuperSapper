package sapper;

public class ScoreboardElements {

    protected String name = " ";
    protected int gainedTime = -1;

    public ScoreboardElements(String name , int time){
        this.name = name;
        this.gainedTime = time;
    }

    @Override
    public String toString() {

        return name + "\tczas: " + gainedTime + " sec";
    }
}
