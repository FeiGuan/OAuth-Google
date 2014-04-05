package edu.nyu.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

//		resp.sendRedirect("https://accounts.google.com/o/oauth2/token?"
//				+ "code=" + authCode + "&client_id=" + Constants.CLIENTID
//				+ "&client_secret=" + Constants.CLIENTSECRET + "&redirect_uri="
//				+ Constants.TOKENURI + "&grant_type=" + "authorization_code");
		// URL url = new URL("https://accounts.google.com/o/oauth2/token");
		// HttpURLConnection conn;
		// conn = (HttpURLConnection) url.openConnection();
		// conn.setRequestMethod("POST");
		// conn.setRequestProperty("code", authCode);
		// conn.setRequestProperty("client_id", Constants.CLIENTID);
		// conn.setRequestProperty("client_secret", Constants.CLIENTSECRET);
		// conn.setRequestProperty("redirect_uri", Constants.TOKENURI);
		// conn.setRequestProperty("grant_type", "authorization_code");
		// conn.connect();
		// resp.getWriter().println(conn.getResponseMessage());
		// resp.getWriter().println(conn.getRequestProperties());
		// // resp.getWriter().println(conn.getContent());
		// // resp.getWriter().println(conn.getHeaderFields());
		// // resp.getWriter().println(conn.getURL());
		// // resp.getWriter().println(conn.getRequestProperties());
		// conn.disconnect();
	}
}
