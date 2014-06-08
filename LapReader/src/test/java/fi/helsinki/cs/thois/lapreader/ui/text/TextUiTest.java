package fi.helsinki.cs.thois.lapreader.ui.text;

import fi.helsinki.cs.thois.lapreader.Controller;
import java.sql.SQLException;
import org.junit.Test;
import junit.framework.Assert;

/**
 *
 * @author niko
 */
public class TextUiTest {
    
    @Test
    public void testCreation() throws SQLException {
        String databaseUrl = "jdbc:h2:mem:account";
        TextUi ui = new TextUi(new Controller(databaseUrl));
    }
    
    @Test
    public void testDigits() {
        assert(TextUi.digits(123) == 3);
        assert(TextUi.digits(689543) == 6);
    }
    
    @Test
    public void testFormatOrderNumber() {
        int number = 123;
        Assert.assertEquals(TextUi.formatOrderNumber(123, 12345), "00123");
        Assert.assertEquals(TextUi.formatOrderNumber(12, 12345), "00012");
        Assert.assertEquals(TextUi.formatOrderNumber(1, 12345), "00001");
        
    }
    
}
