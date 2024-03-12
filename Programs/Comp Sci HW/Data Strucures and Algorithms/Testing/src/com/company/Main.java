package com.company;
import java.util.Scanner;
import java.util.*;
////////////////////////////////////////////////////////////////
class Node
{
    public char iData;              // data item (key)
    public int dData;           // data item
    public Node leftChild;         // this node's left child
    public Node rightChild;        // this node's right child
    public Node p; //this node's parent

    public void displayNode()      // display yourself
    {
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
    }
}  // end class Node
////////////////////////////////////////////////////////////////
class Tree {
    public Node root;             // first node of tree

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
    public void insert(char id, int dd) {
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        newNode.dData = dd;
        if (root == null)                // no node in root
            root = newNode;
        else                          // root occupied
        {
            Node current = root;       // start at root
            Node parent;
            while (true)                // (exits internally)
            {
                parent = current;
                if (id < current.iData)  // go left?
                {
                    current = current.leftChild;
                    if (current == null)  // if end of the line,
                    {                 // insert on left
                        parent.leftChild = newNode;
                        newNode.p = parent;
                        return;
                    }
                }  // end if go left
                else                    // or go right?
                {
                    current = current.rightChild;
                    if (current == null)  // if end of the line
                    {                 // insert on right
                        parent.rightChild = newNode;
                        newNode.p = parent;
                        return;
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
                System.out.print("\nInorder traversal:  ");
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
            System.out.print(localRoot.iData + " ");
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

    public void displayLeftSide(Node localRoot) {
        while (localRoot.leftChild != null) {
            displayLeftSide(localRoot.leftChild);
            break;
        }
        System.out.print(localRoot.iData);
    }

    public void displayLeaves(Node localRoot) {
        if (localRoot == null)
            return;

        if (localRoot.leftChild == null && localRoot.rightChild == null) {
            System.out.print(localRoot.iData);
            return;
        }

        if (localRoot.leftChild != null) {
            displayLeaves(localRoot.leftChild);
        }

        if (localRoot.rightChild != null) {
            displayLeaves(localRoot.rightChild);
        }
    }

}
// end class Tree
////////////////////////////////////////////////////////////////
class StackX
{
    private final int SIZE = 20;
    private int[] st;
    private int top;
    // ------------------------------------------------------------
    public StackX()           // constructor
    {
        st = new int[SIZE];    // make array
        top = -1;
    }
    // ------------------------------------------------------------
    public void push(int j)   // put item on stack
    { st[++top] = j; }
    // ------------------------------------------------------------
    public int pop()          // take item off stack
    { return st[top--]; }
    // ------------------------------------------------------------
    public int peek()         // peek at top of stack
    { return st[top]; }
    // ------------------------------------------------------------
    public boolean isEmpty()  // true if nothing on stack
    { return (top == -1); }
// ------------------------------------------------------------
}  // end class StackX
////////////////////////////////////////////////////////////////
class Vertex
{
    public char label;        // label (e.g. 'A')
    public boolean wasVisited;
    // ------------------------------------------------------------
    public Vertex(char lab)   // constructor
    {
        label = lab;
        wasVisited = false;
    }
// ------------------------------------------------------------
}  // end class Vertex
////////////////////////////////////////////////////////////////
class Graph
{
    private final int MAX_VERTS = 20;
    private Vertex vertexList[]; // list of vertices
    private int adjMat[][];      // adjacency matrix
    public int nVerts;          // current number of vertices
    private StackX theStack;
    // ------------------------------------------------------------
    public Graph()               // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
        // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for(int y=0; y<MAX_VERTS; y++)      // set adjacency
            for(int x=0; x<MAX_VERTS; x++)   //    matrix to 0
                adjMat[x][y] = 0;
        theStack = new StackX();
    }  // end constructor
    // ------------------------------------------------------------
    public void addVertex(char lab)
    {
        vertexList[nVerts++] = new Vertex(lab);
    }
    // ------------------------------------------------------------
    public void addEdge(int start, int end)
    {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }
    public void addDirectedEdge(int start, int end)
    {
        adjMat[start][end] = 1;
    }
    // ------------------------------------------------------------
    public void displayVertex(int v)
    {
        System.out.print(vertexList[v].label);
    }
    // ------------------------------------------------------------
    public void dfs()  // depth-first search
    {                                 // begin at vertex 0
        vertexList[0].wasVisited = true;  // mark it
        displayVertex(0);                 // display it
        theStack.push(0);                 // push it

        while( !theStack.isEmpty() )      // until stack empty,
        {
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex( theStack.peek() );
            if(v == -1)                    // if no such vertex,
                theStack.pop();
            else                           // if it exists,
            {
                vertexList[v].wasVisited = true;  // mark it
                displayVertex(v);                 // display it
                theStack.push(v);                 // push it
            }
        }  // end while

        // stack is empty, so we're done
        for(int j=0; j<nVerts; j++)          // reset flags
            vertexList[j].wasVisited = false;
    }  // end dfs
    // ------------------------------------------------------------
    // returns an unvisited vertex adj to v
    public int getAdjUnvisitedVertex(int v)
    {
        for(int j=0; j<nVerts; j++)
            if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
                return j;
        return -1;
    }  // end getAdjUnvisitedVertex()
    // ------------------------------------------------------------
    public void map(Node root, Graph graph, int type)
    {
        if (root != null)
        {
            graph.addVertex(root.iData);
            if (root.dData != 0)
            {
                if (type == 1)
                {
                    graph.addDirectedEdge(root.p.dData, root.dData);
                }
                else
                {
                    graph.addEdge(root.p.dData, root.dData);
                }

            }

        }
        if (root.leftChild != null) {
            map(root.leftChild, graph, type);
        }

        if (root.rightChild != null) {
            map(root.rightChild, graph, type);
        }

    }
    //-------------------------------------------------------------------
    public void displayVertexList(Graph graph)
    {
        System.out.println("The Vertex Array is: ");
        for (int j = 0; j < graph.nVerts; j++)
        {
            graph.displayVertex(j);
        }
    }
    //---------------------------------------------------------------------
    public void displayAdjMatrix(String userWord)
    {
        System.out.println("The Adjacency Matrix for the graph is: ");
        System.out.print("  ");
        for (int i = 0; i < nVerts; i++)
        {
            System.out.print(userWord.charAt(i));
            System.out.print(" ");
        }
        System.out.println();

        for (int j = 0; j < nVerts; j++)
        {
            System.out.print(userWord.charAt(j));
            System.out.print(" ");
            for (int k = 0; k < nVerts; k++)
            {
                System.out.print(adjMat[j][k]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    //-------------------------------------------------------
    public void findAdjVert(Tree tree, char userChar, String userWord)
    {
        int counter = 0;
        int userChardData = tree.find(userChar).dData;
        for (int i = 0; i < nVerts; i++)
        {
            if (adjMat[userChardData][i] == 1)
            {
                counter++;
                System.out.println(userChar + " is adjacent to " + userWord.charAt(i));
            }
        }
        if (counter == 0)
        {
            System.out.println("I'm sorry but no adjacent vertices were found.");
        }

    }
    //-----------------------------------------------------------------
    public void findTwoEdgesAway(Tree tree, char userChar, String userWord)
    {
        int counter = 0;
        int userChardData2 = tree.find(userChar).dData;
        for (int i = 0; i < nVerts; i++)
        {
            if (adjMat[userChardData2][i] == 1)
            {

                for (int j = 0; j < nVerts; j++)
                {
                    if (adjMat[i][j] == 1)
                    {
                        counter++;
                        System.out.println(userChar + " is two edges away from " + userWord.charAt(j));
                    }
                }
            }
        }
        if (counter == 0)
        {
            System.out.println("I'm sorry but no adjacent vertices were found.");
        }
    }
}  // end class Graph
////////////////////////////////////////////////////////////////

public class Main {

    public static void main(String[] args) {
        String userWord;
        Tree theTree = new Tree();

        System.out.println("Please enter a word in all caps to input into the BST: ");
        Scanner input = new Scanner(System.in);

        userWord = input.nextLine();
        userWord = userWord.toUpperCase(Locale.ROOT);

        for (int i = 0; i < userWord.length(); i++)
        {
            char iD = userWord.charAt(i);
            theTree.insert(iD, i);
        }

        System.out.println("Map the BST into: ");
        System.out.println("1. Directed Graph");
        System.out.println("2. Undirected Graph");

        Scanner input2 = new Scanner(System.in);
        int userGraph = input2.nextInt();
        Graph theGraph = new Graph();

        theGraph.map(theTree.root, theGraph, userGraph);




        Scanner input3 = new Scanner(System.in);
        int userChoice = 0;
        do {
            System.out.println();
            System.out.println("Please choose an option: ");
            System.out.println("1. Display the BST in a tree format");
            System.out.println("2. Display the Vertex Array");
            System.out.println("3. Display the Adjacency Matrix");
            System.out.println("4. Display ALL possible separate paths starting with that vertex in a Depth First Search pattern");
            System.out.println("5. Display ALL its adjacent vertices (one edge apart)");
            System.out.println("6. Display ALL the vertices that are two edges away from it");
            System.out.println("7. Exit");

            userChoice = input3.nextInt();


            switch (userChoice) {
                case 1:
                    theTree.displayTree();
                    break;
                case 2:
                    theGraph.displayVertexList(theGraph);
                    break;
                case 3:
                    theGraph.displayAdjMatrix(userWord);
                    break;
                case 4:
                    System.out.println("Sadly Ashwin did not attempt this option.");
                    break;
                case 5:
                    System.out.println("Please enter the Capital character you wish to search for: ");
                    Scanner input4 = new Scanner(System.in);
                    char userChar = input4.next().charAt(0);
                    theGraph.findAdjVert(theTree, userChar, userWord);
                    break;
                case 6:
                    System.out.println("Please enter the Capital character you wish to search for: ");
                    Scanner input5 = new Scanner(System.in);
                    char userChar2 = input5.next().charAt(0);
                    theGraph.findTwoEdgesAway(theTree, userChar2, userWord);
                    break;
                case 7:
                    System.out.println("Thank you! Have a nice day!");
                    break;

            }
        } while (userChoice != 7);


    }
}