package com.mobilemoney.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobilemoney.dao.RetraitDAO;
import com.mobilemoney.model.Retrait;

@WebServlet("/retrait")
public class RetraitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    RetraitDAO retraitDAO =
    new RetraitDAO();

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

            int idRet =
            Integer.parseInt(
            request.getParameter("idRet"));

            boolean deleted =
            retraitDAO.deleteRetrait(idRet);

            if(deleted) {

                response.sendRedirect(
                "retrait?success=deleted");

            } else {

                response.sendRedirect(
                "retrait?error=delete");
            }

            return;
        }

        // =========================
        // RECHERCHE DATE
        // =========================
        String dateSearch =
        request.getParameter("dateSearch");

        List<Retrait> listeRetraits;

        if(dateSearch != null
           &&
           !dateSearch.isEmpty()) {

            listeRetraits =
            retraitDAO.searchByDate(
            dateSearch);

        } else {

            listeRetraits =
            retraitDAO.getAllRetraits();
        }

        // =========================
        // STATS
        // =========================
        request.setAttribute(
        "totalRetraits",
        retraitDAO.getTotalRetraits());

        request.setAttribute(
        "totalMontant",
        retraitDAO.getTotalMontantRetrait());

        request.setAttribute(
        "totalFrais",
        retraitDAO.getTotalFraisRetrait());

        // =========================
        // LISTE
        // =========================
        request.setAttribute(
        "listeRetraits",
        listeRetraits);

        request.getRequestDispatcher(
        "views/retrait/list.jsp")
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

                int idRet =
                Integer.parseInt(
                request.getParameter("idRet"));

                double fraisRetrait =
                Double.parseDouble(
                request.getParameter(
                "fraisRetrait"));

                Retrait r =
                new Retrait();

                r.setIdRet(idRet);

                r.setFraisRetrait(
                fraisRetrait);

                boolean success =
                retraitDAO.updateRetrait(r);

                if(success) {

                    response.sendRedirect(
                    "retrait?success=updated");

                } else {

                    response.sendRedirect(
                    "retrait?error=update");
                }

                return;
            }

            // =========================
            // EFFECTUER RETRAIT
            // =========================
            String referenceCode =
            request.getParameter(
            "referenceCode");

            boolean success =
            retraitDAO.effectuerRetrait(
            referenceCode);

            if(success) {

                response.sendRedirect(
                "retrait?success=added");

            } else {

                response.sendRedirect(
                "retrait?error=invalid");
            }

        } catch(Exception e) {

            e.printStackTrace();

            response.sendRedirect(
            "retrait?error=server");
        }
    }
}