/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java.dal;    

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.model.*;


public class OrderDao extends DBContext {
    Connection con;
    PreparedStatement st;
    ResultSet rs;

    public OrderDao() {
        con = connection;
    }
    public ArrayList<ProductOrder> listProductOrder() {
        String sql = "SELECT p.id, p.[name],  t.[name], p.des, SUM(od.quantity) as quantity \n"
                + "FROM OrderDetail od\n"
                + "JOIN Product p ON od.pid = p.id\n"
                + "JOIN [Type] t ON p.fType = t.id\n"
                + "GROUP BY p.id, p.[name], t.[name], p.des\n"
                + "ORDER BY SUM(od.quantity) DESC;";
        ArrayList<ProductOrder> list = new ArrayList<>();
        try {
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                ProductOrder p = new ProductOrder(rs.getInt(1), rs.getString(2),null, rs.getString(3), rs.getString(4), rs.getInt(5), 0, 0);
                list.add(p);
            }
        } catch (Exception e) {
                System.err.println("Lỗi khi lấy danh sách ProductOrder: " + e.getMessage());
                e.printStackTrace();
        }
        return list;

    }
    
    public void addOrderDetial(OrderDetail o){
          String sql = "insert into OrderDetail(pid,oid,quantity,price, total) values(?,?,?,?,?)";
          try {

              st = con.prepareStatement(sql);
              BigDecimal roundedPrice = BigDecimal.valueOf(o.getPrice()).setScale(2, RoundingMode.HALF_UP);
              BigDecimal roundedTotal = BigDecimal.valueOf(o.getTotal()).setScale(2, RoundingMode.HALF_UP);
              st.setInt(1, o.getPid());
              st.setInt(2, o.getOid());
              st.setInt(3, o.getQuantity());
              st.setBigDecimal(4, roundedPrice);
              st.setBigDecimal(5, roundedTotal);
              st.executeUpdate();
                      
          } catch (Exception e) {
          }
      }

    
    public int IdOrder(int uid) {
        int id = -1;
        String sql = "SELECT MAX(o.id) AS id FROM [Order] o WHERE o.uid = ?";

        try {
            st = con.prepareStatement(sql);
            st.setInt(1, uid);
            rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
        }

        return id;
    }

    
    public ArrayList<Integer> listOrderIDbyCID(int uid){
         String sql ="select o.id from [Order] o where o.uid =?";
        ArrayList<Integer> list = new ArrayList<>();
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, uid);
            rs = st.executeQuery();
            if (rs.next()) {
                int i = rs.getInt(1);
                list.add(i);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
 public void deleteOrderDetailbyoid(int oid) {
        String sql = "delete OrderDetail  where oid =?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, oid);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
 public void deleteOrderbyuid(int uid) {
        String sql = "delete [Order]  where uid =?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, uid);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

public ProductOrder updateImgProD(int id, String pname,String tname,String des,int quantity) {
        String sql = "select p.img from Product p where p.id =?";
        ProductOrder po = null ;
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                 po = new ProductOrder(id, pname, rs.getString(1), tname, des, quantity, 0  , 0);
            }
        } catch (Exception e) {
        }
        return po;

    }

    public ArrayList<OrderC> getOrderC(){
        String sql ="select o.id, c.[name], o.[date], o.[total] from [dbo].[Order] o join [dbo].[Customer] c on o.uid =c.id ";
        ArrayList<OrderC> list = new ArrayList<>();
        try {
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()){
                OrderC o = new OrderC(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4));
                list.add(o);
            }
        } catch (Exception e) {
            System.out.println(e);
        } 
        return list;
    }
     public OrderC getOrderCbyId(int id){
        String sql ="select o.id, c.[name], o.[date], o.[total] from [dbo].[Order] o join [dbo].[Customer] c on o.uid =c.id where o.id=? ";
        OrderC list= null;
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            while(rs.next()){
                list = new OrderC(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4));
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    
    public ArrayList<OrderC> searchOrderCby(String date){
        String sql ="select o.id, c.[name], o.[date], o.[total] from [dbo].[Order] o join [dbo].[Customer] c on o.uid =c.id where o.[date] = ?" ;
        ArrayList<OrderC> list = new ArrayList<>();
        try {
            st = con.prepareStatement(sql);
            st.setString(1, date);
            rs = st.executeQuery();
            while(rs.next()){
                OrderC o = new OrderC(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4));
                list.add(o);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    
    public ArrayList<ProductOrder> getPrductOrder(int id){
        String sql ="Select p.id, p.[name],p.img,t.name,p.[des], d.quantity, d.price, (d.price*d.quantity) as total from OrderDetail d join Product p on d.pid = p.id join Type t on t.id = p.fType where oid =?";
        ArrayList<ProductOrder> list = new ArrayList<>();
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            while(rs.next()){
                ProductOrder p = new ProductOrder(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getFloat(7), rs.getInt(8));
                list.add(p);
            }
            
        } catch (Exception e) {
        }
        return list;
        
    }
      public ArrayList<ProductOrder> searchPrductOrder(String name){
        String sql ="Select p.id, p.[name],p.img,t.name,p.[des], d.quantity, d.price, (d.price*d.quantity) as total from OrderDetail d join Product p on d.pid = p.id join Type t on t.id = p.fType where p.name LIKE '%"+name+"%'";
        ArrayList<ProductOrder> list = new ArrayList<>();
        try {
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()){
                ProductOrder p = new ProductOrder(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getFloat(7), rs.getInt(8));
                list.add(p);
            }
            
        } catch (Exception e) {
        }
        return list;
        
    }
      
       public void addOrder(Order o){
          String sql = "insert into [Order](uid,total, address, date) values(?,?,?,?)";
          try {
              st = con.prepareStatement(sql);
              BigDecimal roundedTotal = BigDecimal.valueOf(o.getTotal()).setScale(2, RoundingMode.HALF_UP);
              st.setInt(1, o.getUid());
              st.setBigDecimal(2, roundedTotal);
              st.setString(3, o.getAddress());
              st.setString(4, o.getDate());
              st.executeUpdate();
                      
          } catch (Exception e) {
          }
      }

    public static void main(String[] args) {
        OrderDao dao = new OrderDao();
        ArrayList<OrderC> list = dao.searchOrderCby("2024-10-03");
        System.out.println(list.get(0).getName());
    }
}
