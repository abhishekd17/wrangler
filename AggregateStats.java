import java.util.Collections;
import java.util.List;

public class AggregateStats implements Directive {
    private String sourceSizeCol, sourceTimeCol, targetSizeCol, targetTimeCol;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
                .add("source_size", TokenType.COLUMN_NAME, "Column with byte sizes")
                .add("source_time", TokenType.COLUMN_NAME, "Column with time durations")
                .add("target_size", TokenType.COLUMN_NAME, "Output column for total size (MB)")
                .add("target_time", TokenType.COLUMN_NAME, "Output column for total time (s)")
                .build();
    }

    @Override
    public void initialize(Arguments args) {
        sourceSizeCol = ((ColumnName) args.value("source_size")).value().toString();
        sourceTimeCol = ((ColumnName) args.value("source_time")).value().toString();
        targetSizeCol = ((ColumnName) args.value("target_size")).value().toString();
        targetTimeCol = ((ColumnName) args.value("target_time")).value().toString();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException {
        long totalBytes = 0L;
        long totalNanos = 0L;

        for (Row row : rows) {
            Object size = row.getValue(sourceSizeCol);
            Object time = row.getValue(sourceTimeCol);

            if (size instanceof String) {
                try {
                    totalBytes += new ByteSize((String) size).getBytes();
                } catch (IllegalArgumentException e) {
                    // Skip invalid values
                }
            }
            if (time instanceof String) {
                try {
                    totalNanos += new TimeDuration((String) time).getNanoseconds();
                } catch (IllegalArgumentException e) {
                    // Skip invalid values
                }
            }
        }

        Row result = new Row(targetSizeCol, totalBytes / (1024.0 * 1024.0));
        result.add(targetTimeCol, totalNanos / 1_000_000_000.0);
        return Collections.singletonList(result);
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}