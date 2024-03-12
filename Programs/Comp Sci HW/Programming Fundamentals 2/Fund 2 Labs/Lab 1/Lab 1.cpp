#include <iostream>
#include <string>
#include <fstream>

using namespace std;

void howManyStudents(int[], int, int);
int unhappyGrads(string[], int[], int);
int happyUndergrads(string[], int[], int);
void changeVote(string, int, string[], int[], int);
void dupName(string[], int);

int main()
{
	const int SIZE = 20;

	string names[SIZE];
	string status[SIZE];
	int vote[SIZE];
	int surveyOption;
	string nameChange;
	int voteChange;

	ifstream infile;
	infile.open("Fall20_1137_Lab2.txt");

	int choice;

	for (int i = 0; i < SIZE; i++)
	{
		infile >> names[i];
		infile >> status[i];
		infile >> vote[i];
	}

	do
	{
		cout << endl;
		cout << "\tPlease choose an option: " << endl;
		cout << "\t\t1. How many students voted for a certain survey option." << endl;
		cout << "\t\t2. How many graduate students think the virtual classes are below Effective? " << endl;
		cout << "\t\t3. How many undergraduate students think the virtual classes are at or above Neutral?" << endl;
		cout << "\t\t4. A student would like to change their survey answer. " << endl;
		cout << "\t\t5. Is there at least one instance of duplicate names in this list? Display the name if applicable. " << endl;
		cout << "\t\t6. Exit" << endl;

		cin >> choice;

		switch (choice)
		{
		case 1: cout << "Please enter the survey option in 1-5: ";
			cin >> surveyOption;
			howManyStudents(vote, SIZE, surveyOption);
			break;
		case 2: cout << unhappyGrads(status, vote, SIZE) << " student(s) think that virtual classes are below Effective." << endl;
			break;
		case 3: cout << happyUndergrads(status, vote, SIZE) << " student(s) think that virtual classes are at or above Neutral." << endl;
			break;
		case 4: cout << "Please enter the name of the student: ";
			cin >> nameChange;
			cout << "Please enter the survey option in 1-5: ";
			cin >> voteChange;
			changeVote(nameChange, voteChange, names, vote, SIZE);
			break;
		case 5: dupName(names, SIZE);
			break;
		}
	}
	while (choice != 6);
}

void howManyStudents(int vote[], int s, int choice)
{
	int numOfVotes = 0;
	for (int i = 0; i < s; i++)
	{
		if (vote[i] == choice)
			numOfVotes++;
	}
	cout << numOfVotes << " student(s) voted for survey option " << choice << endl;
}
int unhappyGrads(string status[], int vote[], int s)
{
	int numOfVotes = 0;

	for (int i = 0; i < s; i++)
	{
		if (status[i] == "grad")
			if (vote[i] < 4)
				numOfVotes++;
	}
	return numOfVotes;
}
int happyUndergrads(string status[], int vote[], int s)
{
	int numOfVotes = 0;

	for (int i = 0; i < s; i++)
	{
		if (status[i] == "undergrad")
			if (vote[i] >= 3)
				numOfVotes++;
	}
	return numOfVotes;
}
void changeVote(string nameChange, int voteChange, string names[], int vote[], int s)
{
	int count = 0;

	for (int i = 0; i < s; i++)
	{
		if (names[i] == nameChange)
		{
			vote[i] = voteChange;
			cout << "Your survey vote has been updated.\n";
			count++;
		}

	}
	if (count == 0)
		cout << "Your name is not on the list.\n";
}
void dupName(string names[], int s)
{
	int count = 0;
	while (count != 20)
	{
		for (int i = 1; i < s; i++)
		{
			if (i == count)
				i++;
			if (names[count] == names[i])
			{
				cout << "There is at least 1 duplicate name on the list.\n";
				cout << "The instance found is: " << names[count];
				return;
			}		
		}
		count++;
	}
}