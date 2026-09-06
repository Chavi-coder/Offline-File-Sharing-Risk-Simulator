package app;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final RiskEngine engine = new RiskEngine();
    private final ScenarioStore store = new ScenarioStore();

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== OFFLINE FILE-SHARING RISK SIMULATOR ===");
            System.out.println("1. Create risk scenario");
            System.out.println("2. View saved scenarios");
            System.out.println("3. Compare a safer alternative");
            System.out.println("4. Exit");
            int choice = readInt("Choose: ", 1, 4);
            try {
                switch (choice) {
                    case 1 -> createScenario();
                    case 2 -> viewScenarios();
                    case 3 -> compareAlternative();
                    case 4 -> running = false;
                }
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Thank you.");
    }

    private void createScenario() {
        ShareScenario s = readScenario();
        RiskResult result = engine.evaluate(s);
        store.save(s, result);
        printResult(s, result);
    }

    private ShareScenario readScenario() {
        String name = readText("Scenario name: ");
        String fileType = readText("File type (document/image/code/archive): ");
        int sensitivity = readInt("Sensitivity (1-5): ", 1, 5);
        int recipient = readInt("Recipient trust (1=unknown, 5=trusted): ", 1, 5);
        int channel = readInt("Channel safety (1=public link, 5=managed private channel): ", 1, 5);
        boolean encrypted = readYesNo("Is the file encrypted before sharing? (y/n): ");
        boolean expiry = readYesNo("Does the access link expire? (y/n): ");
        boolean auth = readYesNo("Is recipient authentication required? (y/n): ");
        boolean audit = readYesNo("Is access/activity logging available? (y/n): ");
        boolean removable = readYesNo("Can the sender revoke access? (y/n): ");

        return new ShareScenario(name, fileType, sensitivity, recipient, channel,
                encrypted, expiry, auth, audit, removable);
    }

    private void viewScenarios() {
        List<String> records = store.readAll();
        if (records.isEmpty()) {
            System.out.println("No saved scenarios.");
            return;
        }
        System.out.println("\n--- SAVED SCENARIOS ---");
        records.forEach(System.out::println);
    }

    private void compareAlternative() {
        ShareScenario original = readScenario();
        RiskResult before = engine.evaluate(original);

        ShareScenario safer = new ShareScenario(
                original.name() + " - safer alternative",
                original.fileType(),
                original.sensitivity(),
                Math.max(original.recipientTrust(), 4),
                Math.max(original.channelSafety(), 4),
                true, true, true, true, true
        );

        RiskResult after = engine.evaluate(safer);

        System.out.println("\n--- WHAT-IF COMPARISON ---");
        System.out.println("Original score : " + before.score() + "/100 (" + before.level() + ")");
        System.out.println("Safer score    : " + after.score() + "/100 (" + after.level() + ")");
        System.out.println("Risk reduction : " + (before.score() - after.score()) + " points");
        System.out.println("Recommended controls:");
        after.recommendations().forEach(r -> System.out.println(" - " + r));
    }

    private void printResult(ShareScenario s, RiskResult r) {
        System.out.println("\n--- RISK ASSESSMENT ---");
        System.out.println("Scenario: " + s.name());
        System.out.println("Score: " + r.score() + "/100");
        System.out.println("Level: " + r.level());
        System.out.println("Why:");
        r.reasons().forEach(x -> System.out.println(" - " + x));
        System.out.println("Recommendations:");
        r.recommendations().forEach(x -> System.out.println(" - " + x));
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Value cannot be empty.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a number from " + min + " to " + max + ".");
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String v = scanner.nextLine().trim().toLowerCase();
            if (v.equals("y") || v.equals("yes")) return true;
            if (v.equals("n") || v.equals("no")) return false;
            System.out.println("Enter y or n.");
        }
    }
}
