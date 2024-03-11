//Aidan Johnson 1890052

package com.company;
import java.util.Scanner;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String word;
        char currentLetter;
        Tree theTree = new Tree();

        Scanner input = new Scanner(System.in);

        System.out.print("\n\tEnter a word in all capitals to create the Binary Search Tree with: ");
        word = input.next();

        theTree.createQueue(word.length());

        for(int i = 0; i < word.length(); i++)
        {
            currentLetter = word.charAt(i);
            theTree.specialInsert(currentLetter);
        }
        System.out.println("\n\tThe Binary Search Tree with the requirements followed is:");
        theTree.displayTree();
        System.out.println("\n\tThe Queue with possible letters is:");
        theTree.displayDeniedQueue();

        //Bonus
        theTree.findHeight(theTree.root, 0);
        theTree.traverse(2);

        // Part 2
        System.out.println("\tThe Binary Search Tree with the Queue re-added is:");
        theTree.insertQueue();
        theTree.displayTree();
    }
}


////////////////////////////////////////////////////////////////
class Node
{
    public int iData;              // data item (key)
    public int height;             // node height
    public Node leftChild;         // this node's left child
    public Node rightChild;        // this node's right child
    public Node parent;            // this node's parent

    public void displayNode()      // display ourself
    {
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(height);
        System.out.print("} ");
    }
}  // end class Node
////////////////////////////////////////////////////////////////
class Tree {
    public Node root;             // first node of tree
    private Queue deniedQueue;
    private nodeQueue findQueue;

    // -------------------------------------------------------------
    public Tree()                  // constructor
    {
        root = null;
    }            // no nodes in tree yet

    // -------------------------------------------------------------
    public Node find(char key)      // find node with given key
    {                           // (assumes non-empty tree)
        Node current = root;               // start at root
        while (current.iData != key)        // while no match,
        {
            if (key < current.iData)         // go left?
                current = current.leftChild;
            else                            // or go right?
                current = current.rightChild;
            if (current == null)             // if no child,
                return null;                 // didn't find it
        }
        return current;                    // found it
    }  // end find()

    // -------------------------------------------------------------
    public void leftInsert(Node parent, char id)
    {
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        parent.leftChild = newNode;
        return;

    }  // end insert()
    public void rightInsert(Node parent, char id)
    {
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        parent.rightChild = newNode;
        return;

    }  // end insert()

    public void specialInsert(char id) {
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        if (root == null)                // no node in root
            root = newNode;
        else                          // root occupied
        {
            Node current = root;       // start at root
            Node parent;
            while (true)                // (exits internally)
            {
                parent = current;
                if(id <= current.iData)  // go left?
                {
                    current = current.leftChild;
                    if(current == null)  // if end of the line,
                    {                 // insert on left
                        if(vowel(id))
                        {
                            if( uncleCheck(parent) && vowel(parent.iData))
                            {
                                if( vowel(uncle(parent).iData)) {
                                    parent.leftChild = newNode;
                                    newNode.parent = parent;
                                    return;
                                }
                                else
                                {
                                    deniedQueue.insert(id);
                                    return;
                                }
                            }
                            else if (vowel(parent.iData))
                            {
                                deniedQueue.insert(id);
                                return;
                            }
                            else
                            {
                                parent.leftChild = newNode;
                                newNode.parent = parent;
                                return;
                            }
                        }
                        else if (parent.iData != newNode.iData && !(vowel(newNode.iData)))
                        {
                            parent.leftChild = newNode;
                            newNode.parent = parent;
                            return;
                        }
                        else
                        {
                            deniedQueue.insert(id);
                            return;
                        }
                    }

                }  // end if go left
                else                    // or go right?
                {
                    current = current.rightChild;
                    if(current == null)  // if end of the line,
                    {                 // insert on left
                        if(vowel(id))
                        {
                            if( uncleCheck(parent) && vowel(parent.iData))
                            {
                                if( vowel(uncle(parent).iData)) {
                                    parent.rightChild = newNode;
                                    newNode.parent = parent;
                                    return;
                                }
                                else
                                {
                                    deniedQueue.insert(id);
                                    return;
                                }
                            }
                            else if (vowel(parent.iData))
                            {
                                deniedQueue.insert(id);
                                return;
                            }
                            else
                            {
                                parent.rightChild = newNode;
                                newNode.parent = parent;
                                return;
                            }
                        }
                        else if (parent.iData != newNode.iData && !(vowel((char) newNode.iData)))
                        {
                            parent.rightChild = newNode;
                            newNode.parent = parent;
                            return;
                        }
                        else
                        {
                            deniedQueue.insert(id);
                            return;
                        }
                    }
                }  // end else go right
            }  // end while
        }  // end else not root
    }  // end insert()

