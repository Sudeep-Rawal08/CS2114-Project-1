package collections;

import java.util.Scanner;

public class HumanPlayer
    extends Player
{
    public HumanPlayer(char piece)
    {
        super(piece);
    }


    public int getMove()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println(
            "HI, WHICH COLUMN WOULD YOU LIKE TO PLACE YOUR PIECE IN: "
                + this.getPiece());
        int c = sc.nextInt();

        return c;

    }

}
