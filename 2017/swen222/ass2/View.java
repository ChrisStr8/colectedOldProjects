package SSGame.GameInterface;

import SSGame.Game.SSGame;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created on 28/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class View extends JComponent implements Observer {
    private static final long serialVersionUID = 1L;
    private SSGame model;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel menu;
    private JToolBar actions;

    public View(SSGame m){
        model = new SSGame();
        model.addObserver(this);
        this.addKeyListener(new Controller(m));
        this.setFocusable(true);
        frame = new JFrame("Swords and Shields");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new JPanel();

        initMenu();
        initActions();

        mainPanel.add(this);
        mainPanel.add(menu);

        frame.add(mainPanel);
        initGame();
        frame.pack();
        frame.setVisible(true);
    }

    private void initMenu(){
        menu = new JPanel();
        JTextArea title = new JTextArea("Sword and shield");
        title.setEditable(false);
        title.setFont(new Font("Arial", Font.ITALIC,26));

        JButton quit = new JButton("Quit");
        quit.addActionListener(ev -> {
            System.exit(0); // cleanly end the program.
        });

        JButton newGame = new JButton("Begin new game");
        newGame.addActionListener(ev -> initGame());

        JButton info = new JButton("Info");
        info.addActionListener(ev -> {
            JOptionPane.showConfirmDialog(this,
                    new JLabel("author: Christopher Straight ID: 300363269"),
                    "Info", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            repaint();
        });
        menu.add(title);
        menu.add(info);
        menu.add(newGame);
        menu.add(quit);
    }

    private void initGame(){
        model = new SSGame();
        model.addObserver(this);
        gamePanel = new JPanel();
        Dimension minimumSize = new Dimension(100, 50);

        JPanel board = new BoardComponent(model);
        JPanel greenPieces = new PiecesComponent(model, false);
        JPanel yellowPieces = new PiecesComponent(model, true);
        JPanel greenCemetery = new CemeteryComponent(model, false);
        JPanel yellowCemetery = new CemeteryComponent(model, true);

        JSplitPane pieces = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, greenPieces, yellowPieces);
        JSplitPane cemetery = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, greenCemetery, yellowCemetery);

        JSplitPane boardPieces = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, pieces);

        JSplitPane boardCemetery = new JSplitPane(JSplitPane.VERTICAL_SPLIT, boardPieces, cemetery);
        boardCemetery.setMinimumSize(minimumSize);

        gamePanel.add(actions);
        gamePanel.add(boardCemetery);
        frame.add(gamePanel);
        frame.repaint();
    }

    private void initActions(){
        actions = new JToolBar();
        JButton undo = new JButton("Undo");
        undo.addActionListener(ev -> {
            System.exit(0); // cleanly end the program.
        });

        JButton pass = new JButton("Pass");
        pass.addActionListener(ev -> {
            System.exit(0); // cleanly end the program.
        });

        JButton surrender = new JButton("Surrender");
        surrender.addActionListener(ev -> {
            System.exit(0); // cleanly end the program.
        });
        actions.add(undo);
        actions.add(pass);
        actions.add(surrender);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(model.isGameOver()){
            int r = JOptionPane.showConfirmDialog(this, new JLabel("game over"),
                    "game over!", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
        }
        repaint();
    }
}
