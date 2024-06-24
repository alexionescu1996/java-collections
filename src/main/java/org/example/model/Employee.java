package org.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
public class Employee extends Person {
    private Integer id;

    public Employee(Integer id, String name, Integer age, Double salary) {
        super(name, age, salary);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(id);
        return result;
    }
}
