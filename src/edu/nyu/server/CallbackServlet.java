package edu.nyu.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import edu.nyu.server.util.JSONUtil;

public class CallbackServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Map<String, String[]> map = req.getParameterMap();
		resp.setContentType("text/plain");

		/**
		 * authorization code from Google
		 */
		String authCode = null;
		for (String key : map.keySet()) {
			if (key.equals("error") && map.get(key)[0].equals("access_denied")) {
				resp.getWriter().println("Error: access_denied");
				return;
			}
			if (key.equals("code")) {
				authCode = map.get(key)[0];
			}
		}
		// resp.getWriter().println(authCode);

		String urlParameters = "code=" + authCode + "&client_id="
				+ Constants.CLIENTID + "&client_secret="
				+ Constants.CLIENTSECRET + "&redirect_uri="
				+ Constants.TOKENURI + "&grant_type=" + "authorization_code";

		URL url = new URL("https://accounts.google.com/o/oauth2/token");
		String postReqResp = processPost(urlParameters, url);

		JSONObject jsonOb = null;
		String accToken = null;
		try {
			jsonOb = new JSONObject(postReqResp);
			accToken = (String) jsonOb.get("access_token");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		urlParameters = "access_token=" + accToken;
		resp.getWriter().println("access_token: " + accToken);

		url = new URL("https://www.googleapis.com/plus/v1/people/me?"
				+ urlParameters);
		String getReqResp = processGet(url);

		resp.getWriter().println(getReqResp);

	}

	private String processPost(String parameters, URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length",
				"" + Integer.toString(parameters.getBytes().length));
		conn.setUseCaches(false);

		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();
		InputStream in = conn.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, "UTF-8");
		String theString = writer.toString();
		conn.disconnect();
		return theString;
	}

	private String processGet(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setUseCaches(false);

		InputStream in = conn.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, "UTF-8");
		String theString = writer.toString();
		conn.disconnect();
		return theString;
	}
}
