//Aidan Johnson 1890052

import java.util.Scanner;

public class PayrollSystemTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int userChoice, SIZE, counter;
        double corpFund = 0;

        SalariedEmployee salariedEmployee = new SalariedEmployee("John", "Smith", "111-11-1111", 800.00);
        HourlyEmployee hourlyEmployee = new HourlyEmployee("Karen", "Price", "222-22-2222", 16.75, 40);
        CommissionEmployee commissionEmployee = new CommissionEmployee("Sue", "Jones", "333-33-3333", 10000, .06);
        BasePlusCommissionEmployee basePlusCommissionEmployee = new BasePlusCommissionEmployee("Bob", "Lewis", "444-44-4444", 5000, .04, 300);

        ConsultantEmployee consultantEmployee = new ConsultantEmployee("Mary", "Sue", "777-77-7777", 42, 12, 20.30);

        Employee[] employees = new Employee[5];

        employees[0] = salariedEmployee;
        employees[1] = hourlyEmployee;
        employees[2] = commissionEmployee;
        employees[3] = basePlusCommissionEmployee;
        employees[4] = consultantEmployee;

        System.out.println(employees[0]);


        System.out.println("Employees processed individually:");

        System.out.printf("%n%s%n%s: $%,.2f%n%n", salariedEmployee, "earned", salariedEmployee.earnings());
        System.out.printf("%s%n%s: $%,.2f%n%n", hourlyEmployee, "earned", hourlyEmployee.earnings());
        System.out.printf("%s%n%s: $%,.2f%n%n", commissionEmployee, "earned", commissionEmployee.earnings());
        System.out.printf("%s%n%s: $%,.2f%n%n", basePlusCommissionEmployee, "earned", basePlusCommissionEmployee.earnings());
        System.out.printf("%s%n%s: $%,.2f%n%n", consultantEmployee, "earned", consultantEmployee.earnings());



        do {
            System.out.println("\tPlease choose an option: ");
            System.out.println("\t\t1. Give all commission employees’ commission rates 10% raise. (including base plus commission employees)");
            System.out.println("\t\t2. Change the salary portion of base plus commission employees’ contribution to the corp. fund from 5% to 3%");
            System.out.println("\t\t3. Output the total amount currently in the fund");
            System.out.println("\t\t4. Test the CommissionEmployee array");
            System.out.println("\t\t5. Exit ");

            userChoice = input.nextInt();

            switch (userChoice) {
                case 1:
                    for (Employee currentEmployee : employees) {
                        if (currentEmployee instanceof BasePlusCommissionEmployee) {

                            BasePlusCommissionEmployee employee = (BasePlusCommissionEmployee) currentEmployee;

                            employee.setCommissionRate(.10 + employee.getCommissionRate());

                            System.out.printf("\t\t\tThe new base commission rate with 10%% increase for %s %s is: %,.2f%n", employee.getFirstName(), employee.getLastName(), employee.getCommissionRate());
                        } else if (currentEmployee instanceof CommissionEmployee) {

                            CommissionEmployee employee = (CommissionEmployee) currentEmployee;

                            employee.setCommissionRate(.10 + employee.getCommissionRate());

                            System.out.printf("\t\t\tThe new base commission rate with 10%% increase for %s %s is: %,.2f%n", employee.getFirstName(), employee.getLastName(), employee.getCommissionRate());
                        }
                    }
                    System.out.println();
                    break;

                case 2:
                    for (Employee currentEmployee : employees) {
                        if (currentEmployee instanceof BasePlusCommissionEmployee) {
                            ((BasePlusCommissionEmployee) currentEmployee).setContributionAmt(.03);
                        }
                    }
                    System.out.println("\t\t\tNew contribution amount set.\n");
                    break;

                case 3:
                    System.out.print("\t\t\tThe current corp. fund is: ");

                    for (Employee currentEmployee : employees) {
                        corpFund += currentEmployee.corpFundContribution();
                    }
                    System.out.printf("$%,.2f \n\n", corpFund);
                    corpFund = 0;
                    break;

                case 4:
                    System.out.print("\t\t\tPlease enter a value for the size of the array to index:");
                    SIZE = input.nextInt();

                    counter = 0;

                    CommissionEmployee[] c_employees = new CommissionEmployee[SIZE];

                    for (int i = 0; i < employees.length; i++) {
                        if (employees[i] instanceof CommissionEmployee) {
                            c_employees[counter] = (CommissionEmployee) employees[i];
                            counter++;
                        } else if (employees[i] instanceof BasePlusCommissionEmployee) {
                            c_employees[counter] = (BasePlusCommissionEmployee) employees[i];
                            counter++;
                        }
                    }

                    for (Employee employee : c_employees) {
                        System.out.printf("\t\t\t\t%s %s's contribution is: $%.2f \n", employee.getFirstName(), employee.getLastName(), employee.corpFundContribution());
                    }
                    break;
                default:
                    break;
            }
        } while (userChoice != 5);
    }
}


/**************************************************************************
 * (C) Copyright 1992-2018 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
