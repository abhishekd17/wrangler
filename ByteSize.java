import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteSize implements Token {
    private static final Pattern PATTERN = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)([Bb]|[KkMmGgTt][Bb])$");
    private final long bytes;
    private final String raw;

    public ByteSize(String value) {
        this.raw = value.trim();
        if (raw.isEmpty()) {
            throw new IllegalArgumentException("Byte size cannot be empty");
        }
        Matcher matcher = PATTERN.matcher(raw);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid byte size: " + raw);
        }
        double number = Double.parseDouble(matcher.group(1));
        String unit = matcher.group(2).toLowerCase();
        long factor;
        switch (unit) {
            case "b":
                factor = 1L;
                break;
            case "kb":
                factor = 1024L;
                break;
            case "mb":
                factor = 1024L * 1024L;
                break;
            case "gb":
                factor = 1024L * 1024L * 1024L;
                break;
            case "tb":
                factor = 1024L * 1024L * 1024L * 1024L;
                break;
            default:
                throw new IllegalStateException("Unknown unit: " + unit);
        }
        this.bytes = (long) (number * factor);
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public Object value() {
        return bytes;
    }

    public long getBytes() {
        return bytes;
    }
}