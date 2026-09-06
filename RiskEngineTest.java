package app;

public class RiskEngineTest {
    public static void main(String[] args) {
        RiskEngine engine = new RiskEngine();

        ShareScenario risky = new ShareScenario(
                "test", "code", 5, 1, 1, false, false, false, false, false);
        RiskResult result = engine.evaluate(risky);

        if (result.score() < 75 || result.level() != RiskLevel.CRITICAL) {
            throw new AssertionError("Critical scenario test failed.");
        }

        ShareScenario safe = new ShareScenario(
                "safe", "document", 1, 5, 5, true, true, true, true, true);
        RiskResult safeResult = engine.evaluate(safe);

        if (safeResult.score() >= 25 || safeResult.level() != RiskLevel.LOW) {
            throw new AssertionError("Low-risk scenario test failed.");
        }

        System.out.println("All tests passed.");
    }
}
