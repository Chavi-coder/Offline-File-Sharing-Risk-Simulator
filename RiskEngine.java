package app;

import java.util.ArrayList;
import java.util.List;

public class RiskEngine {

    public RiskResult evaluate(ShareScenario s) {
        List<RiskFactor> factors = buildFactors(s);
        int raw = factors.stream().mapToInt(RiskFactor::points).sum();
        int score = Math.max(0, Math.min(100, raw));

        List<String> reasons = factors.stream()
                .filter(f -> f.points() > 0)
                .map(f -> f.name() + ": +" + f.points() + " — " + f.reason())
                .toList();

        List<String> recommendations = factors.stream()
                .filter(f -> f.points() > 0)
                .map(RiskFactor::recommendation)
                .distinct()
                .toList();

        return new RiskResult(score, classify(score), reasons, recommendations);
    }

    private List<RiskFactor> buildFactors(ShareScenario s) {
        List<RiskFactor> f = new ArrayList<>();

        if (s.sensitivity() >= 4)
            f.add(new RiskFactor("Sensitive content", 25,
                    "The file contains highly sensitive information.",
                    "Classify the file and apply stronger protection before sharing."));
        else if (s.sensitivity() == 3)
            f.add(new RiskFactor("Moderate sensitivity", 12,
                    "The file has non-trivial information exposure.",
                    "Prefer a controlled recipient and protected channel."));

        if (s.recipientTrust() <= 2)
            f.add(new RiskFactor("Low recipient trust", 20,
                    "The recipient is not strongly trusted.",
                    "Verify the recipient identity before granting access."));
        else if (s.recipientTrust() == 3)
            f.add(new RiskFactor("Uncertain recipient trust", 10,
                    "Recipient trust is not established.",
                    "Use authenticated, least-privilege access."));

        if (s.channelSafety() <= 2)
            f.add(new RiskFactor("Weak sharing channel", 20,
                    "The chosen channel provides limited control.",
                    "Use a managed private channel instead of a public link."));
        else if (s.channelSafety() == 3)
            f.add(new RiskFactor("Moderate channel control", 8,
                    "Some controls are available but exposure remains.",
                    "Prefer a private channel with access controls."));

        if (!s.encrypted())
            f.add(new RiskFactor("No encryption", 15,
                    "The file is shared without an additional encryption layer.",
                    "Encrypt sensitive files before sharing."));
        if (!s.expiry())
            f.add(new RiskFactor("No expiry", 8,
                    "Access may remain valid longer than necessary.",
                    "Set an access-expiration time."));
        if (!s.authentication())
            f.add(new RiskFactor("No recipient authentication", 10,
                    "Possession of the link may be enough for access.",
                    "Require recipient authentication."));
        if (!s.auditLogging())
            f.add(new RiskFactor("No access logging", 7,
                    "Access cannot be easily reviewed.",
                    "Enable access/activity logging."));
        if (!s.revocable())
            f.add(new RiskFactor("Access not revocable", 5,
                    "The sender cannot quickly withdraw access.",
                    "Choose a mechanism that supports revocation."));

        if (s.fileType().equalsIgnoreCase("code") && !s.encrypted())
            f.add(new RiskFactor("Source-code exposure", 10,
                    "Unprotected source code can reveal intellectual property.",
                    "Encrypt code archives and share only with verified recipients."));

        return f;
    }

    private RiskLevel classify(int score) {
        if (score <= 24) return RiskLevel.LOW;
        if (score <= 49) return RiskLevel.MODERATE;
        if (score <= 74) return RiskLevel.HIGH;
        return RiskLevel.CRITICAL;
    }
}
