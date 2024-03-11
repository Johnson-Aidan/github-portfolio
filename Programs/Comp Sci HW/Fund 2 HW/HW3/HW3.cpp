#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include "Reciept.h"
#include "item.h"
#include "HW3.h"

using namespace std;

int main()
{
	int userChoice;
	double pricePerID[11];
	double tempNum;

	vector<Receipt> receipts;
	int tallyPerID[11];

	string name;
	int numItems;
	int itemID;
	int itemNum;

	int highestTally = -1;
	int highestPosition = -1;

	int defectiveProduct;

	Receipt* temp = nullptr;
	Item* temp2 = nullptr;

	for (int i = 0; i < 11; i++)
	{
		tallyPerID[i] = 0;
	}

	ifstream infile;

	infile.open("itemInfo.txt");
	for (int i = 1; i < 11; i++)
	{
		infile >> tempNum;
		infile >> pricePerID[i];
	}
	infile.close();

	infile.open("weeklySale.txt");
	while (infile >> name)
	{
		temp = new Receipt(name);

		infile >> numItems;
		for (int i = 0; i < numItems; i++)
		{
			infile >> itemID >> itemNum;
			temp2 = new Item(itemID, (pricePerID[itemID] * itemNum), numItems);
			temp->addItem(*temp2);
			tallyPerID[itemID] += itemNum;

			delete temp2;
			temp2 = nullptr;
		}

		receipts.push_back(*temp);
		delete temp;
		temp = nullptr;
	}
	infile.close();

	do
	{
		cout << endl;
		cout << "\tPlease choose an option: " << endl;
		cout << "\t\t1. Display the total charges per receipts." << endl;
		cout << "\t\t2. Display the tally result per Item ID's." << endl;
		cout << "\t\t3. What appears to be the most popular item (by ID) of this farmer stand based on this sales record?" << endl;
		cout << "\t\t4. Defective Product: No charge based on item ID" << endl;
		cout << "\t\t5. (BONUS) Apply the following promotion on existing sales records: Buy 5 units or more of the same item and get one item free (i.e. get credit for one item)." << endl;
		cout << "\t\t6. Exit." << endl;

		cin >> userChoice;

		switch (userChoice)
		{
		case 1:cout << "\t\tThe total charges per receipts is:\n";
			for (int i = 0; i < receipts.size(); i++)
			{
				cout << "\t\t\tReceipt " << i + 1 << ": " << receipts[i].computeTotalCharge() << endl;
			}
			break;

		case 2: cout << "\t\tThe tally result is:\n";
			for (int i = 1; i < 11; i++)
				cout << "\t\t\tItem " << i << " sold: " << tallyPerID[i] << endl;
			break;

		case 3:
			for (int i = 1; i < 11; i++)
			{
				if (tallyPerID[i] > highestTally)
				{
					highestPosition = i;
					highestTally = tallyPerID[i];
				}
			}
			cout << "\t\tThe most popular item by ID is " << highestPosition << ", selling " << highestTally << " items.\n";
			break;

		case 4: cout << "\t\tPlease insert Defective Product ID: ";
			cin >> defectiveProduct;

			for (int i = 0; i < receipts.size(); i++)
				for (int j = 0; j < receipts[i].getNumItems(); j++)
				{
					if (defectiveProduct == receipts[i].getShoppingList()[j].getID())
					{
						cout << "\t\t\t$" << receipts[i].getShoppingList()[j].getPrice() << " of Item " << receipts[i].getShoppingList()[j].getID() << " has been purchased with an individual price of $" << pricePerID[defectiveProduct] << "." << endl;
						receipts[i].getShoppingList()[j].setPrice(0.00);
						cout << "\t\t\tItem " << defectiveProduct << "'s price has set to $0.00." << endl;
					}
				}			
			break;

		case 5: cout << "\t\tPromotion not working currently. Returning to menu.\n";
			/*cout << "\t\tPlease insert Promotion Product ID: ";
			cin >> defectiveProduct;

			for (int i = 0; i < receipts.size(); i++)
				for (int j = 0; j < receipts[i].getNumItems(); j++)
				{
					if (receipts[i].getShoppingList()[j].getUnits() >= 5)
					{
						receipts[i].getShoppingList()[j].setUnits(receipts[i].getShoppingList()[j].getUnits() + 1);
					}
				}*/
			break;

		case 6: break;
		}
	} while (userChoice != 6);

	return 0;
}
