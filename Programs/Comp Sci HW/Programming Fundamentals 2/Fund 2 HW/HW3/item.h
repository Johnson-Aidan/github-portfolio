#ifndef item_h
#define item_h
class Item
{
	private:
		int ID;
		double price;
		int units;

	public:
		Item(int, double, int); //Need to change based on array requirement
		Item() {};
		void setID(int);
		int getID();
		void setPrice(double);
		double getPrice();
		void setUnits(int);
		int getUnits();
		void displayItemInfo();
		double computePricePerItem();
};
#endif