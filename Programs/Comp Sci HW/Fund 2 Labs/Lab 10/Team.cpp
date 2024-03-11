#include "Team.h"
#include <iostream>
#include <string>
using namespace std;

Team::Team(string lname, int maxNum )
{
    leader = lname;
    maxMembers = maxNum;
    teamMembers = new string[maxNum];
    numMembers = 0;
}

// Define the copy constructor here:
Team::Team(const Team& right)
{
    leader = right.leader;
    maxMembers = right.maxMembers;
    numMembers = right.numMembers;
    for (int i = 0; i < this->numMembers; i++)
    {
        this->teamMembers[i] = right.teamMembers[i];
    }
}

// Define the operator= here:
Team Team::operator=(const Team& right)
{
    if (this != &right)
    {
        delete[] right.teamMembers;
        leader = right.leader;
        numMembers = right.numMembers;
        maxMembers = right.maxMembers;
        teamMembers = new string[maxMembers];
        for (int i = 0; i < maxMembers; i++)
            teamMembers[i] = right.teamMembers[i];
    }
    return *this;
}

// Define the operator+ here:
Team Team::operator+(const Team& right)
{
    Team sumTeam(leader, maxMembers + right.maxMembers);
    sumTeam.numMembers = numMembers + right.numMembers;
    int i = 0;
    for (i = 0; i < maxMembers; i++)
    {
        sumTeam.teamMembers[i] = teamMembers[i];
        for (int j = 0; j < right.numMembers; j++)
            sumTeam.teamMembers[i++] = right.teamMembers[j];
    }
    return sumTeam;
}

void Team::setLeader(string newLName)
{
    leader = newLName;
}
void Team::setTeamMembers(string * newTeam)
{
    teamMembers = newTeam;
}
void Team::setNumMembers(int s)
{
    numMembers = s;
}
void Team::addTeamMember(string name)
{
    teamMembers[numMembers] = name;
    numMembers++;
}

void Team::displayTeamInfo() const
{
    cout << "Team Leader: " << leader << endl;
    cout << "Team Members: ";
    for ( int i =0; i < numMembers; i++ )
        cout << teamMembers[i] << " ";
    cout << endl;
}


