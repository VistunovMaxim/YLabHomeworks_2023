package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@ComponentScan
public class DbApiImpl implements DbApi {

    private Connection connectionDB;

    private final String deleteSQL =
            "delete from person where (person_id = ?)";
    private final String insertSQL =
            "insert into person (person_id, first_name, last_name, middle_name) values (?, ?, ?, ?);";
    private final String updateSQL =
            "update person set first_name = ?, last_name = ?, middle_name = ? where person_id = ?";

    @Autowired
    public DbApiImpl(DataSource dataSource) {
        try {
            this.connectionDB = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertPerson(Person person) {
        if (containsSQL(person.getId())) {
            try (PreparedStatement psUpdate = connectionDB.prepareStatement(updateSQL)) {
                psUpdate.setString(1, person.getName());
                psUpdate.setString(2, person.getLastName());
                psUpdate.setString(3, person.getMiddleName());
                psUpdate.setLong(4, person.getId());
                psUpdate.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement psSave = connectionDB.prepareStatement(insertSQL)) {
                psSave.setLong(1, person.getId());
                psSave.setString(2, person.getName());
                psSave.setString(3, person.getLastName());
                psSave.setString(4, person.getMiddleName());
                psSave.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deletePerson(Long personId) {
        if (containsSQL(personId)) {
            try (PreparedStatement psDelete = connectionDB.prepareStatement(deleteSQL)) {
                psDelete.setLong(1, personId);
                psDelete.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Была попытка удаления, но данные не найдены");
        }
    }

    private boolean containsSQL(Long personId) {
        String containsSQL = "select * from person where (person_id = " + personId + ")";
        try (PreparedStatement psContains = connectionDB.prepareStatement(containsSQL);
             ResultSet rs = psContains.executeQuery()) {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
