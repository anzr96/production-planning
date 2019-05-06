package model.entity;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class WorkingCalendar {
    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.DATE)
    private Date beginOfYear;
    @Temporal(TemporalType.DATE)
    private Date endOfYear;
    private int yearValue;
    @JoinColumn(name = "fk_days", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name="INDEX_COL_DAYS")
    private List<WorkingDays> workingDays;
    @JoinColumn(name = "fk_budget", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Budget budget;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBeginOfYear() {
        return beginOfYear;
    }

    public void setBeginOfYear(Date beginOfYear) {
        this.beginOfYear = beginOfYear;
    }

    public Date getEndOfYear() {
        return endOfYear;
    }

    public void setEndOfYear(Date endOfYear) {
        this.endOfYear = endOfYear;
    }

    public List<WorkingDays> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WorkingDays> workingDays) {
        this.workingDays = workingDays;
    }

    public int getYearValue() {
        return yearValue;
    }

    public void setYearValue(int yearValue) {
        this.yearValue = yearValue;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
