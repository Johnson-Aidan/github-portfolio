#include <iostream>
#include <string>
using namespace std;

void encrypt(string &, int);
void decryptKey(string&, int);
void decryptBruteforce(string);

int main()
{
    string phrase;
    int key, menuChoice, decrpytChoice;
    
    do
    {
        cout << "Please select an option:" << endl;
        cout << "\t1. Encrypt\n";
        cout << "\t2. Decrypt\n";
        cout << "\t3. Exit\n\n";

        cin >> menuChoice;
        cin.ignore();

        switch (menuChoice)
        {
            case 1:   cout << "Please input a phrase in all caps to encrypt: ";
                getline(cin, phrase);

                cout << "Please input a key to shift/encrpyt the phrase with: ";
                cin >> key;

                cout << "\nEncrypting please wait..." << endl << endl;
                encrypt(phrase, key);

                cout << "The encrpyted phrase is: " << phrase << endl << endl;
                break;
            
            case 2:  cout << "Please input a phrase in all caps to decrypt: ";
                getline(cin, phrase);

                cout << "Please select an option:" << endl;
                cout << "\t1. Specific key\n";
                cout << "\t2. Brute force\n";
                cin >> decrpytChoice;

                switch (decrpytChoice)
                {
                    case 1: cout << "Please input a key to decrypt with: ";
                        cin >> key;

                        decryptKey(phrase, key);
                        cout << "The decrypted phrase using the key " << key << " is " << phrase << endl << endl;
                        break;

                    case 2: cout << "The decrypted phrases are: " << endl;
                        decryptBruteforce(phrase);
                        cout << endl << endl;
                        break;

                    default: cout << "Invalid choice, returning to menu.\n\n";
                }
                break;

            case 3: cout << "Thank you for using my program!";
                break;

            default: cout << "Invalid choice\n\n";
        }
    } while (menuChoice != 3);

    return 0;
}

void encrypt(string &phrase, int key)
{
    for (int i = 0; i < phrase.size(); i++)
    {
        //Ensures the key will be within the alphabet size 
        //(Allows for larger sized keys but breaks them down to the necessary size to prevent excessive looping)
        key = key % 26; 
        if ((phrase[i] >= 'A') && (phrase[i] <= 'Z')) //Checks if the current letter in "phrase" is a letter before proceeding
        {
            if ((phrase[i] + key) < 'A') //Checks if the final key would be before 'A'
            {
                phrase[i] = 'Z' - ('A' - (phrase[i] + key) - 1); //Finds the overflow and wraps it back behind 'Z'
            }
            else if ((phrase[i] + key) > 'Z') //Checks if the final key would be before 'Z'
            {
                phrase[i] = 'A' + ((phrase[i] - 'A' + key) % 26); //Finds the overflow and wraps it back over 'A'
            }
            else //If the final key would be fine 
            {
                phrase[i] += key; //Implements the key normally
            }
        }
    }
}

void decryptKey(string& phrase, int key)
{
    for (int i = 0; i < phrase.size(); i++)
    {
        key = key % 26;
        if ((phrase[i] >= 'A') && (phrase[i] <= 'Z'))
        {
            if ((phrase[i] - key) < 'A')
            {
                phrase[i] = 'Z' - ('A' - (phrase[i] - key) - 1);
            }
            else if ((phrase[i] - key) > 'Z')
            {
                phrase[i] = 'A' + ((phrase[i] - 'A' - key) % 26);
            }
            else
            {
                phrase[i] -= key;
            }
        }
    }
}

void decryptBruteforce(string phrase)
{
    for (int key = 1; key <= 25; key++)
    {
        string decryptionPhrase = phrase;
        decryptKey(decryptionPhrase, key);//Calls upon the decrypt to go through each key
        cout << "Key #" << key << ": " << decryptionPhrase << endl;
    }
}