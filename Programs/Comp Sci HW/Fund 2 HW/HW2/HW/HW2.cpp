#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>  
using namespace std;

struct Student
{
	string fullName;
	string ID;
};

const int rowSize = 10;
const int colSize = 10;
ifstream infile;

void vipAllocation(string[][colSize], int);
bool isFound(Student*, int, string);
void noPrefSeating(string[][colSize], string, int, vector <string>&);
void prefSeating(string[][colSize], string, int, int, vector <string> &);
void displaySeatingChart(string[][colSize]);
void displayTheVector(vector <string>);
int maxVIPRow(string[][colSize]);

int main()
{
	int userChoice;
	int numOfStudents;
	string eligStudentFile;
	int vipTicketNum;
	string stuRequestFile;
	string seatingChart[rowSize][colSize];
	string tempString;
	string stuID;
	int guestNum;
	int prefChoice;
	vector<string> notAccomodated;
	for (int i = 0; i < rowSize; i++)
	{
		for (int j = 0; j < colSize; j++)
		{
			seatingChart[i][j] = "000";
		}
	}

	cout << "\tPlease enter the number of students who are eligible to attend: ";
	cin >> numOfStudents;

	Student* nameIDList;
	nameIDList = new Student[numOfStudents];

	do
	{
		cout << "\n\tPlease enter the file-name containing the names of the eligible students: ";
		cin >> eligStudentFile;
		infile.open(eligStudentFile);
	} while (!infile);

	cout << "\n\t\tStudents Found:\n";

	for (int i = 0; i < numOfStudents; i++)
	{
		
		infile >> nameIDList[i].ID;
		infile >> nameIDList[i].fullName;
		infile >> tempString;
		nameIDList[i].fullName = nameIDList[i].fullName + " " + tempString;
		cout << "\t\t\t" << nameIDList[i].ID << " " << nameIDList[i].fullName << endl;
		if (!infile)
		{
			break;
		}
	}
	infile.close();

	cout << "\n\tPlease enter the number of VIP tickets: ";
	cin >> vipTicketNum;
	vipAllocation(seatingChart, vipTicketNum);


	do {
		cout << endl;
		cout << "\tPlease choose an option: " << endl;
		cout << "\t\t1. Register for the event:" << endl;
		cout << "\t\t2. Display the seating chart." << endl;
		cout << "\t\t3. Which row has the most number of VIP tickets?" << endl;
		cout << "\t\t4. Display the list of IDs of those who could not be registered." << endl;
		cout << "\t\t5. Exit." << endl;

		cin >> userChoice;

		switch (userChoice)
		{
		case 1: do
			{
				cout << "\n\tPlease enter the file name: ";
				cin >> stuRequestFile;
				infile.open(stuRequestFile);
			} while (!infile);
			do
			{
				infile >> stuID;
				infile >> guestNum;
				infile >> prefChoice;

				if (isFound(nameIDList, numOfStudents, stuID) == true)
				{
					if (guestNum >= 0 && guestNum <= 5 && prefChoice >= -1 && prefChoice <= 9)
					{
						if (prefChoice == -1)
						{
							noPrefSeating(seatingChart, stuID, guestNum, notAccomodated);
						}
						else if (prefChoice >= 0 && prefChoice <= 9)
						{
							prefSeating(seatingChart, stuID, guestNum, prefChoice, notAccomodated);
						}
					}
					else
					{
						notAccomodated.push_back(stuID);
					}
				}
				else 
				{
					notAccomodated.push_back(stuID);
				}
			} while (infile);
			if (numOfStudents > notAccomodated.size())
				notAccomodated.pop_back();
			break;

		case 2: displaySeatingChart(seatingChart);
			break;

		case 3:  cout << "\t\t\tThe row with the most VIPs is: " << maxVIPRow(seatingChart);
			break;

		case 4: displayTheVector(notAccomodated);
			break;

		case 5: break;
		}
	} while (userChoice != 5);

	return 0;
}

