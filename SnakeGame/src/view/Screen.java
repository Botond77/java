/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.GroundElement;
/**
 *
 * @author Botond
 */
public class Screen extends JPanel {
    
    private final Game game;
    private JLabel gameStats;

    public Screen(Game g) {

        this.game = g;
    }

    public void setLayout(JLabel gameStats) {
        this.gameStats = gameStats;

    }

    public void refresh() {
        if (game.isRunning()) {
            return;
        }

        Dimension dim = new Dimension(game.getTileSize(), game.getTileSize());
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        refreshGameStats();

        repaint();

    }

    public String getPlayerName() {

        JOptionPane p = new JOptionPane();
        String path = p.showInputDialog("Enter your name");
        p.getRootFrame().dispose();

        return path;
    }

    public void refreshGameStats() {
        int score = (game.getGroundController().getSnake().size() - 3);
        String s = "Current Score : ";
        s += score + " | Current Speed :" + game.getDelay() + " | Rocks : " + game.getGroundController().getRock().size();
        gameStats.setText(s);
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (!game.isRunning()) {
            return;
        }
        super.paintComponent(g);

        g.setColor(new Color(10, 50, 0));
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, game.getTileSize(), game.getTileSize());
        g.setColor(Color.ORANGE);
        refreshGameStats();
        
        for (GroundElement ge : game.getGroundController().getSnake()) {
            ge.draw(g);
        }

        for (GroundElement ge : game.getGroundController().getRock()) {
            ge.draw(g);
        }

        game.getGroundController().getApple().draw(g);
    }

 
}
