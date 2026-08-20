package com.mobilemoney.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.dao.EnvoiDAO;
import com.mobilemoney.dao.RetraitDAO;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ClientDAO clientDAO =
    new ClientDAO();

    EnvoiDAO envoiDAO =
    new EnvoiDAO();
    
    RetraitDAO retraitDAO =
    		new RetraitDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // TOTAL CLIENTS
        int totalClients =
        clientDAO.getNombreClients();

        // TOTAL ENVOIS
        int totalEnvois =
        envoiDAO.getNombreEnvois();

        // RECETTE
        double recette =
        envoiDAO.getTotalFrais();
        
     // TOTAL RETRAITS
        int totalRetraits =
        retraitDAO.getNombreRetraits();

        // ARGENT TRANSFERE
        double totalMontant =
        envoiDAO.getTotalMontant();

        request.setAttribute(
        "totalClients",
        totalClients);

        request.setAttribute(
        "totalEnvois",
        totalEnvois);

        request.setAttribute(
        "recette",
        recette);
        
        request.setAttribute(
        		"totalRetraits",
        		totalRetraits);

        request.setAttribute(
        		"totalMontant",
        		totalMontant);

        request.getRequestDispatcher(
        "index.jsp")
        .forward(request, response);
    }
}