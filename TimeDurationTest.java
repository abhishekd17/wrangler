import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TimeDurationTest {
    @Test
    public void testTimeDurationParsing() {
        assertEquals(5000000L, new TimeDuration("5ms").getNanoseconds());
        assertEquals(2100000000L, new TimeDuration("2.1s").getNanoseconds());
        assertEquals(60000000000L, new TimeDuration("1min").getNanoseconds());
    }

    @Test
    public void testInvalidTimeDuration() {
        assertThrows(IllegalArgumentException.class, () -> new TimeDuration("5xx"));
    }
}