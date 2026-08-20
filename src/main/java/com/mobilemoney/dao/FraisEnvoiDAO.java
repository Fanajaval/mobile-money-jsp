package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mobilemoney.model.FraisEnvoi;
import com.mobilemoney.util.DBConnection;

public class FraisEnvoiDAO {

    // =========================
    // RECUPERER FRAIS
    // =========================
    public double getFraisEnvoi(double montant) {

        double frais = 0;

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "SELECT frais_env " +
            "FROM frais_envoi " +
            "WHERE ? BETWEEN montant1 AND montant2";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setDouble(1, montant);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                frais =
                rs.getDouble("frais_env");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return frais;
    }

    // =========================
    // LISTE
    // =========================
    public List<FraisEnvoi> getAllFrais() {

        List<FraisEnvoi> liste =
        new ArrayList<>();

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "SELECT * FROM frais_envoi " +
            "ORDER BY montant1 ASC";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            while(rs.next()) {

                FraisEnvoi f =
                new FraisEnvoi();

                f.setIdEnv(
                rs.getInt("idEnv"));

                f.setMontant1(
                rs.getDouble("montant1"));

                f.setMontant2(
                rs.getDouble("montant2"));

                f.setFraisEnv(
                rs.getDouble("frais_env"));

                liste.add(f);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return liste;
    }

    // =========================
    // INSERT
    // =========================
    public boolean addFrais(
    FraisEnvoi f) {

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "INSERT INTO frais_envoi(" +
            "montant1," +
            "montant2," +
            "frais_env" +
            ") VALUES(?,?,?)";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setDouble(
            1,
            f.getMontant1());

            ps.setDouble(
            2,
            f.getMontant2());

            ps.setDouble(
            3,
            f.getFraisEnv());

            return
            ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // UPDATE
    // =========================
    public boolean updateFrais(
    FraisEnvoi f) {

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "UPDATE frais_envoi SET " +
            "montant1=?," +
            "montant2=?," +
            "frais_env=? " +
            "WHERE idEnv=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setDouble(
            1,
            f.getMontant1());

            ps.setDouble(
            2,
            f.getMontant2());

            ps.setDouble(
            3,
            f.getFraisEnv());

            ps.setInt(
            4,
            f.getIdEnv());

            return
            ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // DELETE
    // =========================
    public boolean deleteFrais(
    int idEnv) {

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "DELETE FROM frais_envoi " +
            "WHERE idEnv=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setInt(1, idEnv);

            return
            ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}