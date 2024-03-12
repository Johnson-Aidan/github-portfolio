//Aidan Johnson 1890052
import java.util.Scanner;
import java.util.Random;

public class assignment {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int userSize;
        int maxKey = 0;
        Random randInt  = new Random();

        //User input
        do{
            System.out.println("\tPlease enter a data size, not exceeding 20");
            userSize = input.nextInt();
        }while(userSize > 20);

        Item[] items = new Item[userSize];
        Item[] outputItems = new Item[userSize];
        StackX stack1 = new StackX(userSize);
        StackX stack2 = new StackX(userSize);
        Queue theQueue = new Queue(userSize);

        //Initializing Items
        for(int i = 0; i < userSize; i++)
        {
            int key = randInt.nextInt(10)+1;
            stack1.push(items[i] = new Item(key, i+1));
            if(items[i].getKey() > maxKey)
            {
                maxKey = items[i].getKey();
            }
        }

        //Sorting Max Key
        for(int i = 0; i < userSize; i++)
        {
            popAndPush(maxKey, stack1, stack2, theQueue);
            maxKey = queueToStack(stack1, theQueue);
            progressStatus(userSize, outputItems, stack1, stack2, theQueue);
        }

        // Pushing to output array
        for(int i = 0; i < userSize; i++)
        {
            outputItems[i] = stack2.peek();
            stack2.pop();
            progressStatus(userSize, outputItems, stack1, stack2, theQueue);
        }

    }

    public static int queueToStack(StackX stack1, Queue queue)
    {
        int maxKey = 0;
        while(!queue.isEmpty())

            if(queue.peekFront()!= null && queue.peekFront().key > maxKey)
            {
                maxKey = queue.peekFront().key;
                stack1.push(queue.remove());
            }
            else if(queue.peekFront()!= null)
            {
                stack1.push(queue.remove());
            }
        return maxKey;
    }

    public static void popAndPush(int maxKey, StackX stack1, StackX stack2, Queue queue)
    {
        while(!stack1.isEmpty())
        {
            if(stack1.peek().key == maxKey)
            {
                stack2.push(stack1.pop());
            }
            else if(stack1.peek().key != maxKey)
            {
                queue.insert(stack1.pop());
            }
        }
    }
    public static void progressStatus(int userSize, Item[] output, StackX stack1, StackX stack2, Queue queue)
    {
        System.out.println("\n\n\t- - - - - Current Progress - - - - -");
        stack1.displayStack("Stack1");
        stack2.displayStack("Stack2");
        queue.displayQueue();
        System.out.print("\nOutputArray: ");
        for(int i = 0; i <  userSize; i++)
        {
            System.out.printf("%s ", output[i]);
        }
    }
}
