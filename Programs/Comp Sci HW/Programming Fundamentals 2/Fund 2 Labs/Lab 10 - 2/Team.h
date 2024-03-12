#ifndef TEAM_H
#define TEAM_H
#include <string>
using namespace std;

class Team
{
private:
    string leader;
    string* teamMembers;
    int maxMembers;
    int numMembers;

public:

    // The constructor:
    Team(string, int);

    // The prototype of copy constructor goes here:
    Team(const Team&);

    // The prototype of operator= goes here:
    Team operator=(const Team&);

    // The prototype of operator+ goes here:
    Team operator+(const Team&);

    void setLeader(string);
    void setTeamMembers(string*);
    void setNumMembers(int);
    string getLeader() const
    {
        return leader;
    }
    int getNumMembers() const
    {
        return numMembers;
    }
    void addTeamMember(string); // Adds members and increments the numMembers
    void displayTeamInfo() const;

};
#endif
