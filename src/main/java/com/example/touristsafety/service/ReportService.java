package com.example.touristsafety.service;

import com.example.touristsafety.dto.BalanceSheetDto;
import com.example.touristsafety.dto.BudgetReportDto;
import com.example.touristsafety.dto.ProfitLossDto;
import com.example.touristsafety.entity.*;
import com.example.touristsafety.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private DigitalTouristPassRepository touristPassRepository;

    @Autowired
    private EmergencyResponseOperationRepository rescueOperationRepository;

    @Autowired
    private VendorBillRepository vendorBillRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public ProfitLossDto generateProfitAndLoss() {
        List<DigitalTouristPass> passes = touristPassRepository.findAll();
        double revenue = passes.stream()
                .filter(p -> "PAID".equalsIgnoreCase(p.getPaymentStatus()))
                .mapToDouble(p -> p.getAmount() != null ? p.getAmount() : 0.0)
                .sum();

        List<EmergencyResponseOperation> ops = rescueOperationRepository.findAll();
        double operationalCosts = ops.stream()
                .mapToDouble(op -> op.getTotalCost() != null ? op.getTotalCost() : 0.0)
                .sum();

        double netProfitLoss = revenue - operationalCosts;
        double margin = revenue > 0 ? (netProfitLoss / revenue) * 100.0 : 0.0;

        return new ProfitLossDto(
                Math.round(revenue * 100.0) / 100.0,
                Math.round(operationalCosts * 100.0) / 100.0,
                Math.round(netProfitLoss * 100.0) / 100.0,
                Math.round(margin * 100.0) / 100.0
        );
    }

    public BalanceSheetDto generateBalanceSheet() {
        List<PurchaseOrder> pos = purchaseOrderRepository.findAll();
        double equipmentAssets = pos.stream()
                .mapToDouble(po -> po.getTotalAmount() != null ? po.getTotalAmount() : 0.0)
                .sum();

        ProfitLossDto pnl = generateProfitAndLoss();
        double cashInflow = pnl.getTouristPassRevenue();

        List<Payment> payments = paymentRepository.findAll();
        double cashOutflow = payments.stream()
                .filter(p -> "VENDOR_PAYMENT".equalsIgnoreCase(p.getTransactionType()))
                .mapToDouble(p -> p.getAmount() != null ? p.getAmount() : 0.0)
                .sum() + pnl.getEmergencyRescueOperationalCosts();

        double cashAssets = Math.max(0.0, cashInflow - cashOutflow + 50000.0); // Baseline safety treasury reserve

        double totalAssets = equipmentAssets + cashAssets;

        List<VendorBill> bills = vendorBillRepository.findAll();
        double vendorPayables = bills.stream()
                .filter(b -> !"PAID".equalsIgnoreCase(b.getPaymentStatus()))
                .mapToDouble(b -> b.getAmount() != null ? b.getAmount() : 0.0)
                .sum();

        double totalLiabilities = vendorPayables;
        double netPosition = totalAssets - totalLiabilities;

        return new BalanceSheetDto(
                Math.round(totalAssets * 100.0) / 100.0,
                Math.round(equipmentAssets * 100.0) / 100.0,
                Math.round(cashAssets * 100.0) / 100.0,
                Math.round(totalLiabilities * 100.0) / 100.0,
                Math.round(vendorPayables * 100.0) / 100.0,
                Math.round(netPosition * 100.0) / 100.0
        );
    }

    public BudgetReportDto generateBudgetReport() {
        List<Budget> budgets = budgetRepository.findAll();

        double totalAllocated = budgets.stream()
                .mapToDouble(b -> b.getAllocatedAmount() != null ? b.getAllocatedAmount() : 0.0)
                .sum();

        double totalActual = budgets.stream()
                .mapToDouble(b -> b.getActualAmount() != null ? b.getActualAmount() : 0.0)
                .sum();

        double totalRemaining = totalAllocated - totalActual;
        double overallUtilization = totalAllocated > 0 ? (totalActual / totalAllocated) * 100.0 : 0.0;

        return new BudgetReportDto(
                budgets,
                Math.round(totalAllocated * 100.0) / 100.0,
                Math.round(totalActual * 100.0) / 100.0,
                Math.round(totalRemaining * 100.0) / 100.0,
                Math.round(overallUtilization * 100.0) / 100.0
        );
    }
}
