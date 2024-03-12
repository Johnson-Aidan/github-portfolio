#include "Reciept.h"
#include <string>
#include "item.h"
#include <iostream>
using namespace std;


Receipt::Receipt(string newShopperName)
{
	shopperName = newShopperName;
	shoppingList = new Item[maxItems];
}

void Receipt::setShopperName(string nameGiven)
{
	shopperName = nameGiven;
}

string Receipt::getShopperName()
{
	return shopperName;
}

void Receipt::setShoppingList(Item* list)
{
	shoppingList = list;
}

Item* Receipt::getShoppingList() const
{
	return shoppingList;
}

int Receipt::getNumItems()
{
	return numItems;
}

void Receipt::addItem(Item itemAdded)
{
	if (numItems < maxItems)
	{
		shoppingList[numItems] = itemAdded;
		numItems++;
	}
}

void Receipt::displayTheShoppingList()
{
	for (int i = 0; i < numItems; i++)
	{
		cout << "Item ID: " << shoppingList[i].getID() << endl;
		cout << "Item Price: $" << shoppingList[i].getPrice() << endl;
		cout << "Item Units: " << shoppingList[i].getUnits() << endl;
	}
}

double Receipt::computeTotalCharge()
{
	double chargeTotal = 0;

	for (int i = 0; i < numItems; i++)
	{
		chargeTotal += shoppingList[i].getPrice();
	}

	return chargeTotal;
}

Receipt::Receipt(const Receipt& obj)
{
	this->shopperName = obj.shopperName;
	this->shoppingList = new Item[maxItems];
	this->numItems = obj.numItems;
	for (int i = 0; i < this->numItems; i++)
	{
		this->shoppingList[i] = obj.shoppingList[i];
	}
}