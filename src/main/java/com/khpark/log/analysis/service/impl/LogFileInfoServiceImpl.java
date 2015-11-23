package com.khpark.log.analysis.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khpark.log.analysis.service.LogFileInfoService;

@Service
public class LogFileInfoServiceImpl implements LogFileInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogFileInfoServiceImpl.class);
	private Map<String, HashSet<HashMap<String, Object>>> uriInfo = new HashMap<String, HashSet<HashMap<String, Object>>>();

	@Autowired
	private LogFileAnalysisServiceImpl logFileAnalysisService;

	public void defaultSetting(String filepath, String filename) throws FileNotFoundException {
		logFileAnalysisService.setFileInfo(filepath, filename);
	}

	public void getLogFileInfo() throws IOException {
		logFileAnalysisService.logFileAnalysis();
		uriInfo = logFileAnalysisService.getUriParamInfo();
	}

	public void bothApiCall() {
		phpRpcCall();
		javaHttpCall();
	}

	private void phpRpcCall() {
		for (String uri : uriInfo.keySet()) {
			HashSet<HashMap<String, Object>> params = uriInfo.get(uri);

			for (HashMap<String, Object> param : params) {
				Map<String, Object> inputParams = new HashMap<String, Object>();

				for (String key : param.keySet()) {
					inputParams.put(key, param.get(key));
				}

				LOGGER.info("uri = {}, param Map = {}", uri, inputParams);
			}
		}
	}

	private void javaHttpCall() {
	}
}
