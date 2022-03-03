/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import db.HighScore;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class HighScoreTableModel extends AbstractTableModel{
    private final ArrayList<HighScore> highScores;
    private final String[] colName = new String[]{ "Name", "Difficult", "Eaten foods" };
    
    public HighScoreTableModel(ArrayList<HighScore> highScores){
        this.highScores = highScores;
    }

    @Override
    public int getRowCount() { return highScores.size(); }

    @Override
    public int getColumnCount() { return 3; }

    @Override
    public Object getValueAt(int r, int c) {
       
        HighScore h = highScores.get(r);
        if      (c == 0) return h.name;
        else if (c == 1) return h.level;
        return (h.steps == 0) ? "N/A" : h.steps;
    }

    @Override
    public String getColumnName(int i) { return colName[i]; }    
    
}
