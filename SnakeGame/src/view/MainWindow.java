/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Game;
import controller.GameID;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Botond
 */
public final class MainWindow extends JFrame {

    /**
     * @param args the command line arguments
     */
    private final Game game;
    private final JLabel stats;

    public MainWindow() throws IOException {
        
        stats = new JLabel();
        game = new Game();
        initGameElements();
        initMenus();
        initFrame();

    }

    public void initGameElements() {

        add(stats, BorderLayout.PAGE_START);
        game.loadLevel(new GameID("Unknown", 1));
        addKeyListener(game);
        add(game.getScreen());
        game.getScreen().setLayout(stats);

    }

    public void initMenus() {

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        JMenu menuGameLevel = new JMenu("New Game");
        JButton gameButton = new JButton("Start Game");

        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Hall Of Fame") {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.timerStop();
                game.loadLevel(new GameID("Unknown", 1));
                new HighScoreWindow(game.getHighScores(), MainWindow.this);
            }
        });

        menuGame.add(menuGameLevel);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuGame.add(menuHighScores);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);
        createGameLevelMenuItems(menuGameLevel);

    }

    public void initFrame() {

        pack();
        setTitle("Snake");
        setSize(505, 565);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("view/snake.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    private void createGameLevelMenuItems(JMenu menu) {
        for (Integer i = 1; i <= 10; i++) {
            final int index = i;
            JMenuItem item;
            Integer level = i;
            item = new JMenuItem(new AbstractAction("Level " + i) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.timerStop();
                    game.loadLevel(new GameID("Unknown", level));
                }
            });
            menu.add(item);
        }
    }

    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
