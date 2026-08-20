package com.mobilemoney.model;

public class FraisEnvoi {

    private int idEnv;

    private double montant1;

    private double montant2;

    private double fraisEnv;

    // =========================
    // GETTER & SETTER ID
    // =========================
    public int getIdEnv() {

        return idEnv;
    }

    public void setIdEnv(int idEnv) {

        this.idEnv = idEnv;
    }

    // =========================
    // GETTER & SETTER MONTANT1
    // =========================
    public double getMontant1() {

        return montant1;
    }

    public void setMontant1(double montant1) {

        this.montant1 = montant1;
    }

    // =========================
    // GETTER & SETTER MONTANT2
    // =========================
    public double getMontant2() {

        return montant2;
    }

    public void setMontant2(double montant2) {

        this.montant2 = montant2;
    }

    // =========================
    // GETTER & SETTER FRAIS
    // =========================
    public double getFraisEnv() {

        return fraisEnv;
    }

    public void setFraisEnv(double fraisEnv) {

        this.fraisEnv = fraisEnv;
    }
}