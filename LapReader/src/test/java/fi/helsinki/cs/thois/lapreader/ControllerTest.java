package fi.helsinki.cs.thois.lapreader;

import fi.helsinki.cs.thois.lapreader.controller.Controller;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;

public class ControllerTest {
    String databaseUrl = "jdbc:h2:mem:account";
    
    @Test
    public void testControllerCreation() throws SQLException {
        Controller controller = new Controller(databaseUrl);
    }
    
    @Test
    public void testQueryWithEmptyDatabase() throws SQLException {
        Controller controller = new Controller(databaseUrl);
        List<TestDay> lst = controller.getDays();
        assert(lst.isEmpty());
    }
    

    
    
}
