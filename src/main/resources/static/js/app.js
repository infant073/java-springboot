/* Smart Tourist Safety - Client Application Logic (Vanilla JS + Fetch API) */

const API_BASE = '';
let revenueChart = null;
let sosChart = null;
let budgetChart = null;
let sosAutoRefreshTimer = null;

document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
    loadDashboardData();

    // Start 5-second polling for active SOS alerts monitoring
    sosAutoRefreshTimer = setInterval(() => {
        checkActiveSosBanner();
        if (document.getElementById('section-sos').classList.contains('active')) {
            loadSosSection(false); // refresh without flickering
        }
    }, 5000);
});

/* Navigation Router */
function initNavigation() {
    const navLinks = document.querySelectorAll('.nav-link-item');
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const targetSection = link.getAttribute('data-section');
            switchSection(targetSection);

            navLinks.forEach(l => l.classList.remove('active'));
            link.classList.add('active');
        });
    });
}

function switchSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(sec => sec.classList.remove('active'));
    const activeSec = document.getElementById(`section-${sectionId}`);
    if (activeSec) {
        activeSec.classList.add('active');
    }

    // Set Header Title
    const titleMap = {
        'dashboard': 'Dashboard Overview',
        'tourists': 'Tourist Management',
        'passes': 'Digital Tourist Passes & Revenue',
        'safety-zones': 'Geo-Fenced Safety & Danger Zones',
        'sos': 'Live SOS Emergency Incident Response',
        'rescue-ops': 'Emergency Rescue Operations',
        'vendors': 'Rescue Gear Vendors & Telecom',
        'finance': 'Purchase Orders, Bills & Payments',
        'budgets': 'Safety Budget Allocation & Tracking',
        'reports': 'Financial & Safety Analytics Reports'
    };
    document.getElementById('header-title-text').innerText = titleMap[sectionId] || 'Dashboard';

    // Trigger Section Data Loader
    if (sectionId === 'dashboard') loadDashboardData();
    else if (sectionId === 'tourists') loadTouristsSection();
    else if (sectionId === 'passes') loadPassesSection();
    else if (sectionId === 'safety-zones') loadSafetyZonesSection();
    else if (sectionId === 'sos') loadSosSection(true);
    else if (sectionId === 'rescue-ops') loadRescueOperationsSection();
    else if (sectionId === 'vendors') loadVendorsSection();
    else if (sectionId === 'finance') loadFinanceSection();
    else if (sectionId === 'budgets') loadBudgetsSection();
    else if (sectionId === 'reports') loadReportsSection();
}

/* ==========================================================================
   1. DASHBOARD DATA LOADER
   ========================================================================== */
async function loadDashboardData() {
    try {
        const [tourists, passes, sosAlerts, zones, pnl, budgetReport] = await Promise.all([
            fetch(`${API_BASE}/api/tourists`).then(r => r.json()),
            fetch(`${API_BASE}/api/tourist-passes`).then(r => r.json()),
            fetch(`${API_BASE}/api/sos`).then(r => r.json()),
            fetch(`${API_BASE}/api/safety-zones`).then(r => r.json()),
            fetch(`${API_BASE}/api/reports/profit-loss`).then(r => r.json()),
            fetch(`${API_BASE}/api/reports/budget`).then(r => r.json())
        ]);

        document.getElementById('stat-total-tourists').innerText = tourists.length;
        document.getElementById('stat-active-passes').innerText = passes.filter(p => p.status === 'ACTIVE').length;

        const activeSos = sosAlerts.filter(s => s.status === 'ACTIVE');
        document.getElementById('stat-active-sos').innerText = activeSos.length;
        document.getElementById('stat-active-zones').innerText = zones.filter(z => z.status === 'ACTIVE').length;

        document.getElementById('stat-total-revenue').innerText = `$${pnl.touristPassRevenue.toLocaleString()}`;
        document.getElementById('stat-rescue-costs').innerText = `$${pnl.emergencyRescueOperationalCosts.toLocaleString()}`;

        document.getElementById('stat-allocated-budget').innerText = `$${budgetReport.totalAllocated.toLocaleString()}`;
        document.getElementById('stat-remaining-budget').innerText = `$${budgetReport.totalRemaining.toLocaleString()}`;

        renderDashboardCharts(pnl, sosAlerts, budgetReport);
        checkActiveSosBanner();
    } catch (err) {
        console.error('Error loading dashboard stats:', err);
    }
}

function renderDashboardCharts(pnl, sosAlerts, budgetReport) {
    // 1. Revenue vs Expense Bar Chart
    const ctx1 = document.getElementById('chart-financials').getContext('2d');
    if (revenueChart) revenueChart.destroy();
    revenueChart = new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: ['Pass Revenue', 'Rescue Operations Expense', 'Net Profit/Loss'],
            datasets: [{
                label: 'USD ($)',
                data: [pnl.touristPassRevenue, pnl.emergencyRescueOperationalCosts, pnl.netProfitLoss],
                backgroundColor: ['#10b981', '#ef4444', '#3b82f6'],
                borderRadius: 8
            }]
        },
        options: { responsive: true, plugins: { legend: { display: false } } }
    });

    // 2. SOS Breakdown Doughnut Chart
    const activeCount = sosAlerts.filter(s => s.status === 'ACTIVE').length;
    const dispatchedCount = sosAlerts.filter(s => s.status === 'DISPATCHED' || s.status === 'IN_PROGRESS').length;
    const resolvedCount = sosAlerts.filter(s => s.status === 'RESOLVED').length;

    const ctx2 = document.getElementById('chart-sos-status').getContext('2d');
    if (sosChart) sosChart.destroy();
    sosChart = new Chart(ctx2, {
        type: 'doughnut',
        data: {
            labels: ['Active Unhandled', 'Rescue Dispatched / In-Progress', 'Resolved / Safe'],
            datasets: [{
                data: [activeCount, dispatchedCount, resolvedCount],
                backgroundColor: ['#ef4444', '#f59e0b', '#10b981']
            }]
        },
        options: { responsive: true }
    });

    // 3. Budget vs Expenditure Chart
    const ctx3 = document.getElementById('chart-budget').getContext('2d');
    if (budgetChart) budgetChart.destroy();
    budgetChart = new Chart(ctx3, {
        type: 'bar',
        data: {
            labels: ['Allocated Budget', 'Actual Expenditure', 'Remaining Budget'],
            datasets: [{
                label: 'Budget Amount ($)',
                data: [budgetReport.totalAllocated, budgetReport.totalActual, budgetReport.totalRemaining],
                backgroundColor: ['#6366f1', '#f43f5e', '#14b8a6'],
                borderRadius: 8
            }]
        },
        options: { responsive: true, plugins: { legend: { display: false } } }
    });
}

async function checkActiveSosBanner() {
    try {
        const activeSos = await fetch(`${API_BASE}/api/sos/active`).then(r => r.json());
        const banner = document.getElementById('sos-pulse-banner');
        if (activeSos.length > 0) {
            banner.style.display = 'flex';
            document.getElementById('sos-pulse-count').innerText = `${activeSos.length} ACTIVE EMERGENCY SOS ALERT(S)!`;
        } else {
            banner.style.display = 'none';
        }
    } catch (e) {
        console.error(e);
    }
}

/* ==========================================================================
   2. TOURIST MANAGEMENT SECTION
   ========================================================================== */
