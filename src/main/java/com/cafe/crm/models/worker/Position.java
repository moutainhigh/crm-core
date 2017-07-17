package com.cafe.crm.models.worker;


import javax.persistence.*;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "jobName")
    private String jobName;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    public Position() {
    }

    public Position(String jobName) {
        this.jobName = jobName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return jobName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (id != null ? !id.equals(position.id) : position.id != null) return false;
        return jobName != null ? jobName.equals(position.jobName) : position.jobName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (jobName != null ? jobName.hashCode() : 0);
        return result;
    }
}
