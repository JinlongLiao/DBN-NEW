package com.dci.intellij.dbn.connection.info;

import com.dci.intellij.dbn.connection.DatabaseType;
import org.jetbrains.annotations.NotNull;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public final class ConnectionInfo {
    private final DatabaseType databaseType;
    private final String productName;
    private final String productVersion;
    private final String driverName;
    private final String driverVersion;
    private final String driverJdbcType;
    private final String url;
    private final String userName;

    public ConnectionInfo(DatabaseMetaData metaData) throws SQLException {
        productName = metaData.getDatabaseProductName();
        productVersion = resolveProductVersion(metaData);
        driverName = metaData.getDriverName();
        driverVersion = metaData.getDriverVersion();
        url = metaData.getURL();
        userName = metaData.getUserName();
        driverJdbcType = resolveDriverType(metaData);
        databaseType = DatabaseType.resolve( productName.toLowerCase());
    }

    @NotNull
    private static String resolveDriverType(DatabaseMetaData metaData) throws SQLException {
        return metaData.getJDBCMajorVersion() + (metaData.getJDBCMinorVersion() > 0 ? "." + metaData.getJDBCMinorVersion() : "");
    }

    @NotNull
    private static String resolveProductVersion(DatabaseMetaData metaData) throws SQLException {
        String productVersion = metaData.getDatabaseProductVersion();
        int index = productVersion.indexOf('\n');
        productVersion = index > -1 ? productVersion.substring(0, index) : productVersion;
        return productVersion;
    }

    public String toString() {
        return  "Product name:\t" + productName + '\n' +
                "Product version:\t" + productVersion + '\n' +
                "Driver name:\t\t" + driverName + '\n' +
                "Driver version:\t" + driverVersion + '\n'+
                "JDBC Type:\t\t" + driverJdbcType + '\n' +
                "URL:\t\t" + url + '\n' +
                "User name:\t\t" + userName;
    }

    public DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public String getDriverVersion() {
        return this.driverVersion;
    }

    public String getDriverJdbcType() {
        return this.driverJdbcType;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUserName() {
        return this.userName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConnectionInfo)) return false;
        final ConnectionInfo other = (ConnectionInfo) o;
        final Object this$databaseType = this.getDatabaseType();
        final Object other$databaseType = other.getDatabaseType();
        if (this$databaseType == null ? other$databaseType != null : !this$databaseType.equals(other$databaseType))
            return false;
        final Object this$productName = this.getProductName();
        final Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName))
            return false;
        final Object this$productVersion = this.getProductVersion();
        final Object other$productVersion = other.getProductVersion();
        if (this$productVersion == null ? other$productVersion != null : !this$productVersion.equals(other$productVersion))
            return false;
        final Object this$driverName = this.getDriverName();
        final Object other$driverName = other.getDriverName();
        if (this$driverName == null ? other$driverName != null : !this$driverName.equals(other$driverName))
            return false;
        final Object this$driverVersion = this.getDriverVersion();
        final Object other$driverVersion = other.getDriverVersion();
        if (this$driverVersion == null ? other$driverVersion != null : !this$driverVersion.equals(other$driverVersion))
            return false;
        final Object this$driverJdbcType = this.getDriverJdbcType();
        final Object other$driverJdbcType = other.getDriverJdbcType();
        if (this$driverJdbcType == null ? other$driverJdbcType != null : !this$driverJdbcType.equals(other$driverJdbcType))
            return false;
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $databaseType = this.getDatabaseType();
        result = result * PRIME + ($databaseType == null ? 43 : $databaseType.hashCode());
        final Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        final Object $productVersion = this.getProductVersion();
        result = result * PRIME + ($productVersion == null ? 43 : $productVersion.hashCode());
        final Object $driverName = this.getDriverName();
        result = result * PRIME + ($driverName == null ? 43 : $driverName.hashCode());
        final Object $driverVersion = this.getDriverVersion();
        result = result * PRIME + ($driverVersion == null ? 43 : $driverVersion.hashCode());
        final Object $driverJdbcType = this.getDriverJdbcType();
        result = result * PRIME + ($driverJdbcType == null ? 43 : $driverJdbcType.hashCode());
        final Object $url = this.getUrl();
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        return result;
    }
}
