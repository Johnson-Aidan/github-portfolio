#include <iostream>
#include <string>

using namespace std;

int main()
{
	// The variables
	string first_name; // Users first name
	string last_name; // Users last name
	int graduate; // Users grad
	string grad_status; // String version of Graduate status
	int honor_student; // If Honor student or not
	string honor_status; // If User is an honor student or not
	int hours; // Users undergrad hours
	char midterm_grade; // Users midterm grade
	char final_grade; // Users final grade
	int master_year; // Users master thesis course year
	string grade_requirement; // If user meets requirements with grades
	string hour_requirement; // If user meets requirements with hours

	cout << "Please enter your first name: ";
	cin >> first_name; // Takes the input for users name
	cout << "Please enter your last name: ";
	cin >> last_name;

	cout << endl << "Hello, " << first_name << " " << last_name << "! Thanks for using our program. " << endl << "Please answer the following questions:" << endl << endl;

	cout << "What is your status? (Insert 1 or 2)" << endl << "1. Undergraduate" << endl << "2. Graduate" << endl;
	cin >> graduate; // Finds their graduation status

	if (graduate == 1)
	{
		grad_status = "Undergraduate";

		cout << endl << "Are you an honor student? (Insert 1 or 2)" << endl << "1. Yes" << endl << "2. No" << endl;
		cin >> honor_student; // Finds if they are a honor student

		if (honor_student == 1)
		{
			honor_status = "Yes";
			cout << endl << "Name: " << first_name << " " << last_name << endl;
			cout << "Graduate Status: " << grad_status << endl;
			cout << "Honor Student: " << honor_status << endl;
			cout << "Student, " << first_name << " " << last_name << " is qualified for the assistantship." << endl;
			system("pause");
		}

		else
		{
			honor_status = "No";
			cout << endl << "How many hours of undergraduate level programming have you taken: ";
			cin >> hours;

			if (hours >= 15)
				hour_requirement = "Yes";
			else
				hour_requirement = "No";

			cout << "Enter your grade for midterm exam: ";
			cin >> midterm_grade;
			cout << "Enter your grade for final exam: ";
			cin >> final_grade;

			if ((midterm_grade == 65) || (final_grade == 65))
				grade_requirement = "Yes";
			else
				grade_requirement = "No";


			if(grade_requirement == "No" && hour_requirement== "No")
			{
				cout << endl << "Name: " << first_name << " " << last_name << endl;
				cout << "Graduate Status: " << grad_status << endl;
				cout << "Honor Student: " << honor_status << endl;
				cout << "Programming Hours: " << hours << endl;
				cout << "Grade Requirements: " << grade_requirement << endl << endl;
				cout << "Student, " << first_name << " " << last_name << " is qualified for the assistantship:" << endl;
				cout << "The hour and grade criteria is not met." << endl;
				system("pause");
			}
			else if (hour_requirement == "Yes" || grade_requirement == "Yes")
			{
				if (hour_requirement == "Yes" && grade_requirement == "Yes")
				{
					cout << endl << "Name: " << first_name << " " << last_name << endl;
					cout << "Graduate Status: " << grad_status << endl;
					cout << "Honor Student: " << honor_status << endl;
					cout << "Programming Hours: " << hours << endl;
					cout << "Grade Requirements: " << grade_requirement << endl << endl;
					cout << "Student, " << first_name << " " << last_name << " is qualified for the assistantship:" << endl;
					system("pause");
				}

				else if (hour_requirement == "No")
				{
					cout << endl << "Name: " << first_name << " " << last_name << endl;
					cout << "Graduate Status: " << grad_status << endl;
					cout << "Honor Student: " << honor_status << endl;
					cout << "Programming Hours: " << hours << endl;
					cout << "Grade Requirements: " << grade_requirement << endl << endl;
					cout << "Student, " << first_name << " " << last_name << " is not qualified for the assistantship:" << endl;
					cout << "The hour criteria is not met." << endl;
					system("pause");
				}

				else if (grade_requirement == "No")
				{
					cout << endl << "Name: " << first_name << " " << last_name << endl;
					cout << "Graduate Status: " << grad_status << endl;
					cout << "Honor Student: " << honor_status << endl;
					cout << "Programming Hours: " << hours << endl;
					cout << "Grade Requirements: " << grade_requirement << endl << endl;
					cout << "Student, " << first_name << " " << last_name << " is not qualified for the assistantship:" << endl;
					cout << "The grade criteria is not met." << endl;
					system("pause");
				}
			}
		}

	}
	else if (graduate == 2)
	{
		grad_status = "Graduate";
		cout << "Please enter the academic year of your enrollment in the master thesis course: ";
		cin >> master_year;

		if (master_year == 2020)
		{
			cout << "Enter your grade for midterm exam: ";
			cin >> midterm_grade;
			cout << "Enter your grade for final exam: ";
			cin >> final_grade;

			if ((midterm_grade == 65) && (final_grade == 65))
				grade_requirement = "Yes";
			else
				grade_requirement = "No";
			if (grade_requirement == "Yes")
			{
				cout << endl << "Name: " << first_name << " " << last_name << endl;
				cout << "Graduate Status: " << grad_status << endl;
				cout << "Master thesis course enrollment date: " << master_year << endl << endl;
				cout << "Student, " << first_name << " " << last_name << " is qualified for the assistantship." << endl;
				system("pause");
			}
			else if (grade_requirement == "No")
			{
				cout << endl << "Name: " << first_name << " " << last_name << endl;
				cout << "Graduate Status: " << grad_status << endl;
				cout << "Master thesis course enrollment date: " << master_year << endl << endl;
				cout << "Student, " << first_name << " " << last_name << " is not qualified for the assistantship:" << endl;
				cout << "The grade criteria is not met." << endl;
				system("pause");
			}
		}

		else
		{
			cout << endl << "Name: " << first_name << " " << last_name << endl;
			cout << "Graduate Status: " << grad_status << endl;
			cout << "Master thesis course enrollment date: " << master_year << endl << endl;
			cout << "Student, " << first_name << " " << last_name << " is not qualified for the assistantship:" << endl;
			cout << "The current enrollment in master thesis course criteria is not met." << endl;
			system("pause");
		}
	}
	return 0;
}