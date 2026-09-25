package collections;

/**
 * Represents an AI player in a Connect Four game.
 * The AI uses minimax with alpha-beta pruning to select moves.
 */
public class AIPlayer extends Player
{
    /**
     * The number of columns on the board.
     */
    public static final int COLUMN_COUNT = 7;

    /**
     * The number of rows on the board.
     */
    public static final int ROW_COUNT = 6;

    private int depth;

    /**
     * Constructs an AI player with the specified piece and search depth.
     *
     * @param piece the piece assigned to the AI
     * @param depth the number of moves the AI searches ahead
     */
    public AIPlayer(char piece, int depth)
    {
        super(piece);
        this.depth = depth;
    }

    /**
     * Sets the search depth used by the AI.
     *
     * @param depth the new search depth
     */
    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    /**
     * Finds the best column for the AI to play.
     * Each valid move is evaluated using minimax with alpha-beta pruning.
     *
     * @param board the current game board
     * @return the column containing the best move
     */
    public int bestMove(Board board)
    {
        int bestScore = Integer.MIN_VALUE;
        int bestCol = 0;

        for (int col = 0; col < COLUMN_COUNT; col++)
        {
            if (board.isValid(col))
            {
                board.placePiece(col, this.getPiece());

                int score = minimaxing(
                    board,
                    1,
                    false,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);

                if (score > bestScore)
                {
                    bestScore = score;
                    bestCol = col;
                }

                board.removePiece(col);
            }
        }

        return bestCol;
    }

    /**
     * Evaluates the current board from the AI's perspective.
     * Horizontal, vertical, and diagonal groups of four are evaluated.
     *
     * @param board the board being evaluated
     * @return a score representing how favorable the board is for the AI
     */
    public int evaluation(Board board)
    {
        char[][] grid = board.getBoard();
        char aiPiece = this.getPiece();
        char opponentPiece = (aiPiece == 'X') ? 'O' : 'X';
        int points = 0;

        // Horizontal
        for (int row = 0; row < ROW_COUNT; row++)
        {
            for (int col = 0; col <= COLUMN_COUNT - 4; col++)
            {
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
        for (int row = 0; row <= ROW_COUNT - 4; row++)
        {
            for (int col = 0; col < COLUMN_COUNT; col++)
            {
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
        for (int row = 0; row <= ROW_COUNT - 4; row++)
        {
            for (int col = 0; col <= COLUMN_COUNT - 4; col++)
            {
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
        for (int row = 3; row < ROW_COUNT; row++)
        {
            for (int col = 0; col <= COLUMN_COUNT - 4; col++)
            {
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

    /**
     * Evaluates a group of four board positions.
     * Positive scores favor the AI and negative scores favor the opponent.
     *
     * @param first the first position
     * @param second the second position
     * @param third the third position
     * @param fourth the fourth position
     * @param aiPiece the AI's piece
     * @param opponentPiece the opponent's piece
     * @return the score for the group of four positions
     */
    private int evaluate(
        char first,
        char second,
        char third,
        char fourth,
        char aiPiece,
        char opponentPiece)
    {
        char[] eval = { first, second, third, fourth };
        int aiCount = 0;
        int opponentCount = 0;

        for (char piece : eval)
        {
            if (piece == aiPiece)
            {
                aiCount++;
            }
            else if (piece == opponentPiece)
            {
                opponentCount++;
            }
        }

        if (aiCount == 4)
        {
            return 10000;
        }

        if (opponentCount == 4)
        {
            return -10000;
        }

        if (aiCount > 0 && opponentCount > 0)
        {
            return 0;
        }

        if (aiCount == 3)
        {
            return 500;
        }
        else if (aiCount == 2)
        {
            return 100;
        }
        else if (aiCount == 1)
        {
            return 10;
        }

        if (opponentCount == 3)
        {
            return -500;
        }
        else if (opponentCount == 2)
        {
            return -100;
        }
        else if (opponentCount == 1)
        {
            return -10;
        }

        return 0;
    }

    /**
     * Uses minimax with alpha-beta pruning to evaluate possible moves.
     *
     * @param board the current game board
     * @param currDepth the current search depth
     * @param maximizing whether the current move is maximizing
     * @param alpha the best score currently available to the maximizing player
     * @param beta the best score currently available to the minimizing player
     * @return the evaluated score of the board position
     */
    public int minimaxing(
        Board board,
        int currDepth,
        boolean maximizing,
        int alpha,
        int beta)
    {
        if (currDepth >= depth || board.isFull())
        {
            return evaluation(board);
        }

        if (maximizing)
        {
            int bestScore = Integer.MIN_VALUE;

            for (int col = 0; col < COLUMN_COUNT; col++)
            {
                if (board.isValid(col))
                {
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

                    if (beta <= alpha)
                    {
                        break;
                    }
                }
            }

            return bestScore;
        }

        int bestScore = Integer.MAX_VALUE;
        char opponent = this.getPiece() == 'X' ? 'O' : 'X';

        for (int col = 0; col < COLUMN_COUNT; col++)
        {
            if (board.isValid(col))
            {
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

                if (beta <= alpha)
                {
                    break;
                }
            }
        }

        return bestScore;
    }
}