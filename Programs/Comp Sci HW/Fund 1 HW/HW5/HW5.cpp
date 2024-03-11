#include <string>
#include <iostream>
using namespace std;

bool isAlphabet(char); 
int letterPosition(char); 
string theSuffix(int); 
string theCase(char);

int main()
{
    char alpha_input;
        
    do
    {

        cout << "Enter a character or enter E to exit: ";
        cin >> alpha_input;

        if (isAlphabet(alpha_input))
        {
            cout << "You entered the " << letterPosition(alpha_input) << theSuffix(alpha_input) << " letter in the alphabet in " << theCase(alpha_input) << "." << endl;
        }
        else
        {
            cout << "You entered a non-alphabetical letter." << endl;
        }
    } while (alpha_input != 'E');
    
    cout << "Exiting program . . .";

    cin.get();
    cin.get();
    return 0;
}


bool isAlphabet(char alpha_input)
{
    bool status;

    if (64 < alpha_input && alpha_input < 91)
    {
        status = true;
    }
    else if (96 < alpha_input && alpha_input < 123)
    {
        status = true;
    }
    else
    {
        status = false;
    }
    return status;
}

int letterPosition(char alpha_input)
{
    int alpha_position = 0;

    switch (alpha_input)
    {
        case 'A':
        case 'a': alpha_position = 1;
            break;
        case 'B':
        case 'b': alpha_position = 2;
            break;
        case 'C':
        case 'c': alpha_position = 3;
            break;
        case 'D':
        case 'd': alpha_position = 4;
            break;
        case 'e': alpha_position = 5;
            break;
        case 'F':
        case 'f': alpha_position = 6;
            break;
        case 'G':
        case 'g': alpha_position = 7;
            break;
        case 'H':
        case 'h': alpha_position = 8;
            break;
        case 'I':
        case 'i': alpha_position = 9;
            break;
        case 'J':
        case 'j': alpha_position = 10;
            break;
        case 'K':
        case 'k': alpha_position = 11;
            break;
        case 'L':
        case 'l': alpha_position = 12;
            break;
        case 'M':
        case 'm': alpha_position = 13;
            break;
        case 'N':
        case 'n': alpha_position = 14;
            break;
        case 'O':
        case 'o': alpha_position = 15;
            break;
        case 'P':
        case 'p': alpha_position = 16;
            break;
        case 'Q':
        case 'q': alpha_position = 17;
            break;
        case 'R':
        case 'r': alpha_position = 18;
            break;
        case 'S':
        case 's': alpha_position = 19;
            break;
        case 'T':
        case 't': alpha_position = 20;
            break;
        case 'U':
        case 'u': alpha_position = 21;
            break;
        case 'V':
        case 'v': alpha_position = 22;
            break;
        case 'W':
        case 'w': alpha_position = 23;
            break;
        case 'X':
        case 'x': alpha_position = 24;
            break;
        case 'Y':
        case 'y': alpha_position = 25;
            break;
        case 'Z':
        case 'z': alpha_position = 26;
            break;
    }
    return alpha_position;
}

string theSuffix(int alpha_position)
{
    string suffix;

    switch (letterPosition(alpha_position))
    {
        case 1:
        case 21: suffix = "st";
            break;
        case 2:
        case 22: suffix = "nd";
            break;
        case 3:
        case 23: suffix = "rd";
            break;
        default: suffix = "th";
            break;
    }
    return suffix;
}

string theCase(char alpha_input)
{
    string casing;


    if (64 < alpha_input && alpha_input < 91)
    {
        casing = "uppercase";
    }
    else if (96 < alpha_input && alpha_input < 123)
    {
        casing = "lowercase";
    }

    return casing;
}