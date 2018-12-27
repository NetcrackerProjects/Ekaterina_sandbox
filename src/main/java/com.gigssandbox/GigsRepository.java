package com.gigssandbox;

import com.gigssandbox.entities.Band;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.exceptions.AddingToDatabaseException;
import com.gigssandbox.exceptions.CheckingIfLocationIsBusyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class GigsRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    Collection<Gig> getGigsCollection(String... group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement(); PreparedStatement statementWithParam = connection.prepareStatement("SELECT * FROM gigs WHERE date >= CURRENT_DATE AND (headliner = ? OR support = ?)")) {
            ResultSet resultSet;
            if (group.length == 1) {
                statementWithParam.setString(1, group[0]);
                statementWithParam.setString(2, group[0]);
                resultSet = statementWithParam.executeQuery();
            } else {
                resultSet = statement.executeQuery("SELECT * FROM gigs WHERE date >= CURRENT_DATE");
            }
            Collection<Gig> gigs = new ArrayList<>();
            while (resultSet.next()) {
                gigs.add(Gig.builder()
                        .id(resultSet.getInt(1))
                        .headliner(resultSet.getString(2))
                        .support(resultSet.getString(3))
                        .date(resultSet.getTimestamp(4).toLocalDateTime())
                        .location(resultSet.getString(5))
                        .build());
            }
            return gigs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    void addNewGig(Gig gig) throws AddingToDatabaseException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement statementForId = connection.prepareStatement("SELECT MAX(id) + 1 from gigs"); PreparedStatement mainStatement = connection.prepareStatement("INSERT INTO gigs values (?,?,?,?,?)")) {
            int id = getNextAvailableId(statementForId);
            mainStatement.setInt(1, id);
            mainStatement.setString(2, gig.getHeadliner());
            mainStatement.setString(3, gig.getSupport());
            mainStatement.setTimestamp(4, Timestamp.valueOf(gig.getDate()));
            mainStatement.setString(5, gig.getLocation());
            mainStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AddingToDatabaseException();
        }
    }

    void addNewBand(Band band) throws AddingToDatabaseException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement statementForId = connection.prepareStatement("SELECT MAX(id) + 1 from bands"); PreparedStatement mainStatement = connection.prepareStatement("INSERT INTO bands values (?,?,?,?,?,?)")) {
            int id = getNextAvailableId(statementForId);
            mainStatement.setInt(1, id);
            mainStatement.setString(2, band.getName());
            mainStatement.setArray(3, connection.createArrayOf("varchar", band.getMembers().toArray()));
            mainStatement.setShort(4, band.getCreationYear());
            mainStatement.setString(5, band.getCity());
            mainStatement.setString(6, band.getGenre());
            mainStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AddingToDatabaseException();
        }
    }

    boolean checkIfPlaceIsAlreadyBusy(Gig gig) throws CheckingIfLocationIsBusyException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) from gigs where location = ? AND date = ?")) {
            statement.setString(1, gig.getLocation());
            statement.setTimestamp(2, Timestamp.valueOf(gig.getDate()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) == 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new CheckingIfLocationIsBusyException();
        }
        return false;
    }

    private int getNextAvailableId(PreparedStatement statementForId) throws SQLException {
        ResultSet resultSetWithId = statementForId.executeQuery();
        int id = 0;
        while (resultSetWithId.next()) {
            id = resultSetWithId.getInt(1);
        }
        return id;
    }
}