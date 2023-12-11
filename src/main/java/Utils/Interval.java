package Utils;

import java.util.List;

public class Interval {
    private static List<Integer> ids = new java.util.ArrayList<>();
    private static int increment = 0;
    public interface CleanUp {
        void run();
    }
    public interface Callback {
        void run(CleanUp cleanUp);
    }
    public static int setInterval(Callback runnable, int delay) {
        int id = increment++;
        ids.add(id);
        Thread thread = new Thread(() -> {

            try {
                Thread.sleep(delay);
                while (ids.contains(id)) {
                    runnable.run(() -> {
                        ids.remove(id);
                    });
                    Thread.sleep(delay);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        return id;
    }
    public static void clearInterval(int id) {  
        ids.removeIf(i -> i == id);
    }
}
