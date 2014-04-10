package edu.nyu.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class FacebookCallbackServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String code = req.getParameter("code");
		if (code == null || code.equals("")) {
			// an error occurred, handle this
		}

		String token = null;
		try {
			String g = "https://graph.facebook.com/oauth/access_token?client_id="
					+ FacebookConstants.app_id
					+ "&redirect_uri="
					+ URLEncoder.encode(FacebookConstants.redirect, "UTF-8")
					+ "&client_secret="
					+ FacebookConstants.client_secret
					+ "&code=" + code;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			token = b.toString();
			if (token.startsWith("{"))
				throw new Exception("error on requesting token: " + token
						+ " with code: " + code);
		} catch (Exception e) {
			// an error occurred, handle this
		}

		String graph = null;
		try {
			String g = "https://graph.facebook.com/me?" + token;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String facebookId = null;
		String firstName = null;

		String lastName = null;
		String email = null;
		try {
			JSONObject json = new JSONObject(graph);
			facebookId = json.getString("id");
			firstName = json.getString("first_name");
			lastName = json.getString("last_name");
			email = json.getString("email");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		graph = processGet(new URL("https://graph.facebook.com/" + facebookId
				+ "?fields=picture.type(large)"));

		String profileURL = null;
		try {
			JSONObject json = new JSONObject(graph);
			JSONObject picOb = (JSONObject) json.get("picture");
			JSONObject dataOb = (JSONObject) picOb.get("data");
			profileURL = dataOb.getString("url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		resp.getWriter().println("FirstName:" + firstName + "  \n");
		resp.getWriter().println("LastName:" + lastName + "  \n");
		resp.getWriter().println("Email:" + email + "  \n");
		resp.getWriter().println("ProfileURL:" + profileURL + "  \n");

		graph = processGet(new URL("https://graph.facebook.com/me/friends?"
				+ token));

		String friendNames[] = new String[5];
		String friendIds[] = new String[5];
		try {
			JSONObject json = new JSONObject(graph);
			JSONArray friendArray = json.getJSONArray("data");
			for (int i = 0; i < 5; i++) {
				JSONObject friend = friendArray.getJSONObject(i);
				friendNames[i] = friend.getString("name");
				friendIds[i] = friend.getString("id");
			}
			for (int i = 0; i < 5; i++) {
				resp.getWriter().println(
						"Friend" + i + " name:" + friendNames[i] + " id:"
								+ friendIds[i] + "  \n");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
