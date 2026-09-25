package com.example.touristsafety.config;

import com.example.touristsafety.entity.*;
import com.example.touristsafety.repository.*;
import com.example.touristsafety.service.AuthService;
import com.example.touristsafety.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private RescueGearVendorRepository vendorRepository;

    @Autowired
    private TelecomProviderRepository telecomRepository;

    @Autowired
    private DigitalTouristPassRepository passRepository;

    @Autowired
    private EmergencyRescueServiceRepository rescueServiceRepository;

    @Autowired
    private SatelliteTrackerLeaseRepository leaseRepository;

    @Autowired
    private SafetyZoneRepository zoneRepository;

    @Autowired
    private SosAlertRepository sosAlertRepository;

    @Autowired
    private EmergencyResponseOperationRepository rescueOperationRepository;

    @Autowired
    private PurchaseOrderRepository poRepository;

    @Autowired
    private VendorBillRepository vendorBillRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private AnalyticalAccountRepository accountRepository;

    @Autowired
    private FinancialService financialService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        // Always seed default users if app_users is empty
        seedDefaultUsers();

        if (touristRepository.count() > 0) {
            System.out.println("Sample data already exists. Skipping main data initialization.");
            return;
        }

        System.out.println("Initializing Smart Tourist Safety sample dataset...");

        // 1. Seed Tourists
        Tourist t1 = touristRepository.save(new Tourist(
                "John Doe", "john.doe@example.com", "+1-555-0199", "123 Maple St, Denver CO",
                "Jane Doe", "+1-555-0198", "PASS-1001", "PREMIUM",
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(25), "ACTIVE"
        ));

        Tourist t2 = touristRepository.save(new Tourist(
                "Sarah Smith", "sarah.smith@example.com", "+1-555-0244", "456 Oak Ave, Seattle WA",
                "Robert Smith", "+1-555-0245", "PASS-1002", "ADVENTURE",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(12), "ACTIVE"
        ));

        Tourist t3 = touristRepository.save(new Tourist(
                "Michael Chen", "michael.chen@example.com", "+1-555-0377", "789 Pine Rd, San Jose CA",
                "Lisa Chen", "+1-555-0378", "PASS-1003", "STANDARD",
                LocalDate.now().minusDays(10), LocalDate.now().plusDays(5), "ACTIVE"
        ));

        // 2. Seed Vendors
        RescueGearVendor v1 = vendorRepository.save(new RescueGearVendor(
                "Alpine Gear Tech", "David Miller", "david@alpinegear.com", "+1-800-555-4321",
                "77 Mountain Blvd, Boulder CO", "GST9920148A", "ACTIVE"
        ));

        RescueGearVendor v2 = vendorRepository.save(new RescueGearVendor(
                "Himalayan Rescue Supplies", "Anita Sharma", "anita@himalayanrescue.org", "+91-98765-43210",
                "12 Mall Road, Manali HP", "GST3319876B", "ACTIVE"
        ));

        // 3. Seed Telecom Providers
        TelecomProvider tel1 = telecomRepository.save(new TelecomProvider(
                "GlobalSat Satellite Telecom", "Alex Rivera", "+1-888-555-0123", "alex@globalsat.com", "SATELLITE", "ACTIVE"
        ));

        TelecomProvider tel2 = telecomRepository.save(new TelecomProvider(
                "Apex Mesh Radio Networks", "Siddharth Verma", "+91-98111-22233", "sidd@apexmesh.net", "MESH_RADIO", "ACTIVE"
        ));

        // 4. Seed Emergency Rescue Services
        EmergencyRescueService r1 = rescueServiceRepository.save(new EmergencyRescueService(
                "High Altitude Air Heli Rescue", "AeroRescue Corp", "+1-800-HELI-911", "Western Alpine Region", 2500.00, "AVAILABLE"
        ));

        EmergencyRescueService r2 = rescueServiceRepository.save(new EmergencyRescueService(
                "Rapid K9 Canine Search Squad", "K9 Guard Foundation", "+1-800-DOG-RESCUE", "Northern Forest & Ridge", 800.00, "AVAILABLE"
        ));

        // 5. Seed Satellite Tracker Leases
        leaseRepository.save(new SatelliteTrackerLease(
                tel1.getId(), "SAT-TRK-9011", LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(10), 1200.00, "ACTIVE"
        ));

        // 6. Seed Safety Zones
        SafetyZone z1 = zoneRepository.save(new SafetyZone(
                "Mountain Trekking Safety Zone B",
                "High altitude trekking corridor with unpredictable weather and avalanche risks.",
                11.0168, 76.9558, 5.0, "CRITICAL",
                "ALERT: Extreme terrain! Stay on marked safety paths and maintain active tracker link.", "ACTIVE"
        ));

        SafetyZone z2 = zoneRepository.save(new SafetyZone(
                "Coastal Cliff Danger Zone",
                "Steep oceanic cliff lines subject to rogue waves and erosion hazards.",
                11.2500, 75.7800, 3.5, "HIGH",
                "WARNING: High cliff edge danger! Do not bypass safety barrier nets.", "ACTIVE"
        ));

        SafetyZone z3 = zoneRepository.save(new SafetyZone(
                "Dense Forest Wildlife Corridor",
                "Protected wildlife sanctuary with dense canopy and carnivore activity.",
                11.1500, 76.4000, 8.0, "MEDIUM",
                "NOTICE: Wildlife warning! Avoid solo trekking after 5:00 PM.", "ACTIVE"
        ));

        // 7. Seed Tourist Passes
        DigitalTouristPass p1 = passRepository.save(new DigitalTouristPass(
                t1.getId(), "PASS-1001", "PREMIUM", LocalDate.now().minusDays(5), LocalDate.now().plusDays(25), 150.00, "PAID", "ACTIVE"
        ));
        DigitalTouristPass p2 = passRepository.save(new DigitalTouristPass(
                t2.getId(), "PASS-1002", "ADVENTURE", LocalDate.now().minusDays(2), LocalDate.now().plusDays(12), 250.00, "PAID", "ACTIVE"
        ));
        DigitalTouristPass p3 = passRepository.save(new DigitalTouristPass(
                t3.getId(), "PASS-1003", "STANDARD", LocalDate.now().minusDays(10), LocalDate.now().plusDays(5), 50.00, "PAID", "ACTIVE"
        ));

        // 8. Seed SOS Alert & Response Operation
        SosAlert sos1 = sosAlertRepository.save(new SosAlert(
                t2.getId(), t2.getName(), 11.0180, 76.9565, LocalDateTime.now().minusHours(1),
                "MEDICAL", "Injured ankle on steep trail near Ridge 4. Immediate assistance required.",
                "ACTIVE", "DISPATCHED"
        ));
        sos1.setInsideZoneId(z1.getId());
        sos1.setInsideZoneName(z1.getZoneName());
        sos1.setDistanceToZoneKm(0.15);
        sosAlertRepository.save(sos1);

        rescueOperationRepository.save(new EmergencyResponseOperation(
                sos1.getId(), "Alpine Rescue Squad Bravo", LocalDateTime.now().minusMinutes(50),
                "Lat: 11.0180, Lng: 76.9565 (Mountain Zone B)",
                "Evacuating injured tourist via stretcher to emergency medical post.",
                180.00, 350.00, 600.00, "IN_PROGRESS"
        ));

        // 9. Seed Budgets
        budgetRepository.save(new Budget(
                "Mountain Trekking Safety Zone B Budget", "2026-2027", "Northern Highlands", z1.getZoneName(), 50000.00, 1130.00, "ACTIVE"
        ));

        budgetRepository.save(new Budget(
                "Coastal Safety Operations", "2026-2027", "Western Coastline", z2.getZoneName(), 35000.00, 4500.00, "ACTIVE"
        ));

        // 10. Seed Analytical Accounts
        accountRepository.save(new AnalyticalAccount("Mountain Safety Operations", "EXPENSE", "Operational safety expenses for Zone B", z1.getZoneName()));
        accountRepository.save(new AnalyticalAccount("Tourist Pass Sales Treasury", "REVENUE", "Tourist digital pass revenue account", "Global"));

        // 11. Seed Financial PO & Bill
        PurchaseOrder po = financialService.createPurchaseOrder(new PurchaseOrder(
                "PO-2026-001", v1.getId(), LocalDate.now().minusDays(15),
                "Procurement of 10 Emergency Satellite SOS Beacons & Avalanche Transceivers", 8500.00, "APPROVED"
        ));

        VendorBill bill = financialService.createVendorBill(new VendorBill(
                "BILL-2026-001", po.getId(), v1.getId(), LocalDate.now().minusDays(10), 8500.00, "UNPAID"
        ));

        // 12. Seed Default System Users (only if not already created)
        seedDefaultUsers();

        System.out.println("Smart Tourist Safety dataset initialization complete!");
    }

    /**
     * Seeds 3 default users: ADMIN, FINANCE_OFFICER, TOURIST.
     * Passwords are BCrypt hashed. Runs only if users table is empty.
     */
    private void seedDefaultUsers() {
        if (appUserRepository.count() > 0) {
            System.out.println("Default users already exist. Skipping user seeding.");
            return;
        }

        System.out.println("Creating default system users with BCrypt hashed passwords...");

        Long touristId = null;
        if (touristRepository.count() > 0) {
            touristId = touristRepository.findAll().get(0).getId();
        }

        // ADMIN user
        authService.registerUser(
                "System Administrator",
                "admin@touristsafety.com",
                "Admin@123",
                AppUser.Role.ADMIN,
                null
        );

        // FINANCE_OFFICER user
        authService.registerUser(
                "Finance Officer",
                "finance@touristsafety.com",
                "Finance@123",
                AppUser.Role.FINANCE_OFFICER,
                null
        );

        // TOURIST user - linked to sample tourist record if available
        authService.registerUser(
                "Sample Tourist",
                "tourist@touristsafety.com",
                "Tourist@123",
                AppUser.Role.TOURIST,
                touristId
        );

        System.out.println("Default users created successfully!");
        System.out.println("  ADMIN      : admin@touristsafety.com    / Admin@123");
        System.out.println("  FINANCE    : finance@touristsafety.com  / Finance@123");
        System.out.println("  TOURIST    : tourist@touristsafety.com  / Tourist@123");
    }
}
