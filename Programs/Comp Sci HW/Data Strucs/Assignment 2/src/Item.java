

public class Item {
    int key;
    int timeStamp;

    public Item(int key, int timeStamp)
    {
        this.key = key;
        this.timeStamp = timeStamp;
    }

    public int getKey() {return key;}
    public int getTimeStamp() {return timeStamp;}

    public String toString()
    {
            return String.format("%d:%d", getKey(), getTimeStamp());

    }
}
