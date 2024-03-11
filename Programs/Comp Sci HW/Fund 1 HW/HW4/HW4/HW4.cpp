#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
    ifstream infile;
    infile.open("members.txt");
    string nameSearch;
    string name;   
    int age;       
    int membersChecked = 1;   
    // User input for the name to search
    cout << "Please enter a first name: ";
    cin >> nameSearch;

    while (infile >> name) // Searches file for the name
    {
        infile >> age; // Searches file for the age too
        if (nameSearch != name)
        {
            membersChecked++;
        }
        else
        {
            break; // Breaks out of the while
        }
    }
   
    if (nameSearch != name)
    { // If the name didn't come up
        cout << nameSearch << " is not listed in the file." << endl;
    }
    else
    { // If the name came up
        cout << name << " is " << age << " years old." << endl;
        cout << "This name appears on line #" << membersChecked << endl;
    }
  
    infile.close();
    cin.get();
    cin.get();
    return 0;
}
