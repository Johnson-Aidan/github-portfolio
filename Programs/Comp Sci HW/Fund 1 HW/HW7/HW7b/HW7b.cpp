#include <string>
#include <iostream>
#include <fstream>
using namespace std;

int whatCentury(int);


int main()
{
    ifstream infile;
    infile.open("years.txt");
    const int centuryArray = 22;
    int yearList[20];
    int centuryCount[centuryArray];
    int centLoop = 0;

    for (int counter = 0; counter < centuryArray; counter++)
        centuryCount[counter] = 0;

    cout << "Century\tCount \n";

    for (int count = 0; infile >> yearList[count] && count <= 20; count++)
    {
        //cout << yearList[count] << " " << whatCentury(yearList[count]) << endl;
        centuryCount[whatCentury(yearList[count])]++;
        
    }

    while (centLoop <= centuryArray)
    {
        if (centuryCount[centLoop] >= 1)
        {
            cout << centLoop << " \t" << centuryCount[centLoop] << endl;
            centLoop++;
        }
        else
            centLoop++;
    }
       
    infile.close();
    cin.get();
    cin.get();
    return 0;
}

int whatCentury(int yearList)
{
    int centuryPosition = (yearList / 100) + 1;
    return centuryPosition;
}