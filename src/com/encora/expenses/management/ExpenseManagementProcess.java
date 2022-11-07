package com.encora.expenses.management;

import com.encora.expenses.domain.Employee;
import com.encora.expenses.domain.ExpenseClaim;

public interface ExpenseManagementProcess {

    public int registerExpenseClaim(ExpenseClaim claim);
    public boolean approveClaim(int id, Employee employee);
}
