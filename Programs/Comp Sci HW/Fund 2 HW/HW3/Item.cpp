#include "item.h"
#include <iostream>
using namespace std;
Item::Item(int itemID, double itemPrice, int numUnits)
{
	ID = itemID;
	price = itemPrice;
	units = numUnits;
}

void Item::setID(int IDsent)
{
	ID = IDsent;
}

int Item::getID() 
{
	return ID;
}

void Item::setPrice(double PriceGiven)
{
	price = PriceGiven;
}

double Item::getPrice()
{
	return price;
}

void Item::setUnits(int unitsGiven)
{
	units = unitsGiven;
}

int Item::getUnits()
{
	return units;

}

void Item::displayItemInfo()
{
	cout << "Item ID: " << ID << endl;
	cout << "Item Price: $" << price << endl;
	cout << "Item Units: " << units << endl;
}

double Item::computePricePerItem()
{
	double pricePerItem = 0;
	pricePerItem = (price / units);
	cout << "The price per unit is: ";
	return pricePerItem;
}