package com.techconf.schedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InputFileReader {
	private Log log = LogFactory.getLog(InputFileReader.class);

	public List<String> readInput(String filename) {
		if (filename == null || "".equals(filename)) {
			filename = ScheduleConfigurator.getScheduleConfigData()
					.getInputfilepath();
		}
		List<String> inputList = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			// Read File Line By Line
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				inputList.add(strLine);
			}
		} catch (Exception e) {// Catch exception if any
			log.error(e);
			e.printStackTrace();
		}
		return inputList;
	}
}
