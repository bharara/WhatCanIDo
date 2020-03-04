/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webPage;

import Main.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Star
 */
public class addReview extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<!DOCTYPE html>");
            out.println("<html>" +
                "<head>" +
                "<style>" + GeneralFunctions.getCss() + "</style>" +
                "<title>" +
                "Add Review - WhatCanIDo" +
                "</title>" +
                "</head>" +
                "<body>" +
                "<a href=\"/WhatCanIDo/filter\" class=signUpButton>Filter</a>" +
                "<a href=\"/WhatCanIDo/\" class=signUpButton>Sign Out</a><br>" +
                "<h1>Add Review</h1><br>" +
                        
                "<div class=\"input\">" +
                "        <form action=\"user\" method=\"get\">" +
                "            <label>Item: </label><input type=\"text\" name=\"item\" required style=\"width: 100%;\" list=\"itemlist\">"
                        + "<br><label>Review: </label><input type=\"number\" name=\"review\" required style=\"width: 100%;\" min=1 max=5> <br>" +
                "            <input type=\"submit\" value=\"Submit\">");
                         
                        out.println("<datalist id=\"itemlist\">");
                        for (String s: Content.getContentNameList())
                            if (s!=null)
                                out.println("<option value=\""+s+"\">");
                out.println("</datalist>" +
                "        </form>" +
                "        </div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