    // -------------------------------------------------------------
    public boolean delete(char key) // delete node with given key
    {                           // (assumes non-empty list)
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while (current.iData != key)        // search for node
        {
            parent = current;
            if (key < current.iData)         // go left?
            {
                isLeftChild = true;
                current = current.leftChild;
            } else                            // or go right?
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null)             // end of the line,
                return false;                // didn't find it
        }  // end while
        // found node to delete

        // if no children, simply delete it
        if (current.leftChild == null &&
                current.rightChild == null) {
            if (current == root)             // if root,
                root = null;                 // tree is empty
            else if (isLeftChild)
                parent.leftChild = null;     // disconnect
            else                            // from parent
                parent.rightChild = null;
        }

        // if no right child, replace with left subtree
        else if (current.rightChild == null)
            if (current == root)
                root = current.leftChild;
            else if (isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;

            // if no left child, replace with right subtree
        else if (current.leftChild == null)
            if (current == root)
                root = current.rightChild;
            else if (isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;

        else  // two children, so replace with inorder successor
        {
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if (current == root)
                root = successor;
            else if (isLeftChild)
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
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;   // go to right child
        while (current != null)               // until no more
        {                                 // left children,
            successorParent = successor;
            successor = current;
            current = current.leftChild;      // go to left child
        }
        // if successor not
        if (successor != delNode.rightChild)  // right child,
        {                                 // make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    // -------------------------------------------------------------
    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal:  \n");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
        }
        System.out.println();
    }

    // -------------------------------------------------------------
    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

    // -------------------------------------------------------------
    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            localRoot.displayNode();
            System.out.print("\n");
            inOrder(localRoot.rightChild);
        }
    }

    // -------------------------------------------------------------
    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }

    // -------------------------------------------------------------
    public void displayTree() {
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
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if (temp.leftChild != null ||
                            temp.rightChild != null)
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
    }  // end displayTree()

    public void createQueue(int s){deniedQueue = new Queue(s); findQueue = new nodeQueue(s);}

    private boolean vowel(char id){
        if(id == 'A' || id == 'E' || id == 'I' || id == 'O' || id == 'U' ) { return true; }
        else{ return false; }
    } //Checks if value is vowel or consonant

    private Node uncle (Node id)
    {
       Node current = root;
       Node parent;
       while(true)
       {
           parent = current;
           if(id.iData  <= current.iData && current.leftChild != null)
           {
               current = current.leftChild;
               if(current.iData == id.iData)
               {
                   return parent.rightChild;
               }
           }
           else if(id.iData > current.iData && current.rightChild != null)
           {
               current = current.rightChild;
               if(current.iData == id.iData)
               {
                   return parent.leftChild;
               }
           }
       }
    }

    private boolean uncleCheck (Node id)
    {
        if(id.parent != null)
        {
            if(id.iData <= id.parent.iData){return id.parent.rightChild != null;}
            else { return id.parent.leftChild != null; }
        }
        else return false;
    }

    public void displayDeniedQueue(){deniedQueue.displayQueue();}

    public void insertQueue()
    {
        Node current;
        findQueue.insert(root);
        while(!deniedQueue.isEmpty())
        {
            current = findQueue.remove();

            if(current.leftChild != null)
            {
                findQueue.insert(current.leftChild);
            }
            else
            {
                leftInsert(current, deniedQueue.remove());
                findQueue.insert(current.leftChild);
            }

            if(deniedQueue.isEmpty()) {break;}

            if(current.rightChild != null)
            {
                findQueue.insert(current.rightChild);

            }
            else
            {
                rightInsert(current, deniedQueue.remove());
                findQueue.insert(current.rightChild);
            }
        }
    }

    public int findHeight(Node current, int height)
    {

        if(current.leftChild != null && current.rightChild != null)
        {
            int child1 = findHeight(current.leftChild, height);
            int child2 = findHeight(current.rightChild, height);

            if(child1 >= child2){current.height = child1 + 1;}
            else { current.height = child2 + 1;}
        }
        else if(current.leftChild != null)
        {
            current.height = findHeight(current.leftChild, height) + 1;
        }
        else if(current.rightChild != null)
        {
            current.height = findHeight(current.rightChild, height) + 1;
        }
        else
        {
            current.height = 0;
            return current.height;
        }
        return current.height;
    }


// -------------------------------------------------------------
}// end class Tree
////////////////////////////////////////////////////////////////

class Queue
{
    private int maxSize;
    private char[] queArray;
    private int front;
    private int rear;
    private int nItems;
    //--------------------------------------------------------------
    public Queue(int s)          // constructor
    {
        maxSize = s;
        queArray = new char[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }
    //--------------------------------------------------------------
    public void insert(char j)   // put item at rear of queue
    {
        if(rear == maxSize-1)         // deal with wraparound
            rear = -1;
        queArray[++rear] = j;         // increment rear and insert
        nItems++;                     // one more item
    }
    //--------------------------------------------------------------
    public char remove()         // take item from front of queue
    {
        char temp = queArray[front++]; // get value and incr front
        if(front == maxSize)           // deal with wraparound
            front = 0;
        nItems--;                      // one less item
        return temp;
    }
    //--------------------------------------------------------------
    public char peekFront()      // peek at front of queue
    {
        return queArray[front];
    }
    //--------------------------------------------------------------
    public boolean isEmpty()    // true if queue is empty
    {
        return (nItems==0);
    }
    //--------------------------------------------------------------
    public boolean isFull()     // true if queue is full
    {
        return (nItems==maxSize);
    }
    //--------------------------------------------------------------
    public int size()           // number of items in queue
    {
        return nItems;
    }
    public void displayQueue()
    {
        System.out.print("\n\t\tQueue (front -> rear): ");

        for(int i = 0; i < maxSize; i++)
        {
            System.out.printf("%s ", queArray[i]);
        }
        System.out.print("\n");
    }
//--------------------------------------------------------------
}  // end class Queue

class nodeQueue
{
    private int maxSize;
    private Node[] queArray;
    private int front;
    private int rear;
    private int nItems;
    //--------------------------------------------------------------
    public nodeQueue(int s)          // constructor
    {
        maxSize = s;
        queArray = new Node[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }
    //--------------------------------------------------------------
    public void insert(Node j)   // put item at rear of queue
    {
        if(rear == maxSize-1)         // deal with wraparound
            rear = -1;
        queArray[++rear] = j;         // increment rear and insert
        nItems++;                     // one more item
    }
    //--------------------------------------------------------------
    public Node remove()         // take item from front of queue
    {
        Node temp = queArray[front++]; // get value and incr front
        if(front == maxSize)           // deal with wraparound
            front = 0;
        nItems--;                      // one less item
        return temp;
    }
    //--------------------------------------------------------------
    public Node peekFront()      // peek at front of queue
    {
        return queArray[front];
    }
    //--------------------------------------------------------------
    public boolean isEmpty()    // true if queue is empty
    {
        return (nItems==0);
    }
    //--------------------------------------------------------------
    public boolean isFull()     // true if queue is full
    {
        return (nItems==maxSize);
    }
    //--------------------------------------------------------------
    public int size()           // number of items in queue
    {
        return nItems;
    }
    public void displayQueue()
    {
        System.out.print("\n\t\tQueue (front -> rear): ");

        for(int i = 0; i < maxSize; i++)
        {
            System.out.printf("%s ", queArray[i]);
        }
    }
//--------------------------------------------------------------
}  // end class Queue