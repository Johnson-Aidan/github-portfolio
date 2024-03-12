import java.util.Scanner;

public class rectangle
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        rect rectangle = new rect();

        System.out.print("Please input a width:");
        rectangle.setWidth(input.nextInt());

        System.out.print("Please input a height:");
        rectangle.setHeight(input.nextInt());

        int area = (rectangle.getWidth())*(rectangle.getHeight());

        System.out.printf("%s%d", "The area of your rectangle is: ", area);
    }
}

class rect
{
    public int rectWidth;

    public void setWidth(int width) {
        this.rectWidth = width;
    }

    public int getWidth() {
        return rectWidth;
    }

    public int rectHeight;

    public void setHeight(int height) {
        this.rectHeight = height;
    }

    public int getHeight() {
        return rectHeight;
    }
}