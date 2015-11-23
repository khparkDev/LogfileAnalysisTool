package com.khpark.log.analysis.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface LogFileInfoService {
	public void defaultSetting(String filepath, String filename) throws FileNotFoundException;

	public void getLogFileInfo() throws IOException;
}
