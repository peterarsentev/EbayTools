package com.ebaytools.kernel.entity;

import java.util.Calendar;

public class FileSearching {
    private Long id;
    private String path;
    private String condition;
    private String listType;
    private Integer dayLeft;
    private Integer timeInterval;
    private Calendar runTime;

    public Calendar getRunTime() {
        return runTime;
    }

    public void setRunTime(Calendar runTime) {
        this.runTime = runTime;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Integer getDayLeft() {
        return dayLeft;
    }

    public void setDayLeft(Integer dayLeft) {
        this.dayLeft = dayLeft;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileSearching that = (FileSearching) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
