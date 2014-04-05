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
	public static String CLIENTID = "1077597541857-quegjqtnij6asdnmhbqpcr476su8aghj.apps.googleusercontent.com";
	/**
	 * Callback Redirect URI, change the domain name for your app
	 */
	public static String APPURI = "https://1-dot-handy-curve-541.appspot.com/callback";
	/**
	 * Token Redirect URI, change the domain name for your app
	 */
	public static String TOKENURI = "https://1-dot-handy-curve-541.appspot.com/token";
	/**
	 * Downloaded as a json file from app console,
	 */
	public static String CLIENTSECRET = "CNWyEZCZ_rAQ5-HNlzAvNun0";
}
