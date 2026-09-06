# Offline-File-Sharing-Risk-Simulator
Offline File-Sharing Risk Simulator

 Project title
**Offline File-Sharing Risk Simulator: An Explainable Java Rule-Based Assessment Tool**

Student
Chavi Agrawal  
Registration No.: 24BCY10035

## Overview
This is an offline Java application that evaluates the security risk of a planned file-sharing action before the file is actually shared. The user describes the file, recipient, sharing channel and available security controls. A deterministic rule engine converts those inputs into a 0–100 risk score, risk level, reasons and recommendations.

The project is intentionally a **simulator/decision-support tool**. It does not upload files, contact external services, or perform real-world file sharing.

## Major modules
1. Scenario Input & Validation
2. Explainable Risk Evaluation
3. What-If Safer Alternative
4. Local Scenario History
5. Validation Tests

## Java concepts demonstrated
- Classes and objects
- Records and enums
- Encapsulation
- Constructors and validation
- Method calls and modular design
- Collections and streams
- Exception handling
- File I/O
- Switch expressions / enhanced switch
- Immutable result objects
- Unit-style validation tests

## Requirements
- JDK 17 or later

## Compile
From the repository root:

```bash
javac -d out $(find src/main/java -name "*.java")
```

On Windows PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse src/main/java/*.java).FullName
```

## Run
```bash
java -cp out app.Main
```

## Run tests
```bash
javac -d out (find src/main/java src/test/java -name "*.java")
java -cp out app.RiskEngineTest
```

For Windows PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse src/main/java,src/test/java/*.java).FullName
java -cp out app.RiskEngineTest
```

## Data storage
Saved scenario summaries are written locally to `data/scenarios.txt`. No network connection or external database is required.

## Risk model
The score is deterministic and explainable. Risk points are added for factors such as:
- high sensitivity
- low recipient trust
- weak sharing channel
- missing encryption
- missing expiry
- missing recipient authentication
- missing access logging
- inability to revoke access
- unencrypted source-code sharing

Risk bands:
- 0–24: LOW
- 25–49: MODERATE
- 50–74: HIGH
- 75–100: CRITICAL

## Academic originality
The implementation, wording, risk model, class structure and documentation in this repository were written specifically for this assignment. The project is not copied from a template repository. Similar security ideas exist in industry and academia; the submission presents a deliberately scoped, offline Java simulation rather than a production file-sharing platform.

## Limitations
- It does not verify whether a real cloud service actually has a security control.
- Risk weights are educational heuristics, not a certified security standard.
- It is not a replacement for professional information-security assessment.
