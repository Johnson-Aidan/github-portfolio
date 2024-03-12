#ifndef Point_h
#define Point_h
class Point
{
private:
	int x;
	int y;
public:
	enum Quadrant { I, II, III, IV };
	void setX(int);
	int getX() const;
	void setY(int);
	int getY() const;
	Quadrant whichQuadrant();
};
#endif // !Point

