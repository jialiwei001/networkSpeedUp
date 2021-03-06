package com.pingan.springbootfan01.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Component
public class UserRegister {
	HttpClient httpClient = new HttpClient();
	//数据库链接字段
	String URL = "jdbc:mysql://angrybird.top:3306/vpn?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
	String USER = "abcd";
	String PASSWORD = "jiaoliwei1234";
	//用户注册方法
    public String regist(String email,String name,String wechat,int days) {  
        //httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);     	
        PostMethod postLogin = new PostMethod("http://angrybird.top:8081/auth/login");
        PostMethod postRegist=new PostMethod("http://angrybird.top:8081/auth/register");
        PostMethod logout=new PostMethod("http://angrybird.top:8081/logout");
        GetMethod getMethod = new GetMethod("http://angrybird.top:8081/user");
        NameValuePair[] registData = {new NameValuePair("email",email),new NameValuePair("name",name),new NameValuePair("passwd","abcd1234"),new NameValuePair("repasswd","abcd1234"),new NameValuePair("wechat",wechat),new NameValuePair("imtype","1")};
        NameValuePair[] loginData = {new NameValuePair("email", email), new NameValuePair("passwd", "abcd1234")};
        postLogin.setRequestBody(loginData);
        postRegist.setRequestBody(registData);
        try {
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(logout);
            httpClient.executeMethod(postRegist);
            httpClient.executeMethod(postLogin);
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + "; ");
            }
                getMethod.setRequestHeader("cookie",tmpcookies.toString());            
                httpClient.getState().clearCookies();
                httpClient.executeMethod(getMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "http://angrybird.top:8081/link/"+GetToken(email,days)+"?mu=0";
    }
    //获取增加天数后的日期数据
    private String GetDate_AfterAddDays(Date d,int days)
    {
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       Calendar ca = Calendar.getInstance();
	       ca.setTime(d);
	       ca.add(Calendar.DATE, days);
	       d = ca.getTime();
	       String enddate = format.format(d);
	       return enddate;
    }
	//用户续费
    public String AddDays(String email,int days,Boolean flag)
    {
    	String result=null;
    	boolean flags = flag;
		try {
			String expire_in="";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String getExpireDate_Sql="select expire_in from user where email=\""+email+"\""; 
			PreparedStatement statement = conn.prepareStatement(getExpireDate_Sql);
			ResultSet rs =statement.executeQuery();
			while(rs.next()) {
				expire_in =rs.getString("expire_in");
			}
			rs.close();
			Date d=new Date();
			if (flags){
				try {
					d = dateFormat.parse(expire_in);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String endDate=GetDate_AfterAddDays(d,days);
			String AddDays_Sql="update user SET expire_in =\""+endDate+"\",class_expire=\""+endDate+"\" where email=\""+email+"\"";
			String AddDaysNodeTotal="update user SET transfer_enable =\"214748364800\",class=\"2\" where email=\""+email+"\"";
			statement=conn.prepareStatement(AddDays_Sql);
			statement.executeUpdate();
			statement=conn.prepareStatement(AddDaysNodeTotal);
			statement.executeUpdate();
			conn.close();
			statement.close();
		} catch (ClassNotFoundException e) {			
			result= e.getMessage();
		}catch (SQLException e) {			
			result = e.getMessage();
		}
    	
    	return result;
    }
	//用户续费
	public String findMemberEndTime(String email)
	{
		String result=null;
		String expire_in="";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String getExpireDate_Sql="select expire_in from user where email=\""+email+"\"";
			PreparedStatement statement = conn.prepareStatement(getExpireDate_Sql);
			ResultSet rs =statement.executeQuery();
			while(rs.next()) {
				expire_in =rs.getString("expire_in");
			}
			rs.close();
			return expire_in;
		} catch (ClassNotFoundException e) {
			result= e.getMessage();
		}catch (SQLException e) {
			result = e.getMessage();
		}

		return expire_in;
	}

	//查询泰坦星用户是否存在
	public String findMemberIfNull(String email)
	{
		String result= "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String getExpireDate_Sql="select * from user where email=\""+email+"\"";
			PreparedStatement statement = conn.prepareStatement(getExpireDate_Sql);
			ResultSet rs =statement.executeQuery();
			if (rs.next()) {
				result = "notNull";
			}else {
				result = "isNull";
			}
			rs.close();
			return result;
		} catch (ClassNotFoundException e) {
			result= e.getMessage();
		}catch (SQLException e) {
			result = e.getMessage();
		}

		return result;
	}

    //获取用户订阅TOKEN
    private  String GetToken(String email,int days) {
    	String endDate=GetDate_AfterAddDays(new Date(),days);
    	String token = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String AddDays_Sql;
			String getToken_Sql="select token from user,link where user.id=link.userid and email=\""+email+"\"";
			PreparedStatement statement = conn.prepareStatement(getToken_Sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
			token= rs.getString("token");
			}
			rs.close();
			AddDays_Sql="update user SET expire_in =\""+endDate+"\",class_expire=\""+endDate+"\",class=2,transfer_enable=322122547200,node_connector=2 where email=\""+email+"\"";
			System.out.println(AddDays_Sql);
			statement = conn.prepareStatement(AddDays_Sql);
			int first = statement.executeUpdate();
			System.out.println("第一次设置流量的执行结果= "+first);
			String fixTotalData="update user set transfer_enable = 322122547200  where email=\""+email+"\"";
			PreparedStatement statement2 = conn.prepareStatement(fixTotalData);
			statement2.executeUpdate();
			conn.close();
			statement.close();
			statement2.close();
		} catch (ClassNotFoundException e) {
			token= e.getMessage();
		}catch (SQLException e) {			
			token = e.getMessage();
		}
		return token;
    }

	public Double DataUsageForToday(String email) {
		double usage=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql="select u from user where email='"+email+"'";
			System.out.println(sql);
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs =statement.executeQuery();
			while(rs.next()) {
				usage =Double.parseDouble(rs.getString("u"));
				System.out.println(rs.getString("u"));
			}
			statement.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return usage;
	}
	public String DataUsageForTotal(String email) {
		String dataUsage=null;
		double usage=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql="select d from user where email='"+email+"'";
			PreparedStatement statement = conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs =statement.executeQuery();
			while(rs.next()) {
				usage =Double.parseDouble(rs.getString("d"));
				System.out.println(rs.getString("d"));
			}
			double day = DataUsageForToday(email);
			if ((usage+day)  < 1083741){
				System.out.println("-----------------------没有使用的账号-----------------------：");
				return "0.0";
			}
			System.out.println("查询当天值："+day);
			System.out.println("查询当月值："+usage);
			usage=(day +usage)/1024/1024/1024;
            dataUsage=usage+"";
			System.out.println("相加后的当月已用值："+dataUsage);
			statement.close();
			conn.close();
			return dataUsage;
		} catch (ClassNotFoundException e) {
			dataUsage= e.getMessage();
		}catch (SQLException e) {
			dataUsage = e.getMessage();
		}

		return dataUsage;
	}

	public String DataUsageForAllTotal(String email) {
		String dataUsage=null;
		double allTotal=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql="select transfer_enable from user where email='"+email+"'";
			PreparedStatement statement = conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs =statement.executeQuery();
			while(rs.next()) {
				allTotal =Double.parseDouble(rs.getString("transfer_enable"));
				System.out.println(rs.getString("transfer_enable"));
			}
			allTotal=allTotal/1024/1024/1024;
			dataUsage=allTotal+"";
			statement.close();
			conn.close();
			return dataUsage;
		} catch (ClassNotFoundException e) {
			dataUsage= e.getMessage();
		}catch (SQLException e) {
			dataUsage = e.getMessage();
		}
		return dataUsage;
	}

	public String getSpeedLimit(String email) {
		String speed= null;
		double allTotal=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql="select node_speedlimit from user where email='"+email+"'";
			PreparedStatement statement = conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs =statement.executeQuery();
			while(rs.next()) {
				allTotal =Double.parseDouble(rs.getString("node_speedlimit"));
				System.out.println(rs.getString("node_speedlimit"));
			}
			speed=allTotal+"";
			statement.close();
			conn.close();
			return speed;
		} catch (ClassNotFoundException e) {
			speed= e.getMessage();
		}catch (SQLException e) {
			speed = e.getMessage();
		}
		return speed;
	}

	//用户xiugaizongliuliang
	public String fixTotalData(String email,String totalData)
	{
		String result=null;
		try {
			Double total=Double.valueOf(totalData)*1024*1024*1024;
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String fixTotalData="update user set transfer_enable = \""+total+"\"  where email=\""+email+"\"";
			PreparedStatement statement = conn.prepareStatement(fixTotalData);
			statement.executeUpdate();

			conn.close();
			statement.close();
		} catch (ClassNotFoundException e) {
			result= e.getMessage();
		}catch (SQLException e) {
			result = e.getMessage();
		}

		return result;
	}
	//给用户限速
	public String fixSpeedLimit(String email,String speed)
	{
		String result=null;
		try {
			Double total=Double.valueOf(speed);
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String fixTotalData="update user set node_speedlimit = \""+total+"\"  where email=\""+email+"\"";
			PreparedStatement statement = conn.prepareStatement(fixTotalData);
			statement.executeUpdate();

			conn.close();
			statement.close();
		} catch (ClassNotFoundException e) {
			result= e.getMessage();
		}catch (SQLException e) {
			result = e.getMessage();
		}

		return result;
	}
}

