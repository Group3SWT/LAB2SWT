/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.*;


public class admin_add extends HttpServlet {

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
            out.println("<head>");
            out.println("<title>Servlet admin_add</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet admin_add at " + request.getContextPath() + "</h1>");
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
        TypeDao dao = new TypeDao();
        ArrayList<Type> listType = dao.getAllType();
        request.setAttribute("list", listType);

        request.getRequestDispatcher("view/admin/add.jsp").forward(request, response);
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
        TypeDao daot = new TypeDao();
        ArrayList<Type> listType = daot.getAllType();
        request.setAttribute("list", listType);
        try {
            ProductDao dao = new ProductDao();
            String name = request.getParameter("name");
            String img = request.getParameter("img");
            String des = request.getParameter("des");
            String price1 = request.getParameter("price");
            String tid1 = request.getParameter("tid");
            float price = Float.parseFloat(price1);
            int tid = Integer.parseInt(tid1);

            if (name.equals("") || img.equals("") || des.equals("") || price1.equals("") || tid1.equals("")) {
                request.setAttribute("err", "You must input information!");
                request.getRequestDispatcher("view/admin/add.jsp").forward(request, response);

            } else {
                if (price < 0) {
                    request.setAttribute("err", "You must input price > 0!");
                    request.getRequestDispatcher("view/admin/add.jsp").forward(request, response);
                } else {
                    Product p = new Product(1, name, tid + "", 0, price, des, img);
                    dao.addProduct(p);
                    response.sendRedirect("admin_product");

                }

            }

        } catch (NumberFormatException e) {
            request.setAttribute("err", "You must input price is number more than 0 and sold number!");
            request.getRequestDispatcher("view/admin/add.jsp").forward(request, response);
        }

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
