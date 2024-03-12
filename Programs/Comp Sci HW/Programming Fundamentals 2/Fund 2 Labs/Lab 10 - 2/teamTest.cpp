#include "Team.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

int main()
{
    vector <Team> teamsVec;
    string name;
    int size, numMembers;

    cout << "Enter the max size for all teams: (You can enter 10) ";
    cin >> size;

    ifstream infile("teamsInfo.txt");

    while (infile >> name)
    {
        infile >> numMembers;

        Team tempTeam(name, size);

        for (int i = 0; i < numMembers; i++)
        {
            infile >> name;
            tempTeam.addTeamMember(name);
        }
        teamsVec.push_back(tempTeam);
    }

    for (Team t : teamsVec)
        t.displayTeamInfo();

    cout << "*********************** Lab 11 ************************" << endl;
    // ****************** Lab 11 *********************************

    // Instantiate a Team object called someTeam to be a clone of
    // the first Team object in your vector.
    // This is a test for your copy constructor:
    Team someTeam = teamsVec[0];

    // Use someTeam object and call displayTeamInfo and verify that this
    // object is indeed the clone of the first object of your vector.
       someTeam.displayTeamInfo();

    // Assign the last object of the vector to someTeam using =.
    // This would be a test for your operator= function.
       teamsVec.push_back(someTeam);

    // Use someTeam to call the displayTeamInfo to verify that your someTeam
    // object is now identical to the LAST Team object of the vector.
       someTeam.displayTeamInfo();


    // Write the statement that adds the last object of the vector to the first
    // object of the vector and stores the resultant object in someTeam.
    // We want the first object be the left operand and the last be the second
    // operand.
    // This would be the test for your operator+ function.
    // Notice this would ultimately be the test for both of your overloaded
    // operators, since you add and then assign.
       someTeam = teamsVec[0] + teamsVec[teamsVec.size() - 1];
    // One last time call the displayTeamInfo function using someTeam object to
    // verify the effect of your operator+ function.
       someTeam.displayTeamInfo();


    return 0;
}