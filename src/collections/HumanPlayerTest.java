package collections;

/**
 * Tests the methods of the HumanPlayer class.
 */
public class HumanPlayerTest
    extends student.TestCase
{
    private HumanPlayer human;

    /**
     * Sets up a new human player before each test.
     */
    public void setUp()
    {
        human = new HumanPlayer('X');
    }

    /**
     * Tests that the human player is constructed with the correct piece.
     */
    public void testConstructor()
    {
        assertEquals('X', human.getPiece());
    }

    /**
     * Tests that the human player's piece changes
     * when starting a new game.
     */
    public void testNewGame()
    {
        human.newGame();

        assertEquals('O', human.getPiece());
    }
}