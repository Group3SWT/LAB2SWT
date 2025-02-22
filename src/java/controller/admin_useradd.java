/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.*;
import dal.ProductDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.*;

public class admin_useradd extends HttpServlet {

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
            out.println("<title>Servlet admin_useradd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet admin_useradd at " + request.getContextPath() + "</h1>");
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

        request.getRequestDispatcher("view/admin/user-add.jsp").forward(request, response);
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
        try {
            CustomerDao dao = new CustomerDao();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String pass = request.getParameter("pass");
            String role1 = request.getParameter("role");
            int role = Integer.parseInt(role1);
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || role1.isEmpty()) {
                request.setAttribute("err", "You must input information!");
                request.getRequestDispatcher("view/admin/user-add.jsp").forward(request, response);
            } else {
                ArrayList<Customer> p = dao.getCustomer();
                boolean check = false;
                for (Customer i : p) {
                    if (email.compareTo(i.getEmail()) == 0) {
                        check = true;
                        request.setAttribute("err", "Email is exist!");
                        request.getRequestDispatcher("view/admin/user-add.jsp").forward(request, response);

                    }
                }
                request.setAttribute("role", role);
                if (!check) {
                    Customer c = new Customer(name, email, null, pass, role);
                    if (role == 0) {
                        dao.addCustomeruser(c);
                    } else {
                        dao.addCustomeradmin(c);

                    }
                    response.sendRedirect("admin_user");

                }
            }

        } catch (Exception e) {
            System.out.println(e);
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
