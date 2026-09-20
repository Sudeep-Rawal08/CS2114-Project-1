package collections;

public class Board
{
    private char[][] grid;
    public static final int COLUMN_COUNT = 7;
    public static final int ROW_COUNT = 6;
    public char currTurn = 'X';
    
    public Board()
    {
        grid = new char[ROW_COUNT][COLUMN_COUNT];
        reset();
    }


    public char[][] getBoard()
    {
        return grid;
    }


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
     * Checks if the input column is valid
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
     * x
     */
    public int placePiece(int column, char piece)
    {
        if (isValid(column))
        {
            if(currTurn == 'X') {
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
                    // checks win on this piece

                    return row;
                }
            }
        }
        return -1;
    }
    public boolean removePiece(int col) {
        if(currTurn == 'X') {
            currTurn = 'O';
        }
        else {
            currTurn = 'X';
        }
        for(int row = 0; row < ROW_COUNT; row++) {
            if(grid[row][col] != '*') {
                grid[row][col] = '*';
                return true;
            }
        }
        return false;
    }

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
        // check diag
        int numLeft = 0;
        int numRight = 0;
        int displacement = 1;
        //checks botLeft
        for(int r = row+1; r < ROW_COUNT; r++) {
            if(col - displacement < 0 || grid[r][col-displacement] != piece) {
                break;
            }
            displacement++;
            numLeft++;
            
        }
        
        //Checks botRight
        displacement = 1;
        for(int r = row+1; r < ROW_COUNT; r++) {
            if(col + displacement >= COLUMN_COUNT || grid[r][col+displacement] != piece) {
                break;
            }
            displacement++;
            numRight++;
            
        }
        //Checks TopLeft
        displacement = 1;
        for(int r = row-1; r >= 0; r--) {
            if(col - displacement < 0 || grid[r][col-displacement] != piece) {
                break;
            }
            displacement++;
            numRight++;
            
        }
        //Checks TopRight
        displacement = 1;
        for(int r = row-1; r >= 0; r--) {
            if(col + displacement >= COLUMN_COUNT|| grid[r][col+displacement] != piece) {
                break;
            }
            displacement++;
            numLeft++;
            
        }
        return (numLeft >= 3 || numRight >= 3);

    }


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
