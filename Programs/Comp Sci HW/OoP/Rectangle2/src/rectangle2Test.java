import java.util.Scanner;

public class rectangle2Test
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        rectangle2 rectangle = new rectangle2();

        System.out.print("Please input a width:");
        rectangle.setWidth(input.nextInt());

        System.out.print("Please input a height:");
        rectangle.setHeight(input.nextInt());

        int area = (rectangle.getWidth())*(rectangle.getHeight());

        System.out.printf("%s%d", "The area of your rectangle is: ", area);
    }
}