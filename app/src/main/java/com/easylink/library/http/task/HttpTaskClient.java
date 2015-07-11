package com.easylink.library.http.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.easylink.library.http.exception.ClientErrorException;
import com.easylink.library.http.exception.ServerErrorException;

public class HttpTaskClient {

	private final int DEFAULT_MAX_CONNECTIONS = 3;
	private final int DEFAULT_MAX_ROUTES = 3;
	//private final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;
	@SuppressWarnings("unused")
	private final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;

	private HttpClient mHttpClient;
	//private boolean mReleased;

	private HttpTaskClient(boolean threadSafe, int timeoutMillis) {
		
		if (threadSafe) {
			mHttpClient = getThreadSafeHttpClient(timeoutMillis);
		} else {
			mHttpClient = getDefaultHttpClient(timeoutMillis);
		}
	}

	public static HttpTaskClient newThreadSafeHttpClient(int timeoutMillis) {
		
		return new HttpTaskClient(true, timeoutMillis);
	}

	public static HttpTaskClient newDefaultHttpClient(int timeoutMillis) {
		return new HttpTaskClient(false, timeoutMillis);
	}

	public HttpResponse executeHttpResponse(HttpUriRequest request) throws ClientProtocolException, IOException, ServerErrorException, ClientErrorException {
		
		HttpResponse resp = mHttpClient.execute(request);
		int statusCode = resp.getStatusLine().getStatusCode();
		if(statusCode >= 500){
			throw new ServerErrorException(statusCode);
		}else if(statusCode >= 400){
			throw new ClientErrorException(statusCode);
		}
		return resp;
	}
	
	public HttpEntity executeHttpEntity(HttpUriRequest request) throws ClientProtocolException, IOException, ServerErrorException, ClientErrorException{
		
		return executeHttpResponse(request).getEntity();
	}

	public String executeText(HttpUriRequest request) throws ClientProtocolException, IOException, ServerErrorException, ClientErrorException {
		
		HttpEntity respEntity = executeHttpResponse(request).getEntity();
		String text = EntityUtils.toString(respEntity, "UTF-8");
		respEntity.consumeContent();
		return text;
	}

	public byte[] executeByteArray(HttpUriRequest request) throws ClientProtocolException, IOException, ServerErrorException, ClientErrorException{
		
		HttpEntity respEntity = executeHttpResponse(request).getEntity();
		byte[] byteArray = EntityUtils.toByteArray(respEntity);
		respEntity.consumeContent();
		return byteArray;
	}

	public void executeInputStream(HttpUriRequest request, InputStreamCallback callback) throws IOException, ServerErrorException, ClientErrorException{
		
		HttpEntity respEntity = executeHttpResponse(request).getEntity();
		callback.onInputStream(respEntity.getContent());
		respEntity.consumeContent();
	}

	public InputStream executeInputStream(HttpUriRequest request) throws ClientProtocolException, IOException, ServerErrorException, ClientErrorException{
		
		HttpEntity respEntity = executeHttpResponse(request).getEntity();
		return respEntity.getContent();
	}
	
	public void shutdown() {
	
		try{
			
			mHttpClient.getConnectionManager().shutdown();
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		//mReleased = true;
	 }
	 
	// public boolean isShutdown() {
	//
	// return mReleased;
	// }

	private HttpClient getDefaultHttpClient(int timeoutMillis) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,timeoutMillis);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,timeoutMillis);
		return httpClient;
	}

	private HttpClient getThreadSafeHttpClient(int timeoutMillis) {
		
		HttpParams httpParams = new BasicHttpParams();

		// set conn mgr attribute
		ConnManagerParams.setTimeout(httpParams, 0);//从连接池取出连接的超时时间
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
				new ConnPerRouteBean(DEFAULT_MAX_ROUTES));
		ConnManagerParams.setMaxTotalConnections(httpParams,
				DEFAULT_MAX_CONNECTIONS);

		// set protocol
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

		// set http conn attribute
		HttpConnectionParams.setConnectionTimeout(httpParams, timeoutMillis);//与服务器建立连接的超时时间
		HttpConnectionParams.setSoTimeout(httpParams, timeoutMillis);//SocketTimeout socket读取数据的超时时间
		HttpProtocolParams.setUserAgent(httpParams, "");
		
		// SET ssl socket factory
		SSLSocketFactory sf = null;
		try {

			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// set ShcemeRegister
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		if (sf == null) {
			schemeRegistry.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
		} else {
			schemeRegistry.register(new Scheme("https", sf, 443));
		}

		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				httpParams, schemeRegistry);
		
		return new DefaultHttpClient(manager, httpParams);
	}

	public static interface InputStreamCallback {
		void onInputStream(InputStream input);
	}

	private static class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {

			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}
