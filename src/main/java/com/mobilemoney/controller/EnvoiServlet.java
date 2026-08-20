package com.mobilemoney.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.dao.EnvoiDAO;
import com.mobilemoney.dao.FraisEnvoiDAO;
import com.mobilemoney.dao.FraisRecepDAO;

import com.mobilemoney.model.Client;
import com.mobilemoney.model.Envoi;

import com.mobilemoney.util.EmailUtility;

@WebServlet("/envoi")
public class EnvoiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    EnvoiDAO envoiDAO =
    new EnvoiDAO();

    ClientDAO clientDAO =
    new ClientDAO();

    FraisEnvoiDAO fraisEnvoiDAO =
    new FraisEnvoiDAO();

    FraisRecepDAO fraisRecepDAO =
    new FraisRecepDAO();

    // LISTE
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
        String action =
        request.getParameter("action");

        // DELETE
        if("delete".equals(action)) {

            int idEnv =
            Integer.parseInt(
            request.getParameter("idEnv"));

            // RECUPERER ENVOI
            Envoi envoi =
            envoiDAO.getEnvoiById(idEnv);

            boolean deleted =
            envoiDAO.deleteEnvoi(idEnv);

            if(deleted) {

                // =========================
                // ENVOI MAIL
                // =========================
                if(envoi != null) {

                    Client envoyeur =
                    clientDAO.getClientByNum(
                    envoi.getNumEnvoyeur());

                    Client recepteur =
                    clientDAO.getClientByNum(
                    envoi.getNumRecepteur());

                    // MAIL ENVOYEUR
                    if(envoyeur != null
                       &&
                       envoyeur.getMail() != null) {

                        String sujet =
                        "Suppression de transfert";

                        String message =
                        "Bonjour "
                        + envoyeur.getNom()
                        + ",\n\n"
                        + "Votre transfert a été supprimé.\n\n"
                        + "Référence : "
                        + envoi.getReferenceCode()
                        + "\n"
                        + "Montant : "
                        + envoi.getMontant()
                        + " Ar";

                        EmailUtility.sendEmail(
                        envoyeur.getMail(),
                        sujet,
                        message);
                    }

                    // MAIL RECEPTEUR
                    if(recepteur != null
                       &&
                       recepteur.getMail() != null) {

                        String sujet =
                        "Annulation de réception";

                        String message =
                        "Bonjour "
                        + recepteur.getNom()
                        + ",\n\n"
                        + "Le transfert que vous deviez recevoir a été supprimé.\n\n"
                        + "Référence : "
                        + envoi.getReferenceCode()
                        + "\n"
                        + "Montant : "
                        + envoi.getMontant()
                        + " Ar";

                        EmailUtility.sendEmail(
                        recepteur.getMail(),
                        sujet,
                        message);
                    }
                }

                response.sendRedirect(
                "envoi?success=deleted");

            } else {

                response.sendRedirect(
                "envoi?error=delete");
            }

            return;
        }

        // RECHERCHE PAR DATE
        String dateSearch =
        request.getParameter("dateSearch");

        List<Envoi> listeEnvois;
        
        boolean ajax =
        		"true".equals(
        		request.getParameter("ajax"));

        if(dateSearch != null
           &&
           !dateSearch.isEmpty()) {

            listeEnvois =
            envoiDAO.searchByDate(
            dateSearch);

        } else {

            listeEnvois =
            envoiDAO.getAllEnvois();
        }

        // LISTE CLIENTS
        List<Client> listeClients =
        clientDAO.getAllClients();

        double totalMontant =
        		envoiDAO.getTotalMontant();

        		double totalFraisEnvoi =
        		envoiDAO.getTotalFraisEnvoi();

        		double totalFraisRetrait =
        		envoiDAO.getTotalFraisRetrait();

        		double recetteTotale =
        		totalFraisEnvoi
        		+ totalFraisRetrait;

        		int totalEnvois =
        		envoiDAO.getNombreEnvois();

        		request.setAttribute(
        		"totalMontant",
        		totalMontant);

        		request.setAttribute(
        		"totalFraisEnvoi",
        		totalFraisEnvoi);

        		request.setAttribute(
        		"totalFraisRetrait",
        		totalFraisRetrait);

        		request.setAttribute(
        		"recetteTotale",
        		recetteTotale);

        		request.setAttribute(
        		"totalEnvois",
        		totalEnvois);
        
        request.setAttribute(
        "listeEnvois",
        listeEnvois);
        
	     // ======================
	     // AJAX TABLE ONLY
	     // ======================
	
	     if(ajax) {
	
	         response.setContentType(
	         "text/html;charset=UTF-8");
	
	         for(Envoi e : listeEnvois) {
	
	             response.getWriter().println(
	
	                 "<tr>" +
	
	                 "<form action='envoi' method='post'>" +
	
	                 "<input type='hidden' name='action' value='update'>" +
	
	                 "<input type='hidden' name='idEnv' value='" + e.getIdEnv() + "'>" +
	
	                 "<td>" +
	                 "<span class='badge bg-dark'>" +
	                 e.getReferenceCode() +
	                 "</span>" +
	                 "</td>" +
	
	                 "<td>" +
	                 "<div class='fw-bold'>" +
	                 e.getNomEnvoyeur() +
	                 "</div>" +
	
	                 "<small class='text-muted'>" +
	                 e.getNumEnvoyeur() +
	                 "</small>" +
	                 "</td>" +
	
	                 "<td>" +
	                 "<div class='fw-bold'>" +
	                 e.getNomRecepteur() +
	                 "</div>" +
	
	                 "<small class='text-muted'>" +
	                 e.getNumRecepteur() +
	                 "</small>" +
	                 "</td>" +
	
	                 "<td>" +
	
	                 "<input type='number' " +
	                 "name='montant' " +
	                 "value='" + e.getMontant() + "' " +
	                 "class='form-control edit-field fw-bold text-success' " +
	                 "disabled>" +
	
	                 "</td>" +
	
	                 "<td>" +
	                 e.getFraisEnvoi() +
	                 " Ar" +
	                 "</td>" +
	
	                 "<td>" +
	
	                 "<input type='text' " +
	                 "name='raison' " +
	                 "value='" + e.getRaison() + "' " +
	                 "class='form-control edit-field' " +
	                 "disabled>" +
	
	                 "</td>" +
	
	                 "<td>" +
	                 e.getDateEnvoi() +
	                 "</td>" +
	
	                 "<td>" +
	
	                 "<div class='normal-actions'>" +
	
	                 "<button type='button' " +
	                 "class='btn btn-warning btn-sm' " +
	                 "onclick='enableEdit(this)'>" +
	
	                 "<i class='bi bi-pencil-square'></i>" +
	
	                 "</button>" +
	
	                 "<a href='envoi?action=delete&idEnv="
	                 + e.getIdEnv() +
	                 "' class='btn btn-danger btn-sm'>" +
	
	                 "<i class='bi bi-trash-fill'></i>" +
	
	                 "</a>" +
	
	                 "</div>" +
	
	                 "<div class='edit-actions d-none'>" +
	
	                 "<button class='btn btn-success btn-sm'>" +
	
	                 "<i class='bi bi-check-circle'></i>" +
	
	                 "</button>" +
	
	                 "<button type='button' " +
	                 "class='btn btn-secondary btn-sm' " +
	                 "onclick='cancelEdit()'>" +
	
	                 "<i class='bi bi-x-circle'></i>" +

                 "</button>" +

                 "</div>" +

                 "</td>" +

                 "</form>" +

                 "</tr>"
             );
         }

         return;
     }

        request.setAttribute(
        "listeClients",
        listeClients);

        request.getRequestDispatcher(
        "views/envoi/list.jsp")
        .forward(request, response);
    }

    // INSERT
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");

        try {
        	
        	String action =
        			request.getParameter("action");

        			// ======================
        			// UPDATE
        			// ======================
        	if("update".equals(action)) {

        	    int idEnv =
        	    Integer.parseInt(
        	    request.getParameter("idEnv"));

        	    double montant =
        	    Double.parseDouble(
        	    request.getParameter("montant"));

        	    String raison =
        	    request.getParameter("raison");

        	    // RECALCUL FRAIS
        	    double fraisEnvoi =
        	    fraisEnvoiDAO
        	    .getFraisEnvoi(montant);

        	    double fraisRetrait =
        	    fraisRecepDAO
        	    .getFraisRetrait(montant);

        	    Envoi e =
        	    new Envoi();

        	    e.setIdEnv(idEnv);

        	    e.setMontant(montant);

        	    e.setFraisEnvoi(fraisEnvoi);

        	    e.setFraisRetrait(fraisRetrait);

        	    e.setRaison(raison);

        	    boolean success =
        	    envoiDAO.updateEnvoi(e);

        	    if(success) {
        	    	
        	    	// RECUPERER ENVOI MODIFIE
        	    	Envoi ancienEnvoi =
        	    	envoiDAO.getEnvoiById(idEnv);

        	    	if(ancienEnvoi != null) {

        	    	    Client envoyeur =
        	    	    clientDAO.getClientByNum(
        	    	    ancienEnvoi.getNumEnvoyeur());

        	    	    Client recepteur =
        	    	    clientDAO.getClientByNum(
        	    	    ancienEnvoi.getNumRecepteur());

        	    	    // MAIL ENVOYEUR
        	    	    if(envoyeur != null
        	    	       &&
        	    	       envoyeur.getMail() != null) {

        	    	        String sujet =
        	    	        "Modification de transfert";

        	    	        String message =
        	    	        "Bonjour "
        	    	        + envoyeur.getNom()
        	    	        + ",\n\n"
        	    	        + "Votre transfert a été modifié.\n\n"
        	    	        + "Référence : "
        	    	        + ancienEnvoi.getReferenceCode()
        	    	        + "\n"
        	    	        + "Nouveau montant : "
        	    	        + montant
        	    	        + " Ar\n"
        	    	        + "Nouvelle raison : "
        	    	        + raison;

        	    	        EmailUtility.sendEmail(
        	    	        envoyeur.getMail(),
        	    	        sujet,
        	    	        message);
        	    	    }

        	    	    // MAIL RECEPTEUR
        	    	    if(recepteur != null
        	    	       &&
        	    	       recepteur.getMail() != null) {

        	    	        String sujet =
        	    	        "Modification de réception";

        	    	        String message =
        	    	        "Bonjour "
        	    	        + recepteur.getNom()
        	    	        + ",\n\n"
        	    	        + "Le transfert que vous allez recevoir a été modifié.\n\n"
        	    	        + "Référence : "
        	    	        + ancienEnvoi.getReferenceCode()
        	    	        + "\n"
        	    	        + "Nouveau montant : "
        	    	        + montant
        	    	        + " Ar";

        	    	        EmailUtility.sendEmail(
        	    	        recepteur.getMail(),
        	    	        sujet,
        	    	        message);
        	    	    }
        	    	}

        	        response.sendRedirect(
        	        "envoi?success=updated");

        	    } else {

        	        response.sendRedirect(
        	        "envoi?error=update");
        	    }

        	    return;
        	}

            String numEnvoyeur =
            request.getParameter(
            "numEnvoyeur");

            String numRecepteur =
            request.getParameter(
            "numRecepteur");

            double montant =
            Double.parseDouble(
            request.getParameter(
            "montant"));

            String raison =
            request.getParameter(
            "raison");

            boolean payerFraisRetrait =
            request.getParameter(
            "payerFraisRetrait")
            != null;

            // VALIDATION MONTANT
            if(montant <= 0) {

                response.sendRedirect(
                "envoi?error=montant");

                return;
            }

            // INTERDIRE AUTO ENVOI
            if(numEnvoyeur.equals(
               numRecepteur)) {

                response.sendRedirect(
                "envoi?error=self");

                return;
            }

            // CALCUL FRAIS
            double fraisEnvoi =
            fraisEnvoiDAO
            .getFraisEnvoi(
            montant);

            double fraisRetrait =
            fraisRecepDAO
            .getFraisRetrait(
            montant);

            // CODE REFERENCE
            String code =
            "MM-" +
            (100000 +
            new Random()
            .nextInt(900000));

            // OBJET
            Envoi e =
            new Envoi();

            e.setNumEnvoyeur(
            numEnvoyeur);

            e.setNumRecepteur(
            numRecepteur);

            e.setMontant(
            montant);

            e.setFraisEnvoi(
            fraisEnvoi);

            e.setFraisRetrait(
            fraisRetrait);

            e.setPayerFraisRetrait(
            payerFraisRetrait);

            e.setRaison(
            raison);

            e.setReferenceCode(
            code);

            // INSERT
            boolean success =
            envoiDAO.envoyerArgent(
            e);

            if(success) {

                // =========================
                // RECUPERATION CLIENTS
                // =========================

                Client envoyeur =
                clientDAO.getClientByNum(
                numEnvoyeur);

                Client recepteur =
                clientDAO.getClientByNum(
                numRecepteur);

	             // =========================
	             // EMAIL ENVOYEUR
	             // =========================
	
	             if(envoyeur != null
	                &&
	                envoyeur.getMail() != null
	                &&
	                !envoyeur.getMail().isEmpty()) {
	
	                 // RECHARGER LE CLIENT APRES MISE A JOUR SOLDE
	                 Client envoyeurUpdated =
	                 clientDAO.getClientByNum(
	                 numEnvoyeur);
	
	                 double soldeRestant =
	                 envoyeurUpdated.getSolde();
	
	                 // MONTANT TRANSFERE
	                 double montantTransfere =
	                 payerFraisRetrait
	                 ? (montant + fraisRetrait)
	                 : montant;
	
	                 String sujetEnv =
	                 "Confirmation Envoi MobileMoney";
	
	                 String messageEnv =
	                 "Bonjour "
	                 + envoyeur.getNom()
	                 + ",\n\n"
	
	                 + "Votre transfert a été effectué avec succès.\n\n"
	
	                 + "Montant transféré : "
	                 + montantTransfere
	                 + " Ar\n"
	
	                 + "Frais d'envoi : "
	                 + fraisEnvoi
	                 + " Ar\n"
	
	                 + "Référence : "
	                 + code
	                 + "\n\n"
	
	                 + "Solde restant : "
	                 + soldeRestant
	                 + " Ar\n\n"
	
	                 + "Merci d'utiliser MobileMoney.";
	
	                 EmailUtility.sendEmail(
	                 envoyeur.getMail(),
	                 sujetEnv,
	                 messageEnv);
	             }

                // =========================
                // EMAIL RECEPTEUR
                // =========================

		          if(recepteur != null
		             &&
		             recepteur.getMail() != null
		             &&
		             !recepteur.getMail().isEmpty()) {
	
		              // RECHARGER LE CLIENT APRES MISE A JOUR SOLDE
		              Client recepteurUpdated =
		              clientDAO.getClientByNum(
		              numRecepteur);
	
		              double nouveauSolde =
		              recepteurUpdated.getSolde();
	
		              // MONTANT RECU
		              double montantRecu =
		              payerFraisRetrait
		              ? (montant + fraisRetrait)
		              : montant;
	
		              String sujetRec =
		              "Réception d'argent MobileMoney";
	
		              String messageRec =
		              "Bonjour "
		              + recepteur.getNom()
		              + ",\n\n"
	
		              + "Vous avez reçu un transfert d'argent.\n\n"
	
		              + "Montant reçu : "
		              + montantRecu
		              + " Ar\n"
	
		              + "Code de retrait : "
		              + code
		              + "\n\n"
	
		              + "Nouveau solde : "
		              + nouveauSolde
		              + " Ar\n\n"
	
		              + "Merci d'utiliser MobileMoney.";
	
		              EmailUtility.sendEmail(
		              recepteur.getMail(),
		              sujetRec,
		              messageRec);
		          }

                response.sendRedirect(
                "envoi?success=added");

            } else {

                response.sendRedirect(
                "envoi?error=solde");
            }

        } catch(Exception e) {

            e.printStackTrace();

            response.sendRedirect(
            "envoi?error=server");
        }
    }
}