package com.mobilemoney.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobilemoney.dao.FraisRecepDAO;
import com.mobilemoney.model.FraisRecep;

@WebServlet("/frais-recep")
public class FraisRecepServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    FraisRecepDAO fraisDAO =
    new FraisRecepDAO();

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

            int idRec =
            Integer.parseInt(
            request.getParameter("idRec"));

            boolean deleted =
            fraisDAO.deleteFrais(idRec);

            if(deleted) {

                response.sendRedirect(
                "frais-recep?success=deleted");

            } else {

                response.sendRedirect(
                "frais-recep?error=delete");
            }

            return;
        }

        // =========================
        // LISTE
        // =========================
        List<FraisRecep> liste =
        fraisDAO.getAllFrais();

        request.setAttribute(
        "listeFrais",
        liste);

        // =========================
        // STATS
        // =========================
        request.setAttribute(
        "totalRegles",
        fraisDAO.getNombreRegles());

        request.getRequestDispatcher(
        "views/frais-recep/list.jsp")
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

                int idRec =
                Integer.parseInt(
                request.getParameter("idRec"));

                double montant1 =
                Double.parseDouble(
                request.getParameter("montant1"));

                double montant2 =
                Double.parseDouble(
                request.getParameter("montant2"));

                double fraisRec =
                Double.parseDouble(
                request.getParameter("fraisRec"));

                // =========================
                // VALIDATION
                // =========================
                if(montant1 < 0
                   ||
                   montant2 < 0
                   ||
                   fraisRec < 0
                   ||
                   montant2 < montant1) {

                    response.sendRedirect(
                    "frais-recep?error=validation");

                    return;
                }

                FraisRecep f =
                new FraisRecep();

                f.setIdRec(idRec);

                f.setMontant1(montant1);

                f.setMontant2(montant2);

                f.setFraisRec(fraisRec);

                boolean success =
                fraisDAO.updateFrais(f);

                if(success) {

                    response.sendRedirect(
                    "frais-recep?success=updated");

                } else {

                    response.sendRedirect(
                    "frais-recep?error=update");
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

            double fraisRec =
            Double.parseDouble(
            request.getParameter("fraisRec"));

            // =========================
            // VALIDATION
            // =========================
            if(montant1 < 0
               ||
               montant2 < 0
               ||
               fraisRec < 0
               ||
               montant2 < montant1) {

                response.sendRedirect(
                "frais-recep?error=validation");

                return;
            }

            FraisRecep f =
            new FraisRecep();

            f.setMontant1(montant1);

            f.setMontant2(montant2);

            f.setFraisRec(fraisRec);

            boolean success =
            fraisDAO.insertFrais(f);

            if(success) {

                response.sendRedirect(
                "frais-recep?success=added");

            } else {

                response.sendRedirect(
                "frais-recep?error=add");
            }

        } catch(Exception e) {

            e.printStackTrace();

            response.sendRedirect(
            "frais-recep?error=server");
        }
    }
}