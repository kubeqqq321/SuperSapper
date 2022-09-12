package sapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreboardUI extends JFrame {
    private JPanel scoreboardPanel;
    private JTextArea rankingLabel;
    private JButton beginnerButton;
    private JButton intermediateButton;
    private JButton expertButton;
    private JButton backButton;

    public ScoreboardUI() {
        add(scoreboardPanel);
        setTitle("Supper Sapper - Tabela wyników");
        pack();
        setDefaultCloseOperation(2);


        rankingLabel.setText(Scoreboard.getRanking(0));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        beginnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankingLabel.setText(Scoreboard.getRanking(0));
            }
        });

        intermediateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankingLabel.setText(Scoreboard.getRanking(1));
            }
        });

        expertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankingLabel.setText(Scoreboard.getRanking(2));
            }
        });


    }

}

