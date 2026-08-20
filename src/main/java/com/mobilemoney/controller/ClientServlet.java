package com.mobilemoney.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.model.Client;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {

    ClientDAO dao = new ClientDAO();

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

            String numtel =
                    request.getParameter("numtel");

            dao.deleteClient(numtel);

            response.sendRedirect("client?success=deleted");

            return;
        }

     // DELETE ALL
        if("deleteAll".equals(action)) {

            dao.deleteAllClients();

            response.sendRedirect(
            "client?success=allDeleted");

            return;
        }
        
        // SEARCH
        String search =
                request.getParameter("search");

        List<Client> liste;

        if(search != null && !search.isEmpty()) {

            liste = dao.searchClient(search);

        } else {

            liste = dao.getAllClients();
        }

        request.setAttribute("listeClients", liste);

        request.getRequestDispatcher(
                "views/client/list.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");

        String action =
                request.getParameter("action");

        // UPDATE
        if("update".equals(action)) {

            Client c = new Client();

            c.setNumtel(
                    request.getParameter("numtel"));

            c.setNom(
                    request.getParameter("nom"));

            c.setSexe(
                    request.getParameter("sexe"));

            c.setAge(
                    Integer.parseInt(
                            request.getParameter("age")));

            c.setSolde(
                    Integer.parseInt(
                            request.getParameter("solde")));

            c.setMail(
                    request.getParameter("mail"));

            dao.updateClient(c);

            response.sendRedirect("client?success=updated");

            return;
        }

     // INSERT

        Client c = new Client();

        c.setNumtel(
                request.getParameter("numtel"));

        c.setNom(
                request.getParameter("nom"));

        c.setSexe(
                request.getParameter("sexe"));

        c.setAge(
                Integer.parseInt(
                        request.getParameter("age")));

        c.setSolde(
                Integer.parseInt(
                        request.getParameter("solde")));

        c.setMail(
                request.getParameter("mail"));

        boolean inserted =
                dao.insertClient(c);

        if(inserted) {

            response.sendRedirect(
            "client?success=added");

        } else {

            response.sendRedirect(
            "client?error=exists");
        }
    }
}