package com.encora.expenses;

import com.encora.expenses.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setTitle("Mr");
        employee1.setFirstName("Matt");
        employee1.setSurname("Greencroft");

        System.out.println("-------------------- Pruebas: --------------------");
        System.out.println(employee1.getMailingName());
        System.out.println(employee1.getMailingName(true));
        System.out.println(employee1.getMailingName(false));

        Employee employee2 = new Employee(2, "Manager");
        employee2.setTitle("Dr");
        employee2.setFirstName("Denis");
        employee2.setSurname("Yellow");

        System.out.println("-------------------- Pruebas: --------------------");

        ExpenseClaim claim1 = new ExpenseClaim(1, 1, LocalDate.now());
        System.out.println("id: " + claim1.getId());
        System.out.println("employee id: " + claim1.getEmployeeId());
        System.out.println("date: " + claim1.getDateOfClaim());
        System.out.println("amount: " + claim1.getTotalAmount());
        claim1.setPaid(true);
        System.out.println("paid? " + claim1.getPaid() + ". approved? " + claim1.getApproved());
        claim1.setApproved(true);
        System.out.println("paid? " + claim1.getPaid() + ". approved? " + claim1.getApproved());
        claim1.setPaid(true);
        System.out.println("paid? " + claim1.getPaid() + ". approved? " + claim1.getApproved());

        System.out.println("-------------------- Pruebas: --------------------");

        ExpenseItem item1 = new ExpenseItem(1, 1, ExpenseType.ACCOMODATION, "Work trip to Miami", 300.0);
        System.out.println("id: " + item1.getId());
        System.out.println("claim id: " + item1.getClaimId());
        System.out.println("expense type: " + item1.getExpenseType());
        System.out.println("description: " + item1.getDescription());
        System.out.println("amount: " + item1.getAmount());

        System.out.println("-------------------- Pruebas: --------------------");

        Employees employees = new Employees();
        employees.addEmployee(employee1);
        employees.addEmployee(employee2);
        employees.addEmployee( new Employee
                (3, "Mrs", "Susan", "Brown", "Director", Department.MARKETING) );
        employees.printEmployees();

        System.out.println("-------------------- Pruebas: --------------------");

        //If surname is on employees returns employee
        Employee foundEmployee = employees.findBySurname("Brown");
        checkNull(foundEmployee);


        foundEmployee = employees.findBySurname("Cyan");
        checkNull(foundEmployee);

        System.out.println("-------------------- Pruebas: --------------------");

        System.out.println(employee1.toString());
        System.out.println(employee1);

        Employee employee3 = new Employee();
        employee3.setId(1);
        employee3.setTitle("Mr");
        employee3.setFirstName("Matt");
        employee3.setSurname("Greencroft");

        System.out.println("-------------------- Pruebas: --------------------");

        System.out.println(employee1 == employee3);
        System.out.println(employee1.equals(employee3));
        System.out.println(employee1.getClass());

        System.out.println("-------------------- Pruebas Library: --------------------");

        System.out.println(employee1);

        ObjectMapper objectMapper = new ObjectMapper();
        String employee1Json = objectMapper.writeValueAsString(employee1);
        System.out.println(employee1Json);


        StaffEmployee employeeFromJson = objectMapper.readValue(employee1Json, StaffEmployee.class);
        System.out.println(employeeFromJson);
    }

    //por gusto y amor al arte.
    public static void checkNull(Employee foundEmployee) {
        if (foundEmployee != null) System.out.println("Found " + foundEmployee.getMailingName());
        else System.out.println("Didn't find");
    }
}
