package com.encora.expenses;

import com.encora.expenses.domain.Employee;
import com.encora.expenses.domain.Employees;
import com.encora.expenses.domain.ExpenseClaim;
import com.encora.expenses.exceptions.EmployeeNotFoundException;
import com.encora.expenses.management.ExpenseManagementProcess;
import com.encora.expenses.management.ExpressExpenseManagementProcess;
import com.encora.expenses.management.RegularExpenseManagementProcess;
import com.encora.expenses.ui.UIFunctions;
import com.encora.expenses.utilities.ExpenseAnalysis;
import com.encora.expenses.utilities.ExpenseAnalysisTempImpl;

import java.time.LocalDate;
import java.util.Scanner;

public class ExpenseManagementSystem {
    public static void main(String[] args) {
        Employees employees = new Employees();
        Scanner SC = new Scanner(System.in);
        UIFunctions uiFunctions = new UIFunctions();

        ExpenseManagementProcess expressEMP = new ExpressExpenseManagementProcess();
        ExpenseManagementProcess regularEMP = new RegularExpenseManagementProcess();

        boolean exit = false;

        while (!exit) {
            System.out.println("-------------------------");
            System.out.println("Expense Management System");
            System.out.println("-------------------------");
            System.out.println("e - register new employee");
            System.out.println("c - register new claim");
            System.out.println("p - print all employees");
            System.out.println("a - approve claim");
            System.out.println("r1 - outstanding expense claims");
            System.out.println("r2 - paid expense claims");
            System.out.println("r3 - expense claims above specified amount");
            System.out.println("x - exit");

            String option = SC.nextLine();

            ExpenseAnalysis expenseAnalysis = new ExpenseAnalysisTempImpl();

            switch (option) {
                case "e": //register new employee
                    Employee e = uiFunctions.registerNewEmployee();
                    employees.addEmployee(e);
                    break;
                case "c": //register new claim
                    ExpenseClaim claim = uiFunctions.registerNewExpenseClaim();
                    try {
                        employees.addExpenseClaim(claim);
                        expressEMP.registerExpenseClaim(claim);
                        int id = regularEMP.registerExpenseClaim(claim);
                        System.out.println("The claim has been registered with ID " + id);
                    } catch (EmployeeNotFoundException ex) {
                        System.out.println("There was no employee with ID " + claim.getEmployeeId());
                    }
                    break;
                case "p": //print
                    employees.printEmployees();
                    break;
                case "x": //exit
                    exit = true;
                    break;
                case "a":
                    //get the ID of the claim
                    System.out.println("Enter claim id");
                    int claimId = SC.nextInt();
                    SC.nextLine();
                    //get the employee ID
                    System.out.println("Enter employee id");
                    int employeeId = SC.nextInt();
                    SC.nextLine();
                    //find the employee
                    Employee foundEmployee = employees.findById(employeeId);
                    //call the relevant method
                    System.out.println("R for regular - E for express.");
                    String claimType = SC.nextLine();
                    claimType = claimType.toUpperCase();

                    ExpenseManagementProcess requestedProcess;
                    if (claimType.equals("R")) {
                        requestedProcess = regularEMP;
                    } else {
                        requestedProcess = expressEMP;
                    }
                    boolean result = requestedProcess.approveClaim(claimId, foundEmployee);
                    System.out.println("The result was " + result);

                    break;
                case "r1":
                    expenseAnalysis.printOutstandingClaims();
                    break;
                case "r2":
                    System.out.println("Enter date from ");
                    String dateFrom = SC.nextLine();

                    System.out.println("Enter date to ");
                    String dateTo = SC.nextLine();

                    expenseAnalysis.printPaidClaims(LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
                    break;
                case "r3":
                    System.out.println("Enter amount ");
                    Double amount = SC.nextDouble();
                    SC.nextLine();

                    expenseAnalysis.printClaimsOverAmount(amount);
                    break;
                default:
                    System.out.println("Option not valid");
            }
        }
    }
}
