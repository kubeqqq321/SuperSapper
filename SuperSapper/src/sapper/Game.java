package sapper;

import javax.swing.*;

public abstract class Game extends JFrame {

    protected Parser parser;

    public Game(int width , int height , int bombs){
        parser = new Parser(width,height,bombs);
    }

    public abstract void checkPustePola(int width, int height);

}
