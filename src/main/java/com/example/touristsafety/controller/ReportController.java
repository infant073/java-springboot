package com.example.touristsafety.controller;

import com.example.touristsafety.dto.BalanceSheetDto;
import com.example.touristsafety.dto.BudgetReportDto;
import com.example.touristsafety.dto.ProfitLossDto;
import com.example.touristsafety.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/profit-loss")
    public ResponseEntity<ProfitLossDto> getProfitLossReport() {
        return ResponseEntity.ok(reportService.generateProfitAndLoss());
    }

    @GetMapping("/balance-sheet")
    public ResponseEntity<BalanceSheetDto> getBalanceSheetReport() {
        return ResponseEntity.ok(reportService.generateBalanceSheet());
    }

    @GetMapping("/budget")
    public ResponseEntity<BudgetReportDto> getBudgetReport() {
        return ResponseEntity.ok(reportService.generateBudgetReport());
    }
}
