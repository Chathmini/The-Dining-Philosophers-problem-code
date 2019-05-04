package diningphilosophers;

// concurrent/StickHolder.java
import java.util.concurrent.*;
public class StickHolder {
  private static class Chopstick {}
  private Chopstick stick = new Chopstick();
  private BlockingQueue<Chopstick> holder =
    new ArrayBlockingQueue<>(1);
  public StickHolder() { putDown(); }
  public void pickUp() {
    try {
      holder.take(); // Blocks if unavailable
    } catch(InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
  public void putDown() {
    try {
      holder.put(stick);
    } catch(InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}