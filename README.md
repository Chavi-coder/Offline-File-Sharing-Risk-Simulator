<div align="center">

# 🔐 Offline File-Sharing Risk Simulator

### *An Explainable Java Rule-Based Security Assessment Tool*

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/License-Academic-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=for-the-badge)
![Offline](https://img.shields.io/badge/Mode-100%25%20Offline-purple?style=for-the-badge)

> Evaluate the security risk of a file-sharing event **before** it happens —  
> fully offline, fully explainable, no third-party libraries required.

**Student:** Chavi Agrawal &nbsp;|&nbsp; **Reg. No.:** 24BCY10035  
**Course:** B.Tech – Cyber Security &nbsp;|&nbsp; **University:** VIT Bhopal

</div>

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Risk Model](#-risk-model)
- [Project Structure](#-project-structure)
- [Java Concepts Demonstrated](#-java-concepts-demonstrated)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Compile](#compile)
  - [Run the Application](#run-the-application)
  - [Run Tests](#run-tests)
- [Sample Output](#-sample-output)
- [Data Storage](#-data-storage)
- [Limitations](#️-limitations)
- [Academic Originality](#-academic-originality)

---

## 🔭 Overview

People routinely share files without thinking about **who** can see them, **how** securely they travel, or **whether access can ever be revoked**. This project fills that gap.

The **Offline File-Sharing Risk Simulator** is a console-based Java application that accepts a structured description of a planned file-sharing event and evaluates it against a deterministic, rule-based security engine. It produces:

- 📊 A **0–100 risk score**
- 🏷️ A **risk level** (LOW / MODERATE / HIGH / CRITICAL)
- 📝 Plain-language **reasons** for every point added
- ✅ Actionable **security recommendations**
- 🔄 A **what-if safer alternative** comparison
- 💾 A **local history** of all evaluated scenarios

> ⚠️ This is a **simulator**. It never uploads, downloads, transmits, or modifies any real file.

---

## ✨ Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | **Scenario Input & Validation** | Guided prompts collect file type, sensitivity, recipient trust, channel safety, and five boolean security controls. All inputs are validated before processing. |
| 2 | **Explainable Risk Scoring** | Every risk factor that contributes to the final score is listed with its point value and a plain-language explanation. |
| 3 | **What-If Safer Alternative** | Automatically builds a maximum-security version of your scenario and shows how many risk points you could eliminate. |
| 4 | **Local Scenario History** | Evaluated scenarios are saved to `data/scenarios.txt`. No database or network connection required. |
| 5 | **Automated Validation Tests** | `RiskEngineTest.java` verifies CRITICAL and LOW boundary conditions without any testing framework. |

---

## ⚖️ Risk Model

The engine evaluates **nine independent risk factors**. Points are summed and clamped to `[0, 100]`.

### Risk Factor Weights

| Risk Factor | Condition | Points |
|---|---|:---:|
| Sensitive content | Sensitivity ≥ 4 | **+25** |
| Moderate sensitivity | Sensitivity = 3 | **+12** |
| Low recipient trust | Recipient trust ≤ 2 | **+20** |
| Uncertain recipient trust | Recipient trust = 3 | **+10** |
| Weak sharing channel | Channel safety ≤ 2 | **+20** |
| Moderate channel control | Channel safety = 3 | **+8** |
| No encryption | File not encrypted | **+15** |
| No access expiry | Link never expires | **+8** |
| No recipient authentication | No auth required | **+10** |
| No access/activity logging | Logging disabled | **+7** |
| Access not revocable | Cannot revoke | **+5** |
| Source-code exposure | File type = `code` AND no encryption | **+10** |

### Risk Bands

| Score | Level | Meaning |
|:---:|:---:|---|
| 0 – 24 | 🟢 **LOW** | Generally safe to proceed |
| 25 – 49 | 🟡 **MODERATE** | Improvements recommended |
| 50 – 74 | 🟠 **HIGH** | Multiple controls missing |
| 75 – 100 | 🔴 **CRITICAL** | Do not share without major changes |

---

## 📁 Project Structure

```
Offline-File-Sharing-Risk-Simulator/
│
├── Main.java              # Entry point — launches ConsoleUI
├── ConsoleUI.java         # Interactive menu & all user I/O
├── ShareScenario.java     # Immutable record for a sharing event (with validation)
├── RiskEngine.java        # Core rule engine — applies all 9 risk rules
├── RiskFactor.java        # Record: name, points, reason, recommendation
├── RiskResult.java        # Immutable record: score, level, reasons, recommendations
├── RiskLevel.java         # Enum: LOW | MODERATE | HIGH | CRITICAL
├── ScenarioStore.java     # Reads/writes local history (data/scenarios.txt)
├── RiskEngineTest.java    # Automated boundary tests (no framework needed)
│
├── data/
│   └── scenarios.txt      # Auto-created on first save
│
├── sample_output.txt      # Example console session
├── design.md              # Architecture & UML diagrams
├── statement.md           # Problem statement & scope
└── testing.md             # Test case definitions
```

---

## ☕ Java Concepts Demonstrated

| Concept | Where Used |
|---|---|
| **Records** | `ShareScenario`, `RiskFactor`, `RiskResult` |
| **Enums** | `RiskLevel` |
| **Encapsulation** | Private fields/methods in `RiskEngine`, `ConsoleUI` |
| **Constructor validation** | Compact constructor in `ShareScenario` |
| **Streams** | `filter()`, `mapToInt()`, `map()`, `distinct()`, `toList()` in `RiskEngine` |
| **Exception handling** | `IllegalArgumentException`, `IllegalStateException`, try-catch in `ConsoleUI` |
| **File I/O (NIO.2)** | `Files.writeString()`, `Files.readAllLines()`, `Files.createDirectories()` |
| **Enhanced switch** | Arrow-case switch in `ConsoleUI.start()` |
| **Collections** | `ArrayList`, immutable `List` |
| **Immutable results** | `List.copyOf()` in `RiskResult` |

---

## 🚀 Getting Started

### Prerequisites

- **JDK 17 or later** — [Download from Oracle](https://www.oracle.com/java/technologies/downloads/) or use an OpenJDK distribution.

```bash
java -version   # should show 17+
```

---

### Compile

**Linux / macOS / Git Bash:**
```bash
javac -d out $(find . -name "*.java")
```

**Windows PowerShell:**
```powershell
javac -d out (Get-ChildItem -Recurse *.java).FullName
```

---

### Run the Application

```bash
java -cp out app.Main
```

You will see an interactive menu:

```
=== OFFLINE FILE-SHARING RISK SIMULATOR ===
1. Create risk scenario
2. View saved scenarios
3. Compare a safer alternative
4. Exit
Choose:
```

---

### Run Tests

```bash
java -cp out app.RiskEngineTest
# Expected: All tests passed.
```

The test class verifies:
- A worst-case scenario scores ≥ 75 and is classified **CRITICAL**
- A best-case scenario scores < 25 and is classified **LOW**

---

## 📟 Sample Output

```
Scenario name: Project Source Archive
File type: code
Sensitivity (1-5): 5
Recipient trust (1-5): 2
Channel safety (1-5): 2
Encrypted? n   |   Expires? n   |   Auth? n   |   Logging? n   |   Revocable? n

--- RISK ASSESSMENT ---
Scenario : Project Source Archive
Score    : 100/100
Level    : CRITICAL

Why:
 - Sensitive content: +25 — The file contains highly sensitive information.
 - Low recipient trust: +20 — The recipient is not strongly trusted.
 - Weak sharing channel: +20 — The chosen channel provides limited control.
 - No encryption: +15 — The file is shared without an additional encryption layer.
 - No expiry: +8 — Access may remain valid longer than necessary.
 - No recipient authentication: +10 — Possession of the link may be enough for access.
 - No access logging: +7 — Access cannot be easily reviewed.
 - Access not revocable: +5 — The sender cannot quickly withdraw access.
 - Source-code exposure: +10 — Unprotected source code can reveal intellectual property.

Recommendations:
 - Classify the file and apply stronger protection before sharing.
 - Verify the recipient identity before granting access.
 - Use a managed private channel instead of a public link.
 - Encrypt sensitive files before sharing.
 - Set an access-expiration time.
 - Require recipient authentication.
 - Enable access/activity logging.
 - Choose a mechanism that supports revocation.
 - Encrypt code archives and share only with verified recipients.
```

---

## 💾 Data Storage

Saved scenario summaries are written to **`data/scenarios.txt`** (auto-created on first save).

Each record is a single pipe-delimited line:

```
scenario name | file type | risk score | risk level
```

**Example:**
```
Project Source Archive | code | 100 | CRITICAL
Financial Report Q4    | document | 35 | MODERATE
Team Photo Album       | image | 0 | LOW
```

No file contents are ever stored. No network connection is required.

---

## ⚠️ Limitations

- Does **not** verify whether a real cloud service actually enforces a claimed security control.
- Risk weights are **educational heuristics**, not derived from a certified security standard (NIST, ISO, etc.).
- **Not** a replacement for a professional information-security audit or penetration test.
- Currently supports console interaction only (no GUI).

---

## 🎓 Academic Originality

The implementation, risk model, class structure, and all documentation in this repository were written specifically for this assignment by **Chavi Agrawal (24BCY10035)**. The project is not copied from any template repository.

Security risk concepts (encryption, access control, audit logging, etc.) are well-established principles in the field of information security. This submission presents a deliberately scoped, offline Java simulation rather than a production file-sharing platform, and all such concepts have been independently implemented and documented.

---

<div align="center">

Made with ☕ Java &nbsp;|&nbsp; VIT Bhopal University &nbsp;|&nbsp; 2026

</div>
