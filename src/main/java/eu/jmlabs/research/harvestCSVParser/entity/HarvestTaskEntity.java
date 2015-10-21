package eu.jmlabs.research.harvestCSVParser.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Mark0 on 19/10/15.
 */
@Entity
@Table(name = "harvest_task", schema = "", catalog = "database")
public class HarvestTaskEntity {
    private int id;
    private String name;
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
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HarvestTaskEntity that = (HarvestTaskEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "harvestTask")
    public Collection<HarvestWorklogEntity> getHarvestWorklogs() {
        return harvestWorklogs;
    }

    public void setHarvestWorklogs(Collection<HarvestWorklogEntity> harvestWorklogs) {
        this.harvestWorklogs = harvestWorklogs;
    }
}
