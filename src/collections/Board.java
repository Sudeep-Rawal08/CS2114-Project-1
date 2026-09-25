package collections;

/**
 * Represents a Connect Four game board.
 * The board contains 6 rows and 7 columns and stores
 * the pieces placed by each player.
 *
 * @version 2026-09-25
 */
public class Board
{
    private char[][] grid;

    /**
     * The number of columns on the board.
     */
    public static final int COLUMN_COUNT = 7;

    /**
     * The number of rows on the board.
     */
    public static final int ROW_COUNT = 6;

    /**
     * The piece whose turn it currently is.
     */
    public char currTurn = 'X';

    /**
     * Constructs a new empty Connect Four board.
     */
    public Board()
    {
        grid = new char[ROW_COUNT][COLUMN_COUNT];
        reset();
    }

    /**
     * Gets the board's grid.
     *
     * @return the two-dimensional array representing the board
     */
    public char[][] getBoard()
    {
        return grid;
    }

    /**
     * Creates a string representation of the board.
     *
     * @return the board represented as a string
     */
    public String toString()
    {
        String res = " 1  2  3  4  5  6  7\n";
        for (int row = 0; row < ROW_COUNT; row++)
        {
            String curr = "[";
            for (int col = 0; col < COLUMN_COUNT - 1; col++)
            {
                curr += grid[row][col] + ", ";
            }
            curr += grid[row][COLUMN_COUNT - 1] + "]\n";
            res += curr;
        }
        return res;
    }

    /**
     * Checks if the input column is valid and has space for a piece.
     *
     * @param col the column to check
     * @return true if the column is valid and has an empty space
     */
    public boolean isValid(int col)
    {
        if (col < 0 || col >= 7)
        {
            return false;
        }
        return grid[0][col] == '*';
    }

    /**
     * Places a piece in the specified column.
     * The piece is placed in the lowest available row.
     *
     * @param column the column where the piece should be placed
     * @param piece the piece being placed
     * @return the row where the piece was placed, or -1 if the move is invalid
     */
    public int placePiece(int column, char piece)
    {
        if (isValid(column))
        {
            if (currTurn == 'X') {
                currTurn = 'O';
            }
            else {
                currTurn = 'X';
            }

            for (int row = ROW_COUNT - 1; row >= 0; row--)
            {
                if (grid[row][column] == '*')
                {
                    grid[row][column] = piece;
                    return row;
                }
            }
        }
        return -1;
    }

    /**
     * Removes the topmost piece from the specified column.
     *
     * @param col the column from which to remove a piece
     * @return true if a piece was removed, false if the column was empty
     */
    public boolean removePiece(int col)
    {
        if (currTurn == 'X') {
            currTurn = 'O';
        }
        else {
            currTurn = 'X';
        }

        for (int row = 0; row < ROW_COUNT; row++)
        {
            if (grid[row][col] != '*')
            {
                grid[row][col] = '*';
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the board is completely full.
     *
     * @return true if every column is full, false otherwise
     */
    public boolean isFull()
    {
        for (int col = 0; col < COLUMN_COUNT; col++)
        {
            if (grid[0][col] == '*')
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether placing a piece at the specified position
     * results in four pieces in a row.
     *
     * @param row the row containing the newly placed piece
     * @param col the column containing the newly placed piece
     * @param piece the piece to check for a winning combination
     * @return true if the piece has four connected pieces, false otherwise
     */
    public boolean checkWin(int row, int col, char piece)
    {
        // check row
        int adjacent = 0;
        for (int c = col - 1; c >= 0; c--)
        {
            if (grid[row][c] != piece)
            {
                break;
            }
            else
            {
                adjacent++;
            }
        }

        for (int c = col + 1; c < COLUMN_COUNT; c++)
        {
            if (grid[row][c] != piece)
            {
                break;
            }
            else
            {
                adjacent++;
            }
        }

        if (adjacent >= 3)
        {
            return true;
        }

        // check col
        int below = 0;
        for (int r = row + 1; r < ROW_COUNT; r++)
        {
            if (grid[r][col] != piece)
            {
                break;
            }
            else
            {
                below++;
            }
        }

        if (below >= 3)
        {
            return true;
        }

        // check diagonal
        int numLeft = 0;
        int numRight = 0;
        int displacement = 1;

        // checks bottom-left
        for (int r = row + 1; r < ROW_COUNT; r++)
        {
            if (col - displacement < 0
                || grid[r][col - displacement] != piece)
            {
                break;
            }

            displacement++;
            numLeft++;
        }

        // checks bottom-right
        displacement = 1;
        for (int r = row + 1; r < ROW_COUNT; r++)
        {
            if (col + displacement >= COLUMN_COUNT
                || grid[r][col + displacement] != piece)
            {
                break;
            }

            displacement++;
            numRight++;
        }

        // checks top-left
        displacement = 1;
        for (int r = row - 1; r >= 0; r--)
        {
            if (col - displacement < 0
                || grid[r][col - displacement] != piece)
            {
                break;
            }

            displacement++;
            numRight++;
        }

        // checks top-right
        displacement = 1;
        for (int r = row - 1; r >= 0; r--)
        {
            if (col + displacement >= COLUMN_COUNT
                || grid[r][col + displacement] != piece)
            {
                break;
            }

            displacement++;
            numLeft++;
        }

        return (numLeft >= 3 || numRight >= 3);
    }

    /**
     * Resets the board to an empty state and sets the current
     * turn back to player X.
     */
    public void reset()
    {
        currTurn = 'X';

        for (int row = 0; row < ROW_COUNT; row++)
        {
            for (int col = 0; col < COLUMN_COUNT; col++)
            {
                grid[row][col] = '*';
            }
        }
    }
}