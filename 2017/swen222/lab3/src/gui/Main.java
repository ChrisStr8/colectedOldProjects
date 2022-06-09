package gui;

import javax.swing.SwingUtilities;
import model.GrassLand;
import resources.ImgResources;

public class Main {
  public static void main(String[] s) {
    ImgResources.values();// ImgResource class loading
    SwingUtilities.invokeLater(()->new View(new GrassLand()));
  }
}
