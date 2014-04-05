package edu.nyu.server.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
	
	/**
	 * Convert JSONString to a Map
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> parse(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		// read JSON from a file
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map = mapper.readValue(json,
					new TypeReference<Map<String, Object>>() {
					});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException("JSON_PARSE_ERROR");
		}
		return map;
	}

	/**
	 * Convert JSONString to a List.
	 */


	/**
	 * Convert a map to a JSONString
	 * 
	 * @param map
	 * @return
	 * @throws IOException
	

	/**
	 * Parse a playerIds string to a list<String>
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	
}
