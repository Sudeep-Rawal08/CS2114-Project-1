package collections;

public class AIPlayer extends Player {

    public static final int COLUMN_COUNT = 7;
    public static final int ROW_COUNT = 6;

    private int depth;

    public AIPlayer(char piece, int depth) {
        super(piece);
        this.depth = depth;
    }

    public int bestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int bestCol = 0;

        for (int col = 0; col < COLUMN_COUNT; col++) {
            if (board.isValid(col)) {
                board.placePiece(col, this.getPiece());

                int score = minimaxing(board, 1, false,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);

                if (score > bestScore) {
                    bestScore = score;
                    bestCol = col;
                }

                board.removePiece(col);
            }
        }

        return bestCol;
    }

    public int evaluation(Board board) {
        char[][] grid = board.getBoard();
        char aiPiece = this.getPiece();
        char opponentPiece = (aiPiece == 'X') ? 'O' : 'X';

        int points = 0;

        // Horizontal
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col <= COLUMN_COUNT - 4; col++) {
                points += evaluate(
                    grid[row][col],
                    grid[row][col + 1],
                    grid[row][col + 2],
                    grid[row][col + 3],
                    aiPiece,
                    opponentPiece);
            }
        }

        // Vertical
        for (int row = 0; row <= ROW_COUNT - 4; row++) {
            for (int col = 0; col < COLUMN_COUNT; col++) {
                points += evaluate(
                    grid[row][col],
                    grid[row + 1][col],
                    grid[row + 2][col],
                    grid[row + 3][col],
                    aiPiece,
                    opponentPiece);
            }
        }

        // Diagonal down-right
        for (int row = 0; row <= ROW_COUNT - 4; row++) {
            for (int col = 0; col <= COLUMN_COUNT - 4; col++) {
                points += evaluate(
                    grid[row][col],
                    grid[row + 1][col + 1],
                    grid[row + 2][col + 2],
                    grid[row + 3][col + 3],
                    aiPiece,
                    opponentPiece);
            }
        }

        // Diagonal up-right
        for (int row = 3; row < ROW_COUNT; row++) {
            for (int col = 0; col <= COLUMN_COUNT - 4; col++) {
                points += evaluate(
                    grid[row][col],
                    grid[row - 1][col + 1],
                    grid[row - 2][col + 2],
                    grid[row - 3][col + 3],
                    aiPiece,
                    opponentPiece);
            }
        }

        return points;
    }

    private int evaluate(
        char first,
        char second,
        char third,
        char fourth,
        char aiPiece,
        char opponentPiece) {

        char[] eval = { first, second, third, fourth };

        int aiCount = 0;
        int opponentCount = 0;

        for (char piece : eval) {
            if (piece == aiPiece) {
                aiCount++;
            }
            else if (piece == opponentPiece) {
                opponentCount++;
            }
        }

        if (aiCount == 4) {
            return 10000;
        }

        if (opponentCount == 4) {
            return -10000;
        }

        if (aiCount > 0 && opponentCount > 0) {
            return 0;
        }

        if (aiCount == 3) {
            return 500;
        }
        else if (aiCount == 2) {
            return 100;
        }
        else if (aiCount == 1) {
            return 10;
        }

        if (opponentCount == 3) {
            return -500;
        }
        else if (opponentCount == 2) {
            return -100;
        }
        else if (opponentCount == 1) {
            return -10;
        }

        return 0;
    }

    public int minimaxing(
        Board board,
        int currDepth,
        boolean maximizing,
        int alpha,
        int beta) {

        if (currDepth >= depth || board.isFull()) {
            return evaluation(board);
        }

        if (maximizing) {
            int bestScore = Integer.MIN_VALUE;

            for (int col = 0; col < COLUMN_COUNT; col++) {
                if (board.isValid(col)) {
                    board.placePiece(col, this.getPiece());

                    int score = minimaxing(
                        board,
                        currDepth + 1,
                        false,
                        alpha,
                        beta);

                    board.removePiece(col);

                    bestScore = Math.max(bestScore, score);
                    alpha = Math.max(alpha, bestScore);

                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            return bestScore;
        }

        int bestScore = Integer.MAX_VALUE;
        char opponent = this.getPiece() == 'X' ? 'O' : 'X';

        for (int col = 0; col < COLUMN_COUNT; col++) {
            if (board.isValid(col)) {
                board.placePiece(col, opponent);

                int score = minimaxing(
                    board,
                    currDepth + 1,
                    true,
                    alpha,
                    beta);

                board.removePiece(col);

                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, bestScore);

                if (beta <= alpha) {
                    break;
                }
            }
        }

        return bestScore;
    }
}