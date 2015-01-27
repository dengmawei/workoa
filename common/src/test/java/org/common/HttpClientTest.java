package org.common;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class HttpClientTest {

	
	/** 广告每页url列表*/
	private List<String> adPageUrlList = new ArrayList<String>();
	
	/** 广告标识*/
	private String adFlag = "/mission/detail/";
	
	/** 广告详情url*/
	private String adDetailUrl = "http://www.utouu.com/mission/detail/%s";
	
	/** 广告浏览url*/
	private String adViewUrl = "http://www.utouu.com/mission/appreciate/%s";
	
	/** 广告完成url*/
	private String adDoneUrl = "http://www.utouu.com/mission/apperaddex/%s";
	
	@Before
	public void setUp(){
		adPageUrlList.add("http://www.utouu.com/mission/missionList/2");
		
		adPageUrlList.add("http://www.utouu.com/mission/missionList/1");
		adPageUrlList.add("http://www.utouu.com/mission/missionList/1?page=2&sortType=&");
		adPageUrlList.add("http://www.utouu.com/mission/missionList/1?page=3&sortType=&");
		adPageUrlList.add("http://www.utouu.com/mission/missionList/1?page=4&sortType=&");
		adPageUrlList.add("http://www.utouu.com/mission/missionList/1?page=5&sortType=&");
		adPageUrlList.add("http://www.utouu.com/mission/missionList/5");
	}
	
	@Test
	public void testAnalyzeHttp()throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		//获取广告id列表
		List<String> adIdList = new ArrayList<String>();
		for(String adPageUrl : adPageUrlList){
			HttpGet httpget = new HttpGet(adPageUrl);
			CloseableHttpResponse response = httpclient.execute(httpget);
			String httpString = EntityUtils.toString(response.getEntity());
			
			int index = StringUtils.indexOf(httpString, adFlag);
			while(index > 0){
				adIdList.add(StringUtils.substring(httpString, index + adFlag.length(), index + adFlag.length() + 3));
				index = StringUtils.indexOf(httpString, adFlag, index + adFlag.length() + 3);
			}
			response.close();
		}
		
		//登录
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		//登录用户名
		formparams.add(new BasicNameValuePair("username", "15996270364"));
		//登录密码
		formparams.add(new BasicNameValuePair("password", "dengmawei2008"));
		formparams.add(new BasicNameValuePair("rememberMe", "on"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		HttpPost httppost = new HttpPost("http://www.utouu.com/login");
		httppost.setEntity(entity);
		httpclient.execute(httppost).close();;
		
		//访问广告
		for(String adId : adIdList){
			HttpGet httpDetail = new HttpGet(String.format(adDetailUrl, adId));
			CloseableHttpResponse response = httpclient.execute(httpDetail);
			String adDetail = EntityUtils.toString(response.getEntity());
			response.close();
			int index = StringUtils.indexOf(adDetail, "剩余");
			String count = StringUtils.substring(adDetail, index + "剩余".length(), index + "剩余".length() + 1);
			if(Integer.valueOf(count) == 0){
				System.err.println(String.format("广告Id：%s,剩余次数为0，不再访问", adId));
				continue;
			}
			
			Thread.sleep(RandomUtils.nextInt(1000,2000));
			
			HttpGet httpView = new HttpGet(String.format(adViewUrl, adId));
			httpclient.execute(httpView).close();;
			
			Thread.sleep(RandomUtils.nextInt(5000, 10000));
			
			HttpGet httpDone = new HttpGet(String.format(adDoneUrl, adId));
			httpclient.execute(httpDone).close();
			Thread.sleep(RandomUtils.nextInt(1000, 2000));
			
			System.err.println(String.format("已经观看完广告Id：%s", adId));
		}
		
		System.err.println(adIdList.size());
		System.err.println(adIdList.toString());
		
	}
	
	@Test
	public void testCreate() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://www.utouu.com/");
		CloseableHttpResponse response = httpclient.execute(httpget);
		System.err.println(EntityUtils.toString(response.getEntity()));
	}
	
	@Test
	public void testHttpForm() throws Exception, Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("username", "15996270364"));
		formparams.add(new BasicNameValuePair("password", "dengmawei2008"));
		formparams.add(new BasicNameValuePair("rememberMe", "on"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		HttpPost httppost = new HttpPost("http://www.utouu.com/login");
		httppost.setEntity(entity);
		
		CloseableHttpResponse response = httpclient.execute(httppost);
		
		System.err.println(EntityUtils.toString(response.getEntity()));
		
		HttpGet httpget = new HttpGet("http://www.utouu.com/member/user/userdetail");
		CloseableHttpResponse response1 = httpclient.execute(httpget);
		System.err.println(EntityUtils.toString(response1.getEntity()));
	}
	
	@Test
	public void testFormat(){
		System.err.println(String.format(adDetailUrl, 127));
		System.err.println(String.format(adViewUrl, 127));
		System.err.println(String.format(adDoneUrl, 127));
	}
	
	@Test
	public void testCookie() throws Exception, IOException{
		CookieStore cookieStore = new BasicCookieStore();
		// Populate cookies if needed
		
		//pgv_pvi=6253416448; Hm_lvt_cfa39c19a6e501ec8ee9e98ec59b9298=1415670738,1415754792,1415844725,1415931202;
		//rememberMe=wALMFDtj6Ji4rigiqLlZog6LgIxmJZVkwNWuZetEowQM0jBCcjXiG8JBrTBN8MbeQDHcq97N5a3GUu6e4x+McySMB/+g01onJDk5z52gYfd0uAG3VF3aenvUgQBKx8UEVg4ODTB7jyFiqRns3je7e3Dn+R+/DJVYhnrYwtbQi9b+O8WWGzb+6Gf5OutCZfn0OSYz/iitK0CU0m9P+azMfz95LfTkyw47e3CyQt7XKYfYqHCPDcWFobMJhfTE4ck9Wh2Kek2i/5qEkPl6n1mpvS4dw8EBks2RUTYbTOnKBhIRhJ4+5f1Zx5n0/D1t3xGnwBM92b3F8HHL90FvOQjcHVsEHPYSZmu9uvBPE20DOgNRcVeYvf4lawbgckAjdjW6cYedRZANW53oaYIL31AjFNYlz1a2YxVFlQWTOgsijdo5BxcjzXhzXg11AX+rpHQX9uyGRCafyfbtmwXbP3x5coIRmMxC0CwxN97lRr+1jjes/vjU5g+u9L1Bj8gbifg5OrWciBUBcEJnLIxM1wKa02BDEIxDN+Ds18IJnoYrZ7HvPoRlbz19rihntWu4MgGoPTkOVkjRhfZsmVmvcAOq7y0u2/J1SPkbPKldBK2uCV58ZB3JrSTxiYyEoVJmLUMDzgr9qajTFHCTIpB0B000bqh7JQ+pB1eQ5O0wY6PV0B6dOuRsjl8z3erXBUnsheRh1XoNwFBhSJAgy/InppHaTitQdFdpZFGX7Ls5nRNNroIrzrM8a/poMj4F0C2bdtGgSzUpC2KQztOZTQZCLs0R6Cs5pH8nx5ZnctuL/tvKtOjcAnONqzOiEUrT7yD+OrqhEarVTrqSfYssTydL6AQzeQ==; 
		//JSESSIONID=57E619C5D83F47E0A7F231556FB24F84; 
		//pgv_si=s8954099712; Hm_lpvt_cfa39c19a6e501ec8ee9e98ec59b9298=1415931202
		
		BasicClientCookie cookie = new BasicClientCookie("pgv_pvi", "6253416448");
		BasicClientCookie cookie1 = new BasicClientCookie("Hm_lvt_cfa39c19a6e501ec8ee9e98ec59b9298", "1415670738,1415754792,1415844725,1415931202");
		BasicClientCookie cookie2 = new BasicClientCookie("rememberMe", "2CIiLuvg2Opea/MA3gmjZLafDBYfS6gdLjzNifRZxkz/ks1PzG9mIVc7pF7aYeXygARSO3vzkTmxJYMDt0TqHsQ3gzXfaPM93+3/LB6mR2YrgfjmYP990uHZrr7VhdOoIWIgWXu6h5ue7I0a7TaZ7zv/xlht9k/c8p8lf0GgNXvHeIc2V/jowRbz1pznmv4oeh3SgMdRJoMF/HJSTnjV9cfbImasttTI9ATgAUC0l/995s8yby5KrN6xfk2s5tRtqZlCjCVC+WGbkbJH2IiSj10J3n5KzRpsNad6A/TJ8BllfjxfyI+TBalOGksna/TMumnJ6k8dNE1fokpvU2Xpulk2iTZj3003iGAYiYiYkJ2cbdHzvQ1r+lZlVFlE/eMb0qX/SW59WQx/4b2vQLwB5Q0NS/4TPMzN3B0HwHMkmEI9IP8WHIJGmWdvdFKTRMYC2tQSfZZXCmn8cOD+3z8c6QR+SxYQdUkENNpGFWX1VRJMVvX2+y9IMqE95xfW1qq1RDuVgNYhr/+VLi3DR+byvmIp7ZdYK7htJilW5cN1EmFCfBr9BiFfwkQz0hONlVlZE9zWs07IZsiOYpTYKuCTh0ZE2dC4gpDZfzE7qwYsERwy+caMMlECqVKcS4sHdVEuOW+UaPP8+Mjg6qnTz8ch+ZN8Xk7ioBEgnMrC+CkhDa28s8ggGWE1w9X8RKG9xbLcSTp2EhbKOImPFwh0I+KZSWCKRHD13aQGzDSVKfrlNqjAK+oXkn61qrPACzopLjFuuQ7L+pDzJdbaYJ2YPOn/BlBzZ8IU4R/D0rTSqoQF1y58GRGiEVXgVd7KrDanzcmDTs5kmFbi9hglDV2HljyNFQ==");
		BasicClientCookie cookie3 = new BasicClientCookie("JSESSIONID", "5CF22F4B71A17CE445B54F6F1F88BB16");
		BasicClientCookie cookie4 = new BasicClientCookie("pgv_si", "s8954099712");
		BasicClientCookie cookie5 = new BasicClientCookie("Hm_lpvt_cfa39c19a6e501ec8ee9e98ec59b9298", "1415931202");
		cookieStore.addCookie(cookie);
		cookieStore.addCookie(cookie1);
		cookieStore.addCookie(cookie2);
		cookieStore.addCookie(cookie3);
		cookieStore.addCookie(cookie4);
		cookieStore.addCookie(cookie5);
		

		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet httpget = new HttpGet("http://www.utouu.com/member/user/userdetail");
		CloseableHttpResponse response = httpclient.execute(httpget);
		System.err.println(EntityUtils.toString(response.getEntity()));
	}
	
	/*@Test
	public void testHttpDocument() throws Exception, IOException{
		Document result = Request.Get("http://www.utouu.com").execute().handleResponse(new ResponseHandler<Document>() {
			public Document handleResponse(final HttpResponse response)
					throws IOException {
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine
							.getStatusCode(), statusLine
							.getReasonPhrase());
				}
				if (entity == null) {
					throw new ClientProtocolException("Response contains no content");
				}
				DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
				try {
					DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
					ContentType contentType = ContentType.getOrDefault(entity);
					if (!contentType.equals(ContentType.APPLICATION_XML)) {
						throw new ClientProtocolException("Unexpected content type:"+ contentType);
					}
					return (Document) docBuilder.parse(entity.getContent());
				} catch (ParserConfigurationException ex) {
					throw new IllegalStateException(ex);
				} catch (SAXException ex) {
					throw new ClientProtocolException("Malformed XML document", ex);
				}
			}
		});
		
		System.err.println(result.getElementById("js-index-video").getTextContent());
	}*/
	
}	