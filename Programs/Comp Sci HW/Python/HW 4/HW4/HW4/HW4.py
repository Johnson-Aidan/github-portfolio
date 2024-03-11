#Aidan Johnson 1890052

print('- - - - Function One - - - -')

def funcOne(n):
    if(n <= 2):
        return 1
    else:
        return 2*funcOne(n-2)+3

print('Input a number: ')
userInt = int(input())
print(funcOne(userInt))


print('- - - - Function Two - - - -')

def funcTwo(n):
    if(n <= 1):
        return 1
    else:
        return funcTwo(n-2)+1

print('Input a number: ')
userInt = int(input())
print(funcTwo(userInt))


print('- - - - Function Three - - - -')

def funcThree(n):
    if(n == 1):
        return 1
    return n+funcThree(n-1)

print('Input a number: ')
userInt = int(input())
print(funcThree(userInt))


print('- - - - Function Four - - - -')

def funcFour(n, m):
    if(m != 0):
        return n+funcFour(n, m-1)
    else:
        return 0

print('Input a number: ')
userInt = int(input())
print('Input a second number: ')
userInt2 = int(input())
print(funcFour(userInt, userInt2))