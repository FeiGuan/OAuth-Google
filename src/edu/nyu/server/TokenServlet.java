package edu.nyu.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		Map<String, Object> map = req.getParameterMap();
		for (String key : map.keySet()) {
			resp.getWriter().println(key + " : " + Arrays.toString((String[]) map.get(key)));
		}
	}
}
