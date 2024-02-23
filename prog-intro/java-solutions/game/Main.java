package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int result = new Match(
                new MNKBoard(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])),
                new RandomPlayer(),
                new HumanPlayer(new Scanner(System.in)),
                Integer.parseInt(args[3])
        ).play(true);
        switch (result) {
            case 1:
                System.out.println("First player won");
                break;
            case 2:
                System.out.println("Second player won");
                break;
            case 0:
                System.out.println("Draw");
                break;
            default:
                throw new AssertionError("Unknown result " + result);
        }
    }
}
