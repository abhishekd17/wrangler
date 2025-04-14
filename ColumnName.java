public class ColumnName implements Token {
    private final String name;

    public ColumnName(String name) {
        this.name = name;
    }

    @Override
    public TokenType type() {
        return TokenType.COLUMN_NAME;
    }

    @Override
    public Object value() {
        return name;
    }
}