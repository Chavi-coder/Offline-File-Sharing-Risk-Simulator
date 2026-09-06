package app;

import java.util.List;

public record RiskResult(int score, RiskLevel level, List<String> reasons, List<String> recommendations) {
    public RiskResult {
        reasons = List.copyOf(reasons);
        recommendations = List.copyOf(recommendations);
    }
}
