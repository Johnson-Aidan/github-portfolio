//Aidan Johnson 1890052

package com.company;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String word;
        char currentLetter, userInput, userVert;
        int userDirect;
        Tree theTree = new Tree();

        Scanner input = new Scanner(System.in);

        System.out.print("\tEnter a word in all capitals to create the Binary Search Tree with: ");
        word = input.next();

        Graph theGraph = new Graph(word.length());
        for(int i = 0; i < word.length(); i++)
        {
            currentLetter = word.charAt(i);
            theTree.insert(currentLetter, i);
        }

        do
        {
            System.out.println("\n\tMap the BST into: ");
            System.out.println("\t\t1. Directed Graph");
            System.out.println("\t\t2. Undirected Graph");
            userDirect = input.nextInt();
        }while(userDirect != 1 && userDirect != 2);

        theTree.directedTraverse(userDirect, theGraph);

        do{
            System.out.println("\n\tSelect an option:");
            System.out.println("\t\t1. Display the BST in a tree format.");
            System.out.println("\t\t2. Display the Vertex Array.");
            System.out.println("\t\t3. Display the Adjacency Matrix.");
            System.out.println("\t\t4. Given a vertex: Display ALL possible separate paths starting with that vertex in a Depth First Search Pattern. (BONUS)");
            System.out.println("\t\t5. Given a vertex: Display ALL its adjacent vertices (one edge apart).");
            System.out.println("\t\t6. Given a vertex: Display ALL the vertices that are two edges away from it.");
            System.out.print("\t\t7. Exit\n\t\t");

            userInput = input.next().charAt(0);

            switch(userInput)
            {
                case '1': theTree.displayTree();
                    break;

                case '2':
                    for(int i = 0; i < word.length(); i++)
                    {
                        theGraph.displayVertex(i);
                    }
                    break;

                case '3': theGraph.displayAdjacentArray();
                    break;

                case '4':System.out.print("\t\tThis was not attempted.");
                    break;

                case '5':System.out.print("\t\tEnter the letter: ");
                    userVert = input.next().charAt(0);
                    theGraph.firstAdjacentVertex(theGraph.findVertex(userVert), theGraph.findVertex(userVert));
                    break;

                case '6':System.out.print("\t\tEnter the letter: ");
                    userVert = input.next().charAt(0);
                    theGraph.secondAdjacentVertex(theGraph.findVertex(userVert));
                    break;

                case '7': System.out.println("\n\n\t\tThank you for all the great classes, Dr. Zand! I'll see you next semester as your SI!");
                    break;

                default: System.out.print("\t\tInvalid input - Please input a number 1-7.");
            }

        }while(userInput != '7');

    }
}
////////////////////////////////////////////////////////////////
class Node
{
    public char iData;              // data item (key)
    public int dData;
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
    public void insert(char id, int dId)
    {
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        newNode.dData = dId;
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
    public boolean delete(char key) // delete node with given key
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
                "......................................................");
    }  // end displayTree()
    // -------------------------------------------------------------
    public void directedTraverse(int traverseType, Graph theGraph)
    {
        switch(traverseType)
        {
            case 1:  directedTree(root, root, theGraph);
                break;
            case 2:undirectedTree(root, root, theGraph);
                break;
        }
        System.out.println();
    }
    // -------------------------------------------------------------
    public void directedTree(Node localRoot, Node localRootParent, Graph theGraph)
    {
        if(localRoot != null)
        {
            directedTree(localRoot.leftChild, localRoot, theGraph);
            theGraph.addVertex(localRoot.iData, localRoot.dData);
            if(localRootParent != localRoot)
            {
                theGraph.addDirectedEdge(localRootParent.dData, localRoot.dData);
            }
            directedTree(localRoot.rightChild, localRoot, theGraph);

        }
    }
    public void undirectedTree(Node localRoot, Node localRootParent, Graph theGraph)
    {
        if(localRoot != null)
        {
            undirectedTree(localRoot.leftChild, localRoot, theGraph);
            theGraph.addVertex(localRoot.iData, localRoot.dData);
            if(localRootParent != localRoot)
            {
                theGraph.addUndirectedEdge(localRootParent.dData, localRoot.dData);
            }
            undirectedTree(localRoot.rightChild, localRoot, theGraph);
        }
    }
// -------------------------------------------------------------
}  // end class Tree
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
    private int MAX_VERTS;
    private Vertex vertexList[]; // list of vertices
    private int adjMat[][];      // adjacency matrix
    private int nVerts;          // current number of vertices
    private StackX theStack;
    // ------------------------------------------------------------
    public Graph(int size)               // constructor
    {
        MAX_VERTS = size;
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
    public void addVertex(char lab, int index)
    {
        vertexList[index] = new Vertex(lab);
        nVerts++;
    }
    // ------------------------------------------------------------
    public void addUndirectedEdge(int start, int end)
    {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }
    // ------------------------------------------------------------
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
    public void displayAdjacentArray()
    {
        System.out.print("\t");
        for(int i = 0; i < MAX_VERTS; i++)
        {
            System.out.print(" " + vertexList[i].label);
        }
        System.out.println("\n");
        for(int i = 0; i < MAX_VERTS; i++)
        {
            System.out.print(vertexList[i].label + "\t");
            for(int j = 0; j < MAX_VERTS; j++)
            {
                System.out.print(" " + adjMat[i][j]);
            }
            System.out.println();
        }
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
    //------------------------------------------------------------
    public int findVertex(char targetVert)
    {
        for(int i = 0; i < MAX_VERTS; i++)
        {
            if(targetVert == vertexList[i].label)
            {
                return i;
            }
        }
        return -1;
    }
    //------------------------------------------------------------
    public void firstAdjacentVertex(int targetVertIndex, int startVertIndex)
    {
        boolean notFound = true;
        if(targetVertIndex == -1)
        {
            System.out.println("\t\tVertex not found.");
            return;
        }
        for(int i = 0; i < MAX_VERTS; i++)
        {
            if(adjMat[targetVertIndex][i] == 1 && i != startVertIndex)
            {
                System.out.print(vertexList[i].label);
                notFound = false;
            }
        }
        if(notFound)
        {
            System.out.println("\t\tNo adjacent vertex's found.");
        }
    }
    //------------------------------------------------------------
    public void secondAdjacentVertex(int targetVertIndex)
    {
        boolean notFound = true;
        if(targetVertIndex == -1)
        {
            System.out.println("\t\tVertex not found.");
            return;
        }
        for(int i = 0; i < MAX_VERTS; i++)
        {
            if(adjMat[targetVertIndex][i] == 1)
            {
                firstAdjacentVertex(i, targetVertIndex);
                notFound = false;
            }
        }
        if(notFound)
        {
            System.out.println("\t\tNo adjacent vertex's found.");
        }
    }
// ------------------------------------------------------------
}  // end class Graph
////////////////////////////////////////////////////////////////