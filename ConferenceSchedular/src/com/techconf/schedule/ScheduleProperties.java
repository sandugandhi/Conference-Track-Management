package com.techconf.schedule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.techconf.json.BasicContainerFactory;

/**
 * This class gets the Scheduler global properties from conference.properties
 * one time and makes it available for various parts of the application . Consider
 * the data here to be on a bulletin board. (ApplicationContext)
 */
public class ScheduleProperties {

	private static Properties prop;
	private static Map<String, SessionConfigData> sessionMap;

	// private constructor
	private ScheduleProperties() {
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	public static SessionConfigData getSessionConfigData(String key) {
		return sessionMap.get(key);
	}
	public static Set<String> getSessionConfigDataKeys() {
		return sessionMap.keySet();
	}
	public static Set<Object> getkeys() {
		return prop.keySet();
	}

	static {
		ScheduleProperties sp = new ScheduleProperties();
		prop = new Properties();
		sessionMap = new LinkedHashMap<String, SessionConfigData>();
		String filename = "conference.properties";
		try {
			prop = sp.getPropertiesFromClasspath(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * loads properties file from classpath
	 * 
	 * @param propFileName
	 * @return
	 * @throws IOException
	 */
	private Properties getPropertiesFromClasspath(String filename)
			throws IOException {
		try (InputStream input = getClass().getClassLoader()
				.getResourceAsStream(filename)) {

			if (input == null) {
				System.out.println("ScheduleProperties: "
						+ "Sorry, unable to find " + filename);
				return null;
			}
			prop.load(input);
			String jsonstr = ScheduleProperties.getProperty("sessions");
			JSONParser parser = new JSONParser();
			BasicContainerFactory bcfactory = new BasicContainerFactory();
			try {
				Object json = parser.parse(jsonstr, bcfactory);
				// MyStaticClass.printRecursive(json);
				List l = (List) json;
				Iterator listIterator = l.iterator();

				while (listIterator.hasNext()) {
					Object mapObj = listIterator.next();
					Map m = (Map) mapObj;
					Iterator miterator = m.entrySet().iterator();
					SessionConfigData d = new SessionConfigData();
					while (miterator.hasNext()) {
						Map.Entry entry = (Map.Entry) miterator.next();
						// System.out.println(entry.getKey() + " => " +
						// entry.getValue());

						if (entry.getKey().equals("id")) {
							d.id = (int) (long) entry.getValue();
						}
						if (entry.getKey().equals("startTime")) {
							d.startTime = (int) (long) entry.getValue();
						}

						if (entry.getKey().equals("endTime")) {
							d.endTime = (int) (long) entry.getValue();
						}

						if (entry.getKey().equals("name")) {
							d.name = (String) entry.getValue();
						}

					}
					if (d != null) {
						sessionMap.put(d.name, d);
					}
				}

			} catch (ParseException pe) {
				pe.printStackTrace(System.err);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}

}
/**
 *  For now, a temporary construct to group the session attributes and store them in a map
 * 	Later, to be moved out of this class file into a separate package
 * **/
class SessionConfigData {
	int id;
	int startTime;
	int endTime;
	String name;
}