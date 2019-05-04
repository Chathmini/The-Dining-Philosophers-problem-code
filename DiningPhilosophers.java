/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

// concurrent/DiningPhilosophers.java
// Deadlock can be hidden in a program
import java.util.*;
import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.*;
public class DiningPhilosophers {
  private StickHolder[] sticks;
  private Philosopher[] philosophers;
  public DiningPhilosophers(int n) {
    sticks = new StickHolder[n];
    Arrays.setAll(sticks, i -> new StickHolder());
    philosophers = new Philosopher[n];
    Arrays.setAll(philosophers, i ->
      new Philosopher(i,
        sticks[i], sticks[(i + 1) % n]));    // [1]
    // Fix by reversing stick order:
    // philosophers[1] =                     // [2]
    //   new Philosopher(0, sticks[0], sticks[1]);
    Arrays.stream(philosophers)
      .forEach(CompletableFuture::runAsync); // [3]
  }
  public static void main(String[] args) {
    // Returns right away:
    new DiningPhilosophers(5);               // [4]
    // Keeps main() from exiting:
    ScheduledExecutorService sched =
      Executors.newScheduledThreadPool(1);
    sched.schedule( () -> {
      System.out.println("Shutdown");
      sched.shutdown();
    }, 3, SECONDS);
  }
}

