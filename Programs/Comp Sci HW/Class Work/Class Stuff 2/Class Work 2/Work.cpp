#include <iostream>
#include <string>
#include <fstream>
#include <iomanip>
using namespace std;

const int COLS = 3;
double average(int[][COLS], int);
void studentWithHighestAvg(string[], int, int[][COLS]);
void nameScoresForHW(string[], int, int[][COLS], int);

int main()
{
	const int SIZE = 10;
	string name;
	int HW;

	ifstream infile;
	infile.open("grades.txt");

	int scores[SIZE][COLS];
	string names[SIZE];

	for (int i = 0; i < SIZE; i++)
	{
		infile >> names[i];
		for (int j = 0; j < COLS; j++)
		{
			infile >> scores[i][j];
		}
	}

	cout << fixed << setprecision(2) << showpoint;

	int choice;
	do
	{
		cout << endl << endl;
		cout << "1. Search for a student based on name, and output their average score." << endl;
		cout << "2. Search for a student with the highest overall average score." << endl;
		cout << "3. Search for the scores for a HW and list the names of the students along with their socres." << endl;
		cout << "4. Exit." << endl;
		cin >> choice;

		switch (choice)
		{
		case 1: cout << "Please enter the name: " << endl;
			cin >> name;
			for (int i = 0; i < SIZE; i++)
				if (name == names[i])
					cout << name << " " << average(scores, i) << endl;
			break;
		case 2: studentWithHighestAvg(names, SIZE, scores);
			break;
		case 3: cout << "Pick a HW: " << endl;
			cout << "\t1. HW1" << endl;
			cout << "\t2. HW2" << endl;
			cout << "\t3. HW3" << endl;
			
			cin >> HW;
				nameScoresForHW(names, SIZE, scores, HW - 1);
			break;
		}
	} while (choice != 4);
}

double average(int scores[][COLS], int index)
{
	double total = 0.0;
	for (int i = 0; i < COLS; i++)
	{
		total += scores[index][i];
	}
	return total / COLS;
}

void studentWithHighestAvg(string names[], int s, int scores[][COLS])
{
	double maxAvg;
	string maxName;

	maxAvg = average(scores, 0);
	maxName = names[0];

	for (int i = 1; i < s; i++)
	{
		if (maxAvg < average(scores, i))
		{
			maxAvg = average(scores, i);
			maxName = names[i];
		}
	}
	cout << maxName << " has the highest average of all of " << maxAvg << endl;
}

void nameScoresForHW(string names[], int s, int scores[][COLS], int index)
{
	for (int i = 0; i < s; i++)
	{
		cout << names[i] << " ";
		cout << scores[i][index] << endl;
	}
}