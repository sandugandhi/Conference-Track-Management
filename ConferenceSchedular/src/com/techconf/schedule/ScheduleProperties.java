package com.techconf.schedule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techconf.json.SessionConfigData;

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

    static String readFile(final String path, final Charset encoding) throws IOException {
        final byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

	static {
		ScheduleProperties sp = new ScheduleProperties();
		prop = new Properties();
		sessionMap = new LinkedHashMap<String, SessionConfigData>();
		String filename = "conference.properties";
		String sessionconfigdata = "res/sessionconfigdata.json";
		
		try {
			sp.setPropertiesFromClasspath(filename);
			sp.setSessionDataConfig(sessionconfigdata);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	private void setSessionDataConfig(String filename)
	{
		  ObjectMapper mapper = new ObjectMapper();
		    List <SessionConfigData> l=null;
	        try {
	            l=mapper.readValue(readFile(filename, StandardCharsets.UTF_8), new TypeReference<List <SessionConfigData>>() {});
	            //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(l));
	        } 
	        catch (JsonParseException jpe)
	        {
	        	 jpe.printStackTrace();
	        }
	        catch (JsonMappingException jme)
	        {
	        	 jme.printStackTrace();
	        }
	        catch (final IOException e) {
	            e.printStackTrace();
	        }
	            Iterator<SessionConfigData> listIterator = l.iterator();
	        	while (listIterator.hasNext()) {
	        		SessionConfigData s = (SessionConfigData) listIterator.next();
	        		sessionMap.put(s.getName(), s);
	        	}   
	}
	

	/**
	 * loads properties file from classpath
	 * 
	 * @param propFileName
	 * @return
	 * @throws IOException
	 */
	private void setPropertiesFromClasspath(String filename)
			throws IOException {
		try (InputStream input = getClass().getClassLoader()
				.getResourceAsStream(filename)) {

			if (input == null) {
				System.out.println("ScheduleProperties: "
						+ "Sorry, unable to find " + filename);
			}
			prop.load(input);
			} catch (IOException ex) {
					ex.printStackTrace();
			}
		  
	        /*
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
			*/
	}

}
