//Aidan Johnson
//1890052

public class Student {

    private String name;
    private int[] scores;

    public Student(String name, int[] scores)
    {
        this.name = name;
        this.scores = scores;
    }

    public void displayInfo()
    {
        System.out.print("\t\t\t\t" + name + "\t");

        for(int i = 0; i < scores.length; i++)
            System.out.print(scores[i] + " ");

        System.out.println();
    }
}
