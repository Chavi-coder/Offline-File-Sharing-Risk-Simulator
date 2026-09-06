# Testing Approach

## Test 1: Critical-risk scenario
Input characteristics:
- Sensitivity = 5
- Recipient trust = 1
- Channel safety = 1
- Encryption = No
- Expiry = No
- Authentication = No
- Logging = No
- Revocation = No

Expected: score >= 75 and CRITICAL.

## Test 2: Low-risk scenario
Input characteristics:
- Sensitivity = 1
- Recipient trust = 5
- Channel safety = 5
- Encryption = Yes
- Expiry = Yes
- Authentication = Yes
- Logging = Yes
- Revocation = Yes

Expected: score < 25 and LOW.

## Test 3: Invalid input
Sensitivity outside 1–5 must be rejected.

## Test 4: Empty input
Blank scenario name and file type must be rejected.

## Test 5: Persistence
After a successful assessment, `data/scenarios.txt` should contain a summary record.

The included `RiskEngineTest.java` automates the first two core tests.
