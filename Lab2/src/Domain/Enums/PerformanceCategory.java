package Domain.Enums;

public enum PerformanceCategory {
    EXCELLENT("отличник"),
    GOOD("хорошист"),
    SATISFACTORY("троечник"),
    FAILING("не успевающий");

    private final String label;

    PerformanceCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static PerformanceCategory fromAverage(double average) {
        if (average >= 4.5) {
            return EXCELLENT;
        }
        if (average >= 4.0) {
            return GOOD;
        }
        if (average >= 3.0) {
            return SATISFACTORY;
        }
        return FAILING;
    }
}
