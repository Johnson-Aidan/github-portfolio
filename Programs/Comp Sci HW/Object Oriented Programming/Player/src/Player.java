// Student: Aidan Johnson
// ID: 1890052

import java.security.SecureRandom;

public class Player {

    private static final SecureRandom randomNumbers = new SecureRandom();

    private String name = "";
    private int rock = 0;
    private int paper = 0;
    private int scissors = 0;
    private int wins = 0;
    private int losses = 0;
    private Hand choice;
    private static int numOfGames = 0;
    private static int draws = 0;

    public enum Hand {ROCK, PAPER, SCISSORS};
    public enum Status {WON, LOST, DRAW};

    //Set&Gets

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}

    public Hand playAHand() {
        switch(randomNumbers.nextInt(3))
        {
            case 0:
                choice = Hand.ROCK;
                rock++;
                break;

            case 1:
                choice = Hand.PAPER;
                paper++;
                break;

            case 2:
                choice = Hand.SCISSORS;
                scissors++;
                break;
        }
        return choice;
    }

    public static void setNumOfGames() {numOfGames++;}
    public static int getNumOfGames() {return numOfGames;}

    public static void gameDraw() {draws++;}
    public static int getDraws() {return draws;}

    public void setWin() {wins++;}
    public int getWin() {return wins;}

    public void setLoss() {losses++;}
    public int getLoss() {return losses;}

    public int getRock() {return rock;}
    public int getScissors() {return scissors;}
    public int getPaper() {return paper;}

    public void playerGameStats(){
        System.out.printf("\n\t\t%s%s", "****** ", getName());
        System.out.printf("\n\t\t%s%s", "# of wins: ", getWin());
        System.out.printf("\n\t\t%s%s", "# of Losses: ", getLoss());
        System.out.printf("\n\t\t%s%s", "# of Rocks: ", getRock());
        System.out.printf("\n\t\t%s%s", "# of Scissors: ", getScissors());
        System.out.printf("\n\t\t%s%s\n", "# of Paper: ", getPaper());
    }
}
