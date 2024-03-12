#include <iostream>
#include "Point.h"
using namespace std;

bool isInSameQuadrant(Point, Point);
string quadToString(Point);

int main()
{
	int x1, x2, y1, y2;
	int yM = 0, xM = 0;
	int quadTally[4];

	for (int i = 0; i < 4; i++)
		quadTally[i] = 0;

	Point Point1;
	Point PointII;

	cout << "\n\tEnter the x and y nonzero coordinates for the first point: ";
	cin >> x1;
	cin >> y1;
	Point1.setX(x1);
	Point1.setY(y1);

	cout << "\n\tEnter the x and y nonzero coordinates for the second point: ";
	cin >> x2;
	cin >> y2;
	PointII.setX(x2);
	PointII.setY(y2);

	if (isInSameQuadrant(Point1, PointII))
		cout << "\n\tThese points are in the same quadrant.";
	else
		cout << "\n\tThese points are not in the same quadrant.";

	Point* mirrorP1 = nullptr;
	mirrorP1 = new Point(); 
	mirrorP1->setX(Point1.getX());
	mirrorP1->setY(0 - Point1.getY());

	Point* mirrorP2 = nullptr;
	mirrorP2 = new Point();
	mirrorP2->setX(0 - PointII.getX());
	mirrorP2->setY(PointII.getY());

	cout << "\n\tThe mirror image of the point (" << Point1.getX() << ", " << Point1.getY() << ") over the x-axis would be (" << mirrorP1->getX() << ", " << mirrorP1->getY() << ")";
	cout << "\n\tThe mirror image of the point (" << PointII.getX() << ", " << PointII.getY() << ") over the y-axis would be (" << mirrorP2->getX() << ", " << mirrorP2->getY() << ")";

	cout << "\n\n\tPoint 1 is in Quadrant " << quadToString(Point1) << ".";
	cout << "\n\tPoint 2 is in Quadrant " << quadToString(PointII) << ".";
	cout << "\n\tThe mirror of Point 1 is in Quadrant " << quadToString(*mirrorP1) << ".";
	cout << "\n\tThe mirror of Point 2 is in Quadrant " << quadToString(*mirrorP2) << ".";

	if (quadToString(Point1) == "I")
		quadTally[0]++;
	else if (quadToString(Point1) == "II")
		quadTally[1]++;
	else if (quadToString(Point1) == "III")
		quadTally[2]++;
	else if (quadToString(Point1) == "IV")
		quadTally[3]++;

	if (quadToString(PointII) == "I")
		quadTally[0]++;
	else if (quadToString(PointII) == "II")
		quadTally[1]++;
	else if (quadToString(PointII) == "III")
		quadTally[2]++;
	else if (quadToString(PointII) == "IV")
		quadTally[3]++;

	if (quadToString(*mirrorP1) == "I")
		quadTally[0]++;
	else if (quadToString(*mirrorP1) == "II")
		quadTally[1]++;
	else if (quadToString(*mirrorP1) == "III")
		quadTally[2]++;
	else if (quadToString(*mirrorP1) == "IV")
		quadTally[3]++;

	if (quadToString(*mirrorP2) == "I")
		quadTally[0]++;
	else if (quadToString(*mirrorP2) == "II")
		quadTally[1]++;
	else if (quadToString(*mirrorP2) == "III")
		quadTally[2]++;
	else if (quadToString(*mirrorP2) == "IV")
		quadTally[3]++;

	cout << "\n\n\tQuadrant I: " << quadTally[0];
	cout << "\n\tQuadrant II: " << quadTally[1];
	cout << "\n\tQuadrant III: " << quadTally[2];
	cout << "\n\tQuadrant IV: " << quadTally[3];

	delete mirrorP1;
	delete mirrorP2;
	return 0;
}

bool isInSameQuadrant(Point Point1, Point PointII)
{
	if (Point1.whichQuadrant() == PointII.whichQuadrant())
		return true;
	else
		return false;
}

string quadToString(Point Point)
{
	string quadString;

	if (Point.whichQuadrant() == Point::Quadrant::I)
		quadString = "I";
	else if (Point.whichQuadrant() == Point::Quadrant::II)
		quadString = "II";
	else if (Point.whichQuadrant() == Point::Quadrant::III)
		quadString = "III";
	else if (Point.whichQuadrant() == Point::Quadrant::IV)
		quadString = "IV";

	return quadString;
}