package com.khpark.log.analysis.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public interface LogFileAnalysisService {
	public void setFileInfo(String filepath, String filename) throws FileNotFoundException;

	public Map<String, HashSet<HashMap<String, Object>>> getUriParamInfo();

	public void logFileAnalysis() throws IOException;

}
