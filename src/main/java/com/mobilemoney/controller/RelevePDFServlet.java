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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.BaseColor;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.dao.ReleveDAO;

import com.mobilemoney.model.Client;
import com.mobilemoney.model.Operation;

@WebServlet("/releve-pdf")
public class RelevePDFServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ClientDAO clientDAO =
    new ClientDAO();

    ReleveDAO releveDAO =
    new ReleveDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // =========================
            // PARAMETRES
            // =========================
            String numClient =
            request.getParameter("numClient");

            int mois =
            Integer.parseInt(
            request.getParameter("mois"));

            int annee =
            Integer.parseInt(
            request.getParameter("annee"));

            // =========================
            // CLIENT
            // =========================
            Client client =
            clientDAO.getClientByNum(
            numClient);

            // =========================
            // OPERATIONS
            // =========================
            List<Operation> operations =
            releveDAO.getOperationsMensuelles(
            numClient,
            mois,
            annee);

            // =========================
            // PDF
            // =========================
            response.setContentType(
            "application/pdf");

            // IMPORTANT :
            // INLINE = afficher navigateur
            response.setHeader(
            "Content-Disposition",
            "inline; filename=releve.pdf");

            Document document =
            new Document();

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
            12);

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
            document.add(
            new Paragraph(
            "Contact : "
            + client.getNumtel(),
            normalFont));

            document.add(
            new Paragraph(
            client.getNom(),
            boldFont));

            document.add(
            new Paragraph(
            client.getAge()
            + " ans",
            normalFont));

            document.add(
            new Paragraph(
            client.getSexe(),
            normalFont));

            document.add(
            new Paragraph(
            "Date : "
            + mois
            + "/"
            + annee,
            normalFont));

            document.add(
            new Paragraph(
            "Solde actuel : "
            + client.getSolde()
            + " Ariary",
            boldFont));

            document.add(
            new Paragraph(" "));

            // =========================
            // TABLEAU
            // =========================
            PdfPTable table =
            new PdfPTable(4);

            table.setWidthPercentage(100);

            table.setSpacingBefore(10);

            // =========================
            // HEADERS
            // =========================
            addHeader(table, "Date");
            addHeader(table, "Raison");
            addHeader(table, "Debit");
            addHeader(table, "Credit");

            // =========================
            // FORMAT DATE
            // =========================
            SimpleDateFormat sdf =
            new SimpleDateFormat(
            "dd/MM/yyyy");

            // =========================
            // TOTALS
            // =========================
            double totalDebit = 0;
            double totalCredit = 0;

            // =========================
            // DONNEES
            // =========================
            for(Operation op : operations) {

                table.addCell(
                sdf.format(
                op.getDateOperation()));

                table.addCell(
                op.getRaison());

                // DEBIT
                if(op.getDebit() > 0) {

                    table.addCell(
                    op.getDebit()
                    + " Ar");

                    totalDebit +=
                    op.getDebit();

                } else {

                    table.addCell("-");
                }

                // CREDIT
                if(op.getCredit() > 0) {

                    table.addCell(
                    op.getCredit()
                    + " Ar");

                    totalCredit +=
                    op.getCredit();

                } else {

                    table.addCell("-");
                }
            }

            document.add(table);

            document.add(
            new Paragraph(" "));

            // =========================
            // TOTAUX
            // =========================
            document.add(
            new Paragraph(
            "Total Debit : "
            + totalDebit
            + " Ar",
            boldFont));

            document.add(
            new Paragraph(
            "Total Credit : "
            + totalCredit
            + " Ar",
            boldFont));

            // =========================
            // FIN
            // =========================
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

        cell.setBackgroundColor(
        BaseColor.LIGHT_GRAY);

        cell.setHorizontalAlignment(
        Element.ALIGN_CENTER);

        table.addCell(cell);
    }
}