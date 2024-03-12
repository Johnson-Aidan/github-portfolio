#include "Point.h"
using namespace std;

void Point::setX(int cordX)
{
	x = cordX;
}

int Point::getX() const
{
	return x;
}

void Point::setY(int cordY)
{
	y = cordY;
}

int Point::getY() const
{
	return y;
}

Point::Quadrant Point::whichQuadrant()
{
	Point::Quadrant qu;
	if (x > 0 && y > 0)
	{
		qu = I;
	}
	else if (x > 0 && y < 0)
	{
		qu = IV;
	}
	else if (x < 0 && y > 0)
	{
		qu = II;
	}
	else if (x < 0 && y < 0)
	{
		qu = III;
	}
	return qu;
}
