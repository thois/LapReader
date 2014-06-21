package fi.helsinki.cs.thois.lapreader;

import com.j256.ormlite.dao.ForeignCollection;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Lap;
import fi.helsinki.cs.thois.lapreader.model.Result;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import fi.helsinki.cs.thois.lapreader.parser.Parser;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ControllerTest {
    Parser parser = new OrionParser();
    String databaseUrl = "jdbc:sqlite::memory:";
    Controller controller;
    
    @Before
    public void initialize() throws SQLException {
        controller = new Controller(databaseUrl);
        controller.foreigKeysOn();
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
        TestDay addedDay = controller.addDay(null);
        Assert.assertEquals(TestDay.class, addedDay.getClass());
        List<TestDay> days = controller.getDays();
        System.out.println(days.size());
        assert(days.size() == 1);
    } 
    
    @Test
    public void testAddingHeat() throws SQLException, ParseException {
        TestDay day = controller.addDay("15.06.2013");
        String[] lines = {"01Lap  00m15s18  04m34s43"};
        Heat heat = controller.addHeat(day, lines, "16:53", parser);
        Assert.assertEquals(heat.toString(), controller.getHeats(day).toArray(new Heat[0])[0].toString());
        Lap[] laps = controller.getLaps(heat).toArray(new Lap[0]);
        assert(laps.length == 1);
        assert(laps[0].getTime() == 15180);
        assert(laps[0].getLapNumber() == 1);
        Assert.assertEquals(new Result(1, 15180).toString(), heat.getResult().toString());
        controller.addHeat(day, lines, "", parser);
        controller.addHeat(day, lines, null, parser);
        
    }
    
    @Test
    public void testGettingHeat() throws SQLException, ParseException {
        TestDay day = controller.addDay("15.06.2013");
        String[] lines = {"01Lap  00m15s18  04m34s43"};
        controller.addHeat(day, lines, "16:53", parser);
        ForeignCollection<Heat> h =
                controller.getHeats(controller.getDays().get(0));
        Heat[] heats = h.toArray(new Heat[0]);
        assert(heats.length == 1);
    }
    
    
}
