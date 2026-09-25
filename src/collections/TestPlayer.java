package collections;

/**
 * Tests the methods of the Player class.
 */
public class TestPlayer extends student.TestCase
{
    private Player player;

    /**
     * Sets up a new player before each test.
     */
    public void setUp()
    {
        player = new Player('X');
    }

    /**
     * Tests that the player is constructed with the correct piece.
     */
    public void testConstructor()
    {
        assertEquals('X', player.getPiece());
    }

    /**
     * Tests that a player with X switches to O
     * when starting a new game.
     */
    public void testNewGameX()
    {
        player.newGame();

        assertEquals('O', player.getPiece());
    }

    /**
     * Tests that a player with O switches to X
     * when starting a new game.
     */
    public void testNewGameO()
    {
        player = new Player('O');

        player.newGame();

        assertEquals('X', player.getPiece());
    }

    /**
     * Tests that calling newGame twice returns the player
     * to the original piece.
     */
    public void testNewGameTwice()
    {
        player.newGame();
        player.newGame();

        assertEquals('X', player.getPiece());
    }
}