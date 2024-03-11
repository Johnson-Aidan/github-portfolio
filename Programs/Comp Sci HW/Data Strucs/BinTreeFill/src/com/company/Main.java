//Aidan Johnson 1890052

package com.company;
import java.util.Scanner;
import java.io.*;
import java.util.*;

class TreeApp
{
    public static void main(String[] args) throws IOException
    {
        String word;
        char currentLetter;
        String userInput;
        char userKey;
        Tree theTree = new Tree();

        Scanner input = new Scanner(System.in);

        System.out.print("\tEnter a word in all capitals to create the Binary Search Tree with: ");
        word = input.next();

        for(int i = 0; i < word.length(); i++)
        {
            currentLetter = word.charAt(i);
            theTree.insert(currentLetter);
        }
        //theTree.displayTree();

        do{
            System.out.println("\n\tSelect an option:");
            System.out.println("\t\t1. Display the Tree.");
            System.out.println("\t\t2. Display the word built out of the characters on the left side of this tree.");
            System.out.println("\t\t3. Display the world built out of the characters that form the leaves of this tree.");
            System.out.println("\t\t4. Display the sibling subtree of a character node.");
            System.out.print("\t\t5. Exit\n\t\t");

            userInput = input.next();

            switch(userInput)
            {
                case "1": theTree.displayTree();
                        break;
                case "2": theTree.displayLeftChild(theTree.root);
                    break;
                case "3": theTree.displayLeaves(theTree.root);
                    break;
                case "4": System.out.print("\t\t\tPlease input a character to find the sibling subtree of: ");
                    userKey = input.next().charAt(0);
                    theTree.siblingSubtree(userKey, theTree.root);
                    break;
                case "5": break;
                default: System.out.print("\t\tInvalid input - Please input a number 1-5.");
            }

        }while(userInput != "5");
    }  // end main()
// -------------------------------------------------------------


}  // end class TreeApp
////////////////////////////////////////////////////////////////


class Node
{
    public char iData;              // data item (key)
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
    public Node root;             // first node of tree

    // -------------------------------------------------------------
    public Tree()                  // constructor
    { root = null; }            // no nodes in tree yet
    // -------------------------------------------------------------
    public Node find(char key)      // find node with given key
    {                           // (assumes non-empty tree)
        Node current = root;               // start at root
        while(current.iData != key)        // while no match,
        {
            if(key < current.iData)         // go left?
                current = current.leftChild;
            else                            // or go right?
                current = current.rightChild;
            if(current == null)             // if no child,
                return null;                 // didn't find it
        }
        return current;                    // found it
    }  // end find()
    // -------------------------------------------------------------
    public void insert(char id)
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
    // goes to right child, then right child's left descendants
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
        while(!isRowEmpty)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<nBlanks; j++)
                System.out.print(' ');

            while(!globalStack.isEmpty())
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
            while(!localStack.isEmpty())
                globalStack.push( localStack.pop() );
        }  // end while isRowEmpty is false
        System.out.println(
                "......................................................");
    }  // end displayTree()
    // -------------------------------------------------------------
    public void displayLeftChild(Node localRoot)
    {
        if(localRoot != null)
        {
            displayLeftChild(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
        }
    }
    // -------------------------------------------------------------
    public void displayLeaves(Node localRoot)
    {
        if(localRoot.leftChild == null && localRoot.rightChild == null)
        {
            System.out.print(localRoot.iData + " ");
            return;
        }

        if(localRoot.leftChild != null)
        {
            displayLeaves(localRoot.leftChild);
        }

        if(localRoot.rightChild != null)
        {
            displayLeaves(localRoot.rightChild);
        }
    }
    // -------------------------------------------------------------
    public void siblingSubtree(char userInput, Node mainRoot)
    {
       int count = 0;
       boolean leftOrRight = false;

       Node current = mainRoot;               // start at root
       while(current.iData != userInput)        // while no match,
       {
           if(userInput < current.iData) {         // go left?
               current = current.leftChild;
               if(count == 0)
                   leftOrRight = false;
           }
           else {                            // or go right?
               current = current.rightChild;
               if(count == 0)
                   leftOrRight = true;
           }
           count++;
           if(current == null) {           // if no child,
               count = -1;                 // didn't find it
               break;
           }
       }

       if(count == -1) //User key not found
       {
           System.out.print("\t\t\t\tThe given character was not found.");
           return;
       }

       if(!leftOrRight) //User key on the left side
       {
           for(int i = 0; i < count; i++)
           {

               if(mainRoot.rightChild != null)
                   mainRoot = mainRoot.rightChild;
               else if(mainRoot.leftChild != null && i > 0)
                   mainRoot = mainRoot.leftChild;
               else
               {
                   System.out.println("\t\t\t\tNo sibling subtree was found for this letter.");
                   return;
               }
           }
           displaySubTree(mainRoot);
       }
        if(leftOrRight) //User key on the right side
        {
            for(int i = 0; i < count; i++)
            {
                if(mainRoot.leftChild != null)
                    mainRoot = mainRoot.leftChild;
                else if(mainRoot.rightChild != null && i > 0)
                    mainRoot = mainRoot.rightChild;
                else
                {
                    System.out.println("\t\t\t\tNo sibling subtree was found for this letter.");
                    return;
                }
            }
            displaySubTree(mainRoot);
        }
    }
    public void displaySubTree(Node subRoot)
    {
        Stack globalStack = new Stack();
        globalStack.push(subRoot);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while(!isRowEmpty)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<nBlanks; j++)
                System.out.print(' ');

            while(!globalStack.isEmpty())
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
            while(!localStack.isEmpty())
                globalStack.push( localStack.pop() );
        }  // end while isRowEmpty is false
        System.out.println(
                "......................................................");
    }  // end displayTree()
}  // end class Tree

////////////////////////////////////////////////////////////////
