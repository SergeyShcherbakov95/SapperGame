import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Сергей on 30.04.2015.
 */
public class GameWindow {
    private JFrame gameWindow;
    private JPanel windowPanel;
    private JPanel menuPanel;
    private JPanel gamePanel;
    private JPanel informationPanel;
    private Bomb[][] bombsArray;

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new GameWindow().makeGUI();
                }
            });
        } catch (Exception  e){
            System.out.println("Problem - " + e);
        }
    }

    private GameWindow(){
        gameWindow = new JFrame("Sapper");
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(400, 300);
        gameWindow.setSize(700, 400);

        Image icon = new ImageIcon("C:\\Users\\Сергей\\IdeaProjects\\SapperGame\\someFiles\\icon.png").getImage();
        gameWindow.setIconImage(icon);

        //gameWindow.setLocationByPlatform(true);
        gameWindow.setVisible(true);
    }

    private void makeGUI(){
        windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(10, 10, 0, 0));
        bombsArray = new Bomb[10][10];
        int[][] indexBomb = Bomb.getIndexArray(10, 10);
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                bombsArray[i][j] = new Bomb(i, j, indexBomb[i][j]);
                bombsArray[i][j].setPreferredSize(new Dimension(30, 30));
                gamePanel.add(bombsArray[i][j]);
                bombsArray[i][j].isExist = true;
                bombsArray[i][j].addActionListener(new BombListener());
            }
        }

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 20, 0));
        JButton jButtonStart = new JButton("Start");
        JButton jButtonReplay = new JButton("Replay");
        JButton jButtonSettings = new JButton("Settings");
        JButton jButtonAbout = new JButton("About");
        jButtonStart.setPreferredSize(new Dimension(100, 30));
        jButtonReplay.setPreferredSize(new Dimension(100, 30));
        jButtonSettings.setPreferredSize(new Dimension(100, 30));
        jButtonAbout.setPreferredSize(new Dimension(100, 30));
        menuPanel.add(jButtonStart);
        menuPanel.add(jButtonReplay);
        menuPanel.add(jButtonSettings);
        menuPanel.add(jButtonAbout);

        windowPanel.add(menuPanel, BorderLayout.WEST);
        windowPanel.add(gamePanel, BorderLayout.CENTER);
        gameWindow.add(windowPanel, BorderLayout.CENTER);
    }

    public class BombListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Bomb bomb = (Bomb) e.getSource();
            bomb.setText(bomb.toString());
            JLabel jLabelElement = null;
            if(bomb.value == -1) {
                JOptionPane.showMessageDialog(gamePanel, "Вы проиграли", "Вы проиграли", JOptionPane.QUESTION_MESSAGE);
                return;
            }
            else
                jLabelElement = new JLabel(bomb.value.toString(), SwingConstants.CENTER);
            gamePanel.remove((bomb.thr * 10) + bomb.column);
            bomb.isExist = false;
            Border border = BorderFactory.createLineBorder(new Color(100,100,100));
            jLabelElement.setBorder(border);
            gamePanel.add(jLabelElement, (bomb.thr * 10) + bomb.column);
            if(bomb.value == 0)
                showOtherArea(bomb);
            int indexBombs = 0;
            for(int i = 0; i < 10; i++)
                for(int j = 0; j < 10; j++)
                    if( bombsArray[i][j].isExist == true)
                        indexBombs++;
            if(indexBombs == 10)
                JOptionPane.showMessageDialog(gamePanel, "Вы выграли", "Вы выграли", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showOtherArea(Bomb bomb){
        if(bomb.thr - 1 >= 0 ){
            if(bomb.column - 1 >= 0)
                changeBomb(bomb.thr - 1, bomb.column - 1);
            changeBomb(bomb.thr - 1, bomb.column);
            if(bomb.column + 1 < 10)
                changeBomb(bomb.thr - 1, bomb.column + 1);
        }
        if(bomb.column - 1 >= 0)
            changeBomb(bomb.thr, bomb.column - 1);
        if(bomb.column + 1 < 10)
            changeBomb(bomb.thr, bomb.column + 1);
        if(bomb.thr + 1 < 10){
            if(bomb.column - 1 >= 0)
                changeBomb(bomb.thr + 1, bomb.column - 1);
            changeBomb(bomb.thr + 1, bomb.column);
            if(bomb.column + 1 < 10)
                changeBomb(bomb.thr + 1, bomb.column + 1);
        }
    }

    public void changeBomb(int thr, int column){
        if(bombsArray[thr][column].isExist != false) {
            gamePanel.remove(thr * 10 + column);
            bombsArray[thr][column].isExist = false;
            JLabel jLabelElement = new JLabel(bombsArray[thr][column].value.toString(), SwingConstants.CENTER);
            Border border = BorderFactory.createLineBorder(new Color(100, 100, 100));
            jLabelElement.setBorder(border);
            gamePanel.add(jLabelElement, thr * 10 + column);
            if (bombsArray[thr][column].value == 0)
                showOtherArea(bombsArray[thr][column]);
        }
    }
}