#include <string>
#include <iostream>
#include <fstream>
using namespace std;

void whoIsTheYoungest(string[], int[], const int);
void youngerThan20(string[], int[], const int); 
void findAndLocate(string[], const int); 
void swapIndividuals(string[], int[], const int);

int main()
{
	const int size = 10;
	string names[size];
	int ages[size];
	ifstream infile;
	infile.open("Names.txt");
	string nameGiven;
	int runProg;

	for (int count = 0; count < size; count++)
	{
		infile >> names[count]>> ages[count];
	}

	do
	{
		cout << "\n\n\tPlease select an option: \n";
		cout << "\t\t1.   What is the name and the age of the youngest person in the file.\n";
		cout << "\t\t2.   How many individuals are younger than 20. Output their names along with their ages.\n";
		cout << "\t\t3.   Enter the name of the person you are trying to find in the queue, list who is ahead and who is behind them, where applicable.\n";
		cout << "\t\t4.   Swap two individuals: Enter two numbers in the range of 0-9, so the individuals in those locations will swap places on the list.\n";
		cout << "\t\t5.  Exit.\n";

		cin >> runProg;

		switch(runProg)
		{
		case 1: whoIsTheYoungest(names, ages, size);
		case 2: youngerThan20(names, ages, size);
		case 3: findAndLocate(names, size);
		case 4: swapIndividuals(names, ages, size);
		case 5: break;
		}
	} while (runProg != 5);

	cout << "Quitting program...\n" << endl;
	infile.close();

	
	return 0;
}

void whoIsTheYoungest(string name[], int age[], int size)
{
	int LowestAge = age[0];
	string LowestName = name[0];
	for (int count = 1; count < size; count++)
	{
		if (age[count] < LowestAge)
		{
			LowestAge = age[count];
			LowestName = name[count];
		}
	}
	cout << "\n\tThe youngest person is " << LowestName << " at " << LowestAge << " years old.\n";
}

void youngerThan20(string name[], int age[], int size)
{
	int count = 0;
	cout << "\nThese people are under the age of 20:\n";
	for (int i = 0; i < size; i++)
	{
		if (age[i] < 20)
		{
			cout << "\t" << name[i] << " is " << age[i] << " years old.\n";
			count++;
		}
	}
	if(count == 0)
		cout << "\tThere is no one in the queue that is under the age of 20.\n";
}

void findAndLocate(string name[], int size)
{
	string userName;
	cout << "\t Please enter a name to check:\n";
	cin >> userName;

	for (int i = 0; i < size; i++)
	{
		if (name[i] == userName)
		{
			if (i == 0)
			{
				cout << name[i] << " is the first in line.\n";
				cout << "The person behind " << name[i] << " is " << name[i + 1] << endl;
			}
			else if (i == size - 1)
			{
				cout << name[i] << " is the last in line.\n";
				cout << "The person in front of " << name[i] << " is " << name[i - 1] << endl;
			}
			else
			{
				cout << name[i] << " is in position " << i << " in the line. \n";
				cout << "The person behind " << name[i] << " is " << name[i + 1] << endl;
				cout << "The person in front of " << name[i] << " is " << name[i - 1] << endl;
			}
		}
	}

}

void swapIndividuals(string name[], int age[], int size)
{
	int index1, index2, tempAge;
	string tempName;
	cout << "Please enter two numbers in the range of 0-" << size - 1 << ".\n";
	cin >> index1 >> index2;

	while ((index1 < 0 || index1 >= size) || (index2 < 0 || index2 >= size))
	{
		cout << "One, or Both of the indices are not in the valid range:0-" << size - 1 << ".\n";
		cout << "Please enter TWO valid numbers.\n";

	}
	tempAge = age[index1];
	tempName = name[index2];

	age[index1] = age[index2];
	name[index1] = name[index1];

	age[index2] = tempAge;
	name[index2] = tempName;

	for (int i = 0; i < size; i++)
	{
		cout << name[i] << "\t\t" << age[i] << "\n";
	}

}