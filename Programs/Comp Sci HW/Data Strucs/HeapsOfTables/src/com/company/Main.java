package com.company;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        HashTable hashArr;
        int[] tally = new int[1000];
        int userSize, userFind, userIndex, randTemp;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("Please choose a number of area codes (1-320): ");
            userSize = input.nextInt();
        } while (userSize > 320 || userSize < 1);

        hashArr = new HashTable(userSize);

        for (int i = 0; i < userSize; i++) {
            randTemp = random.nextInt(899) + 100;
            if (tally[randTemp] == 0) {
                tally[randTemp]++;
            } else {
                i--;
            }
        }

       for (int i = 0; i < 999; i++) {
            if (tally[i] > 0) {hashArr.insert(i);}
        }

        System.out.println();

        hashArr.loadFactor(userSize);

        do{
            System.out.print("\n{");
            hashArr.displayFilledIndex();
            System.out.println("}");
            System.out.print("\nPlease choose a non-empty index from the above list to see what is stored inside (0-" + (userSize-1) +") [Enter -1 to stop]: " );
            userIndex = input.nextInt();
            if(userIndex > -1 && userIndex < userSize){hashArr.displayFilledTable(userIndex);}
            else if (userIndex != -1 && (userIndex < 0 || userIndex >= userSize)){System.out.println("That number was not in the proper range.");}
        }while(userIndex != -1);

        do{
            System.out.print("\nPlease insert an area code to find (100-999) [Enter 0 to exit]: ");
            userFind = input.nextInt();
            if (userFind <= 999 && userFind >= 100) {hashArr.find(userFind);}
            else if (userFind > 999 || userFind < 100 && userFind != 0) { System.out.println("That number was not in the proper range.");}
        }while(userFind != 0);


    }
}

////////////////////////////////////////////////////////////////
class Node
{
    public int iData;              // data item (key)
    public Node leftChild;         // this node's left child
    public Node rightChild;        // this node's right child

    public void displayNode()      // display ourself
    {
        System.out.print('{');
        System.out.print(iData);
        System.out.print("} ");
    }
}  // end class Node

////////////////////////////////////////////////////////////////

class Tree
{
    private Node root;             // first node of tree

    // -------------------------------------------------------------
    public Tree()                  // constructor
    { root = null; }            // no nodes in tree yet
    // -------------------------------------------------------------
    public Node find(int key)      // find node with given key
    {                           // (assumes non-empty tree)
        Node current = root;               // found it
        if(current != null)
        {
            // start at root
            while(current.iData != key)        // while no match,
            {
                System.out.println(current.iData);
                if(key < current.iData)         // go left?
                    current = current.leftChild;
                else                            // or go right?
                    current = current.rightChild;
                if(current == null)             // if no child,
                    return null;                 // didn't find it
            }
            return current;
        }
        else {return null;}
    }  // end find()
    // -------------------------------------------------------------
    public void insert(int id)
    {
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        if(root==null)                // no node in root
            root = newNode;
        else                          // root occupied
        {
            Node current = root;       // start at root
            Node parent;
            while(true)                // (exits internally)
            {
                parent = current;
                if(id < current.iData)  // go left?
                {
                    current = current.leftChild;
                    if(current == null)  // if end of the line,
                    {                 // insert on left
                        parent.leftChild = newNode;
                        return;
                    }
                }  // end if go left
                else                    // or go right?
                {
                    current = current.rightChild;
                    if(current == null)  // if end of the line
                    {                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }  // end else go right
            }  // end while
        }  // end else not root
    }  // end insert()
    // -------------------------------------------------------------
    public boolean delete(int key) // delete node with given key
    {                           // (assumes non-empty list)
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while(current.iData != key)        // search for node
        {
            parent = current;
            if(key < current.iData)         // go left?
            {
                isLeftChild = true;
                current = current.leftChild;
            }
            else                            // or go right?
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if(current == null)             // end of the line,
                return false;                // didn't find it
        }  // end while
        // found node to delete

        // if no children, simply delete it
        if(current.leftChild==null &&
                current.rightChild==null)
        {
            if(current == root)             // if root,
                root = null;                 // tree is empty
            else if(isLeftChild)
                parent.leftChild = null;     // disconnect
            else                            // from parent
                parent.rightChild = null;
        }

        // if no right child, replace with left subtree
        else if(current.rightChild==null)
            if(current == root)
                root = current.leftChild;
            else if(isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;

            // if no left child, replace with right subtree
        else if(current.leftChild==null)
            if(current == root)
                root = current.rightChild;
            else if(isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;

        else  // two children, so replace with inorder successor
        {
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if(current == root)
                root = successor;
            else if(isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;

            // connect successor to current's left child
            successor.leftChild = current.leftChild;
        }  // end else two children
        // (successor cannot have a left child)
        return true;                                // success
    }  // end delete()
    // -------------------------------------------------------------
    // returns node with next-highest value after delNode
    // goes to right child, then right child's left descendents
    private Node getSuccessor(Node delNode)
    {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;   // go to right child
        while(current != null)               // until no more
        {                                 // left children,
            successorParent = successor;
            successor = current;
            current = current.leftChild;      // go to left child
        }
        // if successor not
        if(successor != delNode.rightChild)  // right child,
        {                                 // make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }
    // -------------------------------------------------------------
    public void traverse(int traverseType)
    {
        switch(traverseType)
        {
            case 1: System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2: System.out.print("\nInorder traversal:  ");
                inOrder(root);
                break;
            case 3: System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
        }
        System.out.println();
    }
    // -------------------------------------------------------------
    private void preOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }
    // -------------------------------------------------------------
    private void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }
    // -------------------------------------------------------------
    private void postOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }
    // -------------------------------------------------------------
    public void displayTree()
    {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while(isRowEmpty==false)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<nBlanks; j++)
                System.out.print(' ');

            while(globalStack.isEmpty()==false)
            {
                Node temp = (Node)globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if(temp.leftChild != null ||
                            temp.rightChild != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<nBlanks*2-2; j++)
                    System.out.print(' ');
            }  // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );
        }  // end while isRowEmpty is false
        System.out.println(
                "......................................................\n");
    }  // end displayTree()
    // -------------------------------------------------------------
    public boolean isEmpty()
    {
        return root != null;
    }
}  // end class Tree

////////////////////////////////////////////////////////////////

class HashTable
{
    private Tree[] hashArray;   // array of lists
    private int arraySize;
    // -------------------------------------------------------------
    public HashTable(int size)        // constructor
    {
        arraySize = size;
        hashArray = new Tree[arraySize];  // create array
        for(int j=0; j<arraySize; j++)          // fill array
            hashArray[j] = new Tree();     // with lists
    }
    // -------------------------------------------------------------
    public void displayTable()
    {
        for(int j=0; j<arraySize; j++) // for each cell,
        {
            System.out.print(j + ".\n"); // display cell number
            hashArray[j].displayTree(); // display list
        }
    }
    // -------------------------------------------------------------
    public void displayFilledTable(int index)
    {
        hashArray[index].displayTree();
    }
    // -------------------------------------------------------------
    public void displayFilledIndex()
    {
        int tabCount = 0;
        for(int j=0; j<arraySize; j++) // for each cell,
        {
            if(hashArray[j].isEmpty())
            {
                System.out.print(j + ", "); // display cell number
                tabCount++;
                if(tabCount == 20)
                {
                    System.out.println();
                    tabCount = 0;
                }
            }
        }
    }
    // -------------------------------------------------------------
    public int hashFunc(int key)      // hash function
    {
        return key % arraySize;
    }
    // -------------------------------------------------------------
    public void insert(int theLink)  // insert a link
    {
        int key = theLink;
        int hashVal = hashFunc(key);   // hash the key
        hashArray[hashVal].insert(theLink); // insert at hashVal
    }  // end insert()
    // -------------------------------------------------------------
    public void delete(int key)       // delete a link
    {
        int hashVal = hashFunc(key);   // hash the key
        hashArray[hashVal].delete(key); // delete link
    }  // end delete()
    // -------------------------------------------------------------
    public void find(int key)         // find link
    {
        int hashVal = hashFunc(key);   // hash the key
        Node theLink = hashArray[hashVal].find(key);  // get link
        if(theLink != null){
            System.out.println(theLink.iData + " - Area Code Found"); ;                // return link
        }
        else{
            System.out.println("Area Code Not Found");
        }
    }
    // -------------------------------------------------------------
    public void loadFactor(int userSize)
    {
        double count = 0;
        System.out.println("The Load Factor of the Hash Table is: " + userSize + "/" + arraySize + " = " + (double) (userSize/arraySize));

        for(int j=0; j<arraySize; j++) // for each cell,
        {
            if(hashArray[j].isEmpty())
            {
                count++;
            }
        }

        System.out.println("The Average Size of the BST's is: " + userSize + "/" + count + " = " + userSize/count);
    }
// -------------------------------------------------------------
}  // end class HashTable
