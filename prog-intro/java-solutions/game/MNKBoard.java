package game;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements MatchBoard, Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );

    private final Cell[][] field;
    private Cell turn;
    private final int m;
    private final int n;
    private final int k;
    private int emptyCells;

    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        field = new Cell[m][n];
        reset();
    }

    public void reset() {
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        emptyCells = m*n;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public int getHeight() {
        return m;
    }

    @Override
    public int getWidth() {
        return n;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        emptyCells--;

        if (checkWin(move.getRow(), move.getCol())) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        return emptyCells == 0;
    }

    private boolean checkWin(int r, int c) {
        return (
            toOther(r, c, 0, 1) + toOther(r, c, 0, -1) - 1 >= k ||
            toOther(r, c, 1, 0) + toOther(r, c, -1, 0) - 1 >= k ||
            toOther(r, c, 1, 1) + toOther(r, c, -1, -1) - 1 >= k ||
            toOther(r, c, 1, -1) + toOther(r, c, -1, 1) - 1 >= k
        );
    }

    private boolean checkRange(int r, int c) {
        return 0 <= r && r < m && 0 <= c && c < n;
    }

    private int toOther(int r, int c, int diffRow, int diffCol) {
        Cell type = field[r][c];
        int len = 0;
        while (checkRange(r, c) && field[r][c] == type) {
            r += diffRow;
            c += diffCol;
            len++;
        }
        return len;
    }

    public boolean isValid(final Move move) {
        return turn == move.getValue() &&
               checkRange(move.getRow(), move.getCol()) &&
               field[move.getRow()][move.getCol()] == Cell.E;
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("   ");
        for (int i = 1; i <= m; ++i) {
            sb.append(String.format("%3s", i + " "));
        }
        sb.append(System.lineSeparator());
        for (int r = 0; r < n; r++) {
            sb.append(String.format("%3s", (r + 1) + " "));
            for (Cell cell : field[r]) {
                sb.append(String.format("%3s", CELL_TO_STRING.get(cell) + " "));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
