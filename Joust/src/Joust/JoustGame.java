/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovak;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.swing.*;

public class JoustGame {

    private final JFrame frame;
    private final JPanel buttonPanel;
    private JPanel tablePanel;

    private final JButton newGameButton;
    private final JButton fourXfourButton;
    private final JButton sixXsixButton;
    private final JButton eightXeightButton;
    private final JButton current;

    private static int dimension;

 
    private ArrayList<ArrayList<JButton>> tableButtons;
    private ArrayList<Pair<Point, Color>> players;
    private final Paths paths; 
    
    private Color winner;
    private Color first;
    private Color second;
    private Point raisedHorse;
 
  

    
    public JoustGame() {
        frame = new JFrame("Lovagi torna");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        current = new JButton("Lépés");
   
        current.setBackground(Color.WHITE);
        newGameButton = new JButton("Új jatek");
        fourXfourButton = new JButton(" 4x4");
        sixXsixButton = new JButton(" 6x6");
        eightXeightButton = new JButton(" 8x8");
        dimension = 8;

        newGameButton.setPreferredSize(new Dimension(100, 50));
        newGameButton.addActionListener(new startButtonHandler());
        fourXfourButton.addActionListener(new fourXfourButtonHandler());
        sixXsixButton.addActionListener(new sixXsixButtonHandler());
        eightXeightButton.addActionListener(new eightXeightButtonHandler());

        buttonPanel.add(newGameButton);
        buttonPanel.add(fourXfourButton);
        buttonPanel.add(sixXsixButton);
        buttonPanel.add(eightXeightButton);

        JButton exitGameButton = new JButton("Kilépés");
        exitGameButton.setPreferredSize(new Dimension(100, 50));
        exitGameButton.addActionListener(new exitButtonHandler());

        buttonPanel.add(exitGameButton);
        buttonPanel.add(current);
        paths = new Paths();
        
        first = Color.WHITE;
        second = Color.BLACK;
        init();

    }

    private void init() {

        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(dimension, dimension));
        tableButtons = new ArrayList<>();
        for (int i = 0; i < dimension; ++i) {
            ArrayList<JButton> ithRow = new ArrayList<>();
            for (int j = 0; j < dimension; ++j) {
                JButton tableButton = new JButton();
                tableButton.addActionListener(new ButtonHandler());
                tableButton.setPreferredSize(new Dimension(40, 40));
                ithRow.add(tableButton);
                tablePanel.add(tableButton);

            }
            tableButtons.add(ithRow);
        }

        frame.getContentPane().add(BorderLayout.SOUTH, tablePanel);
        frame.getContentPane().add(BorderLayout.NORTH, buttonPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        startNewGame();
    }

    private void startNewGame() {

        Point fWhiteP = new Point(0, 0);
        Point sWhiteP = new Point(0, dimension - 1);
        Point fBlackP = new Point(dimension - 1, 0);
        Point sBlackP = new Point(dimension - 1, dimension - 1);

        players = new ArrayList<>();

        players.add(new Pair(fWhiteP, Color.WHITE));
        players.add(new Pair(sWhiteP, Color.WHITE));
        players.add(new Pair(fBlackP, Color.BLACK));
        players.add(3, new Pair(sBlackP, Color.BLACK));

        raisedHorse = null; //kedztben nincs felemelt lo..
        players.stream().forEach((player) -> {
            tableButtons.get(player.getKey().x).get(player.getKey().y).setBackground(player.getValue());
        });

    }


    class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            JButton clickedButton = (JButton) ae.getSource();
            Point buttonCord = paths.getButtonCord(clickedButton,dimension,tableButtons);
            if (raisedHorse == null) { //amikor nincs felemelve lo..
                raisedHorse = buttonCord; //felemeljük vele az aktuális lovat

            } else {
                for (int i = 0; i < players.size(); i++) {
                    if (raisedHorse.equals(players.get(i).getKey()) && paths.isCorrectPosition(players.get(i).getKey(), buttonCord, clickedButton, players.get(i).getValue(),first)) {
                        players.set(i, new Pair(buttonCord, players.get(i).getValue()));
                        tableButtons.get(players.get(i).getKey().x).get(players.get(i).getKey().y).setBackground(players.get(i).getValue());
                        Color temp = first;
                        first = second;
                        second = temp;
                        current.setBackground(first);
                       
                    } 
                }
                winner=paths.checkingWinners(tableButtons,winner);
                if (winner!=null) {
                    
                    if(winner==Color.BLACK){
                    JOptionPane.showMessageDialog(frame, "Fekete nyert, gratulalok");
                    startNewGame();
                    }
                    if(winner==Color.WHITE){
                    JOptionPane.showMessageDialog(frame, "Feher nyert, gratulalok");
                    startNewGame();
                    }
                    
                    frame.getContentPane().removeAll();
                    tableButtons.removeAll(players);
                     winner = null;
                    init();
                }
                 raisedHorse = null;
            }
        }

    }

    class exitButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    class startButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.getContentPane().removeAll();
            tableButtons.removeAll(players);
            winner = null;
            init();
        }

    };

    class fourXfourButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dimension = 4;
            frame.getContentPane().removeAll();
            tableButtons.removeAll(players);
            winner = null;
            init();

        }
    };

    class sixXsixButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dimension = 6;
            frame.getContentPane().removeAll();
            tableButtons.removeAll(players);
            winner = null;
            init();
        }

    };

    class eightXeightButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dimension = 8;
            frame.getContentPane().removeAll();
            tableButtons.removeAll(players);
            winner = null;
            init();
        }

    };
}
