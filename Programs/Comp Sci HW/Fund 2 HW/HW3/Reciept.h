#ifndef Receipt_h
#define Receipt_h
#include <string>
#include "item.h"
using namespace std;
class Receipt
{
	private:
		string shopperName;
		Item* shoppingList;
		const int maxItems = 20;
		int numItems;
	public: 
		Receipt(string);
		void setShopperName(string); 
		string getShopperName();  
		void setShoppingList(Item*); 
		Item* getShoppingList() const;
		int getNumItems();
		void addItem(Item);
		void displayTheShoppingList(); 
		double computeTotalCharge();
		Receipt(const Receipt&);

		~Receipt()
		{
			delete[] shoppingList;
		}
 };
#endif // !
