#include <string>
#include <iostream>
#include <fstream>
using namespace std;

void getName(string&);
void getVote(int&);
void displayStat(string);
void findStudentVote(string, string);
string voteMeansWhat(int);

int main()
{
    ofstream outputFile;
    outputFile.open("surveyResults.txt");
    string name;
    int vote;
    int runProg;
    string fileName = "surveyResults.txt";

    do
    {
        cout << "\nPlease select an option:\n";
        cout << "1.   Enter Data\n";
        cout << "2.   Quit\n";
        cin >> runProg;

        if (runProg == 1)
        {
            getName(name);
            getVote(vote);

            outputFile << name << " " << vote << endl;
        }

    }
    while (runProg != 2);

    cout << "Quitting program...\n" << endl;
    outputFile.close();
    
    cout << "The Results of the Survey is:\n";

    displayStat(fileName);
    findStudentVote(fileName, name);

    cin.get();
    cin.get();
    return 0;
}


void getName(string& nameInput)
{
    cout << "\nPlease enter the first name (one-word) and press enter: ";

    cin >> nameInput;
}


void getVote(int& voteInput)
{
    cout << "\nPlease rank the cafeteria food by choosing an option:\n";
    cout << "1.       Poor\n";
    cout << "2.       Below Satisfactory\n";
    cout << "3.       Neutral\n";
    cout << "4.       Satisfactory\n";
    cout << "5.       Good\n";

    cin >> voteInput;
}


void displayStat(string fileName)
{
    ifstream infile;
    infile.open(fileName);
    int poor = 0;
    int belowS = 0;
    int neut = 0;
    int satis = 0;
    int good = 0;
    int didNotDo = 0;
    string nameOutput;
    int voteOutput;

    while (infile >> nameOutput) // Searches file for the name
    {
        infile >> voteOutput; // Searches file for the age too
        switch (voteOutput)
        {
            case 1: poor++;
                break;
            case 2: belowS++;
                break;
            case 3: neut++;
                break;
            case 4: satis++;
                break;
            case 5: good++;
                break;
            default: didNotDo++;
        }
    }
  
    cout << poor << " student(s) vote for option 1.\n";
    cout << belowS << " student(s) vote for option 2.\n";
    cout << neut << " student(s) vote for option 3.\n";
    cout << satis << " student(s) vote for option 4.\n";
    cout << good << " student(s) vote for option 5.\n";
    cout << didNotDo << " students(s) did not take the survey.\n" << endl;


    infile.close();
}


void findStudentVote(string fileName, string name)
{
    ifstream infile;
    infile.open(fileName);
    int poor = 0;
    int belowS = 0;
    int neut = 0;
    int satis = 0;
    int good = 0;
    int voteOutput;

    while (infile >> name) // Searches file for the name
    {
        infile >> voteOutput; // Searches file for the age too

        cout << name << voteMeansWhat(voteOutput);
    }
    infile.close();
}

string voteMeansWhat(int voteOutput)
{
    string voteResponse;
    if (voteOutput == 1)
    {
        voteResponse = " thinks the quality of the cafeteria is poor.\n";

    }
    else if (voteOutput == 2)
    {
        voteResponse = " thinks the quality of the cafeteria is below satisfactory.\n";
    }
    else if (voteOutput == 3)
    {
        voteResponse = " is neutral on the quality of the cafeteria.\n";
    }
    else if (voteOutput == 4)
    {
        voteResponse = " thinks the quality of the cafeteria is satisfactory.\n";
    }
    else if (voteOutput == 5)
    {
        voteResponse = " thinks the quality of the cafeteria is good.\n";
    }
    else
    {
        voteResponse = " did not participate in our survey.\n";
    }

    return voteResponse;
}