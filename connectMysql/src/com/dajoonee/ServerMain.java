package com.dajoonee;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServerMain {
   
   ConnectMysql jdbc = new ConnectMysql();

   public static void main(String[] args) {
      
      String a = "-1";
      System.out.println(Integer.parseInt(a)); 
      
      
//      
//      jdbc.start();
      ServerMain server1 = new ServerMain();
      
      //ipconfig /all
      Thread serverThread = new Thread("ServerThread") {
         
         @Override
         public void run() {
         
            try {
               
               
               ServerSocket server = new ServerSocket();
               server.bind(new InetSocketAddress(7878));

                  System.out.println("���� ������ : "+Thread.currentThread().getName());
                  System.out.println("������...");
                  
                  Socket socket = server.accept();
                  
                  
                  InetSocketAddress id = (InetSocketAddress) socket.getLocalSocketAddress();
                  System.out.println("Ŭ���̾�Ʈ�� ���Ἲ��!"+id.getHostString());
                  
                  server1.getData(socket);

                  
                  
                  
//                  data:while(true) {
//                     
//                     DataOutputStream dataos = new DataOutputStream(socket.getOutputStream());
//                     
//                     String msg = "���� ���� ����!";
      //
//                     dataos.writeUTF(msg);
//                     dataos.flush();
//                           
//                     
//                     if(socket!=null) {
//                        dataos.close();
//                        socket.close();
//                        server.close();
//                     }
//                  }
                  
                  
                  
//               }
               
               
            }catch(Exception e) {
               e.printStackTrace();
            }
         }
      };
      
      serverThread.start();
      
      
      

   }
   
   void getData(Socket socket) {
      
      Thread getDataThread = new Thread("getDataThread") {
         @Override
         public void run() {
            System.out.println("������ �޴� ������ : "+ Thread.currentThread().getName());
            
            System.out.println("getData ����");
            try {
               while(true) {
                  DataInputStream dataip = new DataInputStream(socket.getInputStream());
                  String data = dataip.readUTF();
                  if( data != null) {
                     System.out.println("���� ������: "+data);
                     if(data!=null) {
//                        dataip.close();
//                        break;      
                     }
                     if(!socket.isClosed()) {
                        int result = jsonParsing(data);
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        output.writeUTF(String.valueOf(result));
                        System.out.println("���� ������: "+result);
                        output.flush();
//                        output.close();
                     }else {
                        System.out.println("������ ������. �ٽÿ����.");
                     }
                     
                  }
      
                  
                  
                  
                  
                  
               }
               
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      };
      
      getDataThread.start();
      
   }
   
   int jsonParsing(String data) {
      
  
      int result;
      JSONParser parser = new JSONParser();
      try {
         JSONObject obj = (JSONObject)parser.parse(data);
         String id = (String)obj.get("id");
         String pw = (String)obj.get("pw");
         String name = (String)obj.get("name");
         
         result = jdbc.insertData(id, pw, name);
         
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return -1;
      }
      
      return result;
   }
   


}
