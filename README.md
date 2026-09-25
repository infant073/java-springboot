# Smart Tourist Safety Monitoring & Geo-Fencing Incident Response System

A full-stack enterprise web application built using **Java 21**, **Spring Boot 3.2.4**, **Spring Security**, **Spring Data JPA**, **Hibernate**, **MySQL 8.0**, **HTML5**, **CSS3**, **Bootstrap 5**, and **JavaScript (Fetch API)**.

This system provides automated safety management for tourists in wilderness and high-risk regions, real-time geo-fenced danger zone detection using the **Haversine formula**, emergency SOS dispatch operations, role-based security, budget tracking, vendor purchase order management, double-entry accounting journals, and financial analytics reporting.

---

## 🔐 Login & Role-Based Access Control (RBAC)

The system includes a unified login page (`/login.html`) with **Spring Security** authentication using **BCrypt** password hashing and role-based access control.

### Supported User Roles:
1. **ADMIN**: Full system access (Dashboards, Tourists, Passes, Safety Zones, SOS Alerts, Rescue Ops, Vendors, Finance, Budgets, Reports).
2. **FINANCE_OFFICER**: Access to financial management (Dashboard, Tourist Passes, Purchase Orders, Vendor Bills, Payments, Budgets, Financial Reports).
3. **TOURIST**: Access to personal safety management (Tourist Dashboard, Profile, Digital Pass, Safety Zone Information, Trigger SOS, SOS History).

---

## 🔑 Default Login Credentials

| Role | Email | Password | Dashboard URL |
| :--- | :--- | :--- | :--- |
| **ADMIN** | `admin@touristsafety.com` | `Admin@123` | `/admin-dashboard.html` |
| **FINANCE_OFFICER** | `finance@touristsafety.com` | `Finance@123` | `/finance-dashboard.html` |
| **TOURIST** | `tourist@touristsafety.com` | `Tourist@123` | `/tourist-dashboard.html` |

> **Note**: Passwords are automatically hashed using `BCryptPasswordEncoder` upon seeding in the `app_users` table.

---

## 🌟 Key Features

1. **Role-Based Security & Single Sign-On**: Unified login page automatically routes users to their role-specific dashboard upon successful authentication.
2. **Digital Tourist Pass Management**: Issue, view, and process payments for digital passes (`STANDARD`, `PREMIUM`, `ADVENTURE`) with tax invoice receipts.
3. **Geo-Fencing & Danger Zone Monitoring**:
   - Define danger/safety zones with central GPS coordinates (Latitude, Longitude), radius (km), danger levels (`LOW`, `MEDIUM`, `HIGH`, `CRITICAL`), and custom warning alerts.
   - Built-in **Haversine formula** calculator evaluates tourist GPS coordinates to trigger instant danger alerts upon boundary breach.
4. **Live SOS Incident Response**:
   - Real-time active SOS monitoring dashboard with auto-refresh (5s polling).
   - Instant emergency operation dispatch for medical, lost, accident, wildlife, or weather hazards.
5. **Emergency Rescue Operations**:
   - Assign rescue response teams (e.g., *Air Heli Rescue Squad*, *Canine K9 Search Team*).
   - Track fuel, equipment, and service costs automatically linked to regional safety budgets.
6. **Vendor & Equipment Procurement**:
   - Manage rescue gear vendors, telecom satellite providers, and tracker leases.
   - Issue Purchase Orders (POs) and convert POs into Vendor Bills with payment status tracking.
7. **Double-Entry Accounting & Financial Reports**:
   - Automated accounting journal entries (`Debit` / `Credit`) for revenue, vendor bills, and operational expenses.
   - Generate **Balance Sheet**, **Profit & Loss**, and **Budget Analytics** reports.

---

## 🛠️ Technology Stack

- **Backend**: Java 21, Spring Boot 3.2.4, Spring Security, Spring Web REST API, Spring Data JPA, Hibernate, Bean Validation (`jakarta.validation`).
- **Database**: MySQL 8.0+ (`tourist_safety_db`), with H2 memory runtime fallback support.
- **Frontend**: HTML5, Vanilla CSS3, Bootstrap 5, FontAwesome Icons, Chart.js, Vanilla JavaScript (Fetch API).
- **Build Tool**: Apache Maven 3.9+.
- **API Testing**: Postman Collection (`Smart_Tourist_Safety_Postman_Collection.json`).

---

## ⚙️ MySQL Database Setup & Configuration

### 1. Create Database
Open MySQL Workbench, Command Line Client, or phpMyAdmin:

```sql
CREATE DATABASE IF NOT EXISTS tourist_safety_db;
```

### 2. Configure `application.properties`
Located in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tourist_safety_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Infant@2007
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## 🚀 How to Run the Application

### Option A: Using Maven Command Line
Run in the project root directory:

```bash
mvn clean spring-boot:run
```

### Option B: From IDE (IntelliJ IDEA / Eclipse / VS Code)
1. Open the project folder in your IDE.
2. Locate `src/main/java/com/example/touristsafety/SmartTouristSafetyApplication.java`.
3. Right-click and choose **Run As -> Spring Boot App**.

---

## 🌐 Application Web URLs

- **Login Page**: [http://localhost:8080/login.html](http://localhost:8080/login.html)
- **Admin Dashboard**: [http://localhost:8080/admin-dashboard.html](http://localhost:8080/admin-dashboard.html)
- **Finance Dashboard**: [http://localhost:8080/finance-dashboard.html](http://localhost:8080/finance-dashboard.html)
- **Tourist Dashboard**: [http://localhost:8080/tourist-dashboard.html](http://localhost:8080/tourist-dashboard.html)
- **Backend REST API Root**: `http://localhost:8080/api/`

---

## 📡 REST API Reference

### Authentication Endpoints

| HTTP Method | Endpoint | Description | Public / Protected |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Authenticate user & receive role info | Public |
| `POST` | `/api/auth/logout` | Invalidate active user session | Public |
| `GET` | `/api/auth/me` | Fetch active session user details | Public |

#### Login Request Body (`POST /api/auth/login`):
```json
{
  "email": "admin@touristsafety.com",
  "password": "Admin@123"
}
```

#### Successful Login Response (Status `200 OK`):
```json
{
  "message": "Login successful",
  "userId": 1,
  "name": "System Administrator",
  "email": "admin@touristsafety.com",
  "role": "ADMIN",
  "touristId": null
}
```

#### Invalid Login Response (Status `401 Unauthorized`):
```json
{
  "status": 401,
  "message": "Invalid email or password"
}
```

---

### Core Business Endpoints

| Module | HTTP Method | Endpoint | Allowed Roles |
| :--- | :--- | :--- | :--- |
| **Tourists** | `GET/POST/PUT/DELETE` | `/api/tourists` | ADMIN, TOURIST |
| **Safety Zones** | `GET/POST` | `/api/safety-zones` | ADMIN, FINANCE, TOURIST |
| | `GET` | `/api/safety-zones/check?latitude={lat}&longitude={lng}` | ADMIN, TOURIST |
| **SOS Alerts** | `GET/POST` | `/api/sos` | ADMIN, TOURIST |
| | `POST` | `/api/sos/trigger` | TOURIST |
| | `PUT` | `/api/sos/{id}/status` | ADMIN |
| **Rescue Operations** | `GET/POST` | `/api/rescue-operations` | ADMIN |
| **Finance (POs/Bills)** | `GET/POST` | `/api/purchase-orders`, `/api/vendor-bills` | ADMIN, FINANCE_OFFICER |
| **Payments** | `GET/POST` | `/api/payments` | ADMIN, FINANCE_OFFICER |
| **Budgets** | `GET/POST` | `/api/budgets` | ADMIN, FINANCE_OFFICER |
| **Financial Reports** | `GET` | `/api/reports/balance-sheet`, `/api/reports/profit-loss` | ADMIN, FINANCE_OFFICER |

---

## 📬 Postman Testing Instructions

1. Open **Postman**.
2. Import `Smart_Tourist_Safety_Postman_Collection.json`.
3. Locate the **Authentication** folder in the collection.
4. Execute **POST Login - Admin**:
   - URL: `http://localhost:8080/api/auth/login`
   - Body: `{"email": "admin@touristsafety.com", "password": "Admin@123"}`
5. Execute **POST Login - Finance Officer**:
   - URL: `http://localhost:8080/api/auth/login`
   - Body: `{"email": "finance@touristsafety.com", "password": "Finance@123"}`
6. Execute **POST Login - Tourist**:
   - URL: `http://localhost:8080/api/auth/login`
   - Body: `{"email": "tourist@touristsafety.com", "password": "Tourist@123"}`
7. Execute **POST Login - Invalid Credentials**:
   - URL: `http://localhost:8080/api/auth/login`
   - Body: `{"email": "admin@touristsafety.com", "password": "wrongpassword"}`
   - Verify `401 Unauthorized` status response.

---

## 📐 Geo-Fencing Formula & Budget Math

### Haversine Geo-Fencing Formula
Calculates great-circle distance $d$ in kilometers between tourist position $(\text{lat}_1, \text{lon}_1)$ and zone epicenter $(\text{lat}_2, \text{lon}_2)$:

$$\Delta \text{lat} = \text{rad}(\text{lat}_2 - \text{lat}_1), \quad \Delta \text{lon} = \text{rad}(\text{lon}_2 - \text{lon}_1)$$
$$a = \sin^2\left(\frac{\Delta \text{lat}}{2}\right) + \cos(\text{rad}(\text{lat}_1)) \cdot \cos(\text{rad}(\text{lat}_2)) \cdot \sin^2\left(\frac{\Delta \text{lon}}{2}\right)$$
$$c = 2 \cdot \text{atan2}\left(\sqrt{a}, \sqrt{1-a}\right), \quad d = R \cdot c \quad (R = 6371\text{ km})$$
