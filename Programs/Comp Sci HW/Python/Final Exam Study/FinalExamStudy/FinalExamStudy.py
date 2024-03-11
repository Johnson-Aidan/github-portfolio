class Point:
    def __init__(self, x, y):
      self._x = x
      self._y = y

    @property
    def x(self):
        return self._x

    @x.setter
    def x(self, x):
        self._x = x

    @property
    def y(self):
        return self._y

    @y.setter
    def y(self, y):
        self._y = y


class Circle(Point):
   def __init__(self, Point_x, Point_y, radius):
     super().__init__(Point_x, Point_y)
     self._radius = radius
       
   @property
   def radius(self):
       return self._radius

   @radius.setter
   def radius(self, radius):
       self._radius = radius

   def __eq__(self, circle2):
        return (self._x, self._y, self._radius) == (circle2._x, circle2._y, circle2._radius)

   def __repr__(self):
     return f"({self.x}, {self.y}) with a radius of {self._radius}"   


userX = float(input("Enter the center x value: "))
userY = float(input("Enter the center y value: "))
userRadius = float(input("Enter a radius value: "))

circle = Circle(userX, userY, userRadius)
circle2 = Circle(5.0,5.0,5.0)

print("\n")
print(circle)

print("The top coordinate of the circle is: " + "(" + str(circle.x) + ", " + str(circle.y+circle.radius) + ")")
print("The bottom coordinate of the circle is: " + "(" + str(circle.x) + ", " + str(circle.y-circle.radius) + ")")
print("The left coordinate of the circle is: " + "(" + str(circle.x-circle.radius) + ", " + str(circle.y) + ")")
print("The right coordinate of the circle is: " + "(" + str(circle.x+circle.radius) + ", " + str(circle.y) + ")\n")

print("\n")
circle.x = float(input("Enter a new center x value: "))
circle.y = float(input("Enter a new center y value: "))

print("\n")
print(circle)

print("The top coordinate of the circle is: " + "(" + str(circle.x) + ", " + str(circle.y+circle.radius) + ")")
print("The bottom coordinate of the circle is: " + "(" + str(circle.x) + ", " + str(circle.y-circle.radius) + ")")
print("The left coordinate of the circle is: " + "(" + str(circle.x-circle.radius) + ", " + str(circle.y) + ")")
print("The right coordinate of the circle is: " + "(" + str(circle.x+circle.radius) + ", " + str(circle.y) + ")\n")

print("Are these circles the same:")
print(circle)
print(circle2)

if(circle == circle2):
    print("Yes.")
else:
    print("No.")

