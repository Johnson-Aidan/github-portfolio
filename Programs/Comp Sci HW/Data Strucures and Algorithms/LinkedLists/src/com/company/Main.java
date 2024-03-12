package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

       //- - - - - Part I: Word Game - - - - -
        System.out.println("\n- - - - - Part I: Word Game - - - - -\n");

        LinkListWord wordList = new LinkListWord();
        String userWord;

        System.out.println("\tPlease enter a string with no spaces: ");
        userWord = input.next();

        for (int i = 0; i < userWord.length(); i++) {
            wordList.insertLast(userWord.charAt(i), i);
        }

        wordList.displayList();

        removeVowels(wordList);

        wordList.displayList();

        removeRM(wordList);

        wordList.displayList();

        //- - - - Part II: Numbers Game - - - -
        System.out.println("\n- - - - Part II: Numbers Game - - - -\n");

        LinkListNumber numList1 = new LinkListNumber();
        LinkListNumber numList2 = new LinkListNumber();
        LinkListNumber mergedList = new LinkListNumber();
        int[] tally;
        int highestNum = 0;
        int dummy;

       System.out.println("\tPlease select a size for the first Number List: ");
       int numList1Size = input.nextInt();

       System.out.println("\n\tPlease input the sorted numbers from least to greatest to fill the first Number list:\n");
       for(int i = 0; i < numList1Size; i++)
       {
           dummy = input.nextInt();
           numList1.insertLast(dummy, i);
            if(highestNum < dummy) { highestNum = dummy;}
       }

        System.out.println("\tPlease select a size for the second Number List: ");
        int numList2Size = input.nextInt();

        System.out.println("\n\tPlease input the sorted numbers from least to greatest to fill the second Number list:\n");
        for(int i = 0; i < numList2Size; i++)
        {
            dummy = input.nextInt();
            numList2.insertLast(dummy, i);
            if(highestNum < dummy) { highestNum = dummy;}
        }

        tally = new int[highestNum+1];
        System.out.print("Number List 1: ");
        numList1.displayList();
        System.out.print("Number List 2: ");
        numList2.displayList();

        fillTally(tally, numList1, numList2);
        mergeLists(tally, mergedList);

        System.out.print("Merged Number List: ");
        mergedList.displayList();

        findDup(mergedList);

    }

    public static void removeRM(LinkListWord wordList)
    {
        wordList.current = wordList.first;
        while( wordList.current != null)
        {
            if(wordList.current.displayChar() == 'r' || wordList.current.displayChar() == 'R')
            {
                if(wordList.current.next.displayChar() == 'm' || wordList.current.next.displayChar() == 'M')
                {
                    if(wordList.current.next.next !=null)
                    {
                        wordList.delete(wordList.current.next.next.displayKey());
                    }

                    wordList.delete(wordList.current.displayKey());
                    wordList.delete(wordList.current.next.displayKey());
                }
            }
            wordList.current = wordList.current.next;
        }
    }

    public static void removeVowels(LinkListWord wordList)
    {
        char con1, vowel, con2;
        wordList.current = wordList.first;
        while( wordList.current != null)
        {
            con1 = wordList.current.displayChar();

            if(con1 != 'a' && con1 != 'e' && con1 != 'i' && con1 != 'o' && con1 != 'u' && con1 != 'A' && con1 != 'E' && con1 != 'I' && con1 != 'O' && con1 != 'U')
            {
                if (wordList.current.next != null)
                {
                    vowel = wordList.current.next.displayChar();

                    if(vowel == 'a' || vowel == 'e' || vowel == 'i' || vowel == 'o' || vowel == 'u' || vowel == 'A' || vowel == 'E' || vowel == 'I' || vowel == 'O' || vowel == 'U')
                    {
                        if(wordList.current.next.next != null)
                        {
                            con2 = wordList.current.next.next.displayChar();

                            if(con2 != 'a' && con2 != 'e' && con2 != 'i' && con2 != 'o' && con2 != 'u' && con2 != 'A' && con2 != 'E' && con2 != 'I' && con2 != 'O' && con2 != 'U')
                            {
                                wordList.delete(wordList.current.next.displayKey());
                            }

                        }
                    }
                }
            }
            wordList.current = wordList.current.next;
        }
    }

    public static void fillTally(int[] tally, LinkListNumber list1, LinkListNumber list2)
    {
        list1.current = list1.first;
        list2.current = list2.first;
        for(int i = 0; i < tally.length; i++ )
        {
            tally[list1.current.displayInt()]++;
            list1.current = list1.current.next;
            if(list1.current == null) { break;}

        }
        for(int i = 0; i < tally.length; i++ )
        {
            tally[list2.current.displayInt()]++;
            list2.current = list2.current.next;
            if(list2.current == null) { break;}
        }
    }

    public static void mergeLists(int[] tally, LinkListNumber mergedList)
    {
        int count = 1;
        for(int i = 0; i < tally.length; i++ )
        {
            for(int j = 0; j < tally[i]; j++)
            {
                mergedList.insertLast(i, count);
                count++;

            }
        }
    }

    public static void findDup(LinkListNumber list)
    {
        list.current = list.first;
        int counter = 0;
        int lastVal = 0;

        while(list.current.next != null)
        {
            if(list.current.displayInt() == list.current.next.displayInt())
            {
                System.out.print(list.current.displayInt() + " ");
            }
            else if(list.current.displayInt() == lastVal && counter != 0)
            {
                System.out.println(list.current.displayInt());
            }
            lastVal = list.current.displayInt();
            list.current = list.current.next;
            counter++;
        }
        if(list.current.displayInt() == lastVal)
        {
            System.out.println(list.current.displayInt());
        }
    }
}

