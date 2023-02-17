package com.example.seckill.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 生成用户工具类
 *
 * @author lizongzai
 * @since 1.0.0
 */
public class UserUtil {

  private static void createUser(int count) throws Exception {
    List<User> users = new ArrayList<User>(count);
    //生成用户
    for (int i = 0; i < count; i++) {
      User user = new User();
      user.setId(13977000000L + i);
      user.setNickname("user" + i);
      user.setSalt("1a2b3c4d");
      user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSalt()));
      user.setRegisterDate(new Date());
      user.setLoginCount(0);
      users.add(user);
    }
    System.out.println("create user");

    //插入数据库
    Connection conn = getConn();
    String sql = "INSERT INTO `t_user` (`id`, `nickname`, `password`, `salt`, `register_date`, `login_count`)values(?,?,?,?,?,?)";
    //对象进行sql操作
    PreparedStatement pstmt = conn.prepareStatement(sql);
    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);
      pstmt.setLong(1, user.getId());
      pstmt.setString(2, user.getNickname());
      pstmt.setString(3, user.getPassword());
      pstmt.setString(4, user.getSalt());
      pstmt.setTimestamp(5, new Timestamp(user.getRegisterDate().getTime()));
      pstmt.setInt(6, user.getLoginCount());
      pstmt.addBatch();
    }
    pstmt.executeBatch();
    pstmt.close();
    conn.close();
    System.out.println("insert to db");

    //登录，并生成token(即userTicket)
    String urlString = "http://localhost:8080/login/toLogin";
    File file = new File("D:\\IBM\\totuirals\\backend\\seckill\\userTicket.txt");
    if (file.exists()) {
      file.delete();
    }

    RandomAccessFile raf = new RandomAccessFile(file, "rw");
    file.createNewFile();
    raf.seek(0);
    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);
      URL url = new URL(urlString);
      HttpURLConnection co = (HttpURLConnection) url.openConnection();
      co.setRequestMethod("POST");
      co.setDoOutput(true);

      OutputStream out = co.getOutputStream();
      String params =
          "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass("123456");
      out.write(params.getBytes());
      out.flush();

      InputStream inputStream = co.getInputStream();
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      byte buff[] = new byte[1024];

      int len = 0;
      while ((len = inputStream.read(buff)) >= 0) {
        bout.write(buff, 0, len);
      }
      inputStream.close();
      bout.close();

      String response = new String(bout.toByteArray());
      ObjectMapper mapper = new ObjectMapper();
      RespBean respBean = null;
      try {
        respBean = mapper.readValue(response, RespBean.class);
      } catch (Exception e) {
        throw new Exception(e);
      }
      String userTicket = ((String) respBean.getObj());
      System.out.println("create userTicket : " + user.getId());
      System.out.println("create Ticket : " + userTicket);

//      JSONObject jsonObject = null;
//      String response = new String(bout.toByteArray());
//      try{
//        jsonObject = JSON.parseObject(response, (Type) RespBean.class);
//      }catch (Exception e) {
//        e.printStackTrace();
//      }
//      String userTicket = jsonObject.getString("data");
//      System.out.println("create token : " + user.getId());

      String row = user.getId() + "," + userTicket;
      raf.seek(raf.length());
      raf.write(row.getBytes());
      raf.write("\r\n".getBytes());
      System.out.println("write to file : " + user.getId() + " ," + userTicket);

    }
    raf.close();

    System.out.println("over");

  }

  public static Connection getConn() throws Exception {
    String url = "jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    String username = "root";
    String password = "password";
    String driver = "com.mysql.cj.jdbc.Driver";
    //加载驱动
    Class.forName(driver);
    //获取连接对象
    return DriverManager.getConnection(url, username, password);
  }


  public static void main(String[] args) throws Exception {
    createUser(100);
  }
}

