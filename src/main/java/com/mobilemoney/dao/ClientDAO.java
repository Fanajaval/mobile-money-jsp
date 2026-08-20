package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mobilemoney.model.Client;
import com.mobilemoney.util.DBConnection;

public class ClientDAO {

    Connection con;

    // AJOUT CLIENT
    public boolean insertClient(Client c) {

        boolean inserted = false;

        try {

            Connection con = DBConnection.getConnection();

            // VERIFIER SI EXISTE
            String checkSql =
            "SELECT * FROM client WHERE numtel=?";

            PreparedStatement checkPs =
            con.prepareStatement(checkSql);

            checkPs.setString(1, c.getNumtel());

            ResultSet rs =
            checkPs.executeQuery();

            // SI EXISTE
            if(rs.next()) {

                return false;
            }

            // INSERT
            String sql =
            "INSERT INTO client(numtel,nom,sexe,age,solde,mail) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.setString(1, c.getNumtel());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getSexe());
            ps.setInt(4, c.getAge());
            ps.setDouble(5, c.getSolde());
            ps.setString(6, c.getMail());

            inserted = ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return inserted;
    }

    // LISTE CLIENTS
    public List<Client> getAllClients() {

        List<Client> liste = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM client";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Client c = new Client();

                c.setNumtel(rs.getString("numtel"));
                c.setNom(rs.getString("nom"));
                c.setSexe(rs.getString("sexe"));
                c.setAge(rs.getInt("age"));
                c.setSolde(rs.getInt("solde"));
                c.setMail(rs.getString("mail"));

                liste.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }

    // SUPPRIMER
    public void deleteClient(String numtel) {

        try {

            con = DBConnection.getConnection();

            String sql =
                    "DELETE FROM client WHERE numtel=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, numtel);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RECHERCHE PAR NUMERO
    public Client getClientByNum(String numtel) {

        Client c = null;

        try {

            con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM client WHERE numtel=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, numtel);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                c = new Client();

                c.setNumtel(rs.getString("numtel"));
                c.setNom(rs.getString("nom"));
                c.setSexe(rs.getString("sexe"));
                c.setAge(rs.getInt("age"));
                c.setSolde(rs.getInt("solde"));
                c.setMail(rs.getString("mail"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    // MODIFIER CLIENT
    public void updateClient(Client c) {

        try {

            con = DBConnection.getConnection();

            String sql =
                    "UPDATE client SET nom=?, sexe=?, age=?, solde=?, mail=? WHERE numtel=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, c.getNom());
            ps.setString(2, c.getSexe());
            ps.setInt(3, c.getAge());
            ps.setInt(4, c.getSolde());
            ps.setString(5, c.getMail());
            ps.setString(6, c.getNumtel());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RECHERCHE LIKE
    public List<Client> searchClient(String keyword) {

        List<Client> liste = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM client WHERE nom LIKE ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Client c = new Client();

                c.setNumtel(rs.getString("numtel"));
                c.setNom(rs.getString("nom"));
                c.setSexe(rs.getString("sexe"));
                c.setAge(rs.getInt("age"));
                c.setSolde(rs.getInt("solde"));
                c.setMail(rs.getString("mail"));

                liste.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }
    
 // DELETE ALL
    public void deleteAllClients() {

        try {

            Connection con =
            DBConnection.getConnection();

            String sql =
            "DELETE FROM client";

            PreparedStatement ps =
            con.prepareStatement(sql);

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
    
 // =========================
 // TOTAL CLIENTS
 // =========================
 public int getNombreClients() {

     int total = 0;

     try {

         Connection con =
         DBConnection.getConnection();

         String sql =
         "SELECT COUNT(*) AS total FROM client";

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