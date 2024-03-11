package com.company;

import java.awt.desktop.SystemSleepEvent;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        LinkList backwardsWord = new LinkList();
        DoublyLinkedList palindrome = new DoublyLinkedList();
        String word;
        int wordSize;

        // Part I

       System.out.print("\tPlease enter a word to have outputted backwards: ");
        word = input.next();

        for(int i = 0; i < word.length(); i++)
        {
            backwardsWord.insertLast(word.charAt(i));
        }

        System.out.print("\t\t");

        System.out.println("\t\t" + backwards(backwardsWord.getFirst()));

        // Part II

        System.out.print("\n\tPlease enter a word to check if it's a palindrome: ");
        word = input.next();

        for(int i = 0; i < word.length(); i++)
        {
            palindrome.insertLast(word.charAt(i));
        }

        wordSize = word.length() - 1;

        if(palinCheck(palindrome, wordSize))
            {System.out.println("\t\tIt is a palindrome!");}
        else
            {System.out.println("\t\tIt's not a palindrome!");}

        // Bonus


    }

    //Single Ended List
    public static String backwards(Link list)
    {
        if(list.next == null)
            return list.displayLink() + "";
        else
            return backwards(list.next) + list.displayLink();
    }

    public static boolean palinCheck(DoublyLinkedList list, int size)
    {
        boolean status = false;
        String forward = "";
        String backward;

        list.current = list.getFirst();

        for(int i = 0; i < size+1; i++)
        {
            forward += list.current.displayLink();
            if(list.current.next != null)
            {list.current = list.current.next;}
        }

        backward = doubleBackwards(list.getLast());

        System.out.println("\t" + forward + " - " + backward);

        if(forward.equals(backward)){status = true;}

        return status;
    }

    //Doubly linked list
    public static String doubleBackwards(Link list)
    {
        if(list.previous == null)
            return list.displayLink() + "";
        else
            return list.displayLink() + doubleBackwards(list.previous);
    }

}




class Link
{

    public char dData;                 // data item

    public Link next;                  // next link in list

    public Link previous;              // previous link in list
    // -------------------------------------------------------------
    public Link(char d)                // constructor
    {
        dData = d; }
    // -------------------------------------------------------------
    public char displayLink() { return dData;}         // display this link

// -------------------------------------------------------------
}  // end class Link
////////////////////////////////////////////////////////////////

class DoublyLinkedList
{

    private Link first;               // ref to first item
    public Link current;
    private Link last;                // ref to last item
    // -------------------------------------------------------------
    public DoublyLinkedList()         // constructor
    {

        first = null;// no items on list yet

        last = null;

    }
    // -------------------------------------------------------------
    public boolean isEmpty()          // true if no links
    { return first==null; }
    // -------------------------------------------------------------
    public Link getFirst() {return first;}
    // -------------------------------------------------------------
    public Link getLast() {return last;}
    // -------------------------------------------------------------

    // -------------------------------------------------------------
    public void insertLast(char id)   // insert at end of list
    {

        Link newLink = new Link(id);   // make new link

        if( isEmpty() )                // if empty list,
            first = newLink;            // first --> newLink
        else
        {

            last.next = newLink;        // old last --> newLink

            newLink.previous = last;    // old last <-- newLink

        }
        last = newLink;                // newLink <-- last

    }
// -------------------------------------------------------------
}  // end class DoublyLinkedList
////////////////////////////////////////////////////////////////

class LinkList
{
    private Link first;
    public Link current;

    public LinkList()
    {
        first = null;
    }

    public Link getFirst(){return first;}

    public boolean isEmpty()
    {
        return first == null;
    }

    public void insertLast(char id)
    {
        Link newLink = new Link(id);
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


}