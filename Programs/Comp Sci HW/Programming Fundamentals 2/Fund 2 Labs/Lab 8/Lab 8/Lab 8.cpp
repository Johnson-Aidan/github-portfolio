#include <fstream>
#include <iostream>
#include <vector>
using namespace std;

enum Snacks { CHIPS = 0, PRETZELS = 1, NUTS = 2 };
enum Drinks { COKE = 3, SPRITE = 4, WATER = 5 }; 
struct drinkRecord
{
	Drinks dType;
	int dCount;
};

struct snackRecord
{
	Snacks sType;
	int sCount;
};

struct transactionRecord
{
	int tally[6];
	vector<drinkRecord> drinksVec;
	vector<snackRecord> snacksVec;
};

void runResults(transactionRecord);
void displayDVector(vector <drinkRecord>); 
void displaySVector(vector <snackRecord>); 
string toDrink(Drinks);     // to get the name of enum type and not an int
string toSnack(Snacks);     // to get the name of enum type and not an int

int main()
{
	ifstream infile;
	infile.open("transactions.txt");
	int numTrans = 0;
	int numTransact;
	int numItem;
	int whatItem;

	transactionRecord transrecord;

	for (int i = 0; i < 6; i++)
	{
		transrecord.tally[i] = 0;
	}
	while (infile >> numTransact)
	{
		numTrans++;
		for (int i = 0; i < numTransact; i++)
		{
			infile >> numItem;
			infile >> whatItem;
			transrecord.tally[whatItem] += numItem;
			switch (whatItem)
			{
			case 0:
			case 1:
			case 2:
				snackRecord snack;
				snack.sType = static_cast<Snacks>(whatItem);
				snack.sCount = numItem;
				transrecord.snacksVec.push_back(snack);
				break;
			case 3:
			case 4:
			case 5:
				drinkRecord drink;
				drink.dType = static_cast<Drinks>(whatItem);
				drink.dCount = numItem;	
				transrecord.drinksVec.push_back(drink);
				break;
			}
		}
	}

	runResults(transrecord);

	return 0;
}

void runResults(transactionRecord transRecord)
{
	cout << "\n\tDrinks Record:\n";
	displayDVector(transRecord.drinksVec);

	cout << "\n\tSnacks Record:\n";
	displaySVector(transRecord.snacksVec);

	cout << "\n\tTally Report:\n";
	for (int i = 0; i < 6; i++)
	{
		switch (i)
		{
		case 0:
		case 1:
		case 2:
			cout << "\t\t\tTotal " << toSnack(static_cast<Snacks>(i)) << " sold is: " << transRecord.tally[i] << endl;
			break;
		case 3:
		case 4:
		case 5:
			cout << "\t\t\tTotal " << toDrink(static_cast<Drinks>(i)) << " sold is: " << transRecord.tally[i] << endl;
			break;
		}
	}
}

string toDrink(Drinks drinkList)
{
	string dList;
	switch (drinkList)
	{
	case COKE: dList = "COKE";
		break;
	case SPRITE: dList = "SPRITE";
		break;
	case WATER: dList = "WATER";
		break;
	}
	return dList;
}

string toSnack(Snacks snackList)
{
	string sList;
	switch (snackList)
	{
	case CHIPS: sList = "CHIPS";
		break;
	case PRETZELS: sList = "PRETZELS";
		break;
	case NUTS:sList = "NUTS";
		break;
	}
	return sList;
}

void displayDVector(vector <drinkRecord> drinksVec)
{
	
	for (int i = 0; i < drinksVec.size(); i++)
	{
		cout << "\t\t\t" << toDrink(drinksVec[i].dType) << " " << drinksVec[i].dCount << endl;
	}
}
void displaySVector(vector <snackRecord> snacksVec)
{
	for (int i = 0; i < snacksVec.size(); i++)
	{
		cout << "\t\t\t" << toSnack(snacksVec[i].sType) << " " << snacksVec[i].sCount << endl;
	}
}