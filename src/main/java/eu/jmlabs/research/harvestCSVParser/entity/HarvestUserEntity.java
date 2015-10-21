package eu.jmlabs.research.harvestCSVParser.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Mark0 on 19/10/15.
 */
@Entity
@Table(name = "harvest_user", schema = "", catalog = "database")
public class HarvestUserEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private byte employee;
    private Collection<HarvestWorklogEntity> harvestWorklogs;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "firstName", nullable = false, insertable = true, updatable = true, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName", nullable = false, insertable = true, updatable = true, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "username", nullable = true, insertable = true, updatable = true, length = 45)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email", nullable = true, insertable = true, updatable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "employee", nullable = false, insertable = true, updatable = true)
    public byte getEmployee() {
        return employee;
    }

    public void setEmployee(byte employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HarvestUserEntity that = (HarvestUserEntity) o;

        if (id != that.id) return false;
        if (employee != that.employee) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (int) employee;
        return result;
    }

    @OneToMany(mappedBy = "harvestUser")
    public Collection<HarvestWorklogEntity> getHarvestWorklogs() {
        return harvestWorklogs;
    }

    public void setHarvestWorklogs(Collection<HarvestWorklogEntity> harvestWorklogs) {
        this.harvestWorklogs = harvestWorklogs;
    }
}
