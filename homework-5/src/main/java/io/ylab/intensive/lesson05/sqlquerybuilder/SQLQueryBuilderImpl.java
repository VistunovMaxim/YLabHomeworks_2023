package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    private DatabaseMetaData databaseMetaData;

    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        this.databaseMetaData = connection.getMetaData();

    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        ResultSet myRs;
        List<String> list = getTables();

        if (list.contains(tableName)) {
            StringBuilder result = new StringBuilder("SELECT ");
            myRs = databaseMetaData.getColumns(null, null, tableName, null);
            while (myRs.next()) {
                result.append(myRs.getString("COLUMN_NAME") + ", ");
            }
            result.delete(result.length() - 2, result.length());
            result.append(" FROM " + tableName);

            return result.toString();
        } else {
            return null;
        }
    }

    @Override
    public List<String> getTables() throws SQLException {
        ResultSet myRs;
        List list = new ArrayList();
        myRs = databaseMetaData.getTables(null, null, null, new String[]{"SYSTEM TABLE"});
        while (myRs.next()) {
            list.add(myRs.getString("TABLE_NAME"));
        }
        myRs = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
        while (myRs.next()) {
            list.add(myRs.getString("TABLE_NAME"));
        }
        return list;
    }
}
