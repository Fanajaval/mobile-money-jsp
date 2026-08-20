package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mobilemoney.model.Envoi;
import com.mobilemoney.util.DBConnection;

public class EnvoiDAO {

	// =========================
	// LISTE ENVOIS
	// =========================
	public List<Envoi> getAllEnvois() {

	    List<Envoi> liste =
	    new ArrayList<>();

	    try {

	        Connection con =
	        DBConnection.getConnection();

	        String sql =
	        "SELECT e.*, " +
	        "ce.nom AS nom_envoyeur, " +
	        "cr.nom AS nom_recepteur " +
	        "FROM envoi e " +
	        "LEFT JOIN client ce " +
	        "ON e.numEnvoyeur = ce.numtel " +
	        "LEFT JOIN client cr " +
	        "ON e.numRecepteur = cr.numtel " +
	        "ORDER BY e.idEnv DESC";

	        PreparedStatement ps =
	        con.prepareStatement(sql);

	        ResultSet rs =
	        ps.executeQuery();

	        while(rs.next()) {

	            Envoi e =
	            new Envoi();

	            e.setIdEnv(
	            rs.getInt("idEnv"));

	            e.setNumEnvoyeur(
	            rs.getString("numEnvoyeur"));

	            e.setNomEnvoyeur(
	            rs.getString("nom_envoyeur"));

	            e.setNumRecepteur(
	            rs.getString("numRecepteur"));

	            e.setNomRecepteur(
	            rs.getString("nom_recepteur"));

	            e.setMontant(
	            rs.getDouble("montant"));

	            e.setFraisEnvoi(
	            rs.getDouble("frais_envoi"));

	            e.setFraisRetrait(
	            rs.getDouble("frais_retrait"));

	            e.setPayerFraisRetrait(
	            rs.getBoolean("payer_frais_retrait"));

	            e.setRaison(
	            rs.getString("raison"));

	            e.setReferenceCode(
	            rs.getString("reference_code"));

	            e.setDateEnvoi(
	            rs.getTimestamp("date_envoi"));

	            liste.add(e);
	        }

	    } catch(Exception ex) {

	        ex.printStackTrace();
	    }

	    return liste;
	}

// =========================
// ENVOYER ARGENT
// =========================
public boolean envoyerArgent(Envoi e) {

    boolean success = false;

    try {

        Connection con =
        DBConnection.getConnection();

        con.setAutoCommit(false);

        // VERIFIER ENVOYEUR
        String checkSql =
        "SELECT solde FROM client WHERE numtel=?";

        PreparedStatement envPs =
        con.prepareStatement(checkSql);

        envPs.setString(
        1,
        e.getNumEnvoyeur());

        ResultSet envRs =
        envPs.executeQuery();

        if(!envRs.next()) {

            return false;
        }

        // VERIFIER RECEPTEUR
        PreparedStatement recPs =
        con.prepareStatement(checkSql);

        recPs.setString(
        1,
        e.getNumRecepteur());

        ResultSet recRs =
        recPs.executeQuery();

        if(!recRs.next()) {

            return false;
        }

        // INTERDIT MEME CLIENT
        if(e.getNumEnvoyeur()
        .equals(e.getNumRecepteur())) {

            return false;
        }

        // SOLDE ENVOYEUR
        double solde =
        envRs.getDouble("solde");

        double totalDebit =
        e.getMontant()
        + e.getFraisEnvoi();

        // SI ENVOYEUR PAYE AUSSI LE RETRAIT
        if(e.isPayerFraisRetrait()) {

            totalDebit +=
            e.getFraisRetrait();
        }

        // SOLDE INSUFFISANT
        if(solde < totalDebit) {

            return false;
        }

        // DEBIT ENVOYEUR
        String debitSql =
        "UPDATE client " +
        "SET solde = solde - ? " +
        "WHERE numtel=?";

        PreparedStatement debitPs =
        con.prepareStatement(debitSql);

        debitPs.setDouble(
        1,
        totalDebit);

        debitPs.setString(
        2,
        e.getNumEnvoyeur());

        debitPs.executeUpdate();

        // MONTANT RECEPTEUR
        double montantRecepteur =
        e.getMontant();

        if(e.isPayerFraisRetrait()) {

            montantRecepteur +=
            e.getFraisRetrait();
        }

        // CREDIT RECEPTEUR
        String creditSql =
        "UPDATE client " +
        "SET solde = solde + ? " +
        "WHERE numtel=?";

        PreparedStatement creditPs =
        con.prepareStatement(creditSql);

        creditPs.setDouble(
        1,
        montantRecepteur);

        creditPs.setString(
        2,
        e.getNumRecepteur());

        creditPs.executeUpdate();

        // INSERT ENVOI
        String insertSql =
        "INSERT INTO envoi(" +
        "numEnvoyeur," +
        "numRecepteur," +
        "montant," +
        "frais_envoi," +
        "frais_retrait," +
        "payer_frais_retrait," +
        "raison," +
        "reference_code" +
        ") VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement insertPs =
        con.prepareStatement(insertSql);

        insertPs.setString(
        1,
        e.getNumEnvoyeur());

        insertPs.setString(
        2,
        e.getNumRecepteur());

        insertPs.setDouble(
        3,
        e.getMontant());

        insertPs.setDouble(
        4,
        e.getFraisEnvoi());

        insertPs.setDouble(
        5,
        e.getFraisRetrait());

        insertPs.setBoolean(
        6,
        e.isPayerFraisRetrait());

        insertPs.setString(
        7,
        e.getRaison());

        insertPs.setString(
        8,
        e.getReferenceCode());

        success =
        insertPs.executeUpdate() > 0;

        if(success) {

            con.commit();

        } else {

            con.rollback();
        }

    } catch(Exception ex) {

        ex.printStackTrace();
    }

    return success;
}

// =========================
// DELETE ENVOI
// =========================
public boolean deleteEnvoi(int idEnv) {

    boolean success = false;

    try {

        Connection con =
        DBConnection.getConnection();

        con.setAutoCommit(false);

        String getSql =
        "SELECT * FROM envoi WHERE idEnv=?";

        PreparedStatement getPs =
        con.prepareStatement(getSql);

        getPs.setInt(1, idEnv);

        ResultSet rs =
        getPs.executeQuery();

        if(rs.next()) {

            String envoyeur =
            rs.getString("numEnvoyeur");

            String recepteur =
            rs.getString("numRecepteur");

            double montant =
            rs.getDouble("montant");

            double fraisEnvoi =
            rs.getDouble("frais_envoi");

            double fraisRetrait =
            rs.getDouble("frais_retrait");

            boolean payerRetrait =
            rs.getBoolean("payer_frais_retrait");

            // REMBOURSEMENT ENVOYEUR
            double remboursement =
            montant + fraisEnvoi;

            if(payerRetrait) {

                remboursement +=
                fraisRetrait;
            }

            // RETRAIT RECEPTEUR
            double retraitRecepteur =
            montant;

            if(payerRetrait) {

                retraitRecepteur +=
                fraisRetrait;
            }

            // REMBOURSER ENVOYEUR
            String rembSql =
            "UPDATE client " +
            "SET solde = solde + ? " +
            "WHERE numtel=?";

            PreparedStatement rembPs =
            con.prepareStatement(rembSql);

            rembPs.setDouble(
            1,
            remboursement);

            rembPs.setString(
            2,
            envoyeur);

            rembPs.executeUpdate();

            // RETIRER RECEPTEUR
            String retraitSql =
            "UPDATE client " +
            "SET solde = solde - ? " +
            "WHERE numtel=?";

            PreparedStatement retraitPs =
            con.prepareStatement(retraitSql);

            retraitPs.setDouble(
            1,
            retraitRecepteur);

            retraitPs.setString(
            2,
            recepteur);

            retraitPs.executeUpdate();

            // DELETE
            String deleteSql =
            "DELETE FROM envoi WHERE idEnv=?";

            PreparedStatement deletePs =
            con.prepareStatement(deleteSql);

            deletePs.setInt(
            1,
            idEnv);

            success =
            deletePs.executeUpdate() > 0;

            if(success) {

                con.commit();

            } else {

                con.rollback();
            }
        }

    } catch(Exception ex) {

        ex.printStackTrace();
    }

    return success;
}

//=========================
//RECHERCHE PAR DATE
//=========================
public List<Envoi> searchByDate(String date) {

 List<Envoi> liste =
 new ArrayList<>();

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT e.*, " +
     "ce.nom AS nom_envoyeur, " +
     "cr.nom AS nom_recepteur " +
     "FROM envoi e " +
     "LEFT JOIN client ce " +
     "ON e.numEnvoyeur = ce.numtel " +
     "LEFT JOIN client cr " +
     "ON e.numRecepteur = cr.numtel " +
     "WHERE DATE(e.date_envoi)=? " +
     "ORDER BY e.idEnv DESC";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ps.setString(1, date);

     ResultSet rs =
     ps.executeQuery();

     while(rs.next()) {

         Envoi e =
         new Envoi();

         e.setIdEnv(
         rs.getInt("idEnv"));

         e.setNumEnvoyeur(
         rs.getString("numEnvoyeur"));

         e.setNomEnvoyeur(
         rs.getString("nom_envoyeur"));

         e.setNumRecepteur(
         rs.getString("numRecepteur"));

         e.setNomRecepteur(
         rs.getString("nom_recepteur"));

         e.setMontant(
         rs.getDouble("montant"));

         e.setFraisEnvoi(
         rs.getDouble("frais_envoi"));

         e.setFraisRetrait(
         rs.getDouble("frais_retrait"));

         e.setPayerFraisRetrait(
         rs.getBoolean("payer_frais_retrait"));

         e.setRaison(
         rs.getString("raison"));

         e.setReferenceCode(
         rs.getString("reference_code"));

         e.setDateEnvoi(
         rs.getTimestamp("date_envoi"));

         liste.add(e);
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return liste;
}

//=========================
//RECUPERER UN ENVOI
//=========================
public Envoi getEnvoiById(int idEnv) {

 Envoi e = null;

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT * FROM envoi " +
     "WHERE idEnv=?";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ps.setInt(1, idEnv);

     ResultSet rs =
     ps.executeQuery();

     if(rs.next()) {

         e = new Envoi();

         e.setIdEnv(
         rs.getInt("idEnv"));

         e.setNumEnvoyeur(
         rs.getString("numEnvoyeur"));

         e.setNumRecepteur(
         rs.getString("numRecepteur"));

         e.setMontant(
         rs.getDouble("montant"));

         e.setFraisEnvoi(
         rs.getDouble("frais_envoi"));

         e.setFraisRetrait(
         rs.getDouble("frais_retrait"));

         e.setPayerFraisRetrait(
         rs.getBoolean("payer_frais_retrait"));

         e.setRaison(
         rs.getString("raison"));

         e.setReferenceCode(
         rs.getString("reference_code"));

         e.setDateEnvoi(
         rs.getTimestamp("date_envoi"));
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return e;
}

//=========================
//UPDATE ENVOI COMPLET
//=========================
public boolean updateEnvoi(Envoi e) {

 boolean success = false;

 try {

     Connection con =
     DBConnection.getConnection();

     con.setAutoCommit(false);

     // ======================
     // ANCIEN ENVOI
     // ======================
     String oldSql =
     "SELECT * FROM envoi WHERE idEnv=?";

     PreparedStatement oldPs =
     con.prepareStatement(oldSql);

     oldPs.setInt(
     1,
     e.getIdEnv());

     ResultSet rs =
     oldPs.executeQuery();

     if(rs.next()) {

         String envoyeur =
         rs.getString("numEnvoyeur");

         String recepteur =
         rs.getString("numRecepteur");

         double oldMontant =
         rs.getDouble("montant");

         double oldFraisEnvoi =
         rs.getDouble("frais_envoi");

         double oldFraisRetrait =
         rs.getDouble("frais_retrait");

         boolean oldPayerRetrait =
         rs.getBoolean("payer_frais_retrait");

         // ======================
         // REMBOURSEMENT
         // ======================
         double remboursement =
         oldMontant + oldFraisEnvoi;

         if(oldPayerRetrait) {

             remboursement +=
             oldFraisRetrait;
         }

         double retraitRecepteur =
         oldMontant;

         if(oldPayerRetrait) {

             retraitRecepteur +=
             oldFraisRetrait;
         }

         // REMBOURSER ENVOYEUR
         String rembSql =
         "UPDATE client " +
         "SET solde = solde + ? " +
         "WHERE numtel=?";

         PreparedStatement rembPs =
         con.prepareStatement(rembSql);

         rembPs.setDouble(
         1,
         remboursement);

         rembPs.setString(
         2,
         envoyeur);

         rembPs.executeUpdate();

         // RETIRER RECEPTEUR
         String retraitSql =
         "UPDATE client " +
         "SET solde = solde - ? " +
         "WHERE numtel=?";

         PreparedStatement retraitPs =
         con.prepareStatement(retraitSql);

         retraitPs.setDouble(
         1,
         retraitRecepteur);

         retraitPs.setString(
         2,
         recepteur);

         retraitPs.executeUpdate();

	      // ======================
	      // VERIFIER SOLDE ENVOYEUR
	      // ======================
	
	      String soldeSql =
	      "SELECT solde FROM client WHERE numtel=?";
	
	      PreparedStatement soldePs =
	      con.prepareStatement(soldeSql);
	
	      soldePs.setString(
	      1,
	      envoyeur);
	
	      ResultSet soldeRs =
	      soldePs.executeQuery();
	
	      double soldeActuel = 0;
	
	      if(soldeRs.next()) {
	
	          soldeActuel =
	          soldeRs.getDouble("solde");
	      }
	
	      // ======================
	      // NOUVEAUX CALCULS
	      // ======================
	
	      double totalDebit =
	      e.getMontant()
	      + e.getFraisEnvoi();
	
	      // SI ENVOYEUR PAYE LE RETRAIT
	      if(e.isPayerFraisRetrait()) {
	
	          totalDebit +=
	          e.getFraisRetrait();
	      }
	
	      // MONTANT RECU
	      double montantRecepteur =
	      e.getMontant();
	
	      // SI ENVOYEUR PAYE LE RETRAIT
	      if(e.isPayerFraisRetrait()) {
	
	          montantRecepteur +=
	          e.getFraisRetrait();
	      }
	
	      // ======================
	      // VERIFIER SOLDE
	      // ======================
	
	      if(soldeActuel < totalDebit) {
	
	          con.rollback();
	
	          return false;
	      }
	
	      // ======================
	      // DEBIT ENVOYEUR
	      // ======================
	
	      String debitSql =
	      "UPDATE client " +
	      "SET solde = solde - ? " +
	      "WHERE numtel=?";
	
	      PreparedStatement debitPs =
	      con.prepareStatement(debitSql);
	
	      debitPs.setDouble(
	      1,
	      totalDebit);
	
	      debitPs.setString(
	      2,
	      envoyeur);
	
	      debitPs.executeUpdate();
	
	      // ======================
	      // CREDIT RECEPTEUR
	      // ======================
	
	      String creditSql =
	      "UPDATE client " +
	      "SET solde = solde + ? " +
	      "WHERE numtel=?";
	
	      PreparedStatement creditPs =
	      con.prepareStatement(creditSql);
	
	      creditPs.setDouble(
	      1,
	      montantRecepteur);
	
	      creditPs.setString(
	      2,
	      recepteur);
	
	      creditPs.executeUpdate();

         // ======================
         // UPDATE TABLE ENVOI
         // ======================
	      String updateSql =
	    		  "UPDATE envoi SET " +
	    		  "montant=?, " +
	    		  "frais_envoi=?, " +
	    		  "frais_retrait=?, " +
	    		  "payer_frais_retrait=?, " +
	    		  "raison=? " +
	    		  "WHERE idEnv=?";

         PreparedStatement updatePs =
         con.prepareStatement(updateSql);

         updatePs.setDouble(
        		 1,
        		 e.getMontant());

        		 updatePs.setDouble(
        		 2,
        		 e.getFraisEnvoi());

        		 updatePs.setDouble(
        		 3,
        		 e.getFraisRetrait());

        		 updatePs.setBoolean(
        		 4,
        		 e.isPayerFraisRetrait());

        		 updatePs.setString(
        		 5,
        		 e.getRaison());

        		 updatePs.setInt(
        		 6,
        		 e.getIdEnv());

         success =
         updatePs.executeUpdate() > 0;

         if(success) {

             con.commit();

         } else {

             con.rollback();
         }
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return success;
}

//=========================
//TOTAL MONTANT ENVOYE
//=========================
public double getTotalMontant() {

 double total = 0;

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT SUM(montant) AS total FROM envoi";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ResultSet rs =
     ps.executeQuery();

     if(rs.next()) {

         total =
         rs.getDouble("total");
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return total;
}

//=========================
//TOTAL FRAIS
//=========================
public double getTotalFrais() {

 double total = 0;

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT SUM(frais_envoi + frais_retrait) AS total FROM envoi";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ResultSet rs =
     ps.executeQuery();

     if(rs.next()) {

         total =
         rs.getDouble("total");
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return total;
}

//=========================
//TOTAL FRAIS ENVOI
//=========================
public double getTotalFraisEnvoi() {

 double total = 0;

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT SUM(frais_envoi) AS total FROM envoi";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ResultSet rs =
     ps.executeQuery();

     if(rs.next()) {

         total =
         rs.getDouble("total");
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return total;
}

//=========================
//TOTAL FRAIS RETRAIT
//=========================
public double getTotalFraisRetrait() {

 double total = 0;

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT SUM(frais_retrait) AS total FROM envoi";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ResultSet rs =
     ps.executeQuery();

     if(rs.next()) {

         total =
         rs.getDouble("total");
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return total;
}

//=========================
//TOTAL ENVOIS
//=========================
public int getNombreEnvois() {

 int total = 0;

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT COUNT(*) AS total FROM envoi";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ResultSet rs =
     ps.executeQuery();

     if(rs.next()) {

         total =
         rs.getInt("total");
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return total;
}

//=========================
//RELEVE ENVOIS MENSUELS
//=========================
public List<Envoi> getEnvoisMensuels(
     String numClient,
     int mois,
     int annee) {

 List<Envoi> liste =
 new ArrayList<>();

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT e.*, " +
     "ce.nom AS nom_envoyeur, " +
     "cr.nom AS nom_recepteur " +
     "FROM envoi e " +
     "LEFT JOIN client ce " +
     "ON e.numEnvoyeur = ce.numtel " +
     "LEFT JOIN client cr " +
     "ON e.numRecepteur = cr.numtel " +
     "WHERE (" +
     "e.numEnvoyeur=? " +
     "OR e.numRecepteur=? ) " +
     "AND MONTH(e.date_envoi)=? " +
     "AND YEAR(e.date_envoi)=? " +
     "ORDER BY e.date_envoi DESC";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ps.setString(1, numClient);
     ps.setString(2, numClient);
     ps.setInt(3, mois);
     ps.setInt(4, annee);

     ResultSet rs =
     ps.executeQuery();

     while(rs.next()) {

         Envoi e =
         new Envoi();

         e.setReferenceCode(
         rs.getString("reference_code"));

         e.setNomEnvoyeur(
         rs.getString("nom_envoyeur"));

         e.setNomRecepteur(
         rs.getString("nom_recepteur"));

         e.setMontant(
         rs.getDouble("montant"));

         e.setFraisEnvoi(
         rs.getDouble("frais_envoi"));

         e.setDateEnvoi(
         rs.getTimestamp("date_envoi"));

         e.setRaison(
         rs.getString("raison"));

         liste.add(e);
     }

 } catch(Exception e) {

     e.printStackTrace();
 }

 return liste;
}

//=========================
//RELEVE ENVOIS CLIENT PAR MOIS
//=========================
public List<Envoi> getEnvoisClientParMois(
  String numtel,
  String mois) {

 List<Envoi> liste =
 new ArrayList<>();

 try {

     Connection con =
     DBConnection.getConnection();

     String sql =
     "SELECT e.*, " +
     "ce.nom AS nom_envoyeur, " +
     "cr.nom AS nom_recepteur " +
     "FROM envoi e " +
     "LEFT JOIN client ce " +
     "ON e.numEnvoyeur = ce.numtel " +
     "LEFT JOIN client cr " +
     "ON e.numRecepteur = cr.numtel " +
     "WHERE (" +
     "e.numEnvoyeur=? " +
     "OR e.numRecepteur=? " +
     ") " +
     "AND DATE_FORMAT(e.date_envoi,'%Y-%m')=? " +
     "ORDER BY e.date_envoi ASC";

     PreparedStatement ps =
     con.prepareStatement(sql);

     ps.setString(1, numtel);
     ps.setString(2, numtel);
     ps.setString(3, mois);

     ResultSet rs =
     ps.executeQuery();

     while(rs.next()) {

         Envoi e =
         new Envoi();

         e.setIdEnv(
         rs.getInt("idEnv"));

         e.setNumEnvoyeur(
         rs.getString("numEnvoyeur"));

         e.setNomEnvoyeur(
         rs.getString("nom_envoyeur"));

         e.setNumRecepteur(
         rs.getString("numRecepteur"));

         e.setNomRecepteur(
         rs.getString("nom_recepteur"));

         e.setMontant(
         rs.getDouble("montant"));

         e.setFraisEnvoi(
         rs.getDouble("frais_envoi"));

         e.setFraisRetrait(
         rs.getDouble("frais_retrait"));

         e.setRaison(
         rs.getString("raison"));

         e.setReferenceCode(
         rs.getString("reference_code"));

         e.setDateEnvoi(
         rs.getTimestamp("date_envoi"));

         liste.add(e);
     }

 } catch(Exception ex) {

     ex.printStackTrace();
 }

 return liste;
}
}
