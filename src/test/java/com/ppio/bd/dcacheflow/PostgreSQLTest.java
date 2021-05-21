package com.ppio.bd.dcacheflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgreSQLTest {


    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");

//            System.out.println(Driver.class.getName());
            conn = DriverManager.getConnection("jdbc:postgresql://hgmc-cn-st21vo0do001-cn-hangzhou.hologres.aliyuncs.com:80/db_test?preferQueryMode=simple&tcpKeepAlive=true","LTAI4GFy8z6y9KYpuGUpJQRU","aCSMTvu2vJP2rfSFWYSxxgJUzevAr1");
            System.out.println("连接数据库成功!");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from t_ads_business_bw_allocated where dt = '20201001' limit 10;");
            while (rs.next()) {
                String id = rs.getString("machine_id");
                String name = rs.getString("name");
                System.out.println("id=" + id + "  name=" + name);

            }
            rs.close();
            stmt.close();

            System.out.println("-------------------------------------------+++++++++++++++++++++++++++++++++");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from t_ads_business_bw_allocated where dt = '20201001' limit 10;");
            while (rs.next()) {
                String id = rs.getString("machine_id");
                String name = rs.getString("name");
                System.out.println("id=" + id + "  name=" + name);

            }


            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
