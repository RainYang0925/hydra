package com.xiaohongshu.automation.service.restful;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xiaohongshu.automation.utils.Utils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RestfulUtils {

	public enum HttpMethod {
		GET,
		POST,
		PUT,
		DELETE
	}

	public static final String REST_CLIENT_KEY = "REST_CLIENT_KEY";
	public static final String REST_COOKIES_LIST_KEY = "REST_COOKIES_LIST_KEY";

	public String serverURL = "";
	public String userName = "admin";
	public String password = "admin";
	public HttpMethod httpMethod = HttpMethod.GET;
	public String requestDataFile = "";
	public String requestData = "";
	public String requestDataType = "application/json";
	public int expectedResponseCode = 200;
	public String responseBody = "Response<==";
	public int responseStatusCode;
	public boolean isFormData = false;
	public Map<String,String> headerMap = new HashMap<String,String>();
	public String responseCookies = "";
	public boolean jsonP = false;
	public String responseHeader;
	public boolean binaryFile = false;
	MultivaluedMap<String,String> headerMaps;
	public byte[] binaryData;

	public MultivaluedMap<String, String> getHeaderMaps() {
		return headerMaps;
	}

	public void setHeaderMaps(MultivaluedMap<String, String> headerMaps) {
		this.headerMaps = headerMaps;
	}

	public void setBinaryFile(String filePath){
		binaryFile = true;
		byte[] requestData = {};

		if (filePath != null && filePath != "")
			try {
				requestData = FileUtils.readFileToByteArray(new File(filePath));

			} catch (IOException e) {
				LoggerFactory.getLogger(RestfulUtils.class).error("Exception when init Binary Data File: " + filePath);
				e.printStackTrace();

			}
		this.binaryData = requestData;
	}

	public String getResponseHeader() {
		return responseHeader;
	}

	public String getResponseHeadersBy(String key){
		String string ="";
		LoggerFactory.getLogger(RestfulUtils.class).info("responseHeaders is :" + headerMaps.toString() + "");
		try{
			string = headerMaps.get(key).toString();
		}catch (Exception e){
			LoggerFactory.getLogger(RestfulUtils.class).error(key + " is not in the response header");
		}

		return string;
	}

	public void setResponseHeader(String responseHeader) {
		this.responseHeader = responseHeader;
	}

	public void setJsonP(boolean jsonP){
		this.jsonP = jsonP;
	}

	public String initRequestData(){

    	requestData = Utils.replace(requestData);

		requestData = this.getFormDataFromJson(requestData);

		return requestData;

	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {

		this.requestData = requestData;
		LoggerFactory.getLogger(RestfulUtils.class).info("requestData=" + requestData + "");

	}

	public String getResponseCookies() {
		return responseCookies;
	}

	public void setResponseCookies(String responseCookies) {
		this.responseCookies = responseCookies;
	}

	public String getServerURL() {
		return serverURL.trim();
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {

		switch (httpMethod.trim().toLowerCase()) {
			case "get" :
				this.httpMethod = HttpMethod.GET;
				break;
			case "post" :
				this.httpMethod = HttpMethod.POST;
				break;
			case "delete" :
				this.httpMethod = HttpMethod.DELETE;
				break;
			case "put" :
				this.httpMethod = HttpMethod.PUT;
				break;
			default :
				break;
		}
	}

	public String getRequestDataFile() {
		return requestDataFile;
	}

	public void setRequestDataFile(String requestDataFile) {


		String requestData = "";

		if (requestDataFile != null && requestDataFile != "")
			try {
				requestData = FileUtils.readFileToString(new File(requestDataFile));

			} catch (IOException e) {
				LoggerFactory.getLogger(RestfulUtils.class).error("Exception when init Request Data File: " + requestDataFile);
				e.printStackTrace();

			}

		this.requestData = requestData;


		LoggerFactory.getLogger(RestfulUtils.class).info("requestData=" + requestData + "");

	}

	public String getRequestDataType() {
		return requestDataType;
	}

	public void setRequestDataType(String requestDataType) {
		this.requestDataType = "application/"
					+ requestDataType.toLowerCase().trim();
	}

	public Map getRequestHeader(){ return headerMap;}

	public void setRequestHeader(Map headerMap){
		this.headerMap = headerMap;
	}

	public int getExpectedResponseCode() {
		return expectedResponseCode;
	}

	public void setExpectedResponseCode(int expectedResponseCode) {
		this.expectedResponseCode = expectedResponseCode;
	}

	public String getResponseBody() {
		if(jsonP){
			if(responseBody.length() > 0) {
				return responseBody.substring(responseBody.indexOf('(') + 1, responseBody.length() - 1);
			}
		}
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public void  setFormData(boolean isFormData){
		this.isFormData = isFormData;
	}
	public boolean getFormData(){
		return this.isFormData;
	}

	public String getFormDataFromJson(String jsonData){
		if(!getFormData()){
			return jsonData;
		}
		String formData = "";
		try{
			JSONObject object = JSON.parseObject(jsonData);
			Iterator iter = object.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next().toString();
				formData += key + "=" + URLEncoder.encode(object.get(key).toString()) + "&";
			}

			if(formData != ""){
				formData = formData.substring(0,formData.length()-1);
			}
		}
		catch (JSONException e){
			LoggerFactory.getLogger(RestfulUtils.class).error("Exception when loadDataFromJson:" + jsonData);
		}
		return formData;
	}




	public boolean execute(){
		LoggerFactory.getLogger(RestfulUtils.class).info("----------Enter RestfulUtils");
		Client client = null;
		String requestData = this.requestData;
		boolean res = false;
		ClientResponse.Status clientResponseStatus = null;

		try {
			ClientConfig config = new DefaultClientConfig();
			SSLContext ctx = SSLContext.getInstance("TLSv1");
			System.setProperty("https.protocols", "TLSv1");
			TrustManager[] trustAllCerts = { new InsecureTrustManager() };
			ctx.init(null, trustAllCerts, null);
			HostnameVerifier allHostsValid = new InsecureHostnameVerifier();
			config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(allHostsValid, ctx));
			client = Client.create(config);
			client.setFollowRedirects(false);

		} catch (Exception e) {
			LoggerFactory.getLogger(RestfulUtils.class).error("Exception init client:" + e.getMessage());
			e.printStackTrace();
		}

		// server url
		WebResource webResource = client.resource(serverURL);
		WebResource.Builder builder = webResource.getRequestBuilder();

		// Authorization
		if ((this.password.trim() != "") || (this.userName.trim() != "")) {
			String userPassword = this.userName + ":" + this.password;
			String encoding = Base64.getEncoder().encodeToString(userPassword.getBytes());
			builder.header("Authorization", "Basic " + encoding);
		}

		//set header
		for(Map.Entry<String, String> entry : headerMap.entrySet()){
			builder.header(entry.getKey(),entry.getValue());
		}

		LoggerFactory.getLogger(RestfulUtils.class).info("serverURL=[" + serverURL + "]");

		String requestBody = "";
		if(! binaryFile) {
			requestData = initRequestData();
			requestBody = requestData;
		}

		// set input content type
		builder.type(this.requestDataType);

		// execute method
		try {
			ClientResponse response = null;
			switch (httpMethod) {
				case GET :
					response = builder.get(ClientResponse.class);
					break;
				case POST :
					if(binaryFile){
						response = builder.post(ClientResponse.class,this.binaryData);
					}
					else {
						LoggerFactory.getLogger(RestfulUtils.class).info("executeRequestData=[" + requestBody + "]");
						response = builder.post(ClientResponse.class, requestData);
					}
					break;
				case DELETE :
					LoggerFactory.getLogger(RestfulUtils.class).info("executeRequestData=[" + requestBody + "]");
					response = builder
							.delete(ClientResponse.class, requestData);
					break;
				case PUT :
					LoggerFactory.getLogger(RestfulUtils.class).info("executeRequestData=[" + requestBody + "]");
					response = builder.put(ClientResponse.class, requestData);
					break;
				default :
					break;
			}
				LoggerFactory.getLogger(RestfulUtils.class).info("Status=[" + response.getStatus() + "]");
				responseBody = response.getEntity(String.class);
				responseHeader = response.getHeaders().toString();
				headerMaps = response.getHeaders();
				if(response.getHeaders().containsKey("Set-Cookie")){
				String cookies = response.getHeaders().get("Set-Cookie").toString();
				responseCookies = cookies.substring(1,cookies.length()-1);
			}

			LoggerFactory.getLogger(RestfulUtils.class).info("Response=[" + responseBody + "]");
			clientResponseStatus = response.getClientResponseStatus();

		} catch (Exception e) {
			responseBody = e.getMessage();
			LoggerFactory.getLogger(RestfulUtils.class).error("Exception when exeucte :" + e.getMessage());
			e.printStackTrace();
			return false;
		}

		// verify response status
		this.responseStatusCode =  clientResponseStatus.getStatusCode();
		if (expectedResponseCode == clientResponseStatus.getStatusCode()) {
				res = true;
				LoggerFactory.getLogger(RestfulUtils.class).info("Expected response is: "
						+ clientResponseStatus.getStatusCode());
		} else {
			res = false;
			LoggerFactory.getLogger(RestfulUtils.class).info("Expected response is: " + expectedResponseCode
					+ " but the actual is: "
					+ clientResponseStatus.getStatusCode());
		}

		LoggerFactory.getLogger(RestfulUtils.class).info("Execute Result=" + res);
		return res;
	}

	public void setRequestCookies(String cookies){
		headerMap.put("cookie", cookies);

	}

	public static void main(String[] args) throws Exception{

		//String responseBody = "erroTest({\"errorCode\":0,\"errorMsg\":\"\",\"warnMsg\":\"\",\"value\":[],\"total\":0,\"num\":null,\"index\":null,\"indexList\":null,\"extra\":null,\"content\":null,\"success\":true})";
		//System.out.println(responseBody.substring(responseBody.indexOf('(') + 1, responseBody.length() -1) );

//		RestfulUtils restful = new RestfulUtils();
//		restful.setServerURL("http://www.xiaodian.com/api/login/login");
//		restful.setRequestData("{\"uname\":\"lingxue\"}");
//		System.out.println(restful.getRequestData());
//		System.out.println(restful.initRequestData());
//		String replace = "{\"uname\":\"yuanfei\"}";
//		Utils.setReplace(replace);
//		System.out.println(restful.initRequestData());
//		restful.setFormData(true);
//		restful.setRequestDataType("x-www-form-urlencoded");
//		restful.setHttpMethod("POST");
//		restful.execute();


//		RestfulUtils restfulUtils = new RestfulUtils();
//		restfulUtils.setFormData(true);
//		restfulUtils.setRequestDataType("x-www-form-urlencoded");
//		String replace = "{\"password\":{\"a\":1},\"uname\":\"apollo_02\"}";
//		Utils.setReplace(replace);
//		restfulUtils.execute();
//		String cookies = restfulUtils.getResponseCookies();
//		System.out.println(cookies);
//		restfulUtils.setRequestCookies(cookies);
//		restfulUtils.setServerURL("http://www.xiaodian.com/api/login/login");
//		restfulUtils.setRequestData("{\"uname\":\"lingxue\"}");
//	//			restfulUtils.setFormData(true);
////		restfulUtils.setRequestData("{\"uname\" : \"alipay\", \"password\" : 509100140095387653}");
//		restfulUtils.setFormData(true);
//		restfulUtils.setHttpMethod("POST");
//		restfulUtils.setRequestDataType("x-www-form-urlencoded");
//
//		restfulUtils.execute();
//		System.out.println(restfulUtils.getResponseHeader());
//		System.out.println(restfulUtils.getResponseHeadersBy("Set-Cookie"));



//
//		RestfulUtils restfulUtils = new RestfulUtils();
//		restfulUtils.setRequestDataFile("/Users/zhanghao/Documents/Interface/js/Demo/log.json");
//		restfulUtils.setServerURL("http://www.xiaodian.com/api/login/login");
//		restfulUtils.setFormData(true);
//		restfulUtils.setRequestDataType("x-www-form-urlencoded");
//		restfulUtils.setHttpMethod("POST");
//		restfulUtils.execute();
//		System.out.println(restfulUtils.getResponseBody());


		RestfulUtils restfulUtils = new RestfulUtils();
		restfulUtils.setServerURL("http://10.13.132.175:9190/v1/hsjscss/zip/dir2");

		restfulUtils.setBinaryFile("/Users/zhanghao/Documents/Work/test/test.zip");
		restfulUtils.setHttpMethod("post");
		restfulUtils.execute();
		System.out.println(restfulUtils.getResponseBody());
	}

}
