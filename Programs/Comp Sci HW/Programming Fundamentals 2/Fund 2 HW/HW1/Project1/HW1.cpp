#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

const int stuROW = 10;
const int leadROW = 3;
const int COL = 6;

void studentInquiry(int[][COL], string[], string);
void studentAllProject(int[][COL], string[]);
void soloLeader(int[][COL], int[][COL], string[], string[]);
void leaderInquiry(int[][COL], int[][COL], string[], string[], int);
void projectInquiry(int[][COL], int[][COL], string[], string[], int);

int main()
{
	ifstream infile;
	string leaders[leadROW];
	int leadProjects[leadROW][COL] = {0};
	string students[stuROW];
	int stuProjects[stuROW][COL] = {0};

	//Input Variables
	int choice;
	string inputStudent;
	int inputLeader;
	int inputProject;

	//Temp Variables
	string tempName;
	int tempProject;
	char tempChar;
	int found = 0;

	cout << "Processing two input files: ProjectLeaders.txt and ProjectRegistry.txt...";
	
	infile.open("ProjectLeaders.txt");
	cout << "\n\n\tThe Leaders Array is:\n \t\t"; // Reading file into Leader Arrays
	for (int i = 0; i < leadROW; i++)
	{
		infile >> tempName;
		leaders[i] = tempName;
		do
		{
			infile >> tempProject;
			leadProjects[i][tempProject]++;
			infile.get(tempChar);
		} while (tempChar != '\n' && infile);

	}

	for (int i = 0; i < leadROW; i++)
	{
		cout << leaders[i] << "  ";
		for (int j = 0; j < COL; j++)
			cout << leadProjects[i][j] << "  ";
		cout << endl << "\t\t";
	}
	infile.close();
	
	//Students
	infile.open("ProjectRegistry.txt"); // Reading file into Student Arrays
	cout << "\n\tStudents\n\t\t";
	for (int i = 0; i < stuROW; i++)
	{
		infile >> tempName;
		students[i] = tempName;
		do
		{
			infile >> tempProject;
			stuProjects[i][tempProject]++;
			infile.get(tempChar);
		} while (tempChar != '\n' && infile);

	}

	for (int i = 0; i < stuROW; i++)
	{
		cout << students[i] << "  ";
		for (int j = 0; j < COL; j++)
			cout << stuProjects[i][j] << "  ";
		cout << endl << "\t\t";
	}
	infile.close();

	cout << "\nProcessed.\n\n";

	//It's Menu Time
	do {
		cout << endl;
		cout << "\tPlease choose an option: " << endl;
		cout << "\t\t1. Inquiry about a students list of projects." << endl;
		cout << "\t\t2. List the name of at least one student who works on ALL projects." << endl;
		cout << "\t\t3. (BONUS)List the name of at least one student who works with Only One faculty. " << endl;
		cout << "\t\t4. Inquiry about a professor, please choose from the following list." << endl;
		cout << "\t\t5. Inquiry about a project." << endl;
		cout << "\t\t6. Exit." << endl;

		cin >> choice;

		switch (choice)
		{
		case 1: cout << "Please enter the students Last Name you wish to inquire: ";
			cin >> inputStudent;
			
			for (int i = 0; i < stuROW; i++)
			{
				if (students[i] == inputStudent)
				{
					cout << "Name accepted. Pulling projects up now.\n\n";
					studentInquiry(stuProjects, students, inputStudent);
					found++;
				}
			}
			if (found == 0)
				cout << "\n\t\tStudent not found. Please try again.\n";
			found = 0;
			break;

		case 2: studentAllProject(stuProjects, students);
			break;

		case 3: soloLeader(stuProjects, leadProjects, leaders, students);
			break;

		case 4: cout << "\n\t\tPlease select a Professor (1-3):";
			cout << "\n\t\t\t1. Dr. x\n\t\t\t2. Dr. Y\n\t\t\t3. Dr. Z\n";
			cin >> inputLeader;
			leaderInquiry(stuProjects, leadProjects, leaders, students, inputLeader);
			break;

		case 5: cout << "\n\t\tPlease select a Project (1-5):";
			cin >> inputProject; 
			projectInquiry(stuProjects, leadProjects, leaders, students, inputProject);
			break;

		case 6: break;
		}
	} while (choice != 6);


	return 0;
}

void studentInquiry(int stuProjects[][COL], string students[], string inputStudent)
{
	int stuLocation;

	cout << "\tSearching for Projects....\n\n\t";

	cout << inputStudent << " is working on:\t";
	for (int i = 0; i < stuROW; i++)
	{
		if (students[i] == inputStudent)
			stuLocation = i;
	}
	for (int j = 0; j < COL; j++)
	{
		if (stuProjects[stuLocation][j] == 1)
		{
			cout << "Project #" << j << " ";
		}
	}
	cout << "\n";
}

void studentAllProject(int stuProjects[][COL], string students[])
{
	int tracker;

	cout << "\tSearching for Projects....\n\t";

	for (int i = 0; i < stuROW; i++)
	{
		tracker = 0;
		for (int j = 0; j < COL; j++)
		{
			if (stuProjects[i][j] == 1)
			{
				tracker++;
			}
			if (tracker == 5)
				cout << students[i] << " is working on all projects.";
		}
	}

	cout << "\n";
}

void soloLeader(int stuProjects[][COL], int leadProjects[][COL], string leaders[], string students[])
{
	cout << "\n\t\tSorry but I was unable to find a way to get this to work. Moving on.\n";
}

void leaderInquiry(int stuProjects[][COL], int leadProjects[][COL], string leaders[], string students[], int inputLeader)
{
	int leadLocation;
	string chosenLeader = leaders[inputLeader - 1];
	cout << "\tSearching for Other Members....\n\n\t";

	cout << "Dr. " << chosenLeader << " is working on these projects and who they are working with:";
	for (int i = 0; i < leadROW; i++)
	{
		if (leaders[i] == chosenLeader)
			leadLocation = i;
	}
	for (int j = 0; j < COL; j++)
	{
		if (leadProjects[leadLocation][j] == 1)
		{
			cout << "\n\t\tProject #" << j << ": ";
			for (int k = 0; k < leadROW; k++)
				if (leadProjects[k][j] == 1 && leaders[k] != leaders[inputLeader - 1])
					cout << "Dr. " << leaders[k] << " ";
			for (int k = 0; k < stuROW; k++)
				if (stuProjects[k][j] == 1)
					cout << students[k] << " ";
		}
	}
	cout << "\n";
}

void projectInquiry(int stuProjects[][COL], int leadProjects[][COL], string leaders[], string students[], int inputProject)
{
	cout << "\tSearching for Members of the Project....\n\n\t";

	cout << "Project #" << inputProject << " is being working on by:\n\t";

	cout << "Staff Members:\n\t\t";
	for (int k = 0; k < leadROW; k++)
		if (leadProjects[k][inputProject] == 1)
			cout << "Dr. " << leaders[k] << " ";
	cout << "\n\tStudent Members:\n\t\t";
	for (int k = 0; k < stuROW; k++)
		if (stuProjects[k][inputProject] == 1)
			cout << students[k] << " ";

	cout << "\n";
}
