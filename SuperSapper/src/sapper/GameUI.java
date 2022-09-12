package sapper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

public class GameUI extends Game{
    private JPanel mineField;
    private JPanel infoPanel;
    private JPanel gamePanel;
    private JLabel bombToDetectLabel;
    private JLabel timeLabel;
    private JPanel buttonsPanel;
    private JButton resetButton;
    private JButton menuButton;
    private JButton settingsButton;

    private JButton [][] cells;

    private Timer timer;

    private ActionListener stopwatch;

    public GameUI(int width, int height , int bombs){

        super(width,height,bombs);
        setTitle("Super Sapper");
        setDefaultCloseOperation(3);
        gamePanel = new JPanel(new BorderLayout());
        mineField = new JPanel(new GridLayout(width,height,5,5));

        try{
            cells = new JButton[width][height];

            gamePanel.add(infoPanel,BorderLayout.PAGE_START);
            gamePanel.add(mineField,BorderLayout.CENTER);
            gamePanel.add(buttonsPanel , BorderLayout.PAGE_END);

            add(gamePanel);
        }catch (NullPointerException exception){}


        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                buildCells(i,j);
            }
        }

        stopwatch = new Clock();
        timer = new Timer(1000,stopwatch);


        bombToDetectLabel.setText("" + parser.bombsToDetect);

        pack();

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuUI menuUI = new MenuUI();
                menuUI.setVisible(true);
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GameSettingsUI settingsUI = new GameSettingsUI();
                settingsUI.setVisible(true);
            }
        });


    }

    private class Clock implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLabel.setText("" + ++parser.seconds);
        }
    }

    // tworzymy plansze here
    public void buildCells(int width, int height){
        try{
            mineField.add(cells[width][height] = new JButton());
            cells[width][height].setPreferredSize(new Dimension(40,40));
            cells[width][height].setFont(new Font("JetBrains Mono", Font.BOLD,20));
            cells[width][height].setBorder(new EmptyBorder(2,2,2,2));
            NumberColors(width, height);
        } catch(Exception exception) {}


        cells[width][height].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(SwingUtilities.isLeftMouseButton(e) && !parser.board[width][height].Flagged && parser.gameStatus == Parser.inProgress){

                    if(parser.board[width][height].value >=9 ){  //jesli tarafiamy na bombe
                        timer.stop();
                        parser.gameStatus = Parser.youLose;
                        parser.board[width][height].Discovered = true;
                        resetButton.setIcon(new ImageIcon("src/sapper/pictures/przegrana.png"));
                        cells[width][height].setBackground(Color.WHITE);

                        //pętla która odkrywa wszytskie bomby
                        for (int i = 0; i < parser.width; i++ ){
                            for (int j = 0; j < parser.height; j++){
                                if(parser.board[i][j].value >=9 && !parser.board[i][j].Flagged) {//podstawiamy zdjecie bomby gdy nie trafilismy
                                    cells[i][j].setIcon(new ImageIcon("src/sapper/pictures/bomba1.png"));
                                    cells[i][j].setBackground(Color.WHITE);
                                }
                            }
                        }
                    }
                    else{
                    timer.start();
                        if (parser.board[width][height].value == 0){
                            parser.board[width][height].Discovered = true;

                            checkPustePola(width,height);
                        }
                        else{
                            parser.board[width][height].Discovered = true;

                            cells[width][height].setText("" + parser.board[width][height].value);
                            cells[width][height].setBackground(Color.WHITE);

                        }

                        if(parser.checkWin()) {
                            timer.stop();

                            for (int i = 0; i < parser.width; i++) {
                                for (int j = 0; j < parser.height; j++) {
                                    if (!parser.board[width][height].Discovered) {//dodajemy zdjecie flagi sprawdzajac najpierw czy pole jest nieodwiedzone
                                        parser.board[width][height].Flagged = true;
                                        cells[i][j].setIcon(new ImageIcon("src/sapper/pictures/flaga.png"));
                                    }
                                }
                            }

                            bombToDetectLabel.setText(" " + 0);

                            resetButton.setIcon(new ImageIcon("src/sapper/pictures/zwyciezca.png"));


                            int position = Scoreboard.checkPosition(parser.seconds, Parser.gameMode);

                            //Dodawanie zwyciezców do Scoreboard
                            if (position < 5) {  //5 ponieważ to limit wyswietlanych miejsc w ScoreBoard
                                String name = JOptionPane.showInputDialog(gamePanel, "Gratulacje zwycięstwa! " + "\nCzas: " + parser.seconds + "\n Podaj swoją nazwę: ",
                                        "Wygrana",
                                        JOptionPane.WARNING_MESSAGE);

                                Scoreboard.addResult(new ScoreboardElements(name, parser.seconds), position, Parser.gameMode);

                                try {
                                    Scoreboard.saveData();
                                } catch (FileNotFoundException exception) {}

                                JOptionPane.showMessageDialog(gamePanel, Scoreboard.getRanking(Parser.gameMode), "Ranking", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/sapper/pictures/puchar.png"));
                            }
                            else {
                                JOptionPane.showMessageDialog(gamePanel, "Gratulacje zwycięstwa! " + "\nCzas: " + parser.seconds, "Wygrana", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                    //dodawanie i odejmowanie oflagowanych bomb na bombLabel
                } else if (SwingUtilities.isRightMouseButton(e) && parser.gameStatus == Parser.inProgress) {
                    if(!parser.board[width][height].Discovered){
                        if(parser.board[width][height].Flagged){

                            parser.board[width][height].Flagged = false;
                            cells[width][height].setIcon(new ImageIcon());
                            bombToDetectLabel.setText(" " + ++parser.bombsToDetect);
                        }
                        else{
                            parser.board[width][height].Flagged = true;
                            cells[width][height].setIcon(new ImageIcon("src/sapper/pictures/flaga.png"));
                            validate();
                            bombToDetectLabel.setText(" " + --parser.bombsToDetect);

                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e) && parser.gameStatus == Parser.inProgress){
                    resetButton.setIcon(new ImageIcon("src/sapper/pictures/zaskoczona.png"));
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e) && parser.gameStatus == Parser.inProgress){
                    resetButton.setIcon(new ImageIcon("src/sapper/pictures/ogolna.png"));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



    }


    public void checkPustePola(int width, int height) {

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                try{
                    if(!parser.board[width+i][height + j].Flagged){
                       if(parser.board[width+i][height+j].value != 0){

                           cells[width+i][height+j].setText("" + parser.board[width+i][height +j].value);
                       }
                       cells[width + i][height +j].setBackground(Color.WHITE);

                       if(parser.board[width+i][height+j].Discovered == false && parser.board[width+i][height+j].value == 0){
                           parser.board[width+i][height+j].Discovered = true;
                           checkPustePola(width + i , height + j);
                       }
                       else{
                           parser.board[width+i][height+j].Discovered = true;
                       }

                    }

                }catch (Exception exception){}
            }
        }

    }




   // kolory dla odpowiednich liczb które występują w cellsach
   // dla 1 niebieski
   // 2 zielony
   // 3 czerwony itd
    private void NumberColors(int width, int height){
        switch (parser.board[width][height].value) {
            case 1 -> cells[width][height].setForeground(new Color(0, 0, 250));
            case 2 -> cells[width][height].setForeground(new Color(0, 150, 0));
            case 3 -> cells[width][height].setForeground(new Color(195, 0, 0));
            case 4 -> cells[width][height].setForeground(new Color(0, 0, 150));
            case 5 -> cells[width][height].setForeground(new Color(99, 0, 0));
            case 6 -> cells[width][height].setForeground(new Color(0, 150, 150));
            case 7 -> cells[width][height].setForeground(new Color(1, 1, 1));
            case 8 -> cells[width][height].setForeground(new Color(100, 100, 100));
            default -> cells[width][height].setForeground(new Color(0, 0, 0));
        }
    cells[width][height].setBackground(Color.GRAY);
    cells[width][height].setText("");
    cells[width][height].setIcon(new ImageIcon());
    }


    private void reset(){

        resetButton.setIcon(new ImageIcon("src/sapper/pictures/ogolna.png"));
        parser = new Parser(parser.width,parser.height,parser.bombs);
        timer.stop();
        stopwatch = new Clock();
        timer = new Timer(1000,stopwatch);
        bombToDetectLabel.setText(" " + parser.bombsToDetect);
        timeLabel.setText("" + parser.seconds);
        for (int i = 0; i < parser.width; i++) {
            for (int j = 0; j < parser.height; j++) {
                NumberColors(i,j);
            }
        }

    }

}
