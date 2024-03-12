class StackX
{
    private int maxSize;        // size of stack array
    private Item[] stackArray;
    private int top;            // top of stack
    //--------------------------------------------------------------
    public StackX(int s)         // constructor
    {
        maxSize = s;             // set array size
        stackArray = new Item[maxSize];  // create array
        top = -1;                // no items yet
    }
    //--------------------------------------------------------------
    public void push(Item j)    // put item on top of stack
    {
        stackArray[++top] = j;     // increment top, insert item
    }
    //--------------------------------------------------------------
    public Item pop()           // take item from top of stack
    {
        return stackArray[top--];  // access item, decrement top
    }
    //--------------------------------------------------------------
    public Item peek()          // peek at top of stack
    {
        return stackArray[top];
    }
    //--------------------------------------------------------------
    public boolean isEmpty()    // true if stack is empty
    {
        return (top == -1);
    }
    //--------------------------------------------------------------
    public boolean isFull()     // true if stack is full
    {
        return (top == maxSize-1);
    }
    //--------------------------------------------------------------
    public void displayStack(String s)
    {
        System.out.print("\n" + s + " (bottom -> top): ");
        for(int i = 0; i < maxSize; i++)
        {
            System.out.printf("%s ", stackArray[i]);
        }
    }

}