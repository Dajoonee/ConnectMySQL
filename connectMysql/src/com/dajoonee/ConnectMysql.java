package com.dajoonee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectMysql {
   
   Connection conn = null;
   
   ConnectMysql() {
      
      mysqlStart();
        
   }
   
//   @Override
//   public void run() {
//       
//   }
   
   
   void mysqlStart() {
      Thread thread = new Thread() {
         @Override
         public void run() {
            try {
                  String url = "jdbc:mysql://localhost:3306/andTest?"
                        + "charaterEncoding=UTF-8&serverTimezone=UTC&"
                        + "useSSL=false";
//                  String url = "jdbc:mysql://localhost:3306/test";
                  
                  conn = DriverManager.getConnection(url,"dajoonee","4525");
                  System.out.println("MySQL 연결 성공");
                  
                  
               }catch(SQLException e) {
                  System.out.println("MySQL 연결 오류");
//                  System.out.println(e.getMessage());
//                  System.out.println(e.getSQLState());
//                  System.out.println(e.getErrorCode());
                  System.out.println("------------------");
                  e.printStackTrace();
//                  try {
//                        conn.close();
//                     } catch(SQLException e1) {
//                        e1.printStackTrace();
//                     }catch (Exception ee) {
//                        ee.printStackTrace();
//                     }
               }finally {
                  
               }
         }
      };
      thread.start();
   }
   int insertData ( String id, String pw, String name) {
   
      PreparedStatement st =null;
      
      
           
         try {
            
               mysqlStart();
               String sql = "insert into members values(?,?,?)";
                 st = conn.prepareStatement(sql);
                 st.setString(1, id);
                 st.setString(2, pw);
                 st.setString(3, name);
                 st.executeUpdate();
                 System.out.println(">>데이터 저장 성공!<<");
                 
                
            
             
            
         }catch(java.sql.SQLIntegrityConstraintViolationException primarye) {
            System.out.println(">>같은 아이디 존재함");
            return 0;
         }catch(Exception e) {
            System.out.println(">>오류 발생으로 데이터 저장 실패");
            e.printStackTrace();
            return -1;
         }finally {
            if(st!=null)
               try {st.close();}catch(SQLException e) {e.printStackTrace();}
            if(conn!=null)
               try {conn.close();}catch(SQLException e) {e.printStackTrace();}
         }
         
         return 1;
      
   }

}
