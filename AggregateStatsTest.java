import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {
    @Test
    public void testAggregateStats() throws Exception {
        AggregateStats directive = new AggregateStats();
        directive.initialize(new Arguments() {
            @Override
            public Object value(String name) {
                switch (name) {
                    case "source_size":
                        return new ColumnName("data_transfer_size");
                    case "source_time":
                        return new ColumnName("response_time");
                    case "target_size":
                        return new ColumnName("total_size_mb");
                    case "target_time":
                        return new ColumnName("total_time_sec");
                    default:
                        return null;
                }
            }
        });

        List<Row> rows = Arrays.asList(
                new Row("data_transfer_size", "10KB").add("response_time", "5ms"),
                new Row("data_transfer_size", "20MB").add("response_time", "2s"));

        List<Row> results = directive.execute(rows, new ExecutorContext() {
        });
        assertEquals(1, results.size());
        Row result = results.get(0);

        double expectedSizeMB = (10 * 1024 + 20 * 1024 * 1024) / (1024.0 * 1024.0);
        double expectedTimeSec = (5_000_000 + 2_000_000_000) / 1_000_000_000.0;

        System.out.println("Total MB: " + result.getValue("total_size_mb"));
        System.out.println("Total Sec: " + result.getValue("total_time_sec"));

        assertEquals(expectedSizeMB, (Double) result.getValue("total_size_mb"), 0.001);
        assertEquals(expectedTimeSec, (Double) result.getValue("total_time_sec"), 0.001);
    }
}