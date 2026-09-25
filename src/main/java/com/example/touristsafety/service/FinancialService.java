package com.example.touristsafety.service;

import com.example.touristsafety.entity.*;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class FinancialService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private VendorBillRepository vendorBillRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DigitalTouristPassRepository touristPassRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        if (po.getOrderDate() == null) po.setOrderDate(LocalDate.now());
        if (po.getPoNumber() == null) po.setPoNumber("PO-" + System.currentTimeMillis() % 100000);
        if (po.getStatus() == null) po.setStatus("APPROVED");
        return purchaseOrderRepository.save(po);
    }

    @Transactional
    public VendorBill createVendorBill(VendorBill bill) {
        if (bill.getBillDate() == null) bill.getBillDate();
        if (bill.getBillDate() == null) bill.setBillDate(LocalDate.now());
        if (bill.getBillNumber() == null) bill.setBillNumber("BILL-" + System.currentTimeMillis() % 100000);
        if (bill.getPaymentStatus() == null) bill.setPaymentStatus("UNPAID");

        // Link PO if specified
        if (bill.getPurchaseOrderId() != null) {
            PurchaseOrder po = purchaseOrderRepository.findById(bill.getPurchaseOrderId()).orElse(null);
            if (po != null) {
                po.setStatus("BILLED");
                purchaseOrderRepository.save(po);
            }
        }

        VendorBill savedBill = vendorBillRepository.save(bill);

        // Accounting Journal Entry: Equipment Expense (Debit) / Accounts Payable (Credit)
        Journal journal = journalRepository.save(new Journal(
                "JRN-VB-" + savedBill.getId(),
                LocalDate.now(),
                "Vendor Bill #" + savedBill.getBillNumber(),
                "VENDOR_BILL",
                savedBill.getId()
        ));

        journalEntryRepository.save(new JournalEntry(journal.getId(), "Rescue Equipment Asset", savedBill.getAmount(), 0.0, "Vendor Equipment Purchase"));
        journalEntryRepository.save(new JournalEntry(journal.getId(), "Accounts Payable - Vendor", 0.0, savedBill.getAmount(), "Vendor Payable Obligation"));

        return savedBill;
    }

    @Transactional
    public Payment processPayment(Payment payment) {
        if (payment.getPaymentDate() == null) payment.setPaymentDate(LocalDate.now());
        if (payment.getReferenceNumber() == null) payment.setReferenceNumber("PAY-" + System.currentTimeMillis() % 100000);
        if (payment.getStatus() == null) payment.setStatus("COMPLETED");

        Payment savedPayment = paymentRepository.save(payment);

        if ("VENDOR_PAYMENT".equalsIgnoreCase(payment.getTransactionType()) && payment.getTransactionId() != null) {
            VendorBill bill = vendorBillRepository.findById(payment.getTransactionId()).orElse(null);
            if (bill != null) {
                bill.setPaymentStatus("PAID");
                vendorBillRepository.save(bill);
            }

            // Journal Entry: Accounts Payable (Debit) / Cash Bank (Credit)
            Journal journal = journalRepository.save(new Journal(
                    "JRN-PAY-" + savedPayment.getId(),
                    LocalDate.now(),
                    "Vendor Payment #" + savedPayment.getReferenceNumber(),
                    "PAYMENT",
                    savedPayment.getId()
            ));

            journalEntryRepository.save(new JournalEntry(journal.getId(), "Accounts Payable - Vendor", payment.getAmount(), 0.0, "Settlement of Vendor Bill"));
            journalEntryRepository.save(new JournalEntry(journal.getId(), "Cash / Bank Asset", 0.0, payment.getAmount(), "Disbursement of Cash"));

        } else if ("TOURIST_PASS_PAYMENT".equalsIgnoreCase(payment.getTransactionType()) && payment.getTransactionId() != null) {
            DigitalTouristPass pass = touristPassRepository.findById(payment.getTransactionId()).orElse(null);
            if (pass != null) {
                pass.setPaymentStatus("PAID");
                pass.setStatus("ACTIVE");
                touristPassRepository.save(pass);
            }

            // Journal Entry: Cash Bank (Debit) / Tourist Pass Revenue (Credit)
            Journal journal = journalRepository.save(new Journal(
                    "JRN-REV-" + savedPayment.getId(),
                    LocalDate.now(),
                    "Tourist Pass Revenue #" + savedPayment.getReferenceNumber(),
                    "TOURIST_PASS",
                    savedPayment.getId()
            ));

            journalEntryRepository.save(new JournalEntry(journal.getId(), "Cash / Bank Asset", payment.getAmount(), 0.0, "Receipt of Tourist Pass Fee"));
            journalEntryRepository.save(new JournalEntry(journal.getId(), "Tourist Pass Revenue", 0.0, payment.getAmount(), "Earned Pass Revenue"));
        }

        return savedPayment;
    }

    @Transactional
    public DigitalTouristPass createTouristPass(DigitalTouristPass pass) {
        if (pass.getIssueDate() == null) pass.setIssueDate(LocalDate.now());
        if (pass.getExpiryDate() == null) pass.setExpiryDate(LocalDate.now().plusDays(30));
        if (pass.getPassNumber() == null) pass.setPassNumber("PASS-" + System.currentTimeMillis() % 100000);
        if (pass.getPaymentStatus() == null) pass.setPaymentStatus("PAID");
        if (pass.getStatus() == null) pass.setStatus("ACTIVE");

        DigitalTouristPass savedPass = touristPassRepository.save(pass);

        // Auto create payment entry for pass revenue
        Payment payment = new Payment(
                "PAY-PASS-" + savedPass.getId(),
                "TOURIST_PASS_PAYMENT",
                savedPass.getId(),
                LocalDate.now(),
                savedPass.getAmount() != null ? savedPass.getAmount() : 50.0,
                "DIGITAL",
                "COMPLETED"
        );
        processPayment(payment);

        return savedPass;
    }
}
