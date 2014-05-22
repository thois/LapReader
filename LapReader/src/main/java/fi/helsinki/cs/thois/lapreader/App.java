package fi.helsinki.cs.thois.lapreader;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import fi.helsinki.cs.thois.lapreader.data.TestDay;
import fi.helsinki.cs.thois.lapreader.ui.text.TextUi;

/**
 * Hello world!
 *
 */
public class App 
{
    
   private static EbeanServer initDb(boolean dropAndCreateDatabase) {
        ServerConfig config = new ServerConfig();
        config.setName("LapDb");

        DataSourceConfig sqLite = new DataSourceConfig();
        sqLite.setDriver("org.sqlite.JDBC");
        sqLite.setUsername("nlindval");
        sqLite.setPassword("nlindval");
        sqLite.setUrl("jdbc:sqlite:laps.db");
        config.setDataSourceConfig(sqLite);
        config.setDatabasePlatform(new SQLitePlatform());
        config.getDataSourceConfig().setIsolationLevel(Transaction.READ_UNCOMMITTED);
 
        if (dropAndCreateDatabase) {
            config.setDdlGenerate(true);
            config.setDdlRun(true);
        }

        config.setDefaultServer(false);
        config.setRegister(false);

        config.addClass(TestDay.class);

        return EbeanServerFactory.create(config);
    }
        
    public static void main( String[] args )
    {
        EbeanServer server = initDb(true);
        TextUi ui = new TextUi(server);
        
        ui.mainMenu();
        
    }
}
