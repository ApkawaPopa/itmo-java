package game;

import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());
        while (true) {
            String row = in.next(), col = in.next();
            try {
                int r = Integer.parseInt(row);
                int c = Integer.parseInt(col);
                Move move = new Move(r - 1, c - 1, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
                System.out.println("Cell that you want to use isn't empty or doesn't exist");
            } catch (NumberFormatException e) {
                System.err.println("One of coordinates is not a valid number");
            }
            System.out.println("Please, try again");
        }
    }
}
