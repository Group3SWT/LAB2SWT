/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AddCart;
import dal.OrderDao;
import dal.ProductDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Cart;
import model.CartDetail;
import model.Order;
import model.OrderDetail;
import model.Product;


public class pay extends HttpServlet {

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
            out.println("<title>Servlet pay</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet pay at " + request.getContextPath() + "</h1>");
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

        try {
            float total = Float.parseFloat(request.getParameter("total"));
            request.setAttribute("total", total);
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("uid", id);
            request.getRequestDispatcher("view/user/pay.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
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
        DecimalFormat f = new DecimalFormat("##.##");
        String mess="";
        int id = Integer.parseInt(request.getParameter("uid"));
        float total = Float.parseFloat(request.getParameter("total"));
        
        if(request.getParameter("address").trim().isEmpty() && request.getParameter("date").trim().isEmpty()){
            mess = " Can not be left blank !!!";
            request.setAttribute("uid", id);
            request.setAttribute("total", total);
            request.setAttribute("mess", mess);
            request.getRequestDispatcher("view/user/pay.jsp").forward(request, response);
        }else{
            String address = request.getParameter("address");
            String date = request.getParameter("date");
            AddCart dao = new AddCart();
            OrderDao daO = new OrderDao();
            Cart c = dao.getCartById(id);
            Order o = new Order( id, total, address, date);
            daO.addOrder(o);
            ArrayList<CartDetail> listCD = dao.getCartDetail(c.getCid()); // mảng 
            ProductDao p = new ProductDao();
            for (CartDetail cartDetail : listCD) {
   
                 Product pro = p.getProductById(cartDetail.getPid()); 
                 OrderDetail od = new OrderDetail(pro.getId(), daO.IdOrder(o.getUid()),cartDetail.getQuantity() , Float.valueOf(f.format(pro.getPrice())), Float.valueOf(f.format(cartDetail.getQuantity()*pro.getPrice())));
                 daO.addOrderDetial(od);
            } 
            dao.deleteCartDetailbycid(c.getCid());
            response.sendRedirect("home");
        }

    }


}
