package dao;

import db.DataBaseConnection;
import model.Specialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SpecializationDAO {

    public List<Specialization> getAllSpecializations() throws Exception {
        List<Specialization> list = new ArrayList<>();
        String sql = "SELECT specialization_id, name FROM specializations"; // poprawione

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                list.add(new Specialization(
                        rs.getInt("specialization_id"),
                        rs.getString("name")
                ));
            }
        }
        return list;
    }

    public int getSpecializationIdByName(String name) throws Exception {
        String sql = "SELECT specialization_id FROM specializations WHERE name = ?";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("specialization_id");
            }
        }
        throw new Exception("Nie znaleziono specjalizacji: " + name);
    }

    // NOWA METODA - dodaje specjalizację do bazy
    public void addSpecialization(String name) throws Exception {
        String sql = "INSERT INTO specializations (name) VALUES (?)";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
        }
    }
}
