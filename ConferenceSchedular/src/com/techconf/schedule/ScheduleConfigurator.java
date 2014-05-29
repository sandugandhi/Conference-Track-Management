package com.techconf.schedule;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techconf.json.ScheduleConfigData;

/**
 * This class gets the Scheduler global properties from conference.properties
 * one time and makes it available for various parts of the application .
 * Consider the data here to be on a bulletin board. (ApplicationContext)
 */

public class ScheduleConfigurator {

	public static ScheduleConfigData getScheduleConfigData() {
		return SCD;
	}

	// private constructor
	private ScheduleConfigurator() {
	}

	private final static ScheduleConfigData SCD;

	static String readFile(final String path, final Charset encoding)
			throws IOException {
		final byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	static {
		ScheduleConfigurator sc = new ScheduleConfigurator();
		String propfilename = "res/conference.properties.json";

		SCD = (ScheduleConfigData) sc.getPropertiesFromFile(
				propfilename, new TypeReference<ScheduleConfigData>() {
				});
	}

	private <T> Object getPropertiesFromFile(String filename, TypeReference<T> t) {
		ObjectMapper mapper = new ObjectMapper();
		Object o = null;
		try {
			o = mapper.readValue(readFile(filename, StandardCharsets.UTF_8), t);
			// System.out.println(mapper.writerWithDefaultPrettyPrinter()
			// .writeValueAsString(o));
		} catch (JsonParseException | JsonMappingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
}
