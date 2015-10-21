package eu.jmlabs.research.harvestCSVParser.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Mark0 on 19/10/15.
 */
@Entity
@Table(name = "harvest_worklog", schema = "", catalog = "database")
public class HarvestWorklogEntity {
    private int id;
    private Timestamp created;
    private double hours;
    private String notes;
    private HarvestUserEntity harvestUser;
    private HarvestProjectEntity harvestProject;
    private HarvestTaskEntity harvestTask;
    private HarvestDepartmentEntity harvestDepartment;

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
    @Column(name = "created", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "hours", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 16777215)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HarvestWorklogEntity that = (HarvestWorklogEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.hours, hours) != 0) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        temp = Double.doubleToLongBits(hours);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "harvest_user_id", referencedColumnName = "id", nullable = false)
    public HarvestUserEntity getHarvestUser() {
        return harvestUser;
    }

    public void setHarvestUser(HarvestUserEntity harvestUser) {
        this.harvestUser = harvestUser;
    }

    @ManyToOne
    @JoinColumn(name = "harvest_project_id", referencedColumnName = "id", nullable = false)
    public HarvestProjectEntity getHarvestProject() {
        return harvestProject;
    }

    public void setHarvestProject(HarvestProjectEntity harvestProject) {
        this.harvestProject = harvestProject;
    }

    @ManyToOne
    @JoinColumn(name = "harvest_task_id", referencedColumnName = "id", nullable = false)
    public HarvestTaskEntity getHarvestTask() {
        return harvestTask;
    }

    public void setHarvestTask(HarvestTaskEntity harvestTask) {
        this.harvestTask = harvestTask;
    }

    @ManyToOne
    @JoinColumn(name = "harvest_department_id", referencedColumnName = "id", nullable = false)
    public HarvestDepartmentEntity getHarvestDepartment() {
        return harvestDepartment;
    }

    public void setHarvestDepartment(HarvestDepartmentEntity harvestDepartment) {
        this.harvestDepartment = harvestDepartment;
    }
}
