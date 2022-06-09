package model;

import javafx.geometry.BoundingBox;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

public class GrassLand extends Observable {
  public static Random r = new Random();
  public final float maxSize = 100;
  private Wheat w = new Wheat(50, 50);
  private Monster m = new Monster(20, 20);
  private List<Pig> pigs = new ArrayList<>();
  { pigs.add(new Pig(0, r.nextInt((int) maxSize))); }

  public GrassLand(){
    new Timer(50, (e)-> ping()).start();
  }

  public List<Pig> getPigs() { return new ArrayList<>(pigs); }

  public Wheat getWheat() { return w; }

  public Monster getMonster() { return m; }

  public void moveWheat(int x, int y) {
    if (x > 1 || x < -1 || y > 1 || y < -1)
      throw new Error("Invalid Command");
    w.x =inMargin(w.x+x * w.speed);
    w.y =inMargin(w.y+y * w.speed);
    //ping();
  }

  public boolean gameOver(){
    for (Pig p : pigs) {
      if(inWheat(p.bounds())){
        return true;
      }
    }
    return false;
  }

  private float inMargin(float val) {
    return Math.max(0, Math.min(val,maxSize));
  }

  public void ping() {
    if (r.nextInt(10) == 0) {
      Pig p = new Pig(0, r.nextInt((int) maxSize));
      if(!inPig(p)) {
        pigs.add(p);
      }
    }
    for (Pig p : pigs) {
      float x = p.x;
      float y = p.y;
      p.ping();
      if(inPig(p)){
        p.x = x;
        p.y = y;
      }
    }
    m.ping();
    inMonster();
    setChanged();
    notifyObservers();
  }

  private boolean inPig(Pig pig){
    for (Pig p : pigs){
      if(!p.equals(pig) && p.bounds().intersects(pig.bounds())){
        return true;
      }
    }
    return false;
  }

  private boolean inWheat(BoundingBox bounds){
    return w.bounds().intersects(bounds);
  }

  private void inMonster(){
    Iterator<Pig> i = pigs.iterator();
    while(i.hasNext()){
      Pig p = i.next();
      if(m.bounds().intersects(p.bounds())){
        i.remove();
      }
    }
  }

  class Item {//inner class: know about GrassLand
    public Item(float x, float y) { this.x = x; this.y = y; }
    float x; public float getX() { return x; }//no setter
    float y; public float getY() { return y; }
  }

  public class Pig extends Item {
    public Pig(float x, float y) { super(x, y); }
    public final float speed = 0.5f;
    public void ping() {
      if (x > w.x) { x -= speed; }
      if (x < w.x) { x += speed; }
      if (y > w.y) { y -= speed; }
      if (y < w.y) { y += speed; }
    }
    public BoundingBox bounds(){return new BoundingBox(x, y, 10, 10);}
  }

  public class Wheat extends Item {
    public Wheat(float x, float y) { super(x, y); }
    public final float speed = 2;
    public BoundingBox bounds(){return new BoundingBox(x, y, 10, 10);}
  }

  public class Monster extends Item {
    public Monster(float x, float y) { super(x, y); }
    public final float speed = 0.5f;
    public void ping() {
      int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
      if(randomNum==1){
        y -= speed;
      }else if(randomNum==2){
        y += speed;
      }else if(randomNum==3){
        x -= speed;
      }else if(randomNum==4){
        x += speed;
      }
    }
    public BoundingBox bounds(){return new BoundingBox(x, y, 10, 10);}
  }
}