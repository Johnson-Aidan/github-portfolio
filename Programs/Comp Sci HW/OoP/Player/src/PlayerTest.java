// Student: Aidan Johnson
// ID: 1890052

import java.util.Scanner;

public class PlayerTest {

    public static void whoWins(Player.Hand p1Choice, Player.Hand p2Choice, Player p1, Player p2){
        if(p1Choice == p2Choice) {
            System.out.printf("\n\t\t%s%s", "The game is a ", Player.Status.DRAW);
            Player.gameDraw();
        }
        else if((p1Choice == Player.Hand.ROCK && p2Choice == Player.Hand.SCISSORS) || (p1Choice == Player.Hand.SCISSORS && p2Choice == Player.Hand.PAPER) || (p1Choice == Player.Hand.PAPER && p2Choice == Player.Hand.ROCK)) {
            System.out.printf("\n\t\t%s %s%s", p1.getName() , Player.Status.WON, " the game!");
            p1.setWin();
            p2.setLoss();
        }
        else {
            System.out.printf("\n\t\t%s %s%s", p2.getName() , Player.Status.WON, " the game!");
            p2.setWin();
            p1.setLoss();
        }
    }

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        Player player1 = new Player();
        Player player2 = new Player();

        int userChoice;
        Player.Hand player1Choice;
        Player.Hand player2Choice;
        //Player 1 Name
        System.out.print("Please input Player 1's name:");
        player1.setName(input.nextLine());
        //Player 2 Name
        System.out.print("Please input Player 2's name:");
        player2.setName(input.nextLine());

        do{
            //Menu
            System.out.print("\n\tPlease select an option:");
            System.out.print("\n\t\t1. Play the game");
            System.out.print("\n\t\t2. Exit");
            System.out.print("\n\t\tOption:\t");
            userChoice = input.nextInt();

            if(userChoice == 1){
                Player.setNumOfGames(); //Incrementing number of games played by 1
                player1Choice = player1.playAHand();
                player2Choice = player2.playAHand();
                System.out.printf("\n\t\t%s%s%s", player1.getName(), " plays ", player1Choice);
                System.out.printf("\n\t\t%s%s%s", player2.getName(), " plays ", player2Choice);

                whoWins(player1Choice, player2Choice, player1, player2);
            }
            if(userChoice == 2){
                System.out.print("\t********************* Game Stats *********************");
                System.out.printf("\n\t\t%s%s", "Total rounds of games played: ", Player.getNumOfGames());
                System.out.printf("\n\t\t%s%s", "Total number of draws: ", Player.getDraws());
                player1.playerGameStats();
                player2.playerGameStats();
            }
        }while(userChoice != 2);
    }
}
