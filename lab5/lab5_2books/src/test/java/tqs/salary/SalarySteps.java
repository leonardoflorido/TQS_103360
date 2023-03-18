package tqs.salary;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SalarySteps {
    SalaryManager manager;

    @Given("the salary management system is initialized with the following data")
    public void the_salary_management_system_is_initialized_with_the_following_data(final DataTable table) throws Throwable {
        List<Employee> employees = convertDataTableToList(table);
        manager = new SalaryManager(employees);
    }

    @When("the boss increases the salary for the employee with id {string} by {int}%")
    public void the_boss_increases_the_salary_for_the_employee_with_id_by(final String id, final int increaseInPercent) throws Throwable {
        manager.increaseSalary(Integer.parseInt(id.replace("'", "")), increaseInPercent);
    }

    @Then("the payroll for the employee with id {string} should display a salary of {float}")
    public void the_payroll_for_the_employee_with_id_should_display_a_salary_of(final String id, final float salary) throws Throwable {
        Employee nominee = manager.getPayroll(Integer.parseInt(id.replace("'", "")));
        assertThat(nominee.getSalary(), equalTo(salary));
    }

    public List<Employee> convertDataTableToList(DataTable table) {
        List<Employee> employees = new ArrayList<Employee>();
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));
            String user = row.get("user");
            float salary = Float.parseFloat(row.get("salary"));
            Employee employee = new Employee(id, user, salary);
            employees.add(employee);
        }
        return employees;
    }
}
