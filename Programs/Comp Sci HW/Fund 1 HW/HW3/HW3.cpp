#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
	ofstream outputFile;
	outputFile.open("triangle.txt");

	int triInput;

	cout << "Input a number between 1-20 to make a triangle out of: ";
	cin >> triInput;
	while (triInput < 1 || triInput > 20)
	{
		cout << "Please enter a variable within 1-20: ";
		cin >> triInput;
	}
	for (int triAngle = 1; triAngle <= triInput; triAngle += 2)
	{
		for(int angelTri = 0; angelTri < triAngle; angelTri++)
		{			
			if (triAngle > 9)
			{
				outputFile << "0";
			}
			else
			{
				outputFile << triAngle;
			}
		}
		outputFile << "\n";
	}
	outputFile.close();
	cin.get();
	cin.get();
	return 0;
}