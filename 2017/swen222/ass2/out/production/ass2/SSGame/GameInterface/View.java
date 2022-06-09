package SSGame.GameInterface;

import SSGame.Game.GPiece;
import SSGame.Game.SSGame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Created on 28/08/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
class View extends JFrame implements Observer {
    private SSGame model;
    private JSplitPane game;
    private final JPanel menu;
    private final JToolBar actions;
    private GPiece[][] lastBoard;
    private final KeyController keyController;
    private BoardView boardView;

    View(){
        super("Sword and Shield ");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000,1000));
        keyController = new KeyController(null, null);
        menu = menu();
        actions = actions();
        add(menu, BorderLayout.CENTER);
        pack();
        setResizable(true);
        setVisible(true);
    }

    /**
     * creates a JPanel with the game title, info, new game and quit buttons
     * @return
     */
    private JPanel menu(){
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());
        JLabel title = new JLabel("  Sword and shield  ");
        title.setFont(new Font("Arial", Font.ITALIC,50));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 3));

        JButton quit = new JButton("Quit");
        quit.addActionListener(ev -> {
            System.exit(0); // cleanly end the program.
        });

        JButton newGame = new JButton("Begin new game");
        newGame.addActionListener(ev -> newGame());

        JButton info = new JButton("Info");
        info.addActionListener(ev -> {
            JOptionPane.showConfirmDialog(this,
                    new JLabel("author: Christopher Straight ID: 300363269"),
                    "Info", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            repaint();
        });

        buttons.add(info);
        buttons.add(newGame);
        buttons.add(quit);
        menu.add(title, BorderLayout.PAGE_START);
        menu.add(buttons, BorderLayout.CENTER);
        return menu;
    }

    /**
     * initialises a new SSGame and updates View to display it.
     */
    private void newGame(){
        remove(menu);
        if(game!=null) {
            remove(game);
            removeKeyListener(keyController);
        }
        model = new SSGame();
        lastBoard = model.getBoard().boardSave();
        model.addObserver(this);
        //System.out.println("initGame");
        game = gamePanel();
        this.setPreferredSize(new Dimension(1000,1000));
        add(game, BorderLayout.CENTER);
        add(actions, BorderLayout.PAGE_START);
        actions.setVisible(true);
        addKeyListener(keyController);
        pack();
        repaint();
    }

    /**
     * creates a series of nested JSplitPanes to contain each of the view components
     * used to display the game. returns the outermost JSplitPane.
     * @return JSplitPane
     */
    private JSplitPane gamePanel(){
        BoardView board = new BoardView(model);
        board.addMouseListener(new BMouseController(model, board));
        board.addKeyListener(new KeyController(model, board));
        keyController.reset(model, board);
        boardView = board;

        PiecesView greenPieces = new PiecesView(model, false, false);
        greenPieces.addMouseListener(new PMouseController(model, greenPieces));

        PiecesView yellowPieces = new PiecesView(model, true, false);
        yellowPieces.addMouseListener(new PMouseController(model, yellowPieces));

        PiecesView greenCemetery = new PiecesView(model, false, true);
        PiecesView yellowCemetery = new PiecesView(model, true, true);

        JSplitPane pieces = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, greenPieces, yellowPieces);
        pieces.setResizeWeight(0.5);

        JSplitPane cemetery = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, greenCemetery, yellowCemetery);
        cemetery.setResizeWeight(0.5);

        JSplitPane boardPieces = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, pieces);
        boardPieces.setResizeWeight(0.5);
        boardPieces.setDividerLocation(520);

        JSplitPane gamePanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, boardPieces, cemetery);
        gamePanel.setResizeWeight(0.5);
        gamePanel.setDividerLocation(520);
        return gamePanel;
    }

    /**
     * creates a JToolBar with the Undo, Pass and Surrender buttons
     * @return JToolBar
     */
    private JToolBar actions(){
        JToolBar actions = new JToolBar();
        JButton undo = new JButton("Undo");
        undo.addActionListener(ev -> model.undo());

        JButton pass = new JButton("Pass");
        pass.addActionListener(ev -> model.pass());

        JButton surrender = new JButton("Surrender");
        surrender.addActionListener(ev -> model.surrendered = true);
        actions.add(undo);
        actions.add(pass);
        actions.add(surrender);
        actions.setMaximumSize(new Dimension(50, 50));

        return actions;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(boardView.animationStep ==-1 && !Arrays.deepEquals(lastBoard, model.getBoard().boardSave())){
            boardView.animationStep = 50;
        }
        lastBoard = model.getBoard().boardSave();
        repaint();
        if(boardView.animationStep ==-1 && model.isGameOver()){
            model.stopPinging();
            if(model.greenDead()){
                JOptionPane.showMessageDialog(this, "Yellow Won");
            }else if(model.yellowDead()){
                JOptionPane.showMessageDialog(this, "Green Won");
            }else {
                if(model.turn){
                    JOptionPane.showMessageDialog(this, "Green Won");
                }else {
                    JOptionPane.showMessageDialog(this, "Yellow Won");
                }
            }
            remove(game);
            remove(actions);
            actions.setVisible(false);
            add(menu);
            pack();
            repaint();
        }
    }
}
