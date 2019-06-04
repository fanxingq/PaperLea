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
		
//		map.put("sex", "女");
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
        // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        // URL指向要访问的数据库名db1
        String url1 = "jdbc:mysql://127.0.0.1:3306/school";// 端口和数据库名
        // MySQL配置时的用户名
        String user = "root";
        // MySQL配置时的密码
        String password = "123456";
        // 遍历查询结果集
        try {
                // 加载驱动程序
                Class.forName(driver);
                // 1.getConnection()方法，连接MySQL数据库！！
                con = DriverManager.getConnection(url1, user, password);
                if (!con.isClosed())
                    System.out.println("Succeeded connecting to the Database!");
                // 2.创建statement类对象，用来执行SQL语句！！
                Statement statement = con.createStatement();
                // 要执行的SQL语句
                 String sql = "select * from student where sid = 2";
//                String sql = "insert into student values (2,'ss','"+student.ext +"')";
                System.out.println(sql);
                // 3.ResultSet类，用来存放获取的结果集！！
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                     System.out.println("查到了");
                     System.out.println(rs);
                     student.setSid(rs.getInt(1));
                     student.setSname(rs.getString(2));
                     student.setExt(rs.getString(3));
                     System.out.println(student.toString());
                }
                rs.close();
                con.close();

        } catch (ClassNotFoundException e) {
                // 数据库驱动类异常处理
                System.out.println("Sorry,can`t find the Driver!");
                e.printStackTrace();
        } catch (SQLException e) {
                // 数据库连接失败异常处理
                e.printStackTrace();
        } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
        } finally {
                System.out.println("数据库数据成功获取！！");
        }
	}
	
	
	
	
	
	
	
}
