public class UsageDefinition {
    public static Builder builder(String name) {
        return new Builder();
    }

    public static class Builder {
        public Builder add(String name, TokenType type, String description) {
            return this;
        }

        public UsageDefinition build() {
            return new UsageDefinition();
        }
    }
}