package ru.job4j.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastname;
    @Column(name = "unique_id_number", unique = true)
    private String uniqueIdNumber;
    private Timestamp hired;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee_id")
    private Set<Person> accounts = new HashSet<>();

    public static Employee of(String name, String lastname, String uniqueIdNumber) {
        Employee employee = new Employee();
        employee.name = name;
        employee.lastname = lastname;
        employee.uniqueIdNumber = uniqueIdNumber;
        employee.hired = new Timestamp(System.currentTimeMillis());
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUniqueIdNumber() {
        return uniqueIdNumber;
    }

    public void setUniqueIdNumber(String uniqueIdNumber) {
        this.uniqueIdNumber = uniqueIdNumber;
    }

    public Timestamp getHired() {
        return hired;
    }

    public void setHired(Timestamp hired) {
        this.hired = hired;
    }

    public Set<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Person> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Person person) {
        this.accounts.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(uniqueIdNumber, employee.uniqueIdNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueIdNumber);
    }
}
