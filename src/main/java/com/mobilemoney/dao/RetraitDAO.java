package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mobilemoney.model.Retrait;
import com.mobilemoney.util.DBConnection;

public class RetraitDAO {

    // =========================
    // EFFECTUER RETRAIT
    // =========================
    public boolean effectuerRetrait(String referenceCode) {

        Connection conn = null;

        try {

            conn =
            DBConnection.getConnection();

            // =========================
            // VERIFIER ENVOI
            // =========================
            String sqlEnvoi =
            "SELECT * FROM envoi WHERE reference_code=?";

            PreparedStatement psEnvoi =
            conn.prepareStatement(sqlEnvoi);

            psEnvoi.setString(1, referenceCode);

            ResultSet rsEnvoi =
            psEnvoi.executeQuery();

            if(!rsEnvoi.next()) {

                return false;
            }

            String numRecepteur =
            rsEnvoi.getString("numRecepteur");

            double montant =
            rsEnvoi.getDouble("montant");

            double fraisRetrait =
            rsEnvoi.getDouble("frais_retrait");

            boolean payerFrais =
            rsEnvoi.getBoolean(
            "payer_frais_retrait");

            // =========================
            // VERIFIER SI DEJA RETIRE
            // =========================
            String checkRetrait =
            "SELECT * FROM retrait WHERE reference_code=?";

            PreparedStatement psCheck =
            conn.prepareStatement(checkRetrait);

            psCheck.setString(1, referenceCode);

            ResultSet rsCheck =
            psCheck.executeQuery();

            if(rsCheck.next()) {

                return false;
            }

            // =========================
            // INSERT RETRAIT
            // =========================
            String insert =
            "INSERT INTO retrait(reference_code,numRecepteur,montant_retrait,frais_retrait) VALUES(?,?,?,?)";

            PreparedStatement psInsert =
            conn.prepareStatement(insert);

            psInsert.setString(1, referenceCode);

            psInsert.setString(2, numRecepteur);

            psInsert.setDouble(3, montant);

            psInsert.setDouble(4, fraisRetrait);

            int ok =
            psInsert.executeUpdate();

            if(ok > 0) {

                // =========================
                // RETIRER SOLDE CLIENT
                // =========================
                double montantFinal;

                if(payerFrais) {

                    montantFinal = montant;

                } else {

                    montantFinal =
                    montant - fraisRetrait;
                }

                String updateClient =
                "UPDATE client SET solde = solde - ? WHERE numtel=?";

                PreparedStatement psClient =
                conn.prepareStatement(updateClient);

                psClient.setDouble(1, montantFinal);

                psClient.setString(2, numRecepteur);

                psClient.executeUpdate();

                return true;
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // LISTE RETRAITS
    // =========================
    public List<Retrait> getAllRetraits() {

        List<Retrait> liste =
        new ArrayList<>();

        try {

            Connection conn =
            DBConnection.getConnection();

            String sql =
            "SELECT r.*, c.nom " +
            "FROM retrait r " +
            "JOIN client c ON r.numRecepteur = c.numtel " +
            "ORDER BY r.idRet DESC";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            while(rs.next()) {

                Retrait r =
                new Retrait();

                r.setIdRet(
                rs.getInt("idRet"));

                r.setReferenceCode(
                rs.getString("reference_code"));

                r.setNumRecepteur(
                rs.getString("numRecepteur"));

                r.setMontantRetrait(
                rs.getDouble("montant_retrait"));

                r.setFraisRetrait(
                rs.getDouble("frais_retrait"));

                r.setDateRetrait(
                rs.getTimestamp("date_retrait"));

                // =========================
                // POUR JSP
                // =========================
                r.setNomClient(
                rs.getString("nom"));

                r.setNumClient(
                rs.getString("numRecepteur"));

                liste.add(r);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return liste;
    }

    // =========================
    // DELETE RETRAIT
    // =========================
    public boolean deleteRetrait(int idRet) {

        try {

            Connection conn =
            DBConnection.getConnection();

            // =========================
            // RECUP RETRAIT
            // =========================
            String sqlSelect =
            "SELECT * FROM retrait WHERE idRet=?";

            PreparedStatement psSelect =
            conn.prepareStatement(sqlSelect);

            psSelect.setInt(1, idRet);

            ResultSet rs =
            psSelect.executeQuery();

            if(rs.next()) {

                String numRecepteur =
                rs.getString("numRecepteur");

                double montant =
                rs.getDouble("montant_retrait");

                double frais =
                rs.getDouble("frais_retrait");

                // =========================
                // RESTAURER SOLDE
                // =========================
                String update =
                "UPDATE client SET solde = solde + (? - ?) WHERE numtel=?";

                PreparedStatement psUpdate =
                conn.prepareStatement(update);

                psUpdate.setDouble(1, montant);

                psUpdate.setDouble(2, frais);

                psUpdate.setString(3, numRecepteur);

                psUpdate.executeUpdate();

                // =========================
                // DELETE
                // =========================
                String delete =
                "DELETE FROM retrait WHERE idRet=?";

                PreparedStatement psDelete =
                conn.prepareStatement(delete);

                psDelete.setInt(1, idRet);

                return psDelete.executeUpdate() > 0;
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // UPDATE RETRAIT
    // =========================
    public boolean updateRetrait(Retrait r) {

        try {

            Connection conn =
            DBConnection.getConnection();

            String sql =
            "UPDATE retrait SET frais_retrait=? WHERE idRet=?";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setDouble(
            1,
            r.getFraisRetrait());

            ps.setInt(
            2,
            r.getIdRet());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // RECHERCHE DATE
    // =========================
    public List<Retrait> searchByDate(String date) {

        List<Retrait> liste =
        new ArrayList<>();

        try {

            Connection conn =
            DBConnection.getConnection();

            String sql =
            "SELECT r.*, c.nom " +
            "FROM retrait r " +
            "JOIN client c ON r.numRecepteur = c.numtel " +
            "WHERE DATE(r.date_retrait)=? " +
            "ORDER BY r.idRet DESC";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ps.setString(1, date);

            ResultSet rs =
            ps.executeQuery();

            while(rs.next()) {

                Retrait r =
                new Retrait();

                r.setIdRet(
                rs.getInt("idRet"));

                r.setReferenceCode(
                rs.getString("reference_code"));

                r.setNumRecepteur(
                rs.getString("numRecepteur"));

                r.setMontantRetrait(
                rs.getDouble("montant_retrait"));

                r.setFraisRetrait(
                rs.getDouble("frais_retrait"));

                r.setDateRetrait(
                rs.getTimestamp("date_retrait"));

                // =========================
                // POUR JSP
                // =========================
                r.setNomClient(
                rs.getString("nom"));

                r.setNumClient(
                rs.getString("numRecepteur"));

                liste.add(r);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return liste;
    }

    // =========================
    // TOTAL RETRAITS
    // =========================
    public int getTotalRetraits() {

        try {

            Connection conn =
            DBConnection.getConnection();

            String sql =
            "SELECT COUNT(*) FROM retrait";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                return rs.getInt(1);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // =========================
    // TOTAL MONTANT
    // =========================
    public double getTotalMontantRetrait() {

        try {

            Connection conn =
            DBConnection.getConnection();

            String sql =
            "SELECT SUM(montant_retrait) FROM retrait";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble(1);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // =========================
    // TOTAL FRAIS
    // =========================
    public double getTotalFraisRetrait() {

        try {

            Connection conn =
            DBConnection.getConnection();

            String sql =
            "SELECT SUM(frais_retrait) FROM retrait";

            PreparedStatement ps =
            conn.prepareStatement(sql);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble(1);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    
 // =========================
 // RELEVE RETRAITS MENSUELS
 // =========================
 public List<Retrait> getRetraitsMensuels(
         String numClient,
         int mois,
         int annee) {

     List<Retrait> liste =
     new ArrayList<>();

     try {

         Connection conn =
         DBConnection.getConnection();

         String sql =
         "SELECT * FROM retrait " +
         "WHERE numRecepteur=? " +
         "AND MONTH(date_retrait)=? " +
         "AND YEAR(date_retrait)=? " +
         "ORDER BY date_retrait DESC";

         PreparedStatement ps =
         conn.prepareStatement(sql);

         ps.setString(1, numClient);
         ps.setInt(2, mois);
         ps.setInt(3, annee);

         ResultSet rs =
         ps.executeQuery();

         while(rs.next()) {

             Retrait r =
             new Retrait();

             r.setReferenceCode(
             rs.getString("reference_code"));

             r.setMontantRetrait(
             rs.getDouble("montant_retrait"));

             r.setFraisRetrait(
             rs.getDouble("frais_retrait"));

             r.setDateRetrait(
             rs.getTimestamp("date_retrait"));

             liste.add(r);
         }

     } catch(Exception e) {

         e.printStackTrace();
     }

     return liste;
 }
 
//=========================
//RELEVE RETRAITS CLIENT PAR MOIS
//=========================
public List<Retrait> getRetraitsClientParMois(
      String numtel,
      String mois) {

  List<Retrait> liste =
  new ArrayList<>();

  try {

      Connection conn =
      DBConnection.getConnection();

      String sql =
      "SELECT * FROM retrait " +
      "WHERE numRecepteur=? " +
      "AND DATE_FORMAT(date_retrait,'%Y-%m')=? " +
      "ORDER BY date_retrait ASC";

      PreparedStatement ps =
      conn.prepareStatement(sql);

      ps.setString(1, numtel);
      ps.setString(2, mois);

      ResultSet rs =
      ps.executeQuery();

      while(rs.next()) {

          Retrait r =
          new Retrait();

          r.setIdRet(
          rs.getInt("idRet"));

          r.setReferenceCode(
          rs.getString("reference_code"));

          r.setMontantRetrait(
          rs.getDouble("montant_retrait"));

          r.setFraisRetrait(
          rs.getDouble("frais_retrait"));

          r.setDateRetrait(
          rs.getTimestamp("date_retrait"));

          liste.add(r);
      }

  } catch(Exception e) {

      e.printStackTrace();
  }

  return liste;
}

//=========================
//TOTAL RETRAITS
//=========================
public int getNombreRetraits() {

 int total = 0;

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT COUNT(*) AS total FROM retrait";

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