//Aidan Johnson
//1890052

import java.util.Scanner;
import java.security.SecureRandom;

public class ArraysTest {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        SecureRandom random = new SecureRandom();

        int stuNum;
        int scoresNum;
        int currentScore;
        int chosenAssignment;
        int userChoice;

        System.out.print("\tPlease enter the number of students: ");
        stuNum = input.nextInt();

        String[] names = new String[stuNum];

        for(int student = 0; student < stuNum; student++)
        {
            int randNum = 65 + random.nextInt(26);
            char randName1 = (char) randNum;

            randNum = 65 + random.nextInt(26);
            char randName2 = (char) randNum;

            String randomizedName = randName1 + "." + randName2 + ".";
            names[student] = randomizedName;
        }

        System.out.println();
        System.out.print("\tPlease enter the number of scores per student: ");
        scoresNum = input.nextInt();

        int[][] scores = new int[stuNum][scoresNum];
        int[] rows = new int[stuNum];

        for(int i = 0; i < stuNum; i++)
        {
            for(int j = 0; j < scoresNum; j++)
            {
                currentScore = random.nextInt(31)+70;
                scores[i][j] = currentScore;
            }
        }

        do
        {
            System.out.println("\n\tPlease choose an option: ");
            System.out.println("\t\t1. Display the range of scores for a certain assignment: ");
            System.out.println("\t\t2. Display the name and the average score of the student with the highest overall average score");
            System.out.println("\t\t3. Display the range of scores for a certain student: ");
            System.out.println("\t\t4. Display the name of the student with the highest score on a certain assignment: ");
            System.out.println("\t\t5. The names of the students who earned a grade of A for a certain assignment: ");
            System.out.println("\t\t6. The names of the students who earned grades of B or higher for ALL of their scores. ");
            System.out.println("\t\t7. Display the names of the students along with their scores, one student per line. ");
            System.out.println("\t\t8. Data Entry Error: Need to swap the scores for two students: ");
            System.out.println("\t\t9. BONUS: Grade Distribution: For every assignment display the number of A’s, B’s, C’s as well as 100s ");
            System.out.println("\t\t10. BONUS: Which assignment showed the highest student performance based on the highest number of A’s and B’s.");
            System.out.println("\t\t11. Part II: Use your Student array to display the names followed by the scores for each student: one student per line.");
            System.out.println("\t\t12. Exit");
            userChoice = input.nextInt();

            switch(userChoice)
            {
                case 1:
                    System.out.println("\n\t\t\tPlease enter the assignment number: (1-" + scoresNum + ")");
                    chosenAssignment = input.nextInt();
                    chosenAssignment--;

                    System.out.print("\n\t\t\t\tAll scores for this assignment: \n\t\t\t\t\t");
                    for(int i = 0; i < stuNum; i++)
                        System.out.print(scores[i][chosenAssignment] + " ");
                    break;

                case 2:
                    int maxAvg = -1;
                    int maxIndex = -1;

                    for(int i = 0; i < stuNum; i++)
                    {
                        if(average(scores[i]) > maxAvg)
                        {
                            maxIndex = i;
                            maxAvg = average(scores[i]);
                        }
                    }
                    System.out.println("\n\t\t\tThe student with the highest average is " + names[maxIndex] + " with an average score of " + maxAvg + ".");
                    break;

                case 3: input.nextLine();
                    System.out.println("\n\t\t\tPlease enter the name of the student: ");
                    String studentName = input.nextLine();

                    if(findStudent(names, studentName) == -1)
                        System.out.println("\n\t\t\t\tThis student does not exist in the database.");
                    else
                        displayStudentScores(scores[findStudent(names, studentName)], studentName);
                    break;

                case 4:
                    System.out.println("\n\t\t\t\tPlease enter the assignment number: (1-" + scoresNum + ")");
                    chosenAssignment = input.nextInt();
                    chosenAssignment--;

                    for(int i = 0; i < stuNum; i++)
                        rows[i] = scores[i][chosenAssignment];

                    System.out.println("\n\t\t\t\tThe student with the highest score on assignment " + (chosenAssignment + 1) + " is " + names[maxIndex(rows)] + " with a score of " + maxNum(rows));
                    break;

                case 5:
                    System.out.println("\n\t\t\tPlease enter the assignment number: (1-" + scoresNum + ")");
                    chosenAssignment = input.nextInt();

                    System.out.print("\n\t\t\t\tThese students earned an A on assignment " + chosenAssignment + ": ");
                    chosenAssignment--;

                    for(int i = 0; i < scores.length; i++)
                        if(letterGrade(scores[i][chosenAssignment]) == 'A')
                            System.out.print(names[i] + "\t");
                    break;

                case 6:
                    int gradeFound = 0;

                    System.out.print("\n\t\t\t\tThese students earned a B or above on all of their assignments: ");
                    for(int i = 0; i < stuNum; i++)
                    {
                        if(minNum(scores[i]) >= 80)
                        {
                            gradeFound = 1;
                            System.out.print(names[i] + " ");
                        }
                    }

                    if(gradeFound == 0)
                        System.out.print("\n\t\t\t\tNo student made all B's or higher.");
                    break;

                case 7:
                    displayAllScores(scores, names, stuNum, scoresNum);
                    break;

                case 8:
                    System.out.println("\n\t\t\tPlease enter the name of the first student: ");
                    String stuOne = input.nextLine();

                    String dummy = input.nextLine();

                    System.out.println("\n\t\t\tPlease enter the name of the second student: ");
                    String stuTwo = input.nextLine();

                    if(findStudent(names, stuOne) != -1 && findStudent(names, stuTwo) != -1)
                        swapScores(scores, findStudent(names, stuOne), findStudent(names, stuTwo));
                    else
                        System.out.println("\n\t\t\t\tAt least one of these students could not be found.");
                    break;

                case 9:
                    for(int i = 0; i < scoresNum; i++)
                    {
                        for(int j = 0; j < stuNum; j++)
                            rows[j] = scores[j][i];

                        int[] tallyArray = gradeDistribution(rows);

                        System.out.println("\n\t\t\tAssignment " + (i + 1) + "");
                        System.out.println("\n\t\t\t\t# of Cs:\t" + tallyArray[7]);
                        System.out.println("\n\t\t\t\t# of B's:\t" + tallyArray[8]);
                        System.out.println("\n\t\t\t\t# of A's:\t" + tallyArray[9]);
                        System.out.println("\n\t\t\t\t# of 100's:\t" + tallyArray[10]);
                    }
                    break;

                case 10:
                    int maxGrades = -1;
                    int numOfHighGrades;
                    int maxAssignment = -1;

                    for(int i = 0; i < scoresNum; i++)
                    {
                        for(int j = 0; j < stuNum; j++)
                            rows[j] = scores[j][i];

                        int[] tallyArray = gradeDistribution(rows);

                        numOfHighGrades = tallyArray[8] + tallyArray[9];

                        if(numOfHighGrades > maxGrades)
                        {
                            maxGrades = numOfHighGrades;
                            maxAssignment = i;
                        }
                        numOfHighGrades = 0;
                    }
                    System.out.println("The assignment with the most A's and B's is assignment " + (maxAssignment + 1));
                    break;

                case 11:
                    Student[] students = new Student[stuNum];

                    for(int i = 0; i < students.length; i++)
                    {
                        Student currentStudent = new Student(names[i], scores[i]);
                        students[i] = currentStudent;
                    }

                    for(int i = 0; i < students.length; i++)
                        students[i].displayInfo();
                    break;

                case 12: break;

                default: System.out.println("Please choose a number between 1 and 11. Or 12 to exit.");
            }
        } while(userChoice != 12);
    }

    public static int maxNum(int[] stuScores)
    {
        int maxNum = -1;

        for(int i = 0; i < stuScores.length; i++)
            if(stuScores[i] > maxNum)
                maxNum = stuScores[i];
        return maxNum;
    }

    public static int maxIndex(int[] stuScores) {
        int maxNum = -1;
        int maxIndex = -1;

        int i;
        for (i = 0; i < stuScores.length; i++)
            if (stuScores[i] > maxNum)
            {
                maxNum = stuScores[i];
                maxIndex = i;
            }

        return maxIndex;
    }

    public static int minNum(int[] stuScores)
    {
        int minNum = stuScores[0];

        for(int i = 1; i < stuScores.length; i++)
            if(stuScores[i] < minNum)
                minNum = stuScores[i];

        return minNum;
    }

    public static int average(int[] stuScores)
    {
        int sum = 0;
        int average;

        for(int i = 0; i < stuScores.length; i++)
            sum += stuScores[i];

        average = sum / stuScores.length;

        return average;
    }

    public static void swapScores(int[][] scores, int stuOneNum, int stuTwoNum)
    {
        int[] tempArray = new int[scores[stuOneNum].length];

        for(int i = 0; i < tempArray.length; i++)
        {
            tempArray[i] = scores[stuOneNum][i];
            scores[stuOneNum][i] = scores[stuTwoNum][i];
            scores[stuTwoNum][i] = tempArray[i];
        }

        System.out.println("\n\t\t\t\tThe two scores of the two students have been swapped.");
    }

    public static char letterGrade(int grade)
    {
        if(grade >= 90)
            return 'A';

        else if(grade >= 80)
            return 'B';

        else
            return 'C';
    }

    public static void displayStudentScores(int[] stuScores, String stuName)
    {
        System.out.println("\n\t\t\t\t" + stuName);

        for(int i = 0; i < stuScores.length; i++)
            System.out.print(stuScores[i] + " ");
    }

    public static int[] gradeDistribution(int[] rows)
    {
        int[] tallyArray = new int[11];

        for(int i = 0; i < rows.length; i++)
        {
            if(rows[i] == 100)
                tallyArray[10]++;

            else if(letterGrade(rows[i]) == 'A')
                tallyArray[9]++;

            else if(letterGrade(rows[i]) == 'B')
                tallyArray[8]++;
            else
                tallyArray[7]++;
        }

        return tallyArray;
    }

    public static void displayAllScores(int[][] scores, String[] names, int stuNum, int scoresNum)
    {
        System.out.println("\n\t\t\t\tStudent\tScores");

        for(int i = 0; i < stuNum; i++)
        {
            System.out.print("\n\t\t\t\t" + names[i] + "\t");

            for(int j = 0; j < scoresNum; j++)
            {
                System.out.print(scores[i][j] + " ");
            }
        }
    }

    public static int findStudent(String[] names, String requestedStudent)
    {
        int index = -1;

        for(int i = 0; i < names.length; i++)
        {
            if(requestedStudent == names[i]) {
                System.out.println("Student found");
                index = i;
            }
        }

        return index;
    }
}