//Word Game link

class LinkWord
{
    public char iData;
    public int dData;
    public LinkWord next;

    public LinkWord(char iD, int key)
    {
        iData = iD;
        dData = key;
    }

    public void displayLink()
    {
        System.out.print("{" + iData + ", " + dData + "} ");
    }
    public char displayChar() {return iData;}
    public int displayKey() {return dData;}
} // end of class LinkWord

class LinkListWord
{
    public LinkWord first;
    LinkWord current;
    LinkWord previous;

    public LinkListWord()
    {
        first = null;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public void insertLast(char id, int key)
    {
        LinkWord newLink = new LinkWord(id, key);
        if ( isEmpty() )
        {
            first = newLink;
        }
        else
        {
            current = first;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = newLink;

        }
    }

    public void displayList()
    {
        System.out.print("The Word: ");
        current = first;
        while (current != null)
        {
            System.out.print(current.displayChar());
            current = current.next;
        }
        System.out.println(" ");
    }

    public LinkWord delete(int key)
    {
        LinkWord current = first;
        LinkWord previous = first;

        while(current.dData != key)
        {
            if (current.next == null)
                return null;
            else
            {
                previous = current;
                current = current.next;
            }

        }
        if (current == first)
        {
            first = first.next;
        }
        else
            previous.next = current.next;
        return current;
    }
} //end of LinkWord class

//Number Game Link

class LinkNumber
{
    public int iData;
    public int dData;

    public LinkNumber next;

    public LinkNumber(int iD, int dD)
    {
        iData = iD;
        dData = dD;
    }

    public void displayLink()
    {
        System.out.print("{" + iData + "} ");
    }
    public int displayInt() {return iData;}
    public int displayKey() {return dData;}

} // end of class LinkWord

class LinkListNumber
{
    public LinkNumber first;
    LinkNumber current;
    LinkNumber previous;

    public LinkListNumber()
    {
        first = null;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public void insertLast(int id, int dD)
    {
        LinkNumber newLink = new LinkNumber(id, dD);
        if ( isEmpty() )
        {
            first = newLink;
        }
        else
        {
            current = first;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = newLink;

        }
    }

    public void displayList()
    {
        current = first;
        while (current != null)
        {
            System.out.print(current.displayInt() + " ");
            current = current.next;
        }
        System.out.println(" ");
    }

} //end of LinkWord class
