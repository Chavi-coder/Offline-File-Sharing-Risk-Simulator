# Design Documentation

## 1. System Architecture

```mermaid
flowchart LR
    U[User] --> UI[ConsoleUI]
    UI --> V[Scenario Validation]
    V --> E[RiskEngine]
    E --> R[RiskResult]
    R --> UI
    UI --> S[ScenarioStore]
    S --> F[(Local scenarios.txt)]
```

## 2. Workflow

```mermaid
flowchart TD
    A[Start] --> B[Enter sharing scenario]
    B --> C{Valid input?}
    C -- No --> B
    C -- Yes --> D[Build ShareScenario]
    D --> E[Apply risk rules]
    E --> F[Calculate score]
    F --> G[Classify risk]
    G --> H[Show reasons and recommendations]
    H --> I{Save?}
    I --> J[Write local history]
    I --> K[End]
    J --> K
```

## 3. Use Case Diagram

```mermaid
flowchart LR
    User((User))
    User --> UC1[Create scenario]
    User --> UC2[View saved scenarios]
    User --> UC3[Run safer alternative]
    UC1 --> UC4[Receive risk score]
    UC1 --> UC5[Receive recommendations]
```

## 4. Class Diagram

```mermaid
classDiagram
    class Main
    class ConsoleUI
    class ShareScenario
    class RiskEngine
    class RiskFactor
    class RiskResult
    class RiskLevel
    class ScenarioStore

    Main --> ConsoleUI
    ConsoleUI --> RiskEngine
    ConsoleUI --> ScenarioStore
    RiskEngine --> ShareScenario
    RiskEngine --> RiskFactor
    RiskEngine --> RiskResult
    RiskResult --> RiskLevel
```

## 5. Sequence Diagram

```mermaid
sequenceDiagram
    participant U as User
    participant UI as ConsoleUI
    participant E as RiskEngine
    participant S as ScenarioStore

    U->>UI: Enter scenario
    UI->>UI: Validate input
    UI->>E: evaluate(scenario)
    E->>E: Apply rules
    E-->>UI: RiskResult
    UI->>S: save(scenario, result)
    S-->>UI: Saved
    UI-->>U: Score + reasons + recommendations
```

## 6. Storage Design
The project deliberately uses a small local text file rather than a database because the assignment is focused on Java programming concepts. Each saved record stores:

`scenario name | file type | risk score | risk level`

No file contents are stored.
