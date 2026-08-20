package com.mobilemoney.model;

import java.sql.Timestamp;

public class Envoi {

    private int idEnv;

    private String numEnvoyeur;
    private String nomEnvoyeur;

    private String numRecepteur;
    private String nomRecepteur;

    private double montant;

    private double fraisEnvoi;
    private double fraisRetrait;

    private boolean payerFraisRetrait;

    private String raison;

    private String referenceCode;

    private Timestamp dateEnvoi;

    public Envoi() {
    }

    public int getIdEnv() {
        return idEnv;
    }

    public void setIdEnv(int idEnv) {
        this.idEnv = idEnv;
    }

    public String getNumEnvoyeur() {
        return numEnvoyeur;
    }

    public void setNumEnvoyeur(String numEnvoyeur) {
        this.numEnvoyeur = numEnvoyeur;
    }

    public String getNomEnvoyeur() {
        return nomEnvoyeur;
    }

    public void setNomEnvoyeur(String nomEnvoyeur) {
        this.nomEnvoyeur = nomEnvoyeur;
    }

    public String getNumRecepteur() {
        return numRecepteur;
    }

    public void setNumRecepteur(String numRecepteur) {
        this.numRecepteur = numRecepteur;
    }

    public String getNomRecepteur() {
        return nomRecepteur;
    }

    public void setNomRecepteur(String nomRecepteur) {
        this.nomRecepteur = nomRecepteur;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getFraisEnvoi() {
        return fraisEnvoi;
    }

    public void setFraisEnvoi(double fraisEnvoi) {
        this.fraisEnvoi = fraisEnvoi;
    }

    public double getFraisRetrait() {
        return fraisRetrait;
    }

    public void setFraisRetrait(double fraisRetrait) {
        this.fraisRetrait = fraisRetrait;
    }

    public boolean isPayerFraisRetrait() {
        return payerFraisRetrait;
    }

    public void setPayerFraisRetrait(boolean payerFraisRetrait) {
        this.payerFraisRetrait = payerFraisRetrait;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Timestamp getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Timestamp dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
}