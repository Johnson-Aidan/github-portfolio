#include <iostream>
#include <string>
using namespace std;

const int SIZE = 50;

string doReverse(char[]);
bool isPalindrome(char[]);
void letterTally(char[]);
int countChar(char[], char);

int main()
{
	char word[SIZE];
	cout << "\n\t\tPlease input a word of at most 50 letters in all capitals: ";
	cin.getline(word, SIZE);

	cout << "\n\t\t" << word << " backwards is: " << doReverse(word);
	if (isPalindrome(word))
	{
		cout << "\n\t\t" << word << " is a palindrome!";
	}
	else if (isPalindrome(word) == false)
	{
		cout << "\n\t\t" << word << " is not a palindrome!";
	}
	cout << "\n\t\t" << "The amount of times each letter appears is:";
	letterTally(word);

	return 0;
}

string doReverse(char word[])
{
	string backwords;
	char* backPtr = word;

	while (*backPtr != '\0')
	{
		backPtr++;
	}
	while (backPtr != &word[0])
	{
		backPtr--;
		backwords += *backPtr;
	}
	return backwords;
}

bool isPalindrome(char word[])
{
	string backwords;
	char* backPtr = word;
	string forwards;
	char* forPtr = word;

	while (*backPtr != '\0')
	{
		backPtr++;
	}
	while (*forPtr != '\0')
	{
		forwards += *forPtr;
		forPtr++;
	}
	while (backPtr != &word[0])
	{
		backPtr--;
		backwords += *backPtr;
	}
	if (forwards == backwords)
		return true;
}

void letterTally(char word[])
{
	const int alphSIZE = 26;
	int tallyArray[alphSIZE];
	char* strPtr = word;

	for (int i = 0; i < alphSIZE; i++)
	{
		tallyArray[i] = 0;
	}	

	while (*strPtr != '\0')
	{
		tallyArray[*strPtr - 65]++;
			strPtr++;
	}

	for (int i = 0; i < alphSIZE; i++)
	{
		if (tallyArray[i] > 0)
		{
			cout << "\n\t\t\t" << char(i+65) << " showed up " << tallyArray[i] << " times!";
		}
	}
}

