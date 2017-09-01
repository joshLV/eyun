package com.rainsoft.base.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpUtil {
    private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static final int SIZE 	= 1024 * 1024;
	//private static Log log = LogFactory.getLog(HttpUtil.class);
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
   
	
	private HttpUtil() {
	  }
	/*
	 * 调用接口
	 */
	public static String portConnect(String url,String params){
		HttpClient httpclient = new DefaultHttpClient();
		String result = "";
		try{
			HttpPost httpPost =  new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
			nvps.add(new BasicNameValuePair("params", params));  
			httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			//System.out.println(httpPost.getRequestLine()+nvps.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8")); 
			HttpResponse response = httpclient.execute(httpPost);
            response.getStatusLine().getStatusCode();
            result = EntityUtils.toString(response.getEntity());
            //System.out.println(result);//禁止出现System.out.println();
		}catch(Exception e ){
			//e.printStackTrace();
			logger.error("请求的地址无响应，url--------"+url);
		}finally{
			httpclient.getConnectionManager().shutdown(); 
		}
		return result;
	}
	
	
	/*
	 * 调用接口
	 */
	public static String portConnectWithAuthHeader(String url,String params,String headerName,String headerValue){
		HttpClient httpclient = new DefaultHttpClient();
		String result = "";
		try{
			HttpPost httpPost =  new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
			nvps.add(new BasicNameValuePair("params", params));  
			httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			if(StringUtil.isNotEmpty(headerName)&&StringUtil.isNotEmpty(headerValue)){
				httpPost.addHeader(headerName,headerValue);//新增header信息
			}
			//System.out.println(httpPost.getRequestLine()+nvps.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8")); 
			HttpResponse response = httpclient.execute(httpPost);
            response.getStatusLine().getStatusCode();
            result = EntityUtils.toString(response.getEntity());
            //System.out.println("result:"+DesUtil.decrypt(result));
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown(); 
		}
		return result;
	}
	
	public static void main(String[] args){
//		String contents ="{\"hardwareID\":\"00133B0C0C20\"}";
//		try {
//			contents = DesUtil.encrypt(contents);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		String url ="http://127.0.0.1:8080/uicenter/app/getAppStartPic";
//		System.out.println("--------"+contents);
//		portConnect(url, contents);
	}
	
	/**
	   * GET METHOD
	   * @param strUrl String
	   * @param map Map
	   * @throws IOException
	   * @return List
	   */
	  public static List getUrl(String strUrl, Map map) throws IOException {
	    String strtTotalURL = "";
	    List result = new ArrayList();
	    if(strtTotalURL.indexOf("?") == -1) {
	      strtTotalURL = strUrl + "?" + getUrl(map);
	    } else {
	      strtTotalURL = strUrl + "&" + getUrl(map);
	    }
	    logger.debug("strtTotalURL:" + strtTotalURL);
	    URL url = new URL(strtTotalURL);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setUseCaches(false);
	    con.setFollowRedirects(true);
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()),SIZE);
	    while (true) {
	      String line = in.readLine();
	      if (line == null) {
	        break;
	      }
	      else {
	    	  result.add(line);
	      }
	    }
	    in.close();
	    return (result);
	  }

	  /**
	   * POST METHOD
	   * @param strUrl String
	   * @param content Map
	   * @throws IOException
	   * @return List
	   */
	  public static List postUrl(String strUrl, Map map) throws IOException {

	    String content = "";
	    content = getUrl(map);
	    String totalURL = null;
	    if(strUrl.indexOf("?") == -1) {
	      totalURL = strUrl + "?" + content;
	    } else {
	      totalURL = strUrl + "&" + content;
	    }
	    URL url = new URL(strUrl);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    con.setAllowUserInteraction(false);
	    con.setUseCaches(false);
	    con.setRequestMethod("POST");
	    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
	    BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
	        getOutputStream()));
	    bout.write(content);
	    bout.flush();
	    bout.close();
	    BufferedReader bin = new BufferedReader(new InputStreamReader(con.
	        getInputStream()),SIZE);
	    List result = new ArrayList(); 
	    while (true) {
	      String line = bin.readLine();
	      if (line == null) {
	        break;
	      }
	      else {
	    	  result.add(line);
	      }
	    }
	    return (result);
	  }

	  /**
	   * 获得URL
	   * @param map Map
	   * @return String
	   */
	  private static String getUrl(Map map) {
	    if (null == map || map.keySet().size() == 0) {
	      return ("");
	    }
	    StringBuffer url = new StringBuffer();
	    Set keys = map.keySet();
	    for (Iterator i = keys.iterator(); i.hasNext(); ) {
	      String key = String.valueOf(i.next());
	      if (map.containsKey(key)) {
	    	 Object val = map.get(key);
	    	 String str = val!=null?val.toString():"";
	    	 try {
				str = URLEncoder.encode(str, "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        url.append(key).append("=").append(str).
	            append(URL_PARAM_CONNECT_FLAG);
	      }
	    }
	    String strURL = "";
	    strURL = url.toString();
	    if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
	      strURL = strURL.substring(0, strURL.length() - 1);
	    }
	    return (strURL);
	  }
	
}
