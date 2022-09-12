package sapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUI extends JFrame{
    private JPanel menuPanel;
    private JLabel logo;
    private JButton playButton;
    private JButton scoreButton;
    private JButton infoButton;
    private JButton exitButton;

    public MenuUI() {
        add(menuPanel);
        setTitle("SuperSapper");
        pack();
        setDefaultCloseOperation(3);

        try {
            Scoreboard scoreboard = new Scoreboard();
        }catch (Exception exception) {

            try {
                Scoreboard.saveData();
                Scoreboard scoreboard = new Scoreboard();
            }catch(Exception exception1){}
        }



        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new GameSettingsUI().setVisible(true);
               dispose();
            }
        });

        scoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScoreboardUI().setVisible(true);
            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuPanel,"Super Sapper by Jakub Marciniak" , "Informacje", 1);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }



}
