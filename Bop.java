import java.awt.Color;
import java.awt.Font;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Bop {
    public static int seconds;
    public static String command;
    public static ArrayList<Integer> stack;
    public static ArrayList<String> execution;
    public static boolean executing;
    public static String output;
    public static Map<String, String> commandMap;
    public static Font huge;
    public static Font big;
    public static Font small;
    public static double circleRadius;
    public static String display;

    public Bop() {
        seconds = 0;
        command = "";
        stack = new ArrayList<>();
        execution = new ArrayList<>();
        executing = false;
        output = "";
        commandMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("SSSSP", "push"), 
            new AbstractMap.SimpleEntry<String, String>("SSSSS", "pop"),
            new AbstractMap.SimpleEntry<String, String>("SSSPP", "add"),
            new AbstractMap.SimpleEntry<String, String>("SPPPP", "subtract"),
            new AbstractMap.SimpleEntry<String, String>("SSPPP", "multiply"),
            new AbstractMap.SimpleEntry<String, String>("SPSPP", "divide"),
            new AbstractMap.SimpleEntry<String, String>("SPPSP", "mod"),
            new AbstractMap.SimpleEntry<String, String>("SSSPS", "not"),
            new AbstractMap.SimpleEntry<String, String>("SPPPS", "greater"),
            new AbstractMap.SimpleEntry<String, String>("SPPSS", "duplicate"),
            new AbstractMap.SimpleEntry<String, String>("SPSSS", "roll"),
            new AbstractMap.SimpleEntry<String, String>("SPSPS", "input"),
            new AbstractMap.SimpleEntry<String, String>("SPSSP", "output"),
            new AbstractMap.SimpleEntry<String, String>("SSPPS", "start while"),
            new AbstractMap.SimpleEntry<String, String>("SSPSP", "end while"),
            new AbstractMap.SimpleEntry<String, String>("SSPSS", "end programming")
        );
        circleRadius = 0.0;
        display = "";
    }
    
    public static void main(String[] args) {
        Bop m = new Bop();
        
        StdDraw.setCanvasSize(800, 800);
        StdDraw.clear(Color.BLACK);
        huge = new Font("Bahnschrift", Font.PLAIN, 60);
        big = new Font("Bahnschrift", Font.PLAIN, 40);
        small = new Font("Bahnschrift", Font.PLAIN, 20);
        StdDraw.setPenRadius(0.005);

        Timer timer = new Timer();
        timer.schedule(new Tick(), 0, 1000);
        timer.schedule(new Input(), 500, 1000);
    }

    public static void update() {
        StdDraw.enableDoubleBuffering();

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        if (!executing) {
            StdDraw.setFont(huge);
            StdDraw.text(0.5, 0.6, "" + seconds);

            StdDraw.setFont(big);
            if (execution.size() > 0) {
                if (execution.get(execution.size() - 1).contains("S") || execution.get(execution.size() - 1).contains("P")) {
                    StdDraw.text(0.5, 0.4, commandMap.get(execution.get(execution.size() - 1)));
                } else {
                    StdDraw.text(0.5, 0.4, "push " + execution.get(execution.size() - 1));
                }
            }

            StdDraw.setFont(small);
            if (command.length() == 0) {
                StdDraw.setPenColor(Color.GRAY);
            }
            StdDraw.text(0.5, 0.2, display.substring(0, Math.max(0, display.length() - 1)));
            StdDraw.setPenColor(Color.WHITE);
            
        } else {
            StdDraw.text(0.5, 0.5, output);
        }

        StdDraw.show();
    }

    static class Tick extends TimerTask {        
        public void run() {
            seconds += 1;
            Bop.update();
        }
    }

    static class Input extends TimerTask {
        private boolean pushing;

        public Input() {
            pushing = false;
        }
        
        public void run() {
            if (!executing) {
                char c = 'P';
                if (StdDraw.hasNextKeyTyped()) {
                    c = StdDraw.nextKeyTyped();
                    if (c == ' ') {
                        c = 'S';
                    } else {
                        c = 'P';
                    }
                }
                
                if (pushing && c == 'P') {
                    execution.add("" + Math.max(command.length(), 0));
                    pushing = false;
                    command = "";
                }

                if (c == 'S' && command.length() == 0) {
                    display = "";
                }
                
                if (c == 'S' || command.length() > 0) {
                    command += c;

                    if (c == 'S') {
                        display += "Space ";
                    } else {
                        display += "Pause ";
                    }
    
                    if (command.length() == 5 && pushing == false) {
                        execution.add(command);
                        if (command.equals("SSSSP")) {
                            pushing = true;
                        } else if (command.equals("SSPSS")) {
                            executing = true;
                        }
                        command = "";
                    }
    
                    Bop.update();
                }
            } else {
                execute(execution);
            }
        }

        public void execute(ArrayList<String> e) {
            while (e.size() > 0) {
                String c = e.remove(0);
                if (c.equals("SSSSP")) {
                    stack.add(Integer.parseInt(execution.remove(0)));
                } else if (c.equals("SSSSS")) {
                    if (stack.size() > 0) {
                        stack.remove(stack.size() - 1);
                    }
                } else if (c.equals("SSSPP")) {
                    if (stack.size() > 1) {
                        stack.add(stack.remove(stack.size() - 1) + stack.remove(stack.size() - 1));
                    }
                } else if (c.equals("SPPPP")) {
                    if (stack.size() > 1) {
                        stack.add(stack.remove(stack.size() - 2) - stack.remove(stack.size() - 1));
                    }
                } else if (c.equals("SSPPP")) {
                    if (stack.size() > 1) {
                        stack.add(stack.remove(stack.size() - 2) * stack.remove(stack.size() - 1));
                    }
                } else if (c.equals("SPSPP")) {
                    if (stack.size() > 1) {
                        stack.add(stack.remove(stack.size() - 2) / stack.remove(stack.size() - 1));
                    }
                } else if (c.equals("SPPSP")) {
                    if (stack.size() > 1) {
                        stack.add(stack.remove(stack.size() - 2) % stack.remove(stack.size() - 1));
                    }
                } else if (c.equals("SSSPS")) {
                    if (stack.size() > 0) {
                        int i = stack.remove(stack.size() - 1);
                        if (i == 0) {
                            stack.add(1);
                        } else {
                            stack.add(0);
                        }
                    }
                } else if (c.equals("SPPPS")) {
                    if (stack.size() > 1) {
                        int i = stack.remove(stack.size() - 2);
                        int j = stack.remove(stack.size() - 1);
                        if (i > j) {
                            stack.add(1);
                        } else {
                            stack.add(0);
                        }
                    }
                } else if (c.equals("SPPSS")) {
                    if (stack.size() > 0) {
                        stack.add(stack.get(stack.size() - 1));
                    }
                } else if (c.equals("SPSSS")) {
                    if (stack.size() > 1) {
                        roll(stack.remove(stack.size() - 2), stack.remove(stack.size() - 1));
                    }
                } else if (c.equals("SPSPS")) {
                    StdDraw.clear(Color.BLACK);
                    StdDraw.setPenColor(Color.WHITE);
                    StdDraw.text(0.5, 0.5, "Please input a number:");

                    String userInput = "";
                    char inputChar;
                    boolean inputComplete = false;

                    while (!inputComplete) {
                        if (StdDraw.hasNextKeyTyped()) {
                            inputChar = StdDraw.nextKeyTyped();
                            if (Character.isDigit(inputChar)) {
                                userInput += inputChar;
                                StdDraw.clear(Color.BLACK);
                                StdDraw.text(0.5, 0.5, "Please input a number: " + userInput);
                                StdDraw.show();
                            } else if (inputChar == '\n') {
                                inputComplete = true;
                            }
                        }
                    }
                    stack.add(Integer.parseInt(userInput));
                } else if (c.equals("SPSSP")) {
                    if (stack.size() > 0) {
                        output += stack.remove(stack.size() - 1) + "\n";
                    }
                } else if (c.equals("SSPPS")) {
                    ArrayList<String> subExecute = new ArrayList<>();
                    for (int i = 0; i < e.size(); i++) {
                        if (e.get(i).equals("SSPSP")) {
                            break;
                        } else {
                            subExecute.add(e.remove(i));
                            i--;
                        }
                    }
                    while (stack.get(stack.size() - 1) != 0) {
                        execute(new ArrayList<>(subExecute));
                    }
                }
            }
        }

        public void roll(int depth, int numRolls) {
            if (depth > 1 && numRolls > 0) {
                for (int i = 0; i < numRolls; i++) {
                    stack.add(stack.size() - depth, stack.remove(stack.size() - 1));
                }
            }
        }
    }

    static class Circle extends TimerTask {        
        public void run() {
            circleRadius += 0.005;
            StdDraw.setPenColor(Color.GREEN);
            StdDraw.filledCircle(0.5, 0.5, circleRadius);
            StdDraw.show();

            if (circleRadius > 0.05) {
                circleRadius = 0;
                Bop.update();
            }
        }
    }
}