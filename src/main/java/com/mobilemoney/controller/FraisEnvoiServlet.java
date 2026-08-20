package com.mobilemoney.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobilemoney.dao.FraisEnvoiDAO;
import com.mobilemoney.model.FraisEnvoi;

@WebServlet("/frais-envoi")
public class FraisEnvoiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    FraisEnvoiDAO fraisDAO =
    new FraisEnvoiDAO();

    // =========================
    // GET
    // =========================
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action =
        request.getParameter("action");

        // =========================
        // DELETE
        // =========================
        if("delete".equals(action)) {

            int idEnv =
            Integer.parseInt(
            request.getParameter("idEnv"));

            boolean deleted =
            fraisDAO.deleteFrais(idEnv);

            if(deleted) {

                response.sendRedirect(
                "frais-envoi?success=deleted");

            } else {

                response.sendRedirect(
                "frais-envoi?error=delete");
            }

            return;
        }

        // =========================
        // LISTE
        // =========================
        List<FraisEnvoi> liste =
        fraisDAO.getAllFrais();

        request.setAttribute(
        "listeFrais",
        liste);

        request.getRequestDispatcher(
        "views/frais-envoi/list.jsp")
        .forward(request, response);
    }

    // =========================
    // POST
    // =========================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String action =
            request.getParameter("action");

            // =========================
            // UPDATE
            // =========================
            if("update".equals(action)) {

                int idEnv =
                Integer.parseInt(
                request.getParameter("idEnv"));

                double montant1 =
                Double.parseDouble(
                request.getParameter("montant1"));

                double montant2 =
                Double.parseDouble(
                request.getParameter("montant2"));

                double fraisEnv =
                Double.parseDouble(
                request.getParameter("fraisEnv"));

                // =========================
                // VALIDATION
                // =========================
                if(montant1 < 0
                   ||
                   montant2 < 0
                   ||
                   fraisEnv < 0
                   ||
                   montant2 < montant1) {

                    response.sendRedirect(
                    "frais-envoi?error=validation");

                    return;
                }

                FraisEnvoi f =
                new FraisEnvoi();

                f.setIdEnv(idEnv);

                f.setMontant1(montant1);

                f.setMontant2(montant2);

                f.setFraisEnv(fraisEnv);

                boolean success =
                fraisDAO.updateFrais(f);

                if(success) {

                    response.sendRedirect(
                    "frais-envoi?success=updated");

                } else {

                    response.sendRedirect(
                    "frais-envoi?error=update");
                }

                return;
            }

            // =========================
            // INSERT
            // =========================
            double montant1 =
            Double.parseDouble(
            request.getParameter("montant1"));

            double montant2 =
            Double.parseDouble(
            request.getParameter("montant2"));

            double fraisEnv =
            Double.parseDouble(
            request.getParameter("fraisEnv"));

            // =========================
            // VALIDATION
            // =========================
            if(montant1 < 0
               ||
               montant2 < 0
               ||
               fraisEnv < 0
               ||
               montant2 < montant1) {

                response.sendRedirect(
                "frais-envoi?error=validation");

                return;
            }

            FraisEnvoi f =
            new FraisEnvoi();

            f.setMontant1(montant1);

            f.setMontant2(montant2);

            f.setFraisEnv(fraisEnv);

            boolean success =
            fraisDAO.addFrais(f);

            if(success) {

                response.sendRedirect(
                "frais-envoi?success=added");

            } else {

                response.sendRedirect(
                "frais-envoi?error=add");
            }

        } catch(Exception e) {

            e.printStackTrace();

            response.sendRedirect(
            "frais-envoi?error=server");
        }
    }
}