package fi.helsinki.cs.thois.lapreader;

import com.j256.ormlite.dao.ForeignCollection;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ControllerTest {
    String databaseUrl = "jdbc:h2:mem:account";
    Controller controller;
    
    @Before
    public void initialize() throws SQLException {
        controller = new Controller(databaseUrl);
    }
    
    @After
    public void dropDatabases() throws SQLException {
        controller = new Controller(databaseUrl);
        controller.dropTables();
        
    }
    
    @Test
    public void testControllerCreation() throws SQLException {
        Controller controller = new Controller(databaseUrl);
    }
    
    @Test
    public void testQueryWithEmptyDatabase() throws SQLException {
        List<TestDay> lst = controller.getDays();
        assert(lst.isEmpty());
    }
    
    @Test
    public void testAddingDay() throws ParseException, SQLException {
        controller.addDay(null);
        List<TestDay> days = controller.getDays();
        System.out.println(days.size());
        assert(days.size() == 1);
    } 
    
    @Test
    public void testAddingHeat() throws SQLException, ParseException {
        TestDay day = controller.addDay("15.06.2013");
        String[] lines = {"01Lap  00m15s18  04m34s43"};
        controller.addHeat(day, lines, "16:53");
    }
    
    @Test
    public void testGettingHeat() throws SQLException, ParseException {
        TestDay day = controller.addDay("15.06.2013");
        String[] lines = {"01Lap  00m15s18  04m34s43"};
        controller.addHeat(day, lines, "16:53");
        ForeignCollection<Heat> h = controller.getHeats(controller.getDays().get(0));
        Heat[] heats = h.toArray(new Heat[0]);
        assert(heats.length == 1);
    }
    
    
}