async function loadTouristsSection() {
    try {
        const tourists = await fetch(`${API_BASE}/api/tourists`).then(r => r.json());
        const tbody = document.getElementById('tourists-table-body');
        tbody.innerHTML = '';

        tourists.forEach(t => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>#${t.id}</strong></td>
                <td><div class="fw-bold">${escapeHtml(t.name)}</div><div class="text-muted small">${escapeHtml(t.email)}</div></td>
                <td>${escapeHtml(t.phone)}</td>
                <td><span class="badge bg-secondary">${escapeHtml(t.digitalPassNumber || 'N/A')}</span></td>
                <td><span class="badge bg-info text-dark">${escapeHtml(t.passType || 'STANDARD')}</span></td>
                <td><div>${escapeHtml(t.emergencyContactName)}</div><div class="text-muted small">${escapeHtml(t.emergencyContactPhone)}</div></td>
                <td><span class="badge badge-status badge-${(t.status || 'ACTIVE').toLowerCase()}">${t.status}</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-danger me-1" onclick="openTriggerSosModal(${t.id}, '${escapeHtml(t.name)}')">
                        <i class="fas fa-exclamation-triangle"></i> SOS
                    </button>
                    <button class="btn btn-sm btn-outline-primary me-1" onclick="editTourist(${t.id})">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-secondary" onclick="deleteTourist(${t.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        console.error('Error loading tourists:', err);
    }
}

async function saveTourist(event) {
    event.preventDefault();
    const id = document.getElementById('tourist-id').value;
    const touristData = {
        name: document.getElementById('tourist-name').value,
        email: document.getElementById('tourist-email').value,
        phone: document.getElementById('tourist-phone').value,
        address: document.getElementById('tourist-address').value,
        emergencyContactName: document.getElementById('tourist-emergency-name').value,
        emergencyContactPhone: document.getElementById('tourist-emergency-phone').value,
        passType: document.getElementById('tourist-pass-type').value,
        status: 'ACTIVE'
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_BASE}/api/tourists/${id}` : `${API_BASE}/api/tourists`;

    const res = await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(touristData)
    });

    if (res.ok) {
        bootstrap.Modal.getInstance(document.getElementById('modalTourist')).hide();
        loadTouristsSection();
        loadDashboardData();
    } else {
        alert('Error saving tourist. Please verify input data.');
    }
}

async function editTourist(id) {
    const t = await fetch(`${API_BASE}/api/tourists/${id}`).then(r => r.json());
    document.getElementById('tourist-id').value = t.id;
    document.getElementById('tourist-name').value = t.name;
    document.getElementById('tourist-email').value = t.email;
    document.getElementById('tourist-phone').value = t.phone;
    document.getElementById('tourist-address').value = t.address || '';
    document.getElementById('tourist-emergency-name').value = t.emergencyContactName;
    document.getElementById('tourist-emergency-phone').value = t.emergencyContactPhone;
    document.getElementById('tourist-pass-type').value = t.passType || 'STANDARD';

    new bootstrap.Modal(document.getElementById('modalTourist')).show();
}

async function deleteTourist(id) {
    if (confirm('Are you sure you want to remove this tourist record?')) {
        await fetch(`${API_BASE}/api/tourists/${id}`, { method: 'DELETE' });
        loadTouristsSection();
        loadDashboardData();
    }
}

/* ==========================================================================
   3. DIGITAL TOURIST PASSES SECTION
   ========================================================================== */
async function loadPassesSection() {
    try {
        const passes = await fetch(`${API_BASE}/api/tourist-passes`).then(r => r.json());
        const tbody = document.getElementById('passes-table-body');
        tbody.innerHTML = '';

        passes.forEach(p => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>#${p.id}</strong></td>
                <td><span class="badge bg-dark">${escapeHtml(p.passNumber)}</span></td>
                <td>Tourist #${p.touristId}</td>
                <td><span class="badge bg-primary">${escapeHtml(p.passType)}</span></td>
                <td>${p.issueDate}</td>
                <td>${p.expiryDate}</td>
                <td><strong>$${p.amount}</strong></td>
                <td><span class="badge bg-success">${p.paymentStatus}</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-info me-1" onclick="generateInvoiceReceipt(${p.id})">
                        <i class="fas fa-file-invoice-dollar"></i> Invoice
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deletePass(${p.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) { console.error(e); }
}

async function deletePass(id) {
    if (confirm('Delete this tourist pass?')) {
        await fetch(`${API_BASE}/api/tourist-passes/${id}`, { method: 'DELETE' });
        loadPassesSection();
        loadDashboardData();
    }
}

async function generateInvoiceReceipt(passId) {
    const res = await fetch(`${API_BASE}/api/invoices`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ passId: passId })
    }).then(r => r.json());

    document.getElementById('invoice-number').innerText = res.invoiceNumber;
    document.getElementById('invoice-date').innerText = res.issueDate;
    document.getElementById('invoice-pass-num').innerText = res.passNumber;
    document.getElementById('invoice-pass-type').innerText = res.passType;
    document.getElementById('invoice-subtotal').innerText = `$${res.amount.toFixed(2)}`;
    document.getElementById('invoice-tax').innerText = `$${res.taxAmount.toFixed(2)}`;
    document.getElementById('invoice-total').innerText = `$${res.totalAmount.toFixed(2)}`;

    new bootstrap.Modal(document.getElementById('modalInvoice')).show();
}

/* ==========================================================================
   4. SAFETY & DANGER ZONES (WITH GEO-FENCING CALCULATOR)
   ========================================================================== */
async function loadSafetyZonesSection() {
    try {
        const zones = await fetch(`${API_BASE}/api/safety-zones`).then(r => r.json());
        const tbody = document.getElementById('zones-table-body');
        tbody.innerHTML = '';

        zones.forEach(z => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>#${z.id}</strong></td>
                <td><div class="fw-bold">${escapeHtml(z.zoneName)}</div><div class="text-muted small">${escapeHtml(z.description || '')}</div></td>
                <td><span class="badge badge-status badge-${(z.dangerLevel || 'LOW').toLowerCase()}">${z.dangerLevel}</span></td>
                <td><code>${z.latitude}, ${z.longitude}</code></td>
                <td><strong>${z.radius} km</strong></td>
                <td><div class="small text-danger"><i class="fas fa-exclamation-circle"></i> ${escapeHtml(z.warningMessage || '')}</div></td>
                <td><span class="badge badge-status badge-${(z.status || 'ACTIVE').toLowerCase()}">${z.status}</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-primary me-1" onclick="editZone(${z.id})">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteZone(${z.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) { console.error(e); }
}

async function testGeoFencing() {
    const lat = parseFloat(document.getElementById('geo-test-lat').value);
    const lng = parseFloat(document.getElementById('geo-test-lng').value);

    if (isNaN(lat) || isNaN(lng)) {
        alert('Please enter valid latitude and longitude decimal numbers.');
        return;
    }

    const res = await fetch(`${API_BASE}/api/safety-zones/check?latitude=${lat}&longitude=${lng}`).then(r => r.json());
    const box = document.getElementById('geo-fence-result-box');
    box.style.display = 'block';

    if (res.insideDangerZone) {
        box.className = 'geo-fence-alert-box danger';
        box.innerHTML = `
            <i class="fas fa-exclamation-triangle"></i>
            <div>
                <h5 class="fw-bold mb-1">DANGER ZONE BOUNDARY BREACH DETECTED!</h5>
                <div>Position (<strong>${lat}, ${lng}</strong>) is inside <strong>${res.zone ? res.zone.zoneName : 'Danger Area'}</strong>!</div>
                <div class="mt-2 text-danger font-monospace"><strong>Warning:</strong> ${res.warningMessage}</div>
                <div class="small text-muted mt-1">Distance to epicenter: ${res.distanceKm} km (Zone Radius: ${res.zone.radius} km)</div>
            </div>
        `;
    } else {
        box.className = 'geo-fence-alert-box safe';
        box.innerHTML = `
            <i class="fas fa-shield-alt"></i>
            <div>
                <h5 class="fw-bold mb-1">SAFE LOCATION VERIFIED</h5>
                <div>Coordinates (<strong>${lat}, ${lng}</strong>) are in a designated clear zone.</div>
                <div class="small text-muted mt-1">Nearest safety zone is <strong>${res.zone ? res.zone.zoneName : 'N/A'}</strong> (${res.distanceKm ? res.distanceKm + ' km away' : ''})</div>
            </div>
        `;
    }
}

async function saveZone(event) {
    event.preventDefault();
    const id = document.getElementById('zone-id').value;
    const zoneData = {
        zoneName: document.getElementById('zone-name').value,
        description: document.getElementById('zone-description').value,
        latitude: parseFloat(document.getElementById('zone-latitude').value),
        longitude: parseFloat(document.getElementById('zone-longitude').value),
        radius: parseFloat(document.getElementById('zone-radius').value),
        dangerLevel: document.getElementById('zone-danger-level').value,
        warningMessage: document.getElementById('zone-warning-message').value,
        status: 'ACTIVE'
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_BASE}/api/safety-zones/${id}` : `${API_BASE}/api/safety-zones`;

    await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(zoneData)
    });

    bootstrap.Modal.getInstance(document.getElementById('modalZone')).hide();
    loadSafetyZonesSection();
    loadDashboardData();
}

async function editZone(id) {
    const z = await fetch(`${API_BASE}/api/safety-zones/${id}`).then(r => r.json());
    document.getElementById('zone-id').value = z.id;
    document.getElementById('zone-name').value = z.zoneName;
    document.getElementById('zone-description').value = z.description || '';
    document.getElementById('zone-latitude').value = z.latitude;
    document.getElementById('zone-longitude').value = z.longitude;
    document.getElementById('zone-radius').value = z.radius;
    document.getElementById('zone-danger-level').value = z.dangerLevel;
    document.getElementById('zone-warning-message').value = z.warningMessage || '';

    new bootstrap.Modal(document.getElementById('modalZone')).show();
}

async function deleteZone(id) {
    if (confirm('Delete this safety zone?')) {
        await fetch(`${API_BASE}/api/safety-zones/${id}`, { method: 'DELETE' });
        loadSafetyZonesSection();
        loadDashboardData();
    }
}

/* ==========================================================================
   5. LIVE SOS MONITORING SECTION
   ========================================================================== */
async function loadSosSection(showSpinner = false) {
    try {
        const sosAlerts = await fetch(`${API_BASE}/api/sos`).then(r => r.json());
        const tbody = document.getElementById('sos-table-body');
        tbody.innerHTML = '';

        sosAlerts.reverse().forEach(s => {
            const tr = document.createElement('tr');
            tr.className = s.status === 'ACTIVE' ? 'table-danger' : '';
            tr.innerHTML = `
                <td><strong>#SOS-${s.id}</strong></td>
                <td><div class="fw-bold">${escapeHtml(s.touristName || 'Tourist #' + s.touristId)}</div><div class="text-muted small">ID: ${s.touristId}</div></td>
                <td><span class="badge bg-dark">${s.alertType}</span></td>
                <td><code>${s.latitude}, ${s.longitude}</code></td>
                <td>
                    ${s.insideZoneName ? `<span class="badge bg-danger"><i class="fas fa-exclamation-triangle"></i> Inside: ${escapeHtml(s.insideZoneName)}</span>` : '<span class="badge bg-success">Clear Area</span>'}
                </td>
                <td>${s.alertTime ? new Date(s.alertTime).toLocaleString() : 'N/A'}</td>
                <td><span class="badge badge-status badge-${(s.status || 'ACTIVE').toLowerCase()}">${s.status}</span></td>
                <td>
                    <button class="btn btn-sm btn-warning me-1" onclick="updateSosStatus(${s.id}, 'DISPATCHED')">Dispatch</button>
                    <button class="btn btn-sm btn-info me-1 text-white" onclick="updateSosStatus(${s.id}, 'IN_PROGRESS')">In Progress</button>
                    <button class="btn btn-sm btn-success me-1" onclick="updateSosStatus(${s.id}, 'RESOLVED')">Resolve</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) { console.error(e); }
}

function openTriggerSosModal(touristId = '', touristName = '') {
    if (touristId) {
        document.getElementById('sos-tourist-id').value = touristId;
    }
    document.getElementById('sos-modal-tourist-label').innerText = touristName ? `For Tourist: ${touristName}` : '';
    new bootstrap.Modal(document.getElementById('modalTriggerSos')).show();
}

async function submitSosAlert(event) {
    event.preventDefault();
    const touristId = document.getElementById('sos-tourist-id').value || '1';
    const payload = {
        latitude: parseFloat(document.getElementById('sos-latitude').value),
        longitude: parseFloat(document.getElementById('sos-longitude').value),
        alertType: document.getElementById('sos-alert-type').value,
        message: document.getElementById('sos-message').value
    };

    const res = await fetch(`${API_BASE}/api/tourists/${touristId}/sos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (res.ok) {
        bootstrap.Modal.getInstance(document.getElementById('modalTriggerSos')).hide();
        switchSection('sos');
    } else {
        alert('Failed to trigger SOS alert.');
    }
}

async function updateSosStatus(sosId, status) {
    await fetch(`${API_BASE}/api/sos/${sosId}/status`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status: status })
    });
    loadSosSection(false);
    checkActiveSosBanner();
}

/* ==========================================================================
   6. RESCUE OPERATIONS SECTION
   ========================================================================== */
async function loadRescueOperationsSection() {
    try {
        const ops = await fetch(`${API_BASE}/api/rescue-operations`).then(r => r.json());
        const tbody = document.getElementById('rescue-ops-table-body');
        tbody.innerHTML = '';

        ops.forEach(op => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>#OP-${op.id}</strong></td>
                <td><span class="badge bg-danger">SOS #${op.sosAlertId}</span></td>
                <td><div class="fw-bold">${escapeHtml(op.responseTeam)}</div></td>
                <td>${escapeHtml(op.location || 'N/A')}</td>
                <td>
                    <div class="small">Fuel: $${op.fuelCost} | Equip: $${op.equipmentCost} | Service: $${op.serviceCost}</div>
                    <strong>Total: $${op.totalCost}</strong>
                </td>
                <td><span class="badge badge-status badge-${(op.status || 'DISPATCHED').toLowerCase()}">${op.status}</span></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) { console.error(e); }
}

/* ==========================================================================
   7. VENDORS & TELECOM SECTION
   ========================================================================== */
async function loadVendorsSection() {
    try {
        const [vendors, telecom] = await Promise.all([
            fetch(`${API_BASE}/api/vendors`).then(r => r.json()),
            fetch(`${API_BASE}/api/telecom-providers`).then(r => r.json())
        ]);

        const vBody = document.getElementById('vendors-table-body');
        vBody.innerHTML = '';
        vendors.forEach(v => {
            vBody.innerHTML += `
                <tr>
                    <td><strong>#${v.id}</strong></td>
                    <td><strong>${escapeHtml(v.vendorName)}</strong></td>
                    <td>${escapeHtml(v.contactPerson)}</td>
                    <td>${escapeHtml(v.email)} / ${escapeHtml(v.phone)}</td>
                    <td><code>${escapeHtml(v.gstNumber || 'N/A')}</code></td>
                    <td><span class="badge bg-success">${v.status}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteVendor(${v.id})">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });

        const tBody = document.getElementById('telecom-table-body');
        tBody.innerHTML = '';
        telecom.forEach(t => {
            tBody.innerHTML += `
                <tr>
                    <td><strong>#${t.id}</strong></td>
                    <td><strong>${escapeHtml(t.providerName)}</strong></td>
                    <td>${escapeHtml(t.contactPerson)}</td>
                    <td>${escapeHtml(t.phone)}</td>
                    <td><span class="badge bg-info text-dark">${t.serviceType}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteTelecom(${t.id})">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });
    } catch (e) { console.error(e); }
}

async function deleteVendor(id) {
    if (confirm('Delete this vendor?')) {
        await fetch(`${API_BASE}/api/vendors/${id}`, { method: 'DELETE' });
        loadVendorsSection();
    }
}

async function deleteTelecom(id) {
    if (confirm('Delete this telecom provider?')) {
        await fetch(`${API_BASE}/api/telecom-providers/${id}`, { method: 'DELETE' });
        loadVendorsSection();
    }
}

/* ==========================================================================
   8. FINANCE SECTION (POs, BILLS, PAYMENTS)
   ========================================================================== */
async function loadFinanceSection() {
    try {
        const [pos, bills, payments] = await Promise.all([
            fetch(`${API_BASE}/api/purchase-orders`).then(r => r.json()),
            fetch(`${API_BASE}/api/vendor-bills`).then(r => r.json()),
            fetch(`${API_BASE}/api/payments`).then(r => r.json())
        ]);

        const poBody = document.getElementById('po-table-body');
        poBody.innerHTML = '';
        pos.forEach(po => {
            poBody.innerHTML += `
                <tr>
                    <td><strong>${escapeHtml(po.poNumber)}</strong></td>
                    <td>Vendor #${po.vendorId}</td>
                    <td>${po.orderDate}</td>
                    <td>${escapeHtml(po.description)}</td>
                    <td><strong>$${po.totalAmount}</strong></td>
                    <td><span class="badge bg-success">${po.status}</span></td>
                    <td>
                        ${po.status === 'APPROVED' ? `<button class="btn btn-sm btn-outline-warning text-dark me-1" onclick="openCreateBillModal(${po.id}, ${po.vendorId}, ${po.totalAmount})"><i class="fas fa-file-invoice-dollar"></i> Bill</button>` : ''}
                        <button class="btn btn-sm btn-outline-danger" onclick="deletePo(${po.id})">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });

        const billBody = document.getElementById('bills-table-body');
        billBody.innerHTML = '';
        bills.forEach(b => {
            billBody.innerHTML += `
                <tr>
                    <td><strong>${escapeHtml(b.billNumber)}</strong></td>
                    <td>PO #${b.purchaseOrderId}</td>
                    <td>Vendor #${b.vendorId}</td>
                    <td>${b.billDate}</td>
                    <td><strong>$${b.amount}</strong></td>
                    <td><span class="badge badge-status badge-${b.paymentStatus === 'PAID' ? 'active' : 'critical'}">${b.paymentStatus}</span></td>
                    <td>
                        ${b.paymentStatus === 'UNPAID' ? `<button class="btn btn-sm btn-outline-success me-1" onclick="openPayBillModal(${b.id}, ${b.amount})"><i class="fas fa-money-bill"></i> Pay</button>` : ''}
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteBill(${b.id})">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });

        const payBody = document.getElementById('payments-table-body');
        payBody.innerHTML = '';
        payments.forEach(p => {
            payBody.innerHTML += `
                <tr>
                    <td><strong>${escapeHtml(p.referenceNumber)}</strong></td>
                    <td><span class="badge bg-secondary">${p.transactionType}</span></td>
                    <td>${p.paymentDate}</td>
                    <td><strong>$${p.amount}</strong></td>
                    <td>${p.paymentMethod}</td>
                    <td><span class="badge bg-success">${p.status}</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-danger" onclick="deletePayment(${p.id})">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
        });
    } catch (e) { console.error(e); }
}

function openCreateBillModal(poId, vendorId, amount) {
    document.getElementById('bill-po-id').value = poId;
    document.getElementById('bill-vendor-id').value = vendorId;
    document.getElementById('bill-amount').value = amount;
    new bootstrap.Modal(document.getElementById('modalVendorBill')).show();
}

function openPayBillModal(billId, amount) {
    document.getElementById('pay-bill-id').value = billId;
    document.getElementById('pay-amount').value = amount;
    new bootstrap.Modal(document.getElementById('modalPayment')).show();
}

async function submitVendorBill(event) {
    event.preventDefault();
    const payload = {
        purchaseOrderId: parseInt(document.getElementById('bill-po-id').value),
        vendorId: parseInt(document.getElementById('bill-vendor-id').value),
        amount: parseFloat(document.getElementById('bill-amount').value),
        billDate: new Date().toISOString().split('T')[0],
        paymentStatus: 'UNPAID'
    };

    await fetch(`${API_BASE}/api/vendor-bills`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    bootstrap.Modal.getInstance(document.getElementById('modalVendorBill')).hide();
    loadFinanceSection();
    loadDashboardData();
}

async function submitPayment(event) {
    event.preventDefault();
    const billId = parseInt(document.getElementById('pay-bill-id').value);
    const payload = {
        transactionType: 'VENDOR_PAYMENT',
        transactionId: billId,
        paymentDate: new Date().toISOString().split('T')[0],
        amount: parseFloat(document.getElementById('pay-amount').value),
        paymentMethod: document.getElementById('pay-method').value,
        status: 'COMPLETED'
    };

    await fetch(`${API_BASE}/api/payments`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    bootstrap.Modal.getInstance(document.getElementById('modalPayment')).hide();
    loadFinanceSection();
    loadDashboardData();
}

async function deletePo(id) {
    if (confirm('Delete this purchase order?')) {
        await fetch(`${API_BASE}/api/purchase-orders/${id}`, { method: 'DELETE' });
        loadFinanceSection();
        loadDashboardData();
    }
}

async function deleteBill(id) {
    if (confirm('Delete this vendor bill?')) {
        await fetch(`${API_BASE}/api/vendor-bills/${id}`, { method: 'DELETE' });
        loadFinanceSection();
        loadDashboardData();
    }
}

async function deletePayment(id) {
    if (confirm('Delete this payment record?')) {
        await fetch(`${API_BASE}/api/payments/${id}`, { method: 'DELETE' });
        loadFinanceSection();
        loadDashboardData();
    }
}

async function createPurchaseOrder(event) {
    event.preventDefault();
    const poData = {
        vendorId: parseInt(document.getElementById('po-vendor-id').value || '1'),
        description: document.getElementById('po-description').value,
        totalAmount: parseFloat(document.getElementById('po-amount').value),
        status: 'APPROVED'
    };

    await fetch(`${API_BASE}/api/purchase-orders`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(poData)
    });

    bootstrap.Modal.getInstance(document.getElementById('modalPo')).hide();
    loadFinanceSection();
    loadDashboardData();
}

/* ==========================================================================
   9. BUDGETS & ACCOUNTING SECTION
   ========================================================================== */
async function loadBudgetsSection() {
    try {
        const budgets = await fetch(`${API_BASE}/api/budgets`).then(r => r.json());
        const tbody = document.getElementById('budgets-table-body');
        tbody.innerHTML = '';

        budgets.forEach(b => {
            const util = b.budgetUtilization ? b.budgetUtilization.toFixed(1) : 0;
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>#${b.id}</strong></td>
                <td><div class="fw-bold">${escapeHtml(b.budgetName)}</div><div class="text-muted small">${escapeHtml(b.region)} | ${escapeHtml(b.zone)}</div></td>
                <td>$${b.allocatedAmount.toLocaleString()}</td>
                <td>$${b.actualAmount.toLocaleString()}</td>
                <td><strong class="text-success">$${b.remainingBudget.toLocaleString()}</strong></td>
                <td>
                    <div class="d-flex align-items-center gap-2">
                        <div class="progress flex-grow-1" style="height: 8px;">
                            <div class="progress-bar ${util > 80 ? 'bg-danger' : 'bg-primary'}" style="width: ${Math.min(util, 100)}%;"></div>
                        </div>
                        <span class="small fw-bold">${util}%</span>
                    </div>
                </td>
                <td><span class="badge bg-success">${b.status}</span></td>
                <td>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteBudget(${b.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });

        // Load Journals
        const journals = await fetch(`${API_BASE}/api/journals`).then(r => r.json());
        const jBody = document.getElementById('journals-table-body');
        jBody.innerHTML = '';
        journals.forEach(j => {
            jBody.innerHTML += `
                <tr>
                    <td><strong>${j.journalNumber}</strong></td>
                    <td>${j.journalDate}</td>
                    <td>${escapeHtml(j.description)}</td>
                    <td><span class="badge bg-secondary">${j.referenceType}</span></td>
                </tr>
            `;
        });
    } catch (e) { console.error(e); }
}

async function saveBudget(event) {
    event.preventDefault();
    const budgetData = {
        budgetName: document.getElementById('budget-name').value,
        financialYear: '2026-2027',
        region: document.getElementById('budget-region').value,
        zone: document.getElementById('budget-zone').value,
        allocatedAmount: parseFloat(document.getElementById('budget-allocated').value),
        actualAmount: 0.0,
        status: 'ACTIVE'
    };

    await fetch(`${API_BASE}/api/budgets`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(budgetData)
    });

    bootstrap.Modal.getInstance(document.getElementById('modalBudget')).hide();
    loadBudgetsSection();
    loadDashboardData();
}

async function deleteBudget(id) {
    if (confirm('Delete this safety budget allocation?')) {
        await fetch(`${API_BASE}/api/budgets/${id}`, { method: 'DELETE' });
        loadBudgetsSection();
        loadDashboardData();
    }
}

/* ==========================================================================
   10. REPORTS SECTION
   ========================================================================== */
async function loadReportsSection() {
    try {
        const [bs, pnl, budgetRep] = await Promise.all([
            fetch(`${API_BASE}/api/reports/balance-sheet`).then(r => r.json()),
            fetch(`${API_BASE}/api/reports/profit-loss`).then(r => r.json()),
            fetch(`${API_BASE}/api/reports/budget`).then(r => r.json())
        ]);

        // Balance Sheet
        document.getElementById('rep-bs-total-assets').innerText = `$${bs.totalAssets.toLocaleString()}`;
        document.getElementById('rep-bs-equipment').innerText = `$${bs.equipmentAssets.toLocaleString()}`;
        document.getElementById('rep-bs-cash').innerText = `$${bs.cashAssets.toLocaleString()}`;
        document.getElementById('rep-bs-liabilities').innerText = `$${bs.totalLiabilities.toLocaleString()}`;
        document.getElementById('rep-bs-net-position').innerText = `$${bs.netFinancialPosition.toLocaleString()}`;

        // Profit & Loss
        document.getElementById('rep-pnl-revenue').innerText = `$${pnl.touristPassRevenue.toLocaleString()}`;
        document.getElementById('rep-pnl-costs').innerText = `$${pnl.emergencyRescueOperationalCosts.toLocaleString()}`;
        document.getElementById('rep-pnl-net').innerText = `$${pnl.netProfitLoss.toLocaleString()}`;
        document.getElementById('rep-pnl-margin').innerText = `${pnl.profitMarginPercentage}%`;

        // Budget breakdown table
        const rBody = document.getElementById('rep-budget-table-body');
        rBody.innerHTML = '';
        budgetRep.budgets.forEach(b => {
            rBody.innerHTML += `
                <tr>
                    <td><strong>${escapeHtml(b.budgetName)}</strong></td>
                    <td>${escapeHtml(b.region)}</td>
                    <td>${escapeHtml(b.zone)}</td>
                    <td>$${b.allocatedAmount.toLocaleString()}</td>
                    <td>$${b.actualAmount.toLocaleString()}</td>
                    <td>$${b.remainingBudget.toLocaleString()}</td>
                    <td><strong>${b.budgetUtilization.toFixed(1)}%</strong></td>
                </tr>
            `;
        });
    } catch (e) { console.error(e); }
}

/* Utility Helpers */
function escapeHtml(text) {
    if (!text) return '';
    return text.toString()
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}