void vipAllocation(string seatingChart[][colSize], int vipNum)
{
	const int SIZE = 9;
	const int minSIZE = 0;
	int randRow;
	int randCol;
	unsigned seed = time(0);
	srand(seed);
	for (int i = 0; i < vipNum; i++)
	{
		randRow = (rand() % (SIZE - minSIZE + 1)) + minSIZE;
		randCol = (rand() % (SIZE - minSIZE + 1)) + minSIZE;
		if (seatingChart[randRow][randCol] == "000")
			seatingChart[randRow][randCol] = "VIP";
	}
}

void displaySeatingChart(string seatingChart[][colSize])
{
	cout << "\t\tThe current seating arrangement is:\n\t\t\t";
	for (int i = 0; i < rowSize; i++)
	{
		for (int j = 0; j < colSize; j++)
		{
			cout << seatingChart[i][j] << " ";
		}
		cout << "\n\t\t\t";
	}
}

int maxVIPRow(string seatingChart[][colSize])
{
	int vipCounter[rowSize];
	int counter = 0;

	for (int i = 0; i < rowSize; i++)
	{
		vipCounter[i] = 0;
		for (int j = 0; j < colSize; j++)
		{
			if (seatingChart[i][j] == "VIP")
				vipCounter[i]++;
		}
		if (i != 0 && vipCounter[i] > vipCounter[counter])
			counter = i;
	}

	return counter;
}

bool isFound(Student* nameIDList, int numOfStudents, string stringID)
{	
	for (int i = 0; i < numOfStudents; i++)
	{
		if (stringID == nameIDList[i].ID)
			return true;
	}
	return false;
}

void noPrefSeating(string seatingChart[][colSize], string stuID, int guestNum, vector <string>&notAccomodated)
{
	int count = guestNum+1;
	for (int i = 0; i < rowSize; i++)
	{
		for (int j = 0; j < colSize; j++)
		{
			if (seatingChart[i][j] == "000")
			{
				seatingChart[i][j] = stuID;
				count--;
			}
			if (count == 0)
			{
				return;
			}
		}
	}
	int count2 = 0;
	for (int i = 0; i < rowSize; i++)
	{
		for (int j = 0; j < colSize; j++)
		{
			if (seatingChart[i][j] == stuID)
			{
				count2++;
			}
		}
		
	}
	if (count2 == count)
	{
		return;
	}
	else if (count2 != guestNum)
	{
		notAccomodated.push_back(stuID);
		return;
	}
}

void prefSeating(string seatingChart[][colSize], string stuID, int guestNum, int prefChoice, vector <string>& notAccomodated)
{
	int guestCount = guestNum +1;
	int count = 0;
	string* ptr = nullptr;

	for (int i = 0; i < colSize; i++)
	{
		if (seatingChart[prefChoice][i] == "000")
		{
			ptr = &seatingChart[prefChoice][i];

			for (int j = 0; j <= guestCount && ptr < &seatingChart[prefChoice][colSize - 1]; j++)
			{
				if (*ptr == "000")
				{
					ptr++;
					count++;
					if (count == guestCount)
					{
						for (int k = i; k < guestCount + i && k < rowSize; k++)
						{
								seatingChart[prefChoice][k] = stuID;
						}
						return;
					}
					continue;
				}
				else if(*ptr != "000")
				{
					count = 0;
					i += j;
					break;
				}
			}
		}
	}
	int count2 = 0;
	for (int i = 0; i < colSize; i++)
	{
		
		if (seatingChart[prefChoice][i] == stuID)
		{
			count2++;
		}
	}
	if (count2 == guestCount)
	{
		return;
	}
	else if (count2 != guestNum)
	{
		notAccomodated.push_back(stuID);
		return;
	}
}

void displayTheVector(vector <string> notAccomadated)
{
	cout << "\t\tThese Students were not accomodated: \n\t\t\t";
	for (int i = 0; i < notAccomadated.size(); i++)
	{
		cout << notAccomadated[i] << " ";
	}
}
