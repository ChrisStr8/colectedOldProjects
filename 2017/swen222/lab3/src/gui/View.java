package gui;
import java.awt.*; import java.util.*; import javax.swing.*;
import model.GrassLand; import model.GrassLand.Pig;
import resources.ImgResources;

public class View extends JComponent implements Observer {
  private static final long serialVersionUID = 1L;
  GrassLand myModel;

  public View(GrassLand m) {
    myModel = m;
    myModel.addObserver(this);
    this.addKeyListener(new Controller(m));
    this.setFocusable(true);
    JFrame f = new JFrame("Wheat run, use (w,a,s,d) to play");
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.add(this);
    f.pack();
    f.setVisible(true);
  }

  public void paintComponent(Graphics _g) {
    super.paintComponent(_g);
    Graphics2D g = (Graphics2D) _g;
    int minD = Math.min(getHeight(), getWidth());
    float d = minD / 20f;
    float step = minD / myModel.maxSize;
    g.setColor(Color.GREEN.darker());
    g.fillRect(0, 0, minD, minD);
    drawWheat(g, d, step);
    drawPigs(g, d, step);
    drawMonster(g, d, step);
  }

  private void drawPigs(Graphics2D g, float d, float step) {
    for (Pig p : myModel.getPigs()) {
      float px = p.getX() * step;
      float py = p.getY() * step;
      g.drawImage(ImgResources.PIG.img, (int) (px - d), (int) (py - d),
          (int) (d * 2), (int) (d * 2), null);
      g.setColor(Color.black);
      g.drawRect((int) (px - d), (int) (py - d),
              (int) (d * 2), (int) (d * 2));
    }
  }

  private void drawWheat(Graphics2D g, float d, float step) {
    float wx = myModel.getWheat().getX() * step;
    float wy = myModel.getWheat().getY() * step;
    g.drawImage(ImgResources.WHEAT.img, (int) (wx - d), (int) (wy - d),
        (int) (d * 2), (int) (d * 2), null);
  }

  private void drawMonster(Graphics2D g, float d, float step) {
    float mx = myModel.getMonster().getX() * step;
    float my = myModel.getMonster().getY() * step;
    g.drawImage(ImgResources.MONSTER.img, (int) (mx - d), (int) (my - d),
            (int) (d * 2), (int) (d * 2), null);
  }

  public Dimension getPreferredSize() {return new Dimension(600, 600);}

  public void update(Observable arg0, Object arg1) {
    if(myModel.gameOver()){
      int r = JOptionPane.showConfirmDialog(this, new JLabel("game over"),
              "game over!", JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.WARNING_MESSAGE);
    }
    repaint();
  }
}