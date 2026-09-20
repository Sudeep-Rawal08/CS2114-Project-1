package collections;

public class AIPlayer
    extends Player
{
    public static final int COLUMN_COUNT = 7;
    public static final int ROW_COUNT = 6;
    private int depth;

    public AIPlayer(char piece, int depth)
    {
        super(piece);
        this.depth = depth;
    }


    public int bestMove(Board board)
    {
        int bestScore = Integer.MIN_VALUE;
        int bestCol = 0;
        for (int col = 0; col < COLUMN_COUNT; col++)
        {
            if(board.isValid(col)) {
                int row = board.placePiece(col, this.getPiece());
                int score = minimaxing(
                    board,
                    1,
                    false,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE, col, row);
                if(score > bestScore) {
                    bestScore = score;
                    bestCol = col;
                    
                }
                board.removePiece(col);
            }
            
            
        }
        return bestCol;
    }


    public int evaluation(Board board, int col, int row)
    {
        char[][] grid = board.getBoard();
        if (board.checkWin(row, col, grid[row][col]))
        {
            if (grid[row][col] != this.getPiece())
            {
                return -10000;
            }
            else
            {
                return 10000;
            }
        }
        char piece = grid[row][col];
        int points = 0;

        // Accumulate points for column
        int inACol = 0;
        for (int r = row; r < ROW_COUNT; r++)
        {
            if (grid[r][col] == piece)
            {
                inACol++;
            }
            else
            {
                break;
            }
        }
        // Accumulate points for row
        int inARow = 0;
        for (int c = col; c >= 0; c--)
        {
            if (grid[row][c] == piece)
            {
                inARow++;
            }
            else
            {
                break;
            }
        }
        for (int c = col + 1; c < COLUMN_COUNT; c++)
        {
            if (grid[row][c] == piece)
            {
                inARow++;
            }
            else
            {
                break;
            }
        }
        // Accumulate points for diagonal
        int leftDiagonal = 0;
        int rightDiagonal = 0;
        int displacement = 1;
        // checks botLeft
        for (int r = row + 1; r < ROW_COUNT; r++)
        {
            if (col - displacement < 0 || grid[r][col - displacement] != piece)
            {
                break;
            }
            displacement++;
            leftDiagonal++;

        }

        // Checks botRight
        displacement = 1;
        for (int r = row + 1; r < ROW_COUNT; r++)
        {
            if (col + displacement >= COLUMN_COUNT
                || grid[r][col + displacement] != piece)
            {
                break;
            }
            displacement++;
            rightDiagonal++;

        }
        // Checks TopLeft
        displacement = 1;
        for (int r = row - 1; r >= 0; r--)
        {
            if (col - displacement < 0 || grid[r][col - displacement] != piece)
            {
                break;
            }
            displacement++;
            rightDiagonal++;

        }
        // Checks TopRight
        displacement = 1;
        for (int r = row - 1; r >= 0; r--)
        {
            if (col + displacement >= COLUMN_COUNT
                || grid[r][col + displacement] != piece)
            {
                break;
            }
            displacement++;
            leftDiagonal++;

        }
        // Add up the points, If the player is winning then the evaluator
        // dislikes it otherwise the evaluator is happy.
        if (piece == this.getPiece())
        {
            switch (inACol)
            {
                case 3:
                    points += 500;
                    break;
                case 2:
                    points += 100;
                    break;
                default:
                    points += 10;
                    break;
            }
            switch (inARow)
            {
                case 3:
                    points += 500;
                    break;
                case 2:
                    points += 100;
                    break;
                default:
                    points += 10;
                    break;
            }
            switch (leftDiagonal)
            {
                case 3:
                    points += 500;
                    break;
                case 2:
                    points += 100;
                    break;
                default:
                    points += 10;
                    break;
            }
            switch (rightDiagonal)
            {
                case 3:
                    points += 500;
                    break;
                case 2:
                    points += 100;
                    break;
                default:
                    points += 10;
                    break;
            }
        }
        else
        {
            switch (inACol)
            {
                case 3:
                    points -= 500;
                    break;
                case 2:
                    points -= 100;
                    break;
                default:
                    points -= 10;
                    break;
            }
            switch (inARow)
            {
                case 3:
                    points -= 500;
                    break;
                case 2:
                    points -= 100;
                    break;
                default:
                    points -= 10;
                    break;
            }
            switch (leftDiagonal)
            {
                case 3:
                    points -= 500;
                    break;
                case 2:
                    points -= 100;
                    break;
                default:
                    points -= 10;
                    break;
            }
            switch (rightDiagonal)
            {
                case 3:
                    points -= 500;
                    break;
                case 2:
                    points -= 100;
                    break;
                default:
                    points -= 10;
                    break;
            }
        }

        return points;
    }


    public int minimaxing(
        Board board,
        int currDepth,
        boolean maximizing,
        int alpha,
        int beta, int lastCol, int lastRow)
    {
        if(currDepth >= depth || board.isFull()) {
            return evaluation(board, lastCol, lastRow);
        }
        if(maximizing) {
            int bestScore = Integer.MIN_VALUE;
            for(int col = 0; col < COLUMN_COUNT; col++) {
                if(board.isValid(col)) {
                    int row = board.placePiece(col, this.getPiece());
                    
                    int score = minimaxing(board, currDepth+1, false, alpha, beta, col, row);
                    
                    board.removePiece(col);
                    bestScore = Math.max(score, bestScore);
                    alpha = Math.max(alpha, bestScore);
                    if(beta <= alpha) {
                        break;
                    }
                    
                    
                }
            }
            return bestScore;
            
        }
        int bestScore = Integer.MAX_VALUE;
        char opponent = this.getPiece() == 'X' ? 'O': 'X'; 
        for(int col = 0; col < COLUMN_COUNT; col++) {
            if(board.isValid(col)) {
                int row = board.placePiece(col, opponent);
                
                int score = minimaxing(board, currDepth+1, true, alpha, beta, col, row);
                
                board.removePiece(col);
                bestScore = Math.min(score, bestScore);
                alpha = Math.max(alpha, bestScore);
                if(beta <= alpha) {
                    break;
                }
                
                
            }
        }
        return bestScore;
    }


}
