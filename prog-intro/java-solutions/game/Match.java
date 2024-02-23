package game;

public class Match {
    private final MatchBoard board;
    private final Player player1;
    private final Player player2;
    private int gameCount;

    public Match(MatchBoard board, Player player1, Player player2, int gameCount) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.gameCount = gameCount;
    }

    public int play(boolean log) {
        int cnt1 = 0, cnt2 = 0;
        boolean turn = false;
        while (cnt1 < gameCount && cnt2 < gameCount) {
            final int result = (
                (turn = !turn) ?
                    new TwoPlayerGame(board, player1, player2)
                :
                    new TwoPlayerGame(board, player2, player1)
            ).play(log);
            board.reset();
            if (result != 0) {
                if (turn ^ (result == 1)) {
                    ++cnt2;
                } else {
                    ++cnt1;
                }
                if (log) {
                    System.out.println();
                    System.out.println("Current scores: player1: " + cnt1 + ", player2: " + cnt2);
                }
            }
        }
        return cnt1 == gameCount ? 1 : 2;
    }
}
