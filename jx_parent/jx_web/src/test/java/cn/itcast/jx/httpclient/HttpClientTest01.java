package cn.itcast.jx.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest01 {

	public static void main(String[] args) throws Exception {
		//1 创建HttpClient对象--打开浏览器
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//2 创建请求方式get/post/put...
		HttpGet get = new HttpGet("http://localhost:8088/jx_web/homeAction_title");
		//3 发送请求
		HttpResponse response = httpClient.execute(get);
		//4 接收返回值
		//响应头信息,响应的状态码
		System.out.println(response.getStatusLine());
		//响应体信息
		HttpEntity entity = response.getEntity();
		
		String result = EntityUtils.toString(entity);
		
		System.out.println(result);
		
		//5关闭连接
		httpClient.close();
		
		
	}

}
