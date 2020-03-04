package webPage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Main.*;

public class user extends HttpServlet {
    
    static int time = 150;
    static int money = 100;
    static boolean isMovie = true, isBook = true, isGame = true;
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
        response.setContentType("text/html;charset=UTF-8");

        Person username = filter.user;
        Content[] favContent = username.getFavContent(isMovie, isBook, isGame);
        Content[][] temp = username.seprateBestToBudget(favContent, time, money);
        Content[] budgetedContent = temp[1];
        Content[] nonBudgedtedContent = temp[0];
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Recommendation - WhatCanIDo</title>");
            out.println("<style>" + GeneralFunctions.getCss() + "</style>");
            out.println("</head>");
            out.println("<body>"
                    + "<a href='/WhatCanIDo/addReview' class=signUpButton>Add Review</a>"
                    + "<a href='/WhatCanIDo/filter' class=signUpButton>Filter Again</a><a href='/WhatCanIDo/' class=signUpButton>Sign Out</a><br>");
            out.println("<h1>" + username.name.toUpperCase() + " - We recommend you</h1>");
            
            out.println("<h2> Items That Match your budget</h2>");
            out.println("<div class=outerlayer>");
            for (Content c : budgetedContent) {
                if (c!=null) {
                    out.println("<div class=innerlayer>");
                    for (String s: c.webPrint())
                        out.println(s+"<br>");
                    out.println("</div>");
                    out.println("<hr>");
                }
            }
            out.println("</div>");
            
            
            out.println("<h2> Items you may like</h2>");
            out.println("<div class=outerlayer>");
            for (Content c : nonBudgedtedContent) {
                if (c!=null) {
                    out.println("<div class=innerlayer>");
                    for (String s: c.webPrint())
                        out.println(s+"<br>");
                    out.println("</div>");
                    out.println("<hr>");
                }
            }
            
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    
    boolean trueTrue (char c) { return true;}
    boolean trueTrue (String val) {
        if (val==null)
            return false;
        return true;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int review = Integer.parseInt(request.getParameter("review"));
        String item = request.getParameter("item");
        filter.user.addReview(review, Content.stringToContent(item));
        
        processRequest(request, response);
    }
   
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        time = Integer.parseInt(request.getParameter("hours")) * 60 + Integer.parseInt(request.getParameter("minutes"));
        money = Integer.parseInt(request.getParameter("money"));
        isMovie = trueTrue(request.getParameter("movie"));
        isBook = trueTrue(request.getParameter("book"));
        isGame = trueTrue(request.getParameter("game"));
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
