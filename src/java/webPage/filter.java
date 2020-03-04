/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webPage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Main.*;

/**
 *
 * @author Star
 */
public class filter extends HttpServlet {

    public static Person user = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>" +
                "<head>" +
                "<style>" + GeneralFunctions.getCss() + "</style>" +
                "<title>" +
                "Filter Form - WhatCanIDo" +
                "</title>" +
                "</head>" +
                "<body>" +
                "<a href='/WhatCanIDo/addReview' class=signUpButton>Add Review</a>" +
                "<a href='/WhatCanIDo/' class=signUpButton>Sign Out</a><br>" +
                "<h1>What Do You Want To Do?</h1><br>" +
                        
                "<div class=outerlayer style='width: 30%';>" +
                "<form action='user' method='post'>" +
                "	<div class=innerlayer>" +
                "           What can I do for : <input type='number' name='money' value=100 style='width: 30%'> $$ <br>" +
                "		</div>" +
                "		<div class=innerlayer>" +
                "			What can I do in : <input type='number' name=hours style='width: 20%' value=2><input type='number' name=minutes style='width: 20%' value=30>" +
                "			<br>" +
                "		</div>" +
                "		<div class=innerlayer>" +
                "			I would like:- <input type='checkbox' name='movie' value='true' checked='checked'> Movies" +
                "			<input type='checkbox' name='book' value='true' checked='checked'> Books" +
                "			<input type='checkbox' name='game' value=true checked='checked'> Games<br>" +
                "		</div>" +
                "			<input type='submit' name='submit' value='Submit' checked='checked'>" +
                "	</form>" +
                "</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getParameter("name")!=null)
                user = new Person(Person.getRowNumber(request.getParameter("name").toLowerCase()));
        }
        catch (Exception e) {       }
        processRequest(request, response);
    }
    
    public String getServletInfo() {
        return "Short description";
    }
}
