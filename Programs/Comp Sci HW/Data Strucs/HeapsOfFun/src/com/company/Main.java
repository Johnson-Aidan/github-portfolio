package com.company;

import java.io.*;
import java.lang.Math;
import java.util.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        int max = 50, min = 10,
            //dupCount = 0,
            randTemp;
        boolean dup;

        Node[] nodArr = new Node[10];
        Random random = new Random();
        Heap heapArr = new Heap(10);

        System.out.println("Filling Node Array...");
        for(int i = 0; i < 10; i++)
        {
            dup = false;
            randTemp = random.nextInt(41)+10;

            if(i == 0){nodArr[i] = new Node(randTemp);}
            else
            {
                for(int j = 0; j < i; j++)
                {
                    if(randTemp == nodArr[j].getKey())
                    {
                        dup = true;
                        i--;
                        break;
                    }
                }
                if(!dup)
                {
                    nodArr[i] = new Node(randTemp);
                }
            }
        }
        System.out.println("Node Array filled." +
                "\nOutputting results...");
        for(int i = 0; i < 10; i ++)
        {
            System.out.println(i + ". " + nodArr[i].getKey());
            heapArr.insert(nodArr[i].getKey());
        }

        System.out.println("\nOutputting Heap Array...");

        heapArr.displayHeap();

        heapArr.fillNodeInfo();

        System.out.println("\nOutputting Heap BST...");

        heapArr.displayTree();
    }
}

////////////////////////////////////////////////////////////////
class Node
{
    private int iData;             // data item (key)
    private Node leftChild;         // this node's left child
    private Node rightChild;        // this node's right child
    private Node parent;            // this node's parent

    // -------------------------------------------------------------
    public Node(int key)           // constructor
    { iData = key; }
    // -------------------------------------------------------------
    public int getKey()
    { return iData; }
    // -------------------------------------------------------------
    public void setKey(int id)
    { iData = id; }
    // -------------------------------------------------------------
    public Node getLeftChild()
    { return leftChild; }
    // -------------------------------------------------------------
    public void setLeftChild(Node id)
    { leftChild = id; }
    // -------------------------------------------------------------
    public Node getRightChild()
    { return rightChild; }
    // -------------------------------------------------------------
    public void setRightChild(Node id)
    { rightChild = id; }
    // -------------------------------------------------------------
    public void displayNode()      // display ourself
    {
        System.out.print("\nNode {");
        System.out.print(iData);
        System.out.print("}");
        if(leftChild != null)
        {
            System.out.print(" Left Child {");
            System.out.print(leftChild.getKey());
            System.out.print("}");
        }
        if(rightChild != null)
        {
            System.out.print(" Right Child {");
            System.out.print(rightChild.getKey());
            System.out.print('}');
        }

    }
// -------------------------------------------------------------
}  // end class Node
////////////////////////////////////////////////////////////////
class Heap
{
    private Node[] heapArray;
    private int maxSize;           // size of array
    private int currentSize;       // number of nodes in array
    // -------------------------------------------------------------
    public Heap(int mx)            // constructor
    {
        maxSize = mx;
        currentSize = 0;
        heapArray = new Node[maxSize];  // create array
    }
    // -------------------------------------------------------------
    public boolean isEmpty()
    { return currentSize==0; }
    // -------------------------------------------------------------
    public boolean insert(int key)
    {
        if(currentSize==maxSize)
            return false;
        Node newNode = new Node(key);
        heapArray[currentSize] = newNode;
        trickleUp(currentSize++);
        return true;
    }  // end insert()
    // -------------------------------------------------------------
    public void trickleUp(int index)
    {
        int parent = (index-1) / 2;
        Node bottom = heapArray[index];

        while( index > 0 &&
                heapArray[parent].getKey() < bottom.getKey() )
        {
            heapArray[index] = heapArray[parent];  // move it down
            index = parent;
            parent = (parent-1) / 2;
        }  // end while
        heapArray[index] = bottom;
    }  // end trickleUp()
    // -------------------------------------------------------------
    public Node remove()           // delete item with max key
    {                           // (assumes non-empty list)
        Node root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        trickleDown(0);
        return root;
    }  // end remove()
    // -------------------------------------------------------------
    public void trickleDown(int index)
    {
        int largerChild;
        Node top = heapArray[index];       // save root
        while(index < currentSize/2)       // while node has at
        {                               //    least one child,
            int leftChild = 2*index+1;
            int rightChild = leftChild+1;
            // find larger child
            if(rightChild < currentSize &&  // (rightChild exists?)
                    heapArray[leftChild].getKey() <
                            heapArray[rightChild].getKey())
                largerChild = rightChild;
            else
                largerChild = leftChild;
            // top >= largerChild?
            if( top.getKey() >= heapArray[largerChild].getKey() )
                break;
            // shift child up
            heapArray[index] = heapArray[largerChild];
            index = largerChild;            // go down
        }  // end while
        heapArray[index] = top;            // root to index
    }  // end trickleDown()
    // -------------------------------------------------------------
    public boolean change(int index, int newValue)
    {
        if(index<0 || index>=currentSize)
            return false;
        int oldValue = heapArray[index].getKey(); // remember old
        heapArray[index].setKey(newValue);  // change to new

        if(oldValue < newValue)             // if raised,
            trickleUp(index);                // trickle it up
        else                                // if lowered,
            trickleDown(index);              // trickle it down
        return true;
    }  // end change()
    // -------------------------------------------------------------
    public void displayHeap()
    {
        System.out.print("heapArray: ");    // array format
        for(int m=0; m<currentSize; m++)
            if(heapArray[m] != null) {
                System.out.print(heapArray[m].getKey() + " ");
            }
            else
                System.out.print( "-- ");
        System.out.println();
        // heap format
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;                          // current item
        String dots = "...............................";
        System.out.println(dots+dots);      // dotted top line

        while(currentSize > 0)              // for each heap item
        {
            if(column == 0)                  // first item in row?
                for(int k=0; k<nBlanks; k++)  // preceding blanks
                    System.out.print(' ');
            // display item
            System.out.print(heapArray[j].getKey());

            if(++j == currentSize)           // done?
                break;

            if(++column==itemsPerRow)        // end of row?
            {
                nBlanks /= 2;                 // half the blanks
                itemsPerRow *= 2;             // twice the items
                column = 0;                   // start over on
                System.out.println();         //    new row
            }
            else                             // next item on row
                for(int k=0; k<nBlanks*2-2; k++)
                    System.out.print(' ');     // interim blanks
        }  // end for
        System.out.println("\n"+dots+dots); // dotted bottom line
    }  // end displayHeap()
    // -------------------------------------------------------------
    public void displayHeapUpdated()
    {
        System.out.print("heapArray: ");    // array format
        for(int m=0; m<currentSize; m++)
            if(heapArray[m] != null) {
                heapArray[m].displayNode();
                System.out.print(" ");
            }
            else
                System.out.print( "-- ");
        System.out.println();
        // heap format
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;                          // current item
        String dots = "...............................";
        System.out.println(dots+dots);      // dotted top line

        while(currentSize > 0)              // for each heap item
        {
            if(column == 0)                  // first item in row?
                for(int k=0; k<nBlanks; k++)  // preceding blanks
                    System.out.print(' ');
            // display item
            System.out.print(heapArray[j].getKey());

            if(++j == currentSize)           // done?
                break;

            if(++column==itemsPerRow)        // end of row?
            {
                nBlanks /= 2;                 // half the blanks
                itemsPerRow *= 2;             // twice the items
                column = 0;                   // start over on
                System.out.println();         //    new row
            }
            else                             // next item on row
                for(int k=0; k<nBlanks*2-2; k++)
                    System.out.print(' ');     // interim blanks
        }  // end for
        System.out.println("\n"+dots+dots); // dotted bottom line
    }  // end displayHeap()
    // -------------------------------------------------------------
    public void fillNodeInfo()
    {
        for(int i = 4; i >= 0; i--)
        {
            if((i*2)+1 < 10){heapArray[i].setLeftChild(heapArray[(i*2)+1]);}
            if((i*2)+2 < 10){heapArray[i].setRightChild(heapArray[(i*2)+2]);}
        }
    }
// -------------------------------------------------------------
    public void displayTree() {
        for(int i = 0; i < 10; i++)
        {
            Node root = heapArray[i];
            Stack globalStack = new Stack();
            globalStack.push(root);
            int nBlanks = 32;
            boolean isRowEmpty = false;
            System.out.println(
                    "......................................................");
            while (isRowEmpty == false) {
                Stack localStack = new Stack();
                isRowEmpty = true;

                for (int j = 0; j < nBlanks; j++)
                    System.out.print(' ');

                while (globalStack.isEmpty() == false) {
                    Node temp = (Node) globalStack.pop();
                    if (temp != null) {
                        System.out.print(temp.getKey());
                        localStack.push(temp.getLeftChild());
                        localStack.push(temp.getRightChild());

                        if (temp.getLeftChild() != null ||
                                temp.getRightChild() != null)
                            isRowEmpty = false;
                    } else {
                        System.out.print("--");
                        localStack.push(null);
                        localStack.push(null);
                    }
                    for (int j = 0; j < nBlanks * 2 - 2; j++)
                        System.out.print(' ');
                }  // end while globalStack not empty
                System.out.println();
                nBlanks /= 2;
                while (localStack.isEmpty() == false)
                    globalStack.push(localStack.pop());
            }  // end while isRowEmpty is false
            System.out.println(
                    "......................................................");
        }
    }  // end displayTree()
}  // end class Heap
////////////////////////////////////////////////////////////////