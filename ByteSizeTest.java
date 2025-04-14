import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ByteSizeTest {
    @Test
    public void testByteSizeParsing() {
        assertEquals(10L, new ByteSize("10B").getBytes());
        assertEquals(10240L, new ByteSize("10KB").getBytes());
        assertEquals(1572864L, new ByteSize("1.5MB").getBytes());
        assertEquals(1073741824L, new ByteSize("1GB").getBytes());
    }

    @Test
    public void testInvalidByteSize() {
        assertThrows(IllegalArgumentException.class, () -> new ByteSize("10XX"));
    }
}