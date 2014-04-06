package edu.nyu.server;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class OauthServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.sendRedirect("https://accounts.google.com/o/oauth2/auth?"
				+ "scope="
				+ Constants.EMAILSCOPE
				+ " " // + Constants.DOT
				+ Constants.PROFILESCOPE + "&state=%2Fprofile"
				+ "&redirect_uri=" + Constants.APPURI + "&response_type=code"
				+ "&client_id=" + Constants.CLIENTID + "&approval_prompt=force");
	}
}
