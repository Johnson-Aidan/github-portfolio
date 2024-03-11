//Aidan Johnson 1890052

import java.security.SecureRandom;
import java.util.Scanner;

public class TheatreTest {

    static class Ticket {
        private enum Status {STUDENT, NON_STUDENT}
        private int ticketNumber;
        static Status ticketStatus;

        public Ticket(Status ticketStatus, int ticketNumber) {
            this.ticketStatus = ticketStatus; // Defaults to Non Student
            this.ticketNumber = ticketNumber;
        }

        public String toString() {return "\t\t\tTicket: [Ticket#: ]" + getTicketNumber() + "]" + " " + "[Status: " + getTicketStatus() + "]\n";}

        public void setTicketNumber(int ticketNumber) { this.ticketNumber = ticketNumber; }
        public int getTicketNumber() { return ticketNumber; }

        public void setTicketStatus(Status ticketStatus) { Ticket.ticketStatus = ticketStatus; }
        public Status getTicketStatus() { return ticketStatus; }
    }

    static class Seat {
        private int row, col = 0;
        Ticket seatTicket;
        Theatre.Zone seatZone;

        public Seat(Ticket seatTicket) { // Default Constructor
            setRow(seatTicket.getTicketNumber()/10);
            setCol(seatTicket.getTicketNumber() - (row*10));
            if (row >= 1 && row <= 5 && col >= 1 && col <= 5) {
                seatZone = seatZone.UL;
            }
            else if (row >= 1 && row <= 5 && col >= 6 && col <= 10) {
                seatZone = seatZone.UR;
            }
            else if (row >= 6 && row <= 10 && col >= 1 && col <= 5) {
                seatZone = seatZone.LL;
            }
            else if (row >= 6 && row <= 10 && col >= 6 && col <= 10) {
                seatZone= seatZone.LR;
            }
        }
        // Overloaded Constructor
        public Seat(int row, int col, Ticket seatTicket, Theatre.Zone seatZone) {
            seatTicket.setTicketNumber((row*10) + col);
            seatTicket.setTicketStatus(Ticket.Status.NON_STUDENT);
            seatTicket = new Ticket(Ticket.Status.NON_STUDENT, 0);
            seatZone = seatZone.UL;
        }

        public void setRow(int row) { this.row = row; }
        public int getRow() { return row;}

        public void setCol(int col) { this.col = col; }
        public int getCol() { return col; }

        public String toString() {
            return "\t\t\tSeat: " + "[Row: " + getRow() + "] [Column: " + getCol() + "] [Zone: " + getRow() + "]\n" + "\t\t\tTicket: " + "[Ticket#: " + seatTicket.getTicketNumber() + "] [Status: " +  seatTicket.getTicketStatus() + "]\n";
        }
    }

    static class Theatre {
        private enum Zone {UL, UR, LL, LR}
        private final Seat[][] seatingChart = new Seat[10][10];

        public Theatre(int userRandNum) {
            SecureRandom random = new SecureRandom();

            for(int i = 0; i < userRandNum; i++)
            {
                int randTicket = 1 + random.nextInt(100);
                int randStatus = 1 + random.nextInt(2);
                if(randStatus == 1){Ticket ticket = new Ticket(Ticket.Status.STUDENT, randTicket);}
                else if(randStatus == 2){Ticket ticket = new Ticket(Ticket.Status.NON_STUDENT, randTicket);}
            }

        }

        public void displayTheSeatingChart(){
            for(int i = 0; i < 10; i++)
            {
                System.out.print("\t\t\t");
                for(int j = 0; j < 10; j++)
                {
                    System.out.printf("%s%s", seatingChart[i][j], " ");
                }
                System.out.println();
            }
        }

        public void displayTheZone(Zone userChoice){
            if(userChoice == Zone.UL)
            {
                for(int i = 0; i < 5; i++)
                {
                    System.out.print("\t\t\t");
                    for(int j = 0; j < 5; j++)
                    {
                        System.out.printf("%s%s", seatingChart[i][j], " ");
                    }
                    System.out.println();
                }
            }

            else if(userChoice == Zone.UR)
            {
                for(int i = 0; i < 5; i++)
                {
                    System.out.print("\t\t\t");
                    for(int j = 6; j < 10; j++)
                    {
                        System.out.printf("%s%s", seatingChart[i][j], " ");
                    }
                    System.out.println();
                }
            }

            else if(userChoice == Zone.LL)
            {
                for(int i = 6; i < 10; i++)
                {
                    System.out.print("\t\t\t");
                    for(int j = 0; j < 5; j++)
                    {
                        System.out.printf("%s%s", seatingChart[i][j], " ");
                    }
                    System.out.println();
                }
            }

            else if(userChoice == Zone.LR)
            {
                for(int i = 6; i < 10; i++)
                {
                    System.out.print("\t\t\t");
                    for(int j = 6; j < 10; j++)
                    {
                        System.out.printf("%s%s", seatingChart[i][j], " ");
                    }
                    System.out.println();
                }
            }
        }

    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int userChoice, userChoice2, randUserNum;
        Theatre.Zone userZoneChoice;

        System.out.print("\tPlease enter the number of tickets you would like to randomly issue: ");
        randUserNum = input.nextInt();
        Theatre theatre = new Theatre(randUserNum);

        do{

            System.out.println("\tPlease choose an option: ");
            System.out.println("\t\t1. Display the seating chart of the theatre.");
            System.out.println("\t\t2. Swap two seats.");
            System.out.println("\t\t3. Swap two rows.");
            System.out.println("\t\t4. Cancel the reservation.");
            System.out.println("\t\t5. Total number of STUDENT and NON_STUDENT tickets sold?");
            System.out.println("\t\t6. Exit");

            userChoice = input.nextInt();

            switch(userChoice)
            {
                case 1: System.out.println("\t\tPlease make a choice:\n\t\t\t1.The entire chart.\n\t\t\t2.A specific zone.");
                    userChoice2 = input.nextInt();
                    if(userChoice2 == 1){theatre.displayTheSeatingChart();}
                    else if(userChoice2 == 2)
                    {
                        System.out.println("\t\t\t\tWas not able to finish.");
                        //userZoneChoice = Theatre.Zone.valueOf(input.nextLine());
                        //theatre.displayTheZone(userZoneChoice);
                    }
                    break;

                case 2: System.out.println("\t\t\tWas not able to finish.");
                    break;

                case 3: System.out.println("\t\t\tWas not able to finish.");
                    break;

                case 4: System.out.println("\t\t\tWas not able to finish.");
                    break;

                case 5: System.out.println("\t\t\tWas not able to finish.");
                    break;
            }
        }while(userChoice != 6);
    }
}
