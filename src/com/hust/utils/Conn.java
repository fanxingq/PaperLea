package com.hust.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Conn {
	private static Student student = new Student();
	private static LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	public static void main(String[] args) {
		
		RedisTest redisTest = new RedisTest();
		redisTest.start();
		
//		map.put("sex", "Ů");
//		map.put("family", "four");
//		map.put("class", "E");
//		JSONObject object = new JSONObject();
//		object.putAll(map); 
//		student.setExt(object.toString());
		getConn();
		String ext = student.getExt();
		System.out.println(ext);
		JSONObject object = JSONObject.fromObject(ext);
		JSONArray list = JSONArray.fromObject(object);
		for (int i = 0; i < list.size(); i++) {
			JSONObject object2 = list.getJSONObject(i);
			map.putAll(object2);
		}
		for (String key : map.keySet()) {
			redisTest.putTest(key, map.get(key));
		}
		System.out.println(redisTest.getValue("c"));
	}

	public static void getConn(){
		Connection con;
        // ����������
        String driver = "com.mysql.jdbc.Driver";
        // URLָ��Ҫ���ʵ����ݿ���db1
        String url1 = "jdbc:mysql://127.0.0.1:3306/school";// �˿ں����ݿ���
        // MySQL����ʱ���û���
        String user = "root";
        // MySQL����ʱ������
        String password = "123456";
        // ������ѯ�����
        try {
                // ������������
                Class.forName(driver);
                // 1.getConnection()����������MySQL���ݿ⣡��
                con = DriverManager.getConnection(url1, user, password);
                if (!con.isClosed())
                    System.out.println("Succeeded connecting to the Database!");
                // 2.����statement���������ִ��SQL��䣡��
                Statement statement = con.createStatement();
                // Ҫִ�е�SQL���
                 String sql = "select * from student where sid = 2";
//                String sql = "insert into student values (2,'ss','"+student.ext +"')";
                System.out.println(sql);
                // 3.ResultSet�࣬������Ż�ȡ�Ľ��������
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                     System.out.println("�鵽��");
                     System.out.println(rs);
                     student.setSid(rs.getInt(1));
                     student.setSname(rs.getString(2));
                     student.setExt(rs.getString(3));
                     System.out.println(student.toString());
                }
                rs.close();
                con.close();

        } catch (ClassNotFoundException e) {
                // ���ݿ��������쳣����
                System.out.println("Sorry,can`t find the Driver!");
                e.printStackTrace();
        } catch (SQLException e) {
                // ���ݿ�����ʧ���쳣����
                e.printStackTrace();
        } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
        } finally {
                System.out.println("���ݿ����ݳɹ���ȡ����");
        }
	}
	
	
	
	
	
	
	
}
