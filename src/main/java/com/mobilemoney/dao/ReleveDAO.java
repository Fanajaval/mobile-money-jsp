package com.mobilemoney.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mobilemoney.model.Operation;
import com.mobilemoney.util.DBConnection;

public class ReleveDAO {

    public List<Operation> getOperationsMensuelles(
            String numClient,
            int mois,
            int annee) {

        List<Operation> liste =
        new ArrayList<>();

        try {

            Connection con =
            DBConnection.getConnection();

            // =========================
            // ENVOIS
            // =========================
            String sqlEnvoi =
            "SELECT * FROM envoi " +
            "WHERE MONTH(date_envoi)=? " +
            "AND YEAR(date_envoi)=? " +
            "AND (" +
            "numEnvoyeur=? " +
            "OR numRecepteur=? )";

            PreparedStatement ps1 =
            con.prepareStatement(sqlEnvoi);

            ps1.setInt(1, mois);
            ps1.setInt(2, annee);
            ps1.setString(3, numClient);
            ps1.setString(4, numClient);

            ResultSet rs1 =
            ps1.executeQuery();

            while(rs1.next()) {

                Operation op =
                new Operation();

                op.setDateOperation(
                rs1.getTimestamp("date_envoi"));

                // SI CLIENT ENVOIE
                if(numClient.equals(
                   rs1.getString("numEnvoyeur"))) {

                    op.setRaison(
                    rs1.getString("raison"));

                    op.setDebit(
                    rs1.getDouble("montant"));

                    op.setCredit(0);

                }

                // SI CLIENT RECOIT
                else {

                    op.setRaison(
                    "Reception argent");

                    op.setDebit(0);

                    op.setCredit(
                    rs1.getDouble("montant"));
                }

                liste.add(op);
            }

            // =========================
            // RETRAITS
            // =========================
            String sqlRetrait =
            "SELECT * FROM retrait " +
            "WHERE MONTH(date_retrait)=? " +
            "AND YEAR(date_retrait)=? " +
            "AND numRecepteur=?";

            PreparedStatement ps2 =
            con.prepareStatement(sqlRetrait);

            ps2.setInt(1, mois);
            ps2.setInt(2, annee);
            ps2.setString(3, numClient);

            ResultSet rs2 =
            ps2.executeQuery();

            while(rs2.next()) {

                Operation op =
                new Operation();

                op.setDateOperation(
                rs2.getTimestamp("date_retrait"));

                op.setRaison(
                "Retrait Mobile Money");

                op.setDebit(
                rs2.getDouble("montant_retrait"));

                op.setCredit(0);

                liste.add(op);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return liste;
    }
}