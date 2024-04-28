package com.shafi.construction.repo;

public class CustomerContract {
    private String customerId;
    private String contractId;
    private String geoZone;
    private String teamCode;
    private String projectCode;
    private String buildDuration;

    public String getCustomerId() {
        return customerId;
    }

    public String getContractId() {
        return contractId;
    }

    public String getGeoZone() {
        return geoZone;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getBuildDuration() {
        return buildDuration;
    }

    public CustomerContract(String[] fieldArray){
        this.customerId =fieldArray[0];
        this.contractId = fieldArray[1];
        this.geoZone =fieldArray[2];
        this.teamCode=fieldArray[3];
        this.projectCode=fieldArray[4];
        this.buildDuration=fieldArray[5];

    }
}
