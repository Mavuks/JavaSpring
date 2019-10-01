package exservlet;

import util.ConnectionInfo;
import util.DataSourceProvider;
import util.DbUtil;
import util.FileUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener()
public class MyListener implements ServletContextListener{



    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionInfo connectionInfo = DbUtil.loadConnectionInfo();
        DataSourceProvider.setConnectionInfo(connectionInfo);
        DataSource dataSource = DataSourceProvider.getDataSource();

        try(Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement();){

            String sql = FileUtil.readFileFromClasspath("schema.sql");

            stmt.executeUpdate(sql);

          //  System.out.println(sce);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSourceProvider.closePool();
    }


}
