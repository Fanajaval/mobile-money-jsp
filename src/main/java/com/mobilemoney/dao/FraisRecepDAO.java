package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mobilemoney.model.FraisRecep;
import com.mobilemoney.util.DBConnection;

public class FraisRecepDAO {

    // =========================
    // RECUPERER FRAIS RETRAIT
    // =========================
    public double getFraisRetrait(double montant) {

        double frais = 0;

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "SELECT frais_rec " +
            "FROM frais_recep " +
            "WHERE ? BETWEEN montant1 AND montant2";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setDouble(1, montant);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                frais =
                rs.getDouble("frais_rec");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return frais;
    }

    // =========================
    // LISTE
    // =========================
    public List<FraisRecep> getAllFrais() {

        List<FraisRecep> liste =
        new ArrayList<>();

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "SELECT * FROM frais_recep " +
            "ORDER BY idRec DESC";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            while(rs.next()) {

                FraisRecep f =
                new FraisRecep();

                f.setIdRec(
                rs.getInt("idRec"));

                f.setMontant1(
                rs.getDouble("montant1"));

                f.setMontant2(
                rs.getDouble("montant2"));

                f.setFraisRec(
                rs.getDouble("frais_rec"));

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
    public boolean insertFrais(FraisRecep f) {

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "INSERT INTO frais_recep(" +
            "montant1," +
            "montant2," +
            "frais_rec" +
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
            f.getFraisRec());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // UPDATE
    // =========================
    public boolean updateFrais(FraisRecep f) {

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "UPDATE frais_recep SET " +
            "montant1=?," +
            "montant2=?," +
            "frais_rec=? " +
            "WHERE idRec=?";

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
            f.getFraisRec());

            ps.setInt(
            4,
            f.getIdRec());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // DELETE
    // =========================
    public boolean deleteFrais(int idRec) {

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "DELETE FROM frais_recep " +
            "WHERE idRec=?";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setInt(1, idRec);

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // TOTAL REGLES
    // =========================
    public int getNombreRegles() {

        int total = 0;

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "SELECT COUNT(*) AS total " +
            "FROM frais_recep";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                total =
                rs.getInt("total");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return total;
    }
}