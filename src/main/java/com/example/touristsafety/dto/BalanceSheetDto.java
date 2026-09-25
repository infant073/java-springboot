package com.example.touristsafety.dto;

public class BalanceSheetDto {

    private Double totalAssets;
    private Double equipmentAssets;
    private Double cashAssets;
    private Double totalLiabilities;
    private Double vendorPayables;
    private Double netFinancialPosition;

    public BalanceSheetDto() {}

    public BalanceSheetDto(Double totalAssets, Double equipmentAssets, Double cashAssets, Double totalLiabilities, Double vendorPayables, Double netFinancialPosition) {
        this.totalAssets = totalAssets;
        this.equipmentAssets = equipmentAssets;
        this.cashAssets = cashAssets;
        this.totalLiabilities = totalLiabilities;
        this.vendorPayables = vendorPayables;
        this.netFinancialPosition = netFinancialPosition;
    }

    public Double getTotalAssets() { return totalAssets; }
    public void setTotalAssets(Double totalAssets) { this.totalAssets = totalAssets; }

    public Double getEquipmentAssets() { return equipmentAssets; }
    public void setEquipmentAssets(Double equipmentAssets) { this.equipmentAssets = equipmentAssets; }

    public Double getCashAssets() { return cashAssets; }
    public void setCashAssets(Double cashAssets) { this.cashAssets = cashAssets; }

    public Double getTotalLiabilities() { return totalLiabilities; }
    public void setTotalLiabilities(Double totalLiabilities) { this.totalLiabilities = totalLiabilities; }

    public Double getVendorPayables() { return vendorPayables; }
    public void setVendorPayables(Double vendorPayables) { this.vendorPayables = vendorPayables; }

    public Double getNetFinancialPosition() { return netFinancialPosition; }
    public void setNetFinancialPosition(Double netFinancialPosition) { this.netFinancialPosition = netFinancialPosition; }
}
