package app;

public record ShareScenario(
        String name,
        String fileType,
        int sensitivity,
        int recipientTrust,
        int channelSafety,
        boolean encrypted,
        boolean expiry,
        boolean authentication,
        boolean auditLogging,
        boolean revocable) {

    public ShareScenario {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Scenario name is required.");
        if (fileType == null || fileType.isBlank()) throw new IllegalArgumentException("File type is required.");
        if (sensitivity < 1 || sensitivity > 5) throw new IllegalArgumentException("Sensitivity must be 1-5.");
        if (recipientTrust < 1 || recipientTrust > 5) throw new IllegalArgumentException("Recipient trust must be 1-5.");
        if (channelSafety < 1 || channelSafety > 5) throw new IllegalArgumentException("Channel safety must be 1-5.");
    }
}
