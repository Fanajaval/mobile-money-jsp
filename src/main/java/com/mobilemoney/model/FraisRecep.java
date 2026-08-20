package com.mobilemoney.model;

public class FraisRecep {

    private int idRec;

    private double montant1;

    private double montant2;

    private double fraisRec;

    // =========================
    // GETTERS & SETTERS
    // =========================

    public int getIdRec() {

        return idRec;
    }

    public void setIdRec(int idRec) {

        this.idRec = idRec;
    }

    public double getMontant1() {

        return montant1;
    }

    public void setMontant1(double montant1) {

        this.montant1 = montant1;
    }

    public double getMontant2() {

        return montant2;
    }

    public void setMontant2(double montant2) {

        this.montant2 = montant2;
    }

    public double getFraisRec() {

        return fraisRec;
    }

    public void setFraisRec(double fraisRec) {

        this.fraisRec = fraisRec;
    }
}