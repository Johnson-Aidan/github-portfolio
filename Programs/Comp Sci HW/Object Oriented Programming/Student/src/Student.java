// Student: Aidan Johnson
// ID: 1890052

public class Student{
    private String name;
    private int test1;
    private int test2;
    private int test3;
    private int average;
    private int highestTestScore;
    private boolean assitQualified = false;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTest1(int test) {
        this.test1 = test;
    }

    public int getTest1() {
        return test1;
    }

    public void setTest2(int test) {
        this.test2 = test;
    }

    public int getTest2() {
        return test2;
    }

    public void setTest3(int test) {
        this.test3 = test;
    }

    public int getTest3() {
        return test3;
    }

    public int calAverage() {
        average = (test1 + test2 + test3)/3;
        return average;
    }

    public int calHighertst3() {
        highestTestScore = test1;
        if(test2 > highestTestScore){
            highestTestScore = test2;}
        if(test3 > highestTestScore){
            highestTestScore = test3;}
        return highestTestScore;
    }

    public boolean assistantship() {
        if(getTest1() >= 90 || getTest2() >= 90 || getTest3() >= 90)
            assitQualified = true;
        return assitQualified;
    }
}
