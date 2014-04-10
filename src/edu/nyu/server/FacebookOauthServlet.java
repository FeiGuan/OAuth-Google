package edu.nyu.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacebookOauthServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.sendRedirect("https://www.facebook.com/dialog/oauth?"
				+ "client_id=" + FacebookConstants.app_id + "&redirect_uri="
				+ FacebookConstants.redirect + "&scope=" + "email,read_friendlists");
	}
}
