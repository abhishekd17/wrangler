public abstract class DirectivesBaseVisitor<T> {
    public Token visitValue(Object ctx) {
        String value = ctx.toString();
        if (value.matches("[0-9]+(\\.[0-9]+)?[BbKkMmGgTt][Bb]?")) {
            return new ByteSize(value);
        } else if (value.matches("[0-9]+(\\.[0-9]+)?([Mm][Ss]|[Ss]|[Mm][Ii][Nn])")) {
            return new TimeDuration(value);
        }
        return null; // Placeholder
    }
}