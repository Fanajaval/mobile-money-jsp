package com.mobilemoney.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.dao.EnvoiDAO;
import com.mobilemoney.dao.RetraitDAO;

import com.mobilemoney.model.Client;
import com.mobilemoney.model.Envoi;
import com.mobilemoney.model.Retrait;

@WebServlet("/releve")
public class ReleveServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ClientDAO clientDAO =
    new ClientDAO();

    EnvoiDAO envoiDAO =
    new EnvoiDAO();

    RetraitDAO retraitDAO =
    new RetraitDAO();

    // =========================
    // GET
    // =========================
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Client> listeClients =
        clientDAO.getAllClients();

        request.setAttribute(
        "listeClients",
        listeClients);

        request.getRequestDispatcher(
        "views/releve/index.jsp")
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

            String numtel =
            request.getParameter("numtel");

            String mois =
            request.getParameter("mois");

            // =========================
            // CLIENT
            // =========================
            Client client =
            clientDAO.getClientByNum(numtel);

            if(client == null) {

                response.sendRedirect(
                "releve?error=client");

                return;
            }

            // =========================
            // LISTE ENVOIS
            // =========================
            List<Envoi> listeEnvois =
            envoiDAO.getEnvoisClientParMois(
            numtel,
            mois);

            // =========================
            // LISTE RETRAITS
            // =========================
            List<Retrait> listeRetraits =
            retraitDAO.getRetraitsClientParMois(
            numtel,
            mois);

            // =========================
            // PDF
            // =========================
            response.setContentType(
            "application/pdf");

            response.setHeader(
            "Content-Disposition",
            "inline; filename=releve.pdf");

            Document document =
            new Document(PageSize.A4);

            PdfWriter.getInstance(
            document,
            response.getOutputStream());

            document.open();

            // =========================
            // FONTS
            // =========================
            Font titleFont =
            new Font(
            Font.FontFamily.HELVETICA,
            18,
            Font.BOLD);

            Font normalFont =
            new Font(
            Font.FontFamily.HELVETICA,
            12,
            Font.NORMAL);

            Font boldFont =
            new Font(
            Font.FontFamily.HELVETICA,
            12,
            Font.BOLD);

            // =========================
            // TITRE
            // =========================
            Paragraph title =
            new Paragraph(
            "RELEVE D'OPERATIONS",
            titleFont);

            title.setAlignment(
            Element.ALIGN_CENTER);

            document.add(title);

            document.add(
            new Paragraph(" "));

            // =========================
            // INFOS CLIENT
            // =========================
            document.add(new Paragraph(
            "Contact : "
            + client.getNumtel(),
            normalFont));

            document.add(new Paragraph(
            client.getNom(),
            boldFont));

            document.add(new Paragraph(
            client.getAge()
            + " ans",
            normalFont));

            document.add(new Paragraph(
            client.getSexe(),
            normalFont));

            document.add(new Paragraph(
            "Date : "
            + mois,
            normalFont));

            document.add(new Paragraph(
            "Solde actuel : "
            + client.getSolde()
            + " Ar",
            boldFont));

            document.add(
            new Paragraph(" "));

            // =========================
            // TABLEAU
            // =========================
            PdfPTable table =
            new PdfPTable(4);

            table.setWidthPercentage(100);

            table.setWidths(
            new int[]{3,4,3,3});

            // HEADER
            addHeader(table, "Date");
            addHeader(table, "Raison");
            addHeader(table, "Débit");
            addHeader(table, "Crédit");

            double totalDebit = 0;
            double totalCredit = 0;

            SimpleDateFormat sdf =
            new SimpleDateFormat(
            "dd/MM/yyyy");

            // =========================
            // ENVOIS
            // =========================
            for(Envoi e : listeEnvois) {

                table.addCell(
                sdf.format(
                e.getDateEnvoi()));

                table.addCell(
                e.getRaison());

                // SI ENVOYEUR
                if(numtel.equals(
                   e.getNumEnvoyeur())) {

                    table.addCell(
                    e.getMontant()
                    + " Ar");

                    table.addCell("");

                    totalDebit +=
                    e.getMontant();

                } else {

                    table.addCell("");

                    table.addCell(
                    e.getMontant()
                    + " Ar");

                    totalCredit +=
                    e.getMontant();
                }
            }

            // =========================
            // RETRAITS
            // =========================
            for(Retrait r : listeRetraits) {

                table.addCell(
                sdf.format(
                r.getDateRetrait()));

                table.addCell(
                "Retrait");

                table.addCell(
                r.getMontantRetrait()
                + " Ar");

                table.addCell("");

                totalDebit +=
                r.getMontantRetrait();
            }

            document.add(table);

            document.add(
            new Paragraph(" "));

            // =========================
            // TOTAUX
            // =========================
            document.add(new Paragraph(
            "Total Débit : "
            + totalDebit
            + " Ar",
            boldFont));

            document.add(new Paragraph(
            "Total Crédit : "
            + totalCredit
            + " Ar",
            boldFont));

            document.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // HEADER TABLE
    // =========================
    private void addHeader(
            PdfPTable table,
            String text) {

        PdfPCell cell =
        new PdfPCell(
        new Phrase(text));

        cell.setHorizontalAlignment(
        Element.ALIGN_CENTER);

        table.addCell(cell);
    }
}
