package util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {

    private static ConnectionInfo connectionInfo;
    private static DriverManagerDataSource driverManagerDataSource;

    public static void setConnectionInfo(ConnectionInfo connectionInfo) {
        DataSourceProvider.connectionInfo = connectionInfo;
    }

    public static void closePool() {
        if (driverManagerDataSource == null) {
            return;
        }

    }

    public static DataSource getDataSource() {
        if (driverManagerDataSource != null) {
            return driverManagerDataSource;
        }

        if (connectionInfo == null) {
            throw new IllegalStateException(
                    "Connection info is not configured. Use setConnectionInfo()");
        }

        driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl(connectionInfo.getUrl());
        driverManagerDataSource.setUsername(connectionInfo.getUser());
        driverManagerDataSource.setPassword(connectionInfo.getPass());

        return driverManagerDataSource;
    }
}
