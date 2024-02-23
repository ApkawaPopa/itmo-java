package expression;

public class Main {
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("You need to enter modification type and values in command line arguments!");
            return;
        }
        try {
            if (args[0].equals("Triple")) {
                if (args.length < 4) {
                    System.out.println("There are not enough values for Triple!");
                    return;
                }
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                int z = Integer.parseInt(args[3]);
            } else if (args[0].equals("Base")) {
                if (args.length < 2) {
                    System.out.println("There are not enough values for Base!");
                    return;
                }
                int value = Integer.parseInt(args[1]);
                System.out.println(
                    new Add(
                        new Subtract(
                            new Multiply(
                                new Variable("x"),
                                new Variable("x")
                            ),
                            new Multiply(
                                new Const(2),
                                new Variable("x")
                            )
                        ),
                        new Const(1)
                    ).evaluate(value)
                );
            } else {
                System.out.println("Invalid modification");
            }
        } catch (NumberFormatException e) {
            System.out.println("Some command line arguments aren't valid integers!");
        }
    }
}
