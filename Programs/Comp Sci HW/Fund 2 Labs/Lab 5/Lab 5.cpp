#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

int maximum(int**, int, int);
int highFrequentNum(int*, int);
int* arrayOfRowMax(int**, int, int);
void displayArray(int**, int, int);
void swapTheRows(int**, int, int);

int main()
{
	ifstream infile;
	infile.open("Lab5.txt");

	int row, col;
	int** array2d;

	infile >> row;
	array2d = new int * [row];

	infile >> col;
	for(int i = 0; i <row; i++)
		array2d[i] = new int [col];

	cout << "\n\n\tThe Base Array is:\n \t\t";
	for (int i = 0; i < row; i++)
	{
		for (int j = 0; j < col; j++)
		{
			infile >> array2d[i][j];
			cout << array2d[i][j] << "   ";
		}
		cout << "\n\t\t";
	}

	cout << "\n";

	int* array1d;
	int size = maximum(array2d, row, col);
	array1d = new int [size];
	cout << "\tThe 1d array size is: " << size << endl << endl;

	for (int i = 0; i < size; i++)
		array1d[i] = 0;

	for (int i = 0; i < row; i++)
	{
		for (int j = 0; j < col; j++)
		{
			if (array2d[i][j] > -1)
				array1d[array2d[i][j]]++;
		}
	}

	cout << "\tThe Tallied Array looks like:\n\t\t";
	for (int i = 0; i < size; i++)
		cout << array1d[i] << " "; 
	
	cout << "\n\n\tThe most reoccuring number is: " << highFrequentNum(array1d, size);

	vector<int> negatives;
	int vectorSize = 0;

	for (int i = 0; i < row; i++)
	{
		for (int j = 0; j < col; j++)
		{
			if (array2d[i][j] < 0)
			{
				negatives.push_back(i);
				vectorSize++;
				continue;
			}
		}
	}

	cout << "\n\n\tThere are negative numbers in the following rows:\n\t\t";
	for (int i = 0; i < vectorSize; i++)
		cout << negatives[i] << "  ";

	cout << endl << endl;

	int* arrayMax;
	arrayMax = arrayOfRowMax(array2d, row, col);

	cout << "\tThe Biggest Number in Each Row of the 2d Array is: ";
	for (int i = 0; i < row; i++)
	{
		cout << arrayMax[i] << "  ";
	}

	cout << endl << endl;

	int rowSwap1, rowSwap2;
	cout << "\tChoose two rows to swap: ";
	cin >> rowSwap1;
	cin >> rowSwap2;

	swapTheRows(array2d, rowSwap1, rowSwap2);

	displayArray(array2d, row, col);

	delete[] array2d;
	array2d = nullptr;

	delete[] array1d;
	array1d = nullptr;

	delete[] arrayMax;
	arrayMax = nullptr;



	return 0;
}

int maximum(int** array2d, int row, int col)
{
	int size = 0;

	for (int i = 0; i < row; i++)
	{
		for (int j = 0; j < col; j++)
		{
			if (array2d[i][j] > size)
				size = array2d[i][j];
		}
	}

	return size+1;
}

int highFrequentNum(int* array1d, int size)
{
	int highest = 0;

	for (int i = 0; i < size; i++)
		if (array1d[i] > highest)
			highest = array1d[i];

	return highest;
}

int* arrayOfRowMax(int** array2d, int row, int col)
{
	int* arrayMax;
	arrayMax = new int[row];
	int max;

	for (int i = 0; i < row; i++)
	{
		max = 0;
		for (int j = 0; j < col; j++)
		{
			if (array2d[i][j] > max)
				max = array2d[i][j];
		}
		arrayMax[i] = max;
	}
	

	return arrayMax;
}

void displayArray(int** array2d, int row, int col)
{
	cout << "\n\n\tThe 2D Array is:\n\t\t";
	for (int i = 0; i < row; i++)
	{
		for (int j = 0; j < col; j++)
		{
			cout << array2d[i][j] << "   ";
		}
		cout << "\n\t\t";
	}
	cout << " ";
}

void swapTheRows(int** array2d, int row1, int row2)
{
	int* tempPtr = array2d[row1];
	array2d[row1] = array2d[row2];
	array2d[row2] = tempPtr;

}