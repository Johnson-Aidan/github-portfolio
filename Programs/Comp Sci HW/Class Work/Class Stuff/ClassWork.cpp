#include <iostream>?
using namespace std;

int main()
{
	int number[5];

	for (int &val : number)
			cin >> val;

	for (int val : number)
		cout << val << endl;
	
	return 0;
}