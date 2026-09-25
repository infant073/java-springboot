package com.example.touristsafety.dto;

public class ProfitLossDto {

    private Double touristPassRevenue;
    private Double emergencyRescueOperationalCosts;
    private Double netProfitLoss;
    private Double profitMarginPercentage;

    public ProfitLossDto() {}

    public ProfitLossDto(Double touristPassRevenue, Double emergencyRescueOperationalCosts, Double netProfitLoss, Double profitMarginPercentage) {
        this.touristPassRevenue = touristPassRevenue;
        this.emergencyRescueOperationalCosts = emergencyRescueOperationalCosts;
        this.netProfitLoss = netProfitLoss;
        this.profitMarginPercentage = profitMarginPercentage;
    }

    public Double getTouristPassRevenue() { return touristPassRevenue; }
    public void setTouristPassRevenue(Double touristPassRevenue) { this.touristPassRevenue = touristPassRevenue; }

    public Double getEmergencyRescueOperationalCosts() { return emergencyRescueOperationalCosts; }
    public void setEmergencyRescueOperationalCosts(Double emergencyRescueOperationalCosts) { this.emergencyRescueOperationalCosts = emergencyRescueOperationalCosts; }

    public Double getNetProfitLoss() { return netProfitLoss; }
    public void setNetProfitLoss(Double netProfitLoss) { this.netProfitLoss = netProfitLoss; }

    public Double getProfitMarginPercentage() { return profitMarginPercentage; }
    public void setProfitMarginPercentage(Double profitMarginPercentage) { this.profitMarginPercentage = profitMarginPercentage; }
}
