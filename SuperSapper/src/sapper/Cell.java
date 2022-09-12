package sapper;

public class Cell {

    //1 - 8 sasiedzi
    // 9 >= Bomba
    protected short value;
    protected boolean Flagged; // jeżeli na polu znajduje sie flaga
    protected boolean Discovered;   // czy pole jest nacisniete czy nie

    public Cell(){
        value = 0;
        Flagged = false;
        Discovered = false;
    }
}
