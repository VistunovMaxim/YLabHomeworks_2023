package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Component
public class DbInit {

    String insertQuery = "insert into words (obs_word) values (?)";
    @Autowired
    private DataSource dataSource;

    public void init(File file) throws SQLException {
        String ddl = "create table if not exists words (obs_word varchar);";
        DbUtil.applyDdl(ddl, dataSource);

        String trn = "truncate table words";
        DbUtil.applyDdl(trn, dataSource);

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            while (reader.ready()) {
                preparedStatement.setString(1, reader.readLine());
                preparedStatement.executeUpdate();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
