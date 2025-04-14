import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDuration implements Token {
    private static final Pattern PATTERN = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)([Mm][Ss]|[Ss]|[Mm][Ii][Nn])$");
    private final long nanoseconds;
    private final String raw;

    public TimeDuration(String value) {
        this.raw = value.trim();
        if (raw.isEmpty()) {
            throw new IllegalArgumentException("Time duration cannot be empty");
        }
        Matcher matcher = PATTERN.matcher(raw);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid time duration: " + raw);
        }
        double number = Double.parseDouble(matcher.group(1));
        String unit = matcher.group(2).toLowerCase();
        long factor;
        switch (unit) {
            case "ms":
                factor = 1_000_000L;
                break;
            case "s":
                factor = 1_000_000_000L;
                break;
            case "min":
                factor = 60_000_000_000L;
                break;
            default:
                throw new IllegalStateException("Unknown unit: " + unit);
        }
        this.nanoseconds = (long) (number * factor);
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public Object value() {
        return nanoseconds;
    }

    public long getNanoseconds() {
        return nanoseconds;
    }
}