package test.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "classpath:com/hczd/download/config/spring/app-*.xml")
public class PetroChina {
	@Test
	public void test1() throws IOException{
		String code_addr = "http://www.95504.net/UserControl/Image.aspx?o="+Math.random();
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(code_addr);
		client.executeMethod(post);
		System.out.println(post.getResponseBodyAsString());
	}
	@Test
	public void test3() throws IOException{
		String code_addr = "http://www.baidu.com";
		HttpClient client = new HttpClient();
		GetMethod post = new GetMethod(code_addr);
		client.executeMethod(post);
		System.out.println(post.getResponseBodyAsString());
	}
	@Test
	public void test2() throws IOException{
		String code_addr = "http://www.95504.net/";
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(code_addr);
		client.executeMethod(post);
		System.out.println(post.getResponseBodyAsString());
	}
}
