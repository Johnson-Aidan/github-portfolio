#include <string>
#include <iostream>
#include <fstream>
using namespace std;

string theSuffix(int);
int whatCentury(int);

int main()
{
    ifstream infile;
    infile.open("years.txt");
    int yearList[20];
      
    for (int count = 0; infile >> yearList[count] && count <= 20; count++)
    {
        cout << "#" << count << ". " << yearList[count] << " is in the " << whatCentury(yearList[count]) << theSuffix(whatCentury(yearList[count])) << " century." << endl;   
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

string theSuffix(int centuryPosition)
{
    string suffix;

    switch (centuryPosition)
    {
    case 1:
    case 21: suffix = "st";
        break;
    case 2:
    case 22: suffix = "nd";
        break;
    case 3:
    case 23: suffix = "rd";
        break;
    default: suffix = "th";
        break;
    }
    return suffix;
}