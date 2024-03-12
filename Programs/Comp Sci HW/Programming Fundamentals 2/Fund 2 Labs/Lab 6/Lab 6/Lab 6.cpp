#include <iostream>
#include <string>
#include <vector>
#include <iomanip>
#include <fstream>
using namespace std;

ifstream infile;
ofstream outfile;

const int numStudents = 6;
const int numGrades = 5;

struct Student
{
	string student;
	char letterGrade;
};

//Part 1
void averagePerStudent(string[], int[][numGrades]); 
void maxGradePerAssign(string[], int[][numGrades]);

//Part 4
void displayArrayOfStructs(Student[]);



int main()
{
	cout << "\n\t_______________ Part I __________________\n\n\t";
	
	string names[numStudents];
	int scores[numStudents][numGrades];
	
	//Input into Array
	infile.open("inData.txt");
	for (int i = 0; i < numStudents; i++)
	{
		infile >> names[i];
		for (int j = 0; j < numGrades; j++)
		{
				infile >> scores[i][j];
		}
	}
	infile.close();

	averagePerStudent(names, scores);
	maxGradePerAssign(names, scores);

	cout << "\n\t_______________ Part II __________________\n\t";

	int avgScores[numStudents];
	string tempName;
	vector<string> AList;
	vector<string> BList;
	vector<string> FList;
	
	//Input into Array
	infile.open("outData.txt");
	for (int i = 0; i < numStudents; i++)
	{
		infile >> tempName >> avgScores[i];
	}
	infile.close();

	for (int i = 0; i < numStudents; i++)
	{
		if (avgScores[i] >= 90)
			AList.push_back(names[i]);
		else if(avgScores[i] >= 80 && avgScores[i] < 90)
			BList.push_back(names[i]);
		else if(avgScores[i] < 80)
			FList.push_back(names[i]);
	}

	cout << "\n\tThe number of students who made an A: " << AList.size();
	cout << "\n\tThe list of students who made an A:\n\t\t";
	for (int i = 0; i < AList.size(); i++)
		cout << AList[i] << " ";

	cout << "\n\n\tThe number of students who made a B: " << BList.size();
	cout << "\n\tThe list of students who made a B:\n\t\t";
	for (int i = 0; i < BList.size(); i++)
		cout << BList[i] << " ";

	cout << "\n\n\tThe number of students who failed: " << FList.size();
	cout << "\n\tThe list of students who failed:\n\t\t";
	for (int i = 0; i < FList.size(); i++)
		cout << FList[i] << " ";

	cout << "\n\n\t_______________ Part III __________________\n\t";

	string name;
	vector<string> gradeCat[3];
	gradeCat[0] = AList;
	gradeCat[1] = BList;
	gradeCat[2] = FList;
	
	cout << "\n\tPlease enter the student's name you wish to find: ";
	cin >> name;

	for (int i = 0; i < 3; i++)
	{
		for (int j = 0; j < gradeCat[i].size(); j++)
		{
			if( name == gradeCat[i][j])
				if (i == 0)
				{
					cout << "\n\t" << name << "'s grade is: A";
				}
				else if (i == 1)
				{
					cout << "\n\t" << name << "'s grade is: B";
				}
				else if (i == 2)
				{
					cout << "\n\t" << name << "'s grade is: F";
				}
		}
	}


	cout << "\n\n\t_______________ Part IV __________________\n\t";

	Student* Ptr = new Student[numStudents];
	int index = 0;
	for (int i = 0; i < 3; i++)
	{
		for (int j = 0; j < gradeCat[i].size(); j++)
		{
			Ptr[index++].student = gradeCat[i][j];
		}
	}

	displayArrayOfStructs(Ptr);

	return 0;
}

void averagePerStudent(string names[], int scores[][numGrades])
{
	outfile.open("outData.txt");
	int averageScore[numStudents] = { 0 };
	for (int i = 0; i < numStudents; i++)
	{
		for (int j = 0; j < numGrades; j++)
		{
			averageScore[i] = averageScore[i] + scores[i][j];
		}
		averageScore[i] = averageScore[i] / numGrades;
	}
	cout << "The Average of Each Student is:\n\t\t";
	for (int i = 0; i < numStudents; i++)
	{
		cout << names[i] << " " << averageScore[i];
		outfile << names[i] << " " << averageScore[i] << endl;
		cout << "\n\t\t";
	}
	outfile.close();
	cout << "\n\t";
}

void maxGradePerAssign(string names[], int scores[][numGrades])
{
	int highest;
	int highStu;
	cout << "The Highest Grade of Each Assignment is:\n\t\t";

	for (int j = 0; j < numGrades; j++)
	{
		highest = 0;
		for (int i = 0; i < numStudents; i++)
		{
			if (highest < scores[i][j])
			{
				highest = scores[i][j];
				highStu = i;
			}

		}
		cout << "For Assignment #" << j + 1 << ", " << names[highStu] << " has made the highest grade of " << highest << "\n\t\t";
	}
}

void displayArrayOfStructs(Student[])
{

}
