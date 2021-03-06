package com.kulbako.backend.models;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Департамента предприятия.
 * @author Артемий Кульбако
 * @version 1.4
 */
@Entity @Table(name = "departments")
public class Department implements Serializable {

    @Transient private static final long serialVersionUID = 4L;
    @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;
    @NotNull private String name;
    @OneToMany private Set<Employee> employees;

    public Department(String name, Set<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    public Department(String name) {
        this.name = name;
    }

    public Department() {}

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Set<Employee> getEmployees() { return employees; }

    public void setEmployees(Set<Employee> employees) { this.employees = employees; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, employees);
    }

    @Override
    public String toString() { return "Department{" + "id = " + id + ", name = " + name + '}'; }
}
