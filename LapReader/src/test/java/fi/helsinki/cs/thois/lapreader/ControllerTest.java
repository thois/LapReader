package fi.helsinki.cs.thois.lapreader;

import com.j256.ormlite.dao.ForeignCollection;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Lap;
import fi.helsinki.cs.thois.lapreader.model.Result;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import fi.helsinki.cs.thois.lapreader.parser.Parser;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ControllerTest {
    Parser parser = new OrionParser();
    String databaseUrl = "jdbc:sqlite::memory:";
    Controller controller;
    DateFormat tf = new SimpleDateFormat("HH:mm");
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    
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
    
    @Test
    public void testUpdateDay() throws ParseException, SQLException {
        TestDay addedDay = controller.addDay(null);
        int id = addedDay.getId();
        controller.updateDay(addedDay, "15.12.2013");
        TestDay dayFromDatabase = controller.getTestDayById(id);
        Assert.assertEquals("15.12.2013", df.format(dayFromDatabase.getDay()));  
    }
    
    @Test
    public void testDeleteDay() throws SQLException, ParseException {
        TestDay addedDay = controller.addDay(null);
        int id = addedDay.getId();
        String[] lines = {"01Lap  00m15s18  04m34s43"};
        Heat heat = controller.addHeat(addedDay, lines, "16:53", parser);
        controller.deleteDay(addedDay);
        TestDay dayFromDatabase = controller.getTestDayById(id);
        Assert.assertEquals(null, dayFromDatabase);
        Assert.assertEquals(null, controller.getHeatById(heat.getId()));
    }
    
    @Test
    public void testGetTestDayById() throws SQLException, ParseException {
        TestDay addedDay = controller.addDay(null);
        TestDay dayFromDatabase = controller.getTestDayById(addedDay.getId());
        Assert.assertEquals(addedDay.getId(), dayFromDatabase.getId());
        Assert.assertEquals(addedDay.toString(), dayFromDatabase.toString());
    }
    
    private Heat createHeat() throws ParseException, SQLException {
        TestDay day = controller.addDay("15.06.2013");
        String[] lines = {"01Lap  00m15s18  04m34s43", "01Lap  00m16s17  04m34s43"};
        return controller.addHeat(day, lines, "16:53", parser);
    }
    
    @Test
    public void testUpdateHeatTime() throws ParseException, SQLException {
        Heat heat = createHeat();
        controller.updateHeat(heat, "15:32");
        Assert.assertEquals("15:32", tf.format(heat.getTime()));
        Heat heatFromDatabase = controller.getHeatById(heat.getId());
        Assert.assertEquals(heat.toString(), heatFromDatabase.toString());
    }
    
    @Test
    public void testUpdateHeat() throws ParseException, SQLException {
        Heat heat = createHeat();
        heat.setSetupChanges("abcd");
        heat.setTrackTemp(15);
        controller.updateHeat(heat);
        Heat heatFromDatabase = controller.getHeatById(heat.getId());
        Assert.assertEquals("abcd", heatFromDatabase.getSetupChanges());
        assert(heatFromDatabase.getTrackTemp() == 15);
    }
    
    @Test
    public void testGetHeatById() throws ParseException, SQLException {
        Heat heat = createHeat();
        Heat heatFromDatabase = controller.getHeatById(heat.getId());
        Assert.assertEquals(heat.toString(), heatFromDatabase.toString());
    }
    
    @Test
    public void testDeleteHeat() throws ParseException, SQLException {
        Heat heat = createHeat();
        controller.deleteHeat(heat);
        Heat heatFromDatabase = controller.getHeatById(heat.getId());
        Assert.assertEquals(null, heatFromDatabase);
    }
    
    @Test
    public void testGetBestLapFromHeat() throws ParseException, SQLException {
        Heat heat = createHeat();
        Lap best = controller.getBestLap(heat);
        assert(best.getTime() == 15180);
        assert(best.getLapNumber() == 1);
    }
    
    @Test
    public void testGetBestLapFromDay() throws ParseException, SQLException {
        TestDay day = controller.addDay("11.06.2013");
        String[] lines = {"01Lap  00m50s18  04m34s43",
            "02Lap  00m37s16  04m34s43"};
        controller.addHeat(day, lines, "16:53", parser);
        lines[0] = "01Lap  00m05s18  04m34s43";
        createHeat();
        Lap best = controller.getBestLap(day);
        assert(best.getTime() == 37160);
        assert(best.getLapNumber() == 2);
    }
    
    @Test
    public void testGetBestResult() throws ParseException, SQLException {
        TestDay day = controller.addDay("11.06.2013");
        String[] lines = {"01Lap  00m50s18  04m34s43",
            "02Lap  00m37s16  04m34s43"};
        controller.addHeat(day, lines, "16:53", parser);
        String[]lines2 = {"01Lap  00m05s18  04m34s43"};
        controller.addHeat(day, lines2, "16:59", parser);
        createHeat();
        Result best = controller.getBestResult(day);
        assert(best.getTime() == 87340);
        assert(best.getLaps()== 2);
    }
    
    @Test
    public void getHeatByResult() throws ParseException, SQLException {
        TestDay day = controller.addDay("11.06.2013");
        String[] lines = {"01Lap  00m50s18  04m34s43",
            "02Lap  00m37s16  04m34s43"};
        controller.addHeat(day, lines, "16:53", parser);
        Heat heat = createHeat();
        Heat heatFromdatabase = controller.getHeatByResult(heat.getResult());
        Assert.assertEquals(heat.toString(), heatFromdatabase.toString());
    }
}
