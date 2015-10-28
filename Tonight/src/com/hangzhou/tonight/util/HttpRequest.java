package com.hangzhou.tonight.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import com.hangzhou.tonight.base.BaseApplication;

import android.util.Log;

/**
 * 
 * @author WYH
 *
 */
public class HttpRequest {
	
	
	 private static DefaultHttpClient httpClient;


	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//设置请求体的类型是文本类型
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			 //设置请求体的长度
			//conn.setRequestProperty("Content-Length", String.valueOf(param.length()));
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			 

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//@SuppressWarnings("deprecation")
			//String param2=URLEncoder.encode(param);
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();					
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	
	/*
     * Function  :   发送Post请求到服务器
     * Param     :   params请求体内容，encode编码格式
     */
    public static String submitPostData(String strUrlPath,Map<String, String> params, String encode) {
        
        byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
        try {            
            
            //String urlPath = "http://192.168.1.9:80/JJKSms/RecSms.php"; 
            URL url = new URL(strUrlPath);  
             
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpClient = new DefaultHttpClient();
            
            if(null!=BaseApplication.sessionId && !"".equals(BaseApplication.sessionId)){
            	 Log.e("ssss","App.sessionId="+BaseApplication.sessionId);
                    httpURLConnection.setRequestProperty("Cookie","PHPSESSID=" + BaseApplication.sessionId); 
                    //httpPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);

            }
            
            httpURLConnection.setConnectTimeout(5000);     //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);   //使用Post方式不能使用缓存  
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            httpURLConnection.getPermission();
           // httpURLConnection.setInstanceFollowRedirects(false); 
   
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            
            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
            	
            	createSession(strUrlPath);
            	
            	
            	/* CookieStore mCookieStore = httpClient.getCookieStore();
                 List<Cookie> cookies = mCookieStore.getCookies();
                 for (int i = 0; i < cookies.size(); i++) {
                     //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
                     if ("PHPSESSID".equals(cookies.get(i).getName())) {
                    	 BaseApplication.sessionId = cookies.get(i).getValue();
                         break;
                     }

                 }*/
            	
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return "err: " + e.getMessage().toString();
        }
        return "-1";
    }
    
    /*
     * Function  :   封装请求体信息
     * Param     :   params请求体内容，encode编码格式
     */
   public static StringBuffer getRequestData(Map<String, String> params, String encode) {
      StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
      try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                      .append("=")
                      .append(URLEncoder.encode(entry.getValue(), encode))
                      .append("&");
            }
           stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
           e.printStackTrace();
       }
      System.out.println(stringBuffer.toString());
       return stringBuffer;
    }
    
   /*
    * Function  :   处理服务器的响应结果（将输入流转化成字符串）
    * Param     :   inputStream服务器的响应输入流
    */
   public static String dealResponseResult(InputStream inputStream) {
      String resultData = null;      //存储处理结果
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byte[] data = new byte[1024];
      int len = 0;
       try {
          while((len = inputStream.read(data)) != -1) {
             byteArrayOutputStream.write(data, 0, len);
          }
     } catch (IOException e) {
         e.printStackTrace();
        }
       resultData = new String(byteArrayOutputStream.toByteArray());    
       return resultData;
   } 
   
   
   public synchronized static void createSession(String strUrlPath) {
       
	   
       if(null==BaseApplication.sessionId || "".equals(BaseApplication.sessionId)){
    	   BaseApplication.sessionCreateTime=new Date().getTime();
               BaseApplication.sessionId=getSessionId(strUrlPath);
               System.out.println("session创建！"+BaseApplication.sessionId);
              
       }
        
       //20分钟后更换session
               long nowTime=new Date().getTime();
               int min=(int)((nowTime-BaseApplication.sessionCreateTime)/(1000*60));
       if(min>=15){
    	   BaseApplication.sessionCreateTime=new Date().getTime();
               BaseApplication.sessionId=getSessionId(strUrlPath);
               System.out.println("session重新创建！");
              
       }else{
    	   BaseApplication.sessionCreateTime=new Date().getTime();
       }

}
   
   

   public static String getSessionId(String strUrlPath){
       
       URL _url;
       String sessionid=null; 
       try {
               _url = new URL(strUrlPath);
                
               HttpURLConnection con= (HttpURLConnection) _url.openConnection(); 
               CookieStore mCookieStore = httpClient.getCookieStore();
               List<Cookie> cookies = mCookieStore.getCookies();
               for (int i = 0; i < cookies.size(); i++) {
                   //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
                   if ("PHPSESSID".equals(cookies.get(i).getName())) {
                	   sessionid = cookies.get(i).getValue();
                       break;
                   }

               }
                
               con.disconnect();
       } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
       }
       
       if(sessionid==null){
    	   sessionid=String.valueOf(BaseApplication.sessionCreateTime);
       }
        
       return sessionid;
}

	
	
	
}
