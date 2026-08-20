package com.mobilemoney.model;

import java.sql.Timestamp;

public class Retrait {

    private int idRet;

    private String referenceCode;

    private String numRecepteur;

    private double montantRetrait;

    private double fraisRetrait;

    private Timestamp dateRetrait;

    // =========================
    // POUR AFFICHAGE JSP
    // =========================
    private String nomClient;

    private String numClient;

    // =========================
    // GETTERS / SETTERS
    // =========================

    public int getIdRet() {

        return idRet;
    }

    public void setIdRet(int idRet) {

        this.idRet = idRet;
    }

    public String getReferenceCode() {

        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {

        this.referenceCode = referenceCode;
    }

    public String getNumRecepteur() {

        return numRecepteur;
    }

    public void setNumRecepteur(String numRecepteur) {

        this.numRecepteur = numRecepteur;
    }

    public double getMontantRetrait() {

        return montantRetrait;
    }

    public void setMontantRetrait(double montantRetrait) {

        this.montantRetrait = montantRetrait;
    }

    public double getFraisRetrait() {

        return fraisRetrait;
    }

    public void setFraisRetrait(double fraisRetrait) {

        this.fraisRetrait = fraisRetrait;
    }

    public Timestamp getDateRetrait() {

        return dateRetrait;
    }

    public void setDateRetrait(Timestamp dateRetrait) {

        this.dateRetrait = dateRetrait;
    }

    // =========================
    // NOM CLIENT
    // =========================

    public String getNomClient() {

        return nomClient;
    }

    public void setNomClient(String nomClient) {

        this.nomClient = nomClient;
    }

    // =========================
    // NUM CLIENT
    // =========================

    public String getNumClient() {

        return numClient;
    }

    public void setNumClient(String numClient) {

        this.numClient = numClient;
    }
}