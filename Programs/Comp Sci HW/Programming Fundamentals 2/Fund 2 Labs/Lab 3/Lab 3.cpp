//Aidan Johnson
// No. 8 & 9 from Book.

#include <iostream>
#include <cstdlib>
#include <ctime>  
using namespace std;

void bubbleSort(int[], int);
void swap(int&, int&);
int linearSearch(int[], int, int);


int main()
{
	const int SIZE = 20;
	const int minSIZE = 1;
	int Array[SIZE]{ 11, 15, 2, 5, 6, 10, 4, 7, 16, 13, 12, 17, 1, 20, 19, 18, 3, 8, 9, 14 };
	int userChoice;
	int linearChoice;
	unsigned seed = time(0);
	srand(seed);

	cout << "This program will take an initally preset array and do one of three things:" << endl;
	cout << "1. Let you find a number from 1 through 20." << endl;
	cout << "2. Sort the array with Bubble Sort." << endl;
	cout << "Or 3. Randomize the array with any number from 1 through 20.\n\t NOTE: ONCE YOU CHOOSE THIS OPTION, YOU MAY NOT GET EVERY NUMBER AND YOU MAY HAVE DUPLICATES" << endl << endl;

	do {
		cout << endl;
		cout << "\tPlease choose an option: " << endl;
		cout << "\t\t1. Search for a number in the array." << endl;
		cout << "\t\t2. Sort the array with Bubble Sort." << endl;
		cout << "\t\t3. Randomize the Array." << endl;
		cout << "\t\t4. Exit." << endl;

		cin >> userChoice;

		switch (userChoice)
		{
		case 1: 
			cout << "What number would you like to find (1-20): ";
			cin >> linearChoice;
			cout << "It was found in position: " << linearSearch(Array, SIZE, linearChoice);
			break;
		case 2: 
			cout << "Sorting array....";
			bubbleSort(Array, SIZE);
			break;
		case 3: 	for (int i = 0; i < SIZE; i++)
			{
				Array[i] = (rand() % (SIZE - minSIZE + 1)) + minSIZE;
			}
		
			cout << "Array has been randomized!\n";
			break;
		case 4:
			break;

		}
	} while (userChoice != 4);



	return 0;
}

void bubbleSort(int array[], int s)
{
	int maxElement;

	for (maxElement = s - 1; maxElement > 0; maxElement--)
	{
		for (int i = 0; i < maxElement; i++)
		{
			if (array[i] > array[i + 1])
			{
				swap(array[i], array[i + 1]);
			}
		}
	}

	cout << "\n\n\tNewly sorted array:\n" << "\t";

	for (int i = 0; i < s; i++)
	{
		cout << array[i] << " ";
	}
	cout << "\n";
}

int linearSearch(int array[], int s, int choice)
{
	int count = 0;
	int i = 0;
	int position = -1;
	bool found = false;

	while (i < s && !found)
	{
		if (array[i] == choice)
		{
			found = true;
			position = i;
		}
		i++;
		count++;
	}
	cout << "Found in " << count << " iterations.\n";
	return position;
}

void swap(int& a, int& b)
{
	int temp = a;
	a = b;
	b = temp;
}