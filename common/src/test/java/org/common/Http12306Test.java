package org.common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

public class Http12306Test {

	@Test
	public void testBuyTicket()throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HostnameVerifier hv = new HostnameVerifier() {
	        public boolean verify(String urlHostName, SSLSession session) {
	            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
	                               + session.getPeerHost());
	            return true;
	        }
	    };
		
		//HttpsURLConnection.setDefaultHostnameVerifier(hv);
		
		/*//登录
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		//登录用户名
		formparams.add(new BasicNameValuePair("loginUserDTO.user_name", "dengmawei%40126.com"));
		//登录密码
		formparams.add(new BasicNameValuePair("userDTO.password", "dengmawei2008"));
		formparams.add(new BasicNameValuePair("randCode", "d3ad"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		HttpPost httppost = new HttpPost("https://kyfw.12306.cn/otn/login/loginAysnSuggest");
		httppost.setEntity(entity);
		httpclient.execute(httppost).close();*/
	}
	
	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
				.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
				.getSocketFactory());
	}

	static class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
}
