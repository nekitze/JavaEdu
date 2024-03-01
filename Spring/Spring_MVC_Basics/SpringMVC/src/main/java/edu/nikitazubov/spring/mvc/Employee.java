package edu.nikitazubov.spring.mvc;

import edu.nikitazubov.spring.mvc.validation.CheckEmail;
import jakarta.validation.constraints.*;

import java.util.HashMap;
import java.util.Map;

public class Employee {
    @Size(min = 2, message = "Name must be min 2 symbols")
    private String name;

    @NotBlank(message = "Surname is required field")
    private String surname;

    @Min(value = 200, message = "Must be greater than 199")
    @Max(value = 1000, message = "Must be less than 1001")
    private int salary;
    private String department;
    private String laptop;
    private String[] languages;

    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{2}", message = "Please user pattern XXX-XX-XX")
    private String phoneNumber;

    @CheckEmail(value = "abc.com", message = "Email must ends with abc.com")
    private String email;

    private Map<String, String> departments;
    private Map<String, String> laptops;
    private Map<String, String> languageList;

    public Employee() {
        departments = new HashMap<>();
        departments.put("IT", "Information Technology");
        departments.put("HR", "Human Resources");
        departments.put("Sales", "Sales");

        laptops = new HashMap<>();
        laptops.put("Apple", "Apple");
        laptops.put("Lenovo", "Lenovo");
        laptops.put("ASUS", "ASUS");

        languageList = new HashMap<>();
        languageList.put("English", "EN");
        languageList.put("Deutch", "DE");
        languageList.put("French", "FR");
    }

    public Map<String, String> getDepartments() {
        return departments;
    }

    public void setDepartments(Map<String, String> departments) {
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLaptop() {
        return laptop;
    }

    public void setLaptop(String laptop) {
        this.laptop = laptop;
    }

    public Map<String, String> getLaptops() {
        return laptops;
    }

    public void setLaptops(Map<String, String> laptops) {
        this.laptops = laptops;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public Map<String, String> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(Map<String, String> languageList) {
        this.languageList = languageList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
