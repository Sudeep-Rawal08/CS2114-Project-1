package collections;

/**
 * Tests the methods of the Board class.
 */
public class BoardTestClass extends student.TestCase
{
    private Board board;

    /**
     * Sets up a new board before each test.
     */
    public void setUp()
    {
        board = new Board();
    }

    /**
     * Tests that a new board starts with X's turn
     * and is not full.
     */
    public void testConstructor()
    {
        assertEquals('X', board.currTurn);
        assertFalse(board.isFull());
    }

    /**
     * Tests whether columns are correctly identified as valid
     * or invalid.
     */
    public void testIsValid()
    {
        assertTrue(board.isValid(0));
        assertTrue(board.isValid(6));
        assertFalse(board.isValid(-1));
        assertFalse(board.isValid(7));
    }

    /**
     * Tests placing a piece in an available column.
     */
    public void testPlacePiece()
    {
        int row = board.placePiece(0, 'X');

        assertEquals(5, row);
        assertEquals('X', board.getBoard()[5][0]);
        assertEquals('O', board.currTurn);
    }

    /**
     * Tests that placing a piece in an invalid column fails.
     */
    public void testPlaceInvalidPiece()
    {
        assertEquals(-1, board.placePiece(-1, 'X'));
        assertEquals(-1, board.placePiece(7, 'X'));
    }

    /**
     * Tests removing a piece from a column.
     */
    public void testRemovePiece()
    {
        board.placePiece(0, 'X');

        assertTrue(board.removePiece(0));
        assertEquals('*', board.getBoard()[5][0]);
    }

    /**
     * Tests removing a piece from an empty column.
     */
    public void testRemoveEmptyColumn()
    {
        assertFalse(board.removePiece(0));
    }

    /**
     * Tests whether the board correctly identifies when it is full.
     */
    public void testIsFull()
    {
        assertFalse(board.isFull());

        for (int col = 0; col < 7; col++)
        {
            for (int i = 0; i < 6; i++)
            {
                board.placePiece(col, 'X');
            }
        }

        assertTrue(board.isFull());
    }

    /**
     * Tests whether a horizontal group of four pieces is detected.
     */
    public void testCheckWinHorizontal()
    {
        board.placePiece(0, 'X');
        board.placePiece(1, 'X');
        board.placePiece(2, 'X');
        int row = board.placePiece(3, 'X');

        assertTrue(board.checkWin(row, 3, 'X'));
    }

    /**
     * Tests whether a vertical group of four pieces is detected.
     */
    public void testCheckWinVertical()
    {
        int row = -1;

        for (int i = 0; i < 4; i++)
        {
            row = board.placePiece(0, 'X');
        }

        assertTrue(board.checkWin(row, 0, 'X'));
    }

    /**
     * Tests whether a diagonal group of four pieces is detected.
     */
    public void testCheckWinDiagonal()
    {
        board.getBoard()[5][0] = 'X';
        board.getBoard()[4][1] = 'X';
        board.getBoard()[3][2] = 'X';
        board.getBoard()[2][3] = 'X';

        assertTrue(board.checkWin(2, 3, 'X'));
    }

    /**
     * Tests that a board without four connected pieces
     * does not report a win.
     */
    public void testNoWin()
    {
        board.placePiece(0, 'X');

        assertFalse(board.checkWin(5, 0, 'X'));
    }

    /**
     * Tests that resetting the board clears its pieces
     * and resets the current turn to X.
     */
    public void testReset()
    {
        board.placePiece(0, 'X');
        board.placePiece(1, 'O');

        board.reset();

        assertEquals('X', board.currTurn);
        assertTrue(board.isValid(0));
        assertTrue(board.isValid(1));
    }

    /**
     * Tests the string representation of the board.
     */
    public void testToString()
    {
        String result = board.toString();

        assertTrue(result.contains("1"));
        assertTrue(result.contains("7"));
        assertTrue(result.contains("*"));
    }
}