import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static int seconds;
    public static String command;

    public Main() {
        seconds = 0;
        command = "";
    }
    
    public static void main(String[] args) {
        Main m = new Main();
        
        StdDraw.setCanvasSize(800, 800);
        StdDraw.clear(Color.BLACK);

        Timer timer = new Timer();
        timer.schedule(new Tick(), 0, 1000);
        timer.schedule(new Input(), 500, 1000);
    }

    public static void update() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.text(0.5, 0.5, "" + seconds);
        //StdDraw.text(0.5, 0.2, command);

        StdDraw.show();
    }

    static class Tick extends TimerTask {        
        public void run() {
            seconds += 1;
            Main.update();
        }
    }

    static class Input extends TimerTask {
        public void run() {
            char c = 'P';
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (c == ' ') {
                    c = 'S';
                } else {
                    c = 'P';
                }
            }
            if (c == 'S' || command.length() > 0) {
                command += c;

                if (command.length() == 5) {
                    if (command.equals("SSSSS")) {
                        System.out.println("push");
                        command = "";
                    }
                }

                //Main.update();
            }
        }
    }
}