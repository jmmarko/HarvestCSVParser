package eu.jmlabs.research.harvestCSVParser.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Mark0 on 19/10/15.
 */
@Entity
@Table(name = "harvest_project", schema = "", catalog = "database")
public class HarvestProjectEntity {
    private int id;
    private String name;
    private String code;
    private Collection<HarvestWorklogEntity> harvestWorklogsById;

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
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code", nullable = true, insertable = true, updatable = true, length = 45)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HarvestProjectEntity that = (HarvestProjectEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "harvestProject")
    public Collection<HarvestWorklogEntity> getHarvestWorklogsById() {
        return harvestWorklogsById;
    }

    public void setHarvestWorklogsById(Collection<HarvestWorklogEntity> harvestWorklogsById) {
        this.harvestWorklogsById = harvestWorklogsById;
    }
}
