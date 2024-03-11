#include <iostream>
#include <string>
#include <fstream>
#include <vector>
using namespace std;

ofstream outfile;


int binarySearch(int[], int, int);
int numsAreLessThan(int[], int, int);

int main()
{
	const int SIZE = 31; //Final value of 98 is removed from text file due to having 32 values in text file. Permission was granted by Mohamed.
	int fileValue[SIZE];
	int userChoice;
	int dummyVar;
	int dummyVar2;
	int min = 5;
	int max = 5;
	int range;
	vector<int> iterations;
	ifstream infile;
	
	infile.open("Lab3_input.txt");
	
	outfile.open("Lab3_LogFile.txt");

	int choice;

	for (int i = 0; i < SIZE; i++)
		infile >> fileValue[i];

	infile.close();

	do {
		cout << endl;
		cout << "\tPlease choose an option: " << endl;
		cout << "\t\t1. Search for a number." << endl;
		cout << "\t\t2. Display the numeric range of iterations over the search history of this program. " << endl;
		cout << "\t\t3. Display the number of items less than a certain value." << endl;
		cout << "\t\t4. Exit." << endl;

		cin >> choice;

		switch (choice)
		{
			case 1: cout << "Please enter a number in 1-100 inclusive: ";
				cin >> userChoice; 
				
				if (binarySearch(fileValue, SIZE, userChoice) > -1)
					cout << userChoice << " found in index " << binarySearch(fileValue, SIZE, userChoice) << endl;
				else
					cout << userChoice << " was not found on the list.\n";
				break;

			case 2: infile.open("Lab3_LogFile.txt");

				while (infile >> dummyVar)
				{
					infile >> dummyVar2;
					iterations.push_back(dummyVar2);
				}
				for (int i = 0; i < iterations.size(); i++)
				{
				
					cout << iterations[i] << " ";

					if (iterations[i] < min)
						min = iterations[i];
					if (iterations[i] > max)
						max = iterations[i];
				}
				range = max - min;
				cout << "\nThe range of the search history iterations is: " << range;
				break;

			case 3: cout << "Please enter a number in 1-100 inclusive: ";
				cin >> userChoice;
				cout << "There are " << numsAreLessThan(fileValue, SIZE, userChoice) << " numbers less than or equal to " << userChoice;
				break;

			case 4: 
				break;

		}
	} while (choice != 4);
	
	infile.close();
	outfile.close();
	return 0;
}

int binarySearch(int array[], int size, int value)
{
	int first = 0,              // First array element
		last = size - 1,        // Last array element
		middle,                 // Mid point of search
		position = -1,          // Position of search value
		iteration = 0;
	bool found = false;         // Flag


	while (!found && first <= last)
	{
		middle = (first + last) / 2;    // Calculate mid point
		if (array[middle] == value)    // If value is found at mid
		{
			found = true;
			position = middle;
		}
		else if (array[middle] > value) // If value is in lower half
			last = middle - 1;
		else
			first = middle + 1;         // If value is in upper half
		iteration++;
	}

	outfile << value;
	outfile << "\t" << iteration << endl;

	return position;
}

int numsAreLessThan(int fileValue[], int SIZE, int choice)
{
	int count = 0;
	for (int i = 0; i < SIZE; i++)
	{
		if (fileValue[i] <= choice)
			count++;
	}

	return count;
}