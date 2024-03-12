//1890052 Aidan Johnson

import java.util.Random;

public class Assignment1 {
    //Populates Arrays with random sizes and ints and check for duplicates in a single array

    static int fillArray(int[] array, int SIZE)
    {
        //The max size of the array as it is lowered
        int max = array.length;
        Random randInt  = new Random();

        for (int i = 1; i < SIZE; i++ )
        {
            array[i] = randInt.nextInt(100);
        }

        System.out.print("\n\tBefore moving:\n\t\t");
        for (int i = 1; i < SIZE; i++)
        {
            System.out.printf("|%d", array[i]);
        }

        System.out.println("\n");
        for (int i = 1; i < max; i++)
        {
            for (int j = i + 1; j < SIZE; j++)
            {
                if (array[j] == array[i])
                {
                    int moveLeft = j;
                    for (int k = j+1; k < max; k++, moveLeft++)
                    {
                        array[moveLeft] = array[k];
                    }
                    SIZE--;
                    max--;
                    j--;
                }
            }
        }

        System.out.print("\tAfter moving:\n\t\t");
        for (int i = 1; i < SIZE; i++)
        {
            System.out.printf("|%d", array[i]);
        }
        return SIZE;
    }

    static void showArray(int[] arrayA, int[] arrayB, int sizeArrayA, int sizeArrayB)
    {
        System.out.println("\n\n- - - ListA and ListB Arrays - - -");
        System.out.println("\tListA:");
        System.out.print("\t\t|");
        for (int i = 1; i < sizeArrayA; i++)
        {
            System.out.print(arrayA[i] + "|");
        }

        System.out.println("\n\n\tListB: ");
        System.out.print("\t\t|");
        for (int i = 1; i < sizeArrayB; i++)
        {
            System.out.print(arrayB[i] + "|");
        }
        System.out.println("\n");
    }

    //Method for updating Tally Array with values from ListA and ListB
    static void tallyMark(int[] arrayA, int[] arrayB)
    {
        for (int i = 0; i < arrayA.length; i++)
        {
            arrayB[arrayA[i]]++;
        }
    }

    //Show the common elements
    static void showCommon(int[] array)
    {
        System.out.println("- - - - - Common Elements - - - - -");
        System.out.print("\t\t|");
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == 2)
            {
                System.out.print(i + "|");
            }
        }
    }

    //Method to delete Common Elements from Array's
    static void removeCommon(int[] arrayA, int[] arrayB, int[] theList, int sizeArrayA, int sizeArrayB)
    {
        System.out.println("\n\n- - Deleting Common Elements... - -");

        for(int i = 0; i < theList.length; i++)
        {
            if (theList[i] == 2)
            {

                for (int j = 1; j < sizeArrayA; j++)
                {
                    if (arrayA[j] == i)
                    {
                        for (int k = j; k < arrayA.length - 1; k++)
                            arrayA[k] = arrayA[k + 1];
                    }
                }

                for (int k = 1; k < sizeArrayB; k++)
                {
                    if (arrayB[k] == i)
                    {
                        for (int l = k; l < arrayB.length -1; l++)
                            arrayB[l] = arrayB[l+1];
                    }
                }
            }
        }
        System.out.print("\t\t\tDeleted");
    }

    static void theList(int[] array)
    {
        System.out.println("- - - - - Combined Arrays - - - - -");
        System.out.print("\t\t|");
        for (int i = 1; i < array.length; i++)
        {
            if (array[i] == 1)
            {
                System.out.print(i + "|");
            }
        }
    }

    static boolean keyFinder(int[] arrayA, int[] arrayB)
    {
        System.out.println("- - - - - - Finding Key - - - - - -");

        int tallyA = 0, tallyB = 0;

        for (int i = 1; i < arrayA.length; i++)
        {
            if (arrayA[i] == 0 && arrayA[i+1] == 0){
                break;
            }
            tallyA++;
        }

        for (int i = 1; i < arrayB.length; i++)
        {
            if (arrayB[i] == 0 && arrayB[i+1] == 0)
            {
                break;
            }
            tallyB++;
        }

        if(tallyA < tallyB)
        {
            System.out.println("\tListA is the Key Array.\n");
            return true;
        }
        else if(tallyB < tallyA)
        {
            System.out.println("\tListB is the Key Array.\n");
            return false;
        }
        else{
            System.out.println("\tListA is the Key Array.\n");
            return true;
        }
    }

    static void findGameSum(int[] arrayA, int[] arrayB)
    {
        System.out.println("- - - - - It's Game Time - - - - -");
        int gameSum = 0;
        int gameSkip = 0;

        System.out.print("\tSums to be added:\n\t\t");
        for (int i = 1; i < arrayA.length; i++)
        {
            if (arrayA[i] == 0 && arrayA[i+1] == 0){
                break;
            }
            if (arrayA[i] > arrayB.length-1 || arrayB[arrayA[i]] == 0)
            {
                gameSkip++;
                continue;
            }
            System.out.printf("|%d", arrayB[arrayA[i]]);

            gameSum += arrayB[arrayA[i]];
        }
        System.out.printf("|\n\t%s\n\t\t%d\n\n", "Total Sum:", gameSum);
        System.out.printf("\t%s \n\t\t%d", "# of Elements Skipped:", gameSkip);
    }

    public static void main(String[] args)
    {
        //Part I
        System.out.println("- - - - - - - - - Part I - - - - - - - - -");
        int[] ListA = new int[50];
        int[] ListB = new int[50];
        Random randSIZE = new Random();
        int SIZE = randSIZE.nextInt(49) + 1;
        int ListASize, ListBSize;
        int[] tallyArray = new int[100];

        System.out.println("\n- - - List A Initialization - - -");
        ListASize = fillArray(ListA, SIZE);

        System.out.println("\n\n- - - List B Initialization - - -");
        ListBSize = fillArray(ListB, SIZE);

        //Finding the common elements
        tallyMark(ListA, tallyArray);
        tallyMark(ListB, tallyArray);

        //Output of Lists before removal of common elements
        showArray(ListA, ListB, ListASize, ListBSize);

        //Displays the common elements in order
        showCommon(tallyArray);

        //Removes the common elements and shows what was removed
        removeCommon(ListA, ListB, tallyArray, ListASize, ListBSize);

        //Shows the arrays without the common elements
        showArray(ListA, ListB, ListASize, ListBSize);

        //Outputs the combined arrays
        theList(tallyArray);

        // Part II

        System.out.print("\n\n\n- - - - - - - - - Part II - - - - - - - - -");

        //One last look at the arrays
        showArray(ListA, ListB, ListASize, ListBSize);

        //Finds the key to figure out which array to use first and then uses it as the key for the final method
        if(keyFinder(ListA, ListB))
        {
            findGameSum(ListA, ListB);
        }
        else{
            findGameSum(ListB, ListA);
        }
    }
}