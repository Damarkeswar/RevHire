package com.revhire.model;

import java.sql.Timestamp;

public class Company {

    private int companyId;
    private int employerId;
    private String companyName;
    private String industry;
    private int companySize;
    private String description;
    private String website;
    private String location;
    private Timestamp createdAt;

    // getters & setters

    public int getCompanyId() {
        return companyId;
    }
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getEmployerId() {
        return employerId;
    }
    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getCompanySize() {
        return companySize;
    }
    public void setCompanySize(int companySize) {
        this.companySize = companySize;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
