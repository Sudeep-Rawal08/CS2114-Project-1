package collections;

/**
 * Tests the methods of the AIPlayer class.
 */
public class AITestClass
    extends student.TestCase
{
    private AIPlayer ai;
    private Board board;

    /**
     * Sets up a new AI player and board before each test.
     */
    public void setUp()
    {
        ai = new AIPlayer('O', 4);
        board = new Board();
    }

    /**
     * Tests that the AI player is constructed with the correct piece.
     */
    public void testConstructor()
    {
        assertEquals('O', ai.getPiece());
    }

    /**
     * Tests that the AI selects a valid column for its move.
     */
    public void testBestMove()
    {
        int move = ai.bestMove(board);

        assertTrue(move >= 0);
        assertTrue(move < 7);
    }

    /**
     * Tests that an empty board has an evaluation of zero.
     */
    public void testEvaluationEmptyBoard()
    {
        assertEquals(0, ai.evaluation(board));
    }

    /**
     * Tests that a board containing AI pieces receives
     * a positive evaluation.
     */
    public void testEvaluationAIPieces()
    {
        board.placePiece(0, 'O');
        board.placePiece(1, 'O');
        board.placePiece(2, 'O');

        assertTrue(ai.evaluation(board) > 0);
    }

    /**
     * Tests that a board containing opponent pieces receives
     * a negative evaluation.
     */
    public void testEvaluationOpponentPieces()
    {
        board.placePiece(0, 'X');
        board.placePiece(1, 'X');
        board.placePiece(2, 'X');

        assertTrue(ai.evaluation(board) < 0);
    }

    /**
     * Tests that minimaxing returns a score within the
     * valid evaluation range.
     */
    public void testMinimaxing()
    {
        int score = ai.minimaxing(
            board,
            1,
            true,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE);

        assertTrue(score >= -10000);
        assertTrue(score <= 10000);
    }
}