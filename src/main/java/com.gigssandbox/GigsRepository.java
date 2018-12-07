package com.gigssandbox;

import com.gigssandbox.entities.Band;
import com.gigssandbox.entities.Gig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GigsRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgrespass1488";

    public List<Gig> getGigsList(String... group) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement();PreparedStatement statementWithParam = connection.prepareStatement("SELECT * FROM gigs WHERE date >= CURRENT_DATE AND (headliner = ? OR support = ?)")) {
            ResultSet resultSet;
            if (group.length == 1) {
                statementWithParam.setString(1, group[0]);
                statementWithParam.setString(2, group[0]);
                resultSet = statementWithParam.executeQuery();
            }
            else {
                resultSet = statement.executeQuery("SELECT * FROM gigs WHERE date >= CURRENT_DATE");
            }
            List<Gig> gigs = new ArrayList<>();
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Band> getAllBandsList() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bands");
            List<Band> bands = new ArrayList<>();
            while (resultSet.next()) {
                String[] membersArray = (String[]) resultSet.getArray(3).getArray();
                bands.add(Band.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .members(new ArrayList<>(Arrays.asList(membersArray)))
                        .createYear(resultSet.getShort(4))
                        .city(resultSet.getString(5))
                        .genre(resultSet.getString(6))
                        .build()

                );
            }
            return bands;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    //вопрос - можно ли оставить здесь try with resources и пробрасывать исключение дальше?
    public void addNewGig(Gig gig) throws SQLException {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement statementForId = connection.prepareStatement("SELECT MAX(id) + 1 from gigs"); PreparedStatement mainStatement = connection.prepareStatement("INSERT INTO gigs values (?,?,?,?,?)")) {
            int id = getNextAvailableId(statementForId);
            mainStatement.setInt(1, id);
            mainStatement.setString(2, gig.getHeadliner());
            mainStatement.setString(3, gig.getSupport());
            mainStatement.setTimestamp(4, Timestamp.valueOf(gig.getDate()));
            mainStatement.setString(5, gig.getLocation());
            mainStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void addNewBand(Band band) throws SQLException {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement statementForId = connection.prepareStatement("SELECT MAX(id) + 1 from bands"); PreparedStatement mainStatement = connection.prepareStatement("INSERT INTO bands values (?,?,?,?,?,?)")) {
            int id = getNextAvailableId(statementForId);
            mainStatement.setInt(1, id);
            mainStatement.setString(2, band.getName());
            mainStatement.setArray(3, connection.createArrayOf("varchar", band.getMembers().toArray()));
            mainStatement.setShort(4, band.getCreateYear());
            mainStatement.setString(5, band.getCity());
            mainStatement.setString(6,band.getGenre());
            mainStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
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
