/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovak;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.swing.JButton;

public class Paths {

    private final ArrayList<Pair<Integer, Integer>> relativePaths;
    
    public ArrayList<Pair<Integer, Integer>> getRelativePaths() {return relativePaths;}

    public Paths() {
        relativePaths = new ArrayList<>();
        relativePaths.add(0, new Pair(1, 2));
        relativePaths.add(1, new Pair(1, -2));
        relativePaths.add(2, new Pair(-1, 2));
        relativePaths.add(3, new Pair(-1, -2));
        relativePaths.add(4, new Pair(2, 1));
        relativePaths.add(5, new Pair(2, -1));
        relativePaths.add(6, new Pair(-2, 1));
        relativePaths.add(7, new Pair(-2, -1));


    }
     public final Point getButtonCord(JButton b, int dimension, ArrayList<ArrayList<JButton>> tableButtons) {
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                if (tableButtons.get(i).get(j) == b) {
                    return new Point(i, j);
                }
            }
        }

        return null;
    }
     public final Boolean isCorrectPosition(Point a, Point b, JButton button, Color c,Color first) {
        if (c.equals(first)) {
            int i = 0;
            while (i < relativePaths.size()) {
                Point temp = new Point();
                temp.setLocation(a.getX() + relativePaths.get(i).getKey(), a.getY() + relativePaths.get(i).getValue());
                i++;
                if (b.x == temp.x && b.y == temp.y && button.getBackground() != Color.BLACK && button.getBackground() != Color.WHITE) {
                    return true;
                }
            }
        } else {
            return false;
        }

        return false;

    }
      public final Color checkingWinners(ArrayList<ArrayList<JButton>> tableButtons, Color winner) {

        for (int i = 0; i < tableButtons.size(); i++) {

            if (winner != null) {
                if (winner.equals(Color.BLACK)) {
                    return Color.BLACK;
                } else {
                    return Color.WHITE;
                }
            }
            winner = perpendicular(i + 1,tableButtons);
            for (int j = 0; j < tableButtons.get(i).size() - 3; j++) {
                Color temp = fourInARowOrInAColumn(i, j,tableButtons);
                if(temp!=null)
                return temp;
            }
        }
        return null;
    }
        public final Color fourInARowOrInAColumn(int index, int index2,ArrayList<ArrayList<JButton>> tableButtons) {
        int playerBlackColumnCount = 0;
        int playerWhiteRowCount = 0;
        int playerBlackRowCount = 0;
        int playerWhiteColumnCount = 0;

        for (int q = index2; q < index2+4 ; q++) {

            if (tableButtons.get(index).get(q).getBackground().equals(Color.WHITE)) {
                playerWhiteRowCount++;
            }
            if (tableButtons.get(index).get(q).getBackground().equals(Color.BLACK)) {
                playerBlackRowCount++;
            }
            if (tableButtons.get(q).get(index).getBackground().equals(Color.WHITE)) {

                playerWhiteColumnCount++;
            }
            if (tableButtons.get(q).get(index).getBackground().equals(Color.BLACK)) {
                playerBlackColumnCount++;
            }
            if (playerWhiteRowCount == 4 || playerWhiteColumnCount == 4) {
                return Color.WHITE;
            }
            if (playerBlackRowCount == 4 || playerBlackColumnCount == 4) {
                return Color.BLACK;
            }
        }

        return null;

    }
       public final Color perpendicular(int index,ArrayList<ArrayList<JButton>> tableButtons) {

        if (index < tableButtons.size()) {
            int i = 1;
            while (i < tableButtons.size()) {

                if (tableButtons.get(index).get(i - 1).getBackground().equals(Color.BLACK) && tableButtons.get(index - 1).get(i - 1).getBackground().equals(Color.BLACK) && tableButtons.get(index).get(i).getBackground().equals(Color.BLACK) && tableButtons.get(index - 1).get(i).getBackground().equals(Color.BLACK)) {
                    return Color.BLACK;
                }

                if (tableButtons.get(index).get(i - 1).getBackground().equals(Color.WHITE) && tableButtons.get(index - 1).get(i - 1).getBackground().equals(Color.WHITE) && tableButtons.get(index).get(i).getBackground().equals(Color.WHITE) && tableButtons.get(index - 1).get(i).getBackground().equals(Color.WHITE)) {
                    return Color.WHITE;
                }
                i++;
            }
        } else {
            return null;
        }

        return null;
    }
 
}
