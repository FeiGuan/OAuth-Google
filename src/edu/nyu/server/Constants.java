package edu.nyu.server;

public class Constants {
	/**
	 * a dot to separate different scope
	 */
	public static String DOT = "%20";
	/**
	 * a slash to start the state
	 */
	public static String SLASH = "%2F";
	public static String PROFILESCOPE = "profile";
	public static String EMAILSCOPE = "email";
	/**
	 * Create a client id for your app at https://console.developers.google.com,
	 * APIs & auth -> Credentials -> Create new client ID -> Web application ->
	 * set the authorized origin URL as appspot URL -> set redirect URI as the
	 * callback URI
	 */
	public static String CLIENTID = "144852651700.apps.googleusercontent.com";
								  
	/**
	 * Callback Redirect URI, change the domain name for your app
	 */
	public static String APPURI = "http://1-dot-tough-bearing-554.appspot.com/googlecallback";
	/**
	 * Downloaded as a json file from app console,
	 */
	public static String CLIENTSECRET = "HFnUZ0uExD8h-iUfWZchsc6b";
									   
	public static String SCOPE = "https://www.googleapis.com/auth/plus.login";
}
