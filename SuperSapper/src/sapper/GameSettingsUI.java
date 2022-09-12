package sapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsUI extends JFrame{
    private JPanel difficultyPanel;
    private JRadioButton beginnerRadioButton;
    private JRadioButton expertRadioButton;
    private JRadioButton customRadioButton;
    private JRadioButton intermediateRadioButton;
    private JTextField heightField;
    private JTextField widthField;
    private JTextField minesField;
    private JButton startGameButton;
    private JButton menuButton;


    public GameSettingsUI(){
        add(difficultyPanel);
        setTitle("Super Sapper");
        pack();
        setDefaultCloseOperation(3);

    beginnerRadioButton.setSelected(true);


    //ustawienia planszy według wikipedii
    startGameButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (beginnerRadioButton.isSelected()) {
                new GameUI(9,9,10).setVisible(true);
                dispose();
                Parser.gameMode = 0;
            }
            if (intermediateRadioButton.isSelected()){
                new GameUI(16,16,40).setVisible(true);
                dispose();
                Parser.gameMode = 1;
            }
            if (expertRadioButton.isSelected()){
                new GameUI(16,30, 99).setVisible(true);
                dispose();
                Parser.gameMode = 2;
            }
            if (customRadioButton.isSelected()){
                try{

                    int widthInt = Integer.parseInt(widthField.getText());
                    int heightInt = Integer.parseInt(heightField.getText());
                    int mineFieldInt = Integer.parseInt(minesField.getText());

                    if(Integer.parseInt(widthField.getText()) * Integer.parseInt(heightField.getText()) / 2 < Integer.parseInt(minesField.getText())){
                        JOptionPane.showMessageDialog(difficultyPanel, "Zbyt dużo bomb");
                    }
                    else if(widthInt >= 8 && widthInt <= 30 && heightInt >= 8 && heightInt <= 30 && mineFieldInt >=10 && mineFieldInt <= 668 ){
                        new GameUI(widthInt , heightInt, mineFieldInt);
                        dispose();
                        Parser.gameMode = 3;
                    }
                    else{
                        JOptionPane.showMessageDialog(difficultyPanel,"W grze własnej:" +
                                "\nszerokość 8 - 30 " +
                                "\nwysokość 8 - 24" +
                                "\nbomby 10 - 668");
                    }

                }catch(NumberFormatException exception){
                    JOptionPane.showMessageDialog(difficultyPanel,"Pole zawiera niewłaściwy znak, bądź jest puste");
                }
            }



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


    }

}
