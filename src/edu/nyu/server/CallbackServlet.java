package edu.nyu.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import edu.nyu.server.util.JSONUtil;

public class CallbackServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Map<String, String[]> map = req.getParameterMap();
		resp.setContentType("text/plain");
		// resp.getWriter().println("hw");

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
		resp.getWriter().println("Authorization Code: " + authCode);

		// ///////////////////////////////////////////////////////////////////

		String urlParameters = "code=" + authCode + "&client_id="
				+ Constants.CLIENTID + "&client_secret="
				+ Constants.CLIENTSECRET + "&redirect_uri="
				+ Constants.TOKENURI + "&grant_type=" + "authorization_code";

		URL url = new URL("https://accounts.google.com/o/oauth2/token");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length",
				"" + Integer.toString(urlParameters.getBytes().length));
		conn.setUseCaches(false);

		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		InputStream in = conn.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, "UTF-8");
		String theString = writer.toString();
		resp.getWriter().println(theString);
		conn.disconnect();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendRedirect("https://1-dot-hip-heading-541.appspot.com/callback");
		resp.setContentType("text/plain");
		resp.getWriter().println("hw");
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			Map<Object, Object> parameterMap = (Map) JSONUtil.parse(buffer
					.toString());
			
			resp.getWriter().println(parameterMap);
		} catch (Exception e) {

		}
	}
}
