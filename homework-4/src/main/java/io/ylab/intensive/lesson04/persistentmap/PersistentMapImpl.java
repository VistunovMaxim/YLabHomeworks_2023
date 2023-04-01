package io.ylab.intensive.lesson04.persistentmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;
    private String name;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.name = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String containsKeySQL = "select key from persistent_map where (key = '" + key + "')";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(containsKeySQL);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List list = new ArrayList();
        String getKeysSQL = "select key from persistent_map where (map_name = '" + this.name + "')";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(getKeysSQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("key"));
            }
        }
        return list;
    }

    @Override
    public String get(String key) throws SQLException {
        String getSQL = "select value from persistent_map where " +
                "(map_name = '" + this.name + "' and key = '" + key + "')";
        String value;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(getSQL);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            value = rs.getString("value");
        }
        return value;
    }

    @Override
    public void remove(String key) throws SQLException {
        String removeSQL = "delete from persistent_map where (map_name = ? and key = ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(removeSQL)) {
            ps.setString(1, this.name);
            ps.setString(2, key);
            ps.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException { // update?? разобраться во всех функциях SQL
        if (this.containsKey(key)) {
            this.remove(key);
        }
        String putSQL = "insert into persistent_map (map_name, key, value) values (?, ?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(putSQL)) {
            ps.setString(1, this.name);
            ps.setString(2, key);
            ps.setString(3, value);
            ps.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String clearSQL = "delete from persistent_map where (map_name = ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(clearSQL)) {
            ps.setString(1, this.name);
            ps.executeUpdate();
        }
    }
}
