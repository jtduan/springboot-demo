package rest.entity;

import rest.constants.Role;

import javax.persistence.*;
import java.util.EnumSet;

@Entity
@Table(name ="employee")
@DiscriminatorValue("EMPLOYEE")
public class Employee extends BaseEntity implements UserType{

    @Convert(converter = RolesConverter.class)
    private EnumSet<Role> roles;

    private double salary;

    public EnumSet<Role> getRoles() {
        return roles;
    }

    public void setRoles(EnumSet<Role> roles) {
        this.roles = roles;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
