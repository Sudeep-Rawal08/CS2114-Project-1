package collections;
public class Player
{
    private char piece;
    
    public Player(char piece) {
        this.piece = piece;
    }
    
    public char getPiece() {
        return piece;
    }
    public void newGame() {
        if(piece == 'O') {
            piece = 'X';
        }
        else {
            piece = 'O';
        }
    }

}
