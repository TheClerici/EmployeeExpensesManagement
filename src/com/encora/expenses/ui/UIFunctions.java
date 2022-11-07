package com.encora.expenses.ui;

import com.encora.expenses.domain.*;
import com.encora.expenses.exceptions.InvalidEmployeeIdException;
import com.encora.expenses.exceptions.NameTooShortException;
import com.encora.expenses.utilities.EmployeeUtilities;

import java.time.LocalDate;
import java.util.Scanner;

public class UIFunctions {

    public Employee registerNewEmployee() {

        Employee employee = new Employee();
        Scanner SC = new Scanner(System.in);
        EmployeeUtilities employeeUtilities = new EmployeeUtilities();

        boolean validId = false;
        while(!validId) {
            System.out.println("Enter the id");
            String inputId = SC.nextLine();
            try {
                Integer id = employeeUtilities.validateEmployeeId(inputId);
                employee.setId(id);
                validId = true;
            } catch (InvalidEmployeeIdException e) {
                System.out.println("The id you entered was invalid - try again");
            }
        }

        System.out.println("Enter the title");
        String title = SC.nextLine();
        employee.setTitle(title);

        boolean validName = false;
        while(!validName) {
            System.out.println("Enter the first name");
            String firstName = SC.nextLine();

            System.out.println("Enter the surname");
            String surname = SC.nextLine();

            try {
                employeeUtilities.validateEmployeeName(firstName, surname);
                employee.setFirstName(firstName);
                employee.setSurname(surname);
                validName = true;
            } catch (NameTooShortException e) {
                System.out.println("The name you entered was too short - try again");
            }
        }

        System.out.println("Enter the job title");
        String jobTitle = SC.nextLine();
        employee.setJobTitle(jobTitle);

        boolean validDepartment = false;
        while(!validDepartment) {
            System.out.println("Enter the department");
            String department = SC.nextLine();
            try {
                Department d = Department.valueOf(department.toUpperCase());
                employee.setDepartment(d);
                validDepartment = true;
            } catch (IllegalArgumentException e) {
                System.out.println("The department you entered was not valid - try again");
            }
        }

        System.out.println("Staff member? Y/N");
        String staffMember = SC.nextLine();
        if(staffMember.toUpperCase().equals("Y")) {
            StaffEmployee staff = new StaffEmployee(employee);

            System.out.println("Enter the username");
            String username = SC.nextLine();
            staff.setUsername(username);

            System.out.println("Enter the password");
            String password = SC.nextLine();
            staff.setUsername(password);

            return staff;
        }

        return employee;
    }

    public ExpenseClaim registerNewExpenseClaim() {

        Scanner SC = new Scanner(System.in);
        EmployeeUtilities employeeUtilities = new EmployeeUtilities();

        System.out.println("Enter the claim id");
        int claimId = SC.nextInt();
        SC.nextLine();

        System.out.println("Enter the emeployee id");
        int employeeId = SC.nextInt();
        SC.nextLine();

        LocalDate dateOfClaim = LocalDate.now();

        ExpenseClaim claim = new ExpenseClaim(claimId, employeeId, dateOfClaim);

        boolean finished = false;
        while (!finished) {
            System.out.println("Enter the expense item id");
            int eiId = SC.nextInt();
            SC.nextLine();

            boolean validExpenseType = false;
            ExpenseType eiType = null;
            while(!validExpenseType) {
                System.out.println("Enter the expense type");
                String expenseType = SC.nextLine();
                try {
                    eiType = ExpenseType.valueOf(expenseType.toUpperCase());
                    validExpenseType= true;
                } catch (IllegalArgumentException e) {
                    System.out.println("The expense type you entered was not valid - try again");
                }
            }
            System.out.println("Enter the description");
            String description = SC.nextLine();

            System.out.println("Enter the amount");
            Double amount = SC.nextDouble();
            SC.nextLine();

            ExpenseItem ei = new ExpenseItem(eiId, claimId, eiType, description, amount);
            claim.addExpenseItem(ei);

            System.out.println("Enter another expense item? Y/N");
            String anyMore = SC.nextLine();

            if (!anyMore.toUpperCase().equals("Y")) {
                finished = true;
            }
        }

        return claim;
    }
}
