package com.example.touristsafety.controller;

import com.example.touristsafety.entity.AnalyticalAccount;
import com.example.touristsafety.entity.Journal;
import com.example.touristsafety.entity.JournalEntry;
import com.example.touristsafety.repository.AnalyticalAccountRepository;
import com.example.touristsafety.repository.JournalEntryRepository;
import com.example.touristsafety.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AccountingController {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private AnalyticalAccountRepository analyticalAccountRepository;

    @GetMapping("/journals")
    public ResponseEntity<List<Journal>> getAllJournals() {
        return ResponseEntity.ok(journalRepository.findAll());
    }

    @GetMapping("/journals/{id}/entries")
    public ResponseEntity<List<JournalEntry>> getJournalEntries(@PathVariable Long id) {
        return ResponseEntity.ok(journalEntryRepository.findByJournalId(id));
    }

    @GetMapping("/analytical-accounts")
    public ResponseEntity<List<AnalyticalAccount>> getAnalyticalAccounts() {
        return ResponseEntity.ok(analyticalAccountRepository.findAll());
    }
}
