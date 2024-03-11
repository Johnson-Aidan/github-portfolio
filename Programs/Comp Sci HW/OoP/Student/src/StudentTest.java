// Student: Aidan Johnson
// ID: 1890052

import java.util.Scanner;

public class StudentTest
{
    public static int calMax2(int stu1, int stu2) {
        int highest = 0;
        if (stu1 > stu2) {highest = 1;}
        if (stu2 > stu1) {highest = 2;}
        if (stu1 == stu2) {highest = 3;}
        return highest;
    }

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        Student student1 = new Student();
        Student student2 = new Student();
        int userChoice;
        String dummy;

        //Student 1

        System.out.print("Please input Student 1's Full Name:");
        student1.setName(input.nextLine());

        System.out.print("Please input Student 1's test 1 grade:");
        student1.setTest1(input.nextInt());

        System.out.print("Please input Student 1's test 2 grade:");
        student1.setTest2(input.nextInt());

        System.out.print("Please input Student 1's test 3 grade:");
        student1.setTest3(input.nextInt());

        dummy = input.nextLine(); //Dummy to remove the line break.

        //Student 2

        System.out.print("\nPlease input Student 2's Full Name:");
        student2.setName(input.nextLine());

        System.out.print("Please input Student 2's test 1 grade:");
        student2.setTest1(input.nextInt());

        System.out.print("Please input Student 2's test 2 grade:");
        student2.setTest2(input.nextInt());

        System.out.print("Please input Student 2's test 3 grade:");
        student2.setTest3(input.nextInt());

        dummy = input.nextLine(); //Dummy to remove the line break.

        // Report
        //Student 1
        System.out.printf("\n\t%s%s", student1.getName(), "'s Report:");
        System.out.printf("\n\t\t%s%s", "The Average Test Score is: ", student1.calAverage());
        System.out.printf("\n\t\t%s%s", "The Highest Test Score is: ", student1.calHighertst3());

        //Student 2
        System.out.printf("\n\n\t%s%s", student2.getName(), "'s Report:");
        System.out.printf("\n\t\t%s%s", "The Average Test Score is: ", student2.calAverage());
        System.out.printf("\n\t\t%s%s", "The Highest Test Score is: ", student2.calHighertst3());


        //Highest out of all 3 Comparison
        if(student1.calHighertst3() > student2.calHighertst3()){
            System.out.printf("\n\n\t%s%s%s",student1.getName(), " had the highest score out of them and ", student2.getName());
        }
        if(student2.calHighertst3() > student1.calHighertst3()){
            System.out.printf("\n\n\t%s%s%s",student2.getName(), " had the highest score out of them and ", student1.getName());
        }
        if(student1.calHighertst3() == student2.calHighertst3()){
            System.out.printf("\n\n\t%s%s%s%s",student1.getName(), " and ", student2.getName(), " had the same highest score.");
        }

        //Assistantship Report
        System.out.println("\n\n\tList of Students Eligable for Assistantship:");
        if(student1.assistantship() == true){ System.out.printf("\t\t%s\n", student1.getName());}
        if(student2.assistantship() == true){ System.out.printf("\t\t%s", student2.getName());}





            do{
            System.out.println("\n\n\tPlease choose the assignment you would like to know the name of which student made the highest score on.\n\tChoose option 4 to exit:");
            System.out.println("\t\t1. Test 1");
            System.out.println("\t\t2. Test 2");
            System.out.println("\t\t3. Test 3");
            System.out.println("\t\t4. Exit");

            userChoice = input.nextInt();

            switch(userChoice)
            {
                case 1:
                    switch (calMax2((student1.getTest1()), student2.getTest1())) {
                        case 1: System.out.printf("%s%s%d", student1.getName(), " had the higher test 1 score with: ", student1.getTest1());
                        case 2: System.out.printf("%s%s%d", student2.getName(), " had the higher test 1 score with: ", student2.getTest1());
                        case 3: System.out.printf("%s%s%s%s%d", student1.getName(), " and ", student2.getName(), " had gotten the same highest score for Test 1 with: ", student1.getTest1());
                    }
                    break;
                case 2:
                    switch (calMax2((student1.getTest2()), student2.getTest2())) {
                        case 1: System.out.printf("%s%s%d", student1.getName(), " had the higher test 2 score with: ", student1.getTest2());
                        case 2: System.out.printf("%s%s%d", student2.getName(), " had the higher test 2 score with: ", student2.getTest2());
                        case 3: System.out.printf("%s%s%s%s%d", student1.getName(), " and ", student2.getName(), " had gotten the same highest score for Test 2 with: ", student1.getTest3());
                    }
                    break;
                case 3:
                    switch (calMax2((student1.getTest3()), student2.getTest3())) {
                        case 1: System.out.printf("%s%s%d", student1.getName(), " had the higher test 3 score with: ", student1.getTest3());
                        case 2: System.out.printf("%s%s%d", student2.getName(), " had the higher test 3 score with: ", student2.getTest3());
                        case 3: System.out.printf("%s%s%s%s%d", student1.getName(), " and ", student2.getName(), " had gotten the same highest score for Test 3 with: ", student1.getTest3());
                    }
                    break;
                case 4: break;
            }
        }while(userChoice != 4);
    }
}