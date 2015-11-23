package com.khpark.log.analysis.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.khpark.log.analysis.aspect.Loggable;
import com.khpark.log.analysis.base.Constants;
import com.khpark.log.analysis.service.LogFileAnalysisService;
import com.khpark.log.analysis.service.PatternType;

/**
 * 로그 파일 파싱
 * 
 */
@Service
public class LogFileAnalysisServiceImpl implements LogFileAnalysisService {
	private String filepath;
	private String filename;
	private InputStream is;
	private File srcFile;
	private Map<String, HashSet<HashMap<String, Object>>> uriInfo = new HashMap<String, HashSet<HashMap<String, Object>>>();

	public void setFileInfo(String filepath, String filename) throws FileNotFoundException {
		this.filepath = filepath;
		this.filename = filename;
		this.srcFile = new File(this.filepath + this.filename);
		this.is = new FileInputStream(srcFile);
	}

	public Map<String, HashSet<HashMap<String, Object>>> getUriParamInfo() {
		return this.uriInfo;
	}

	@SuppressWarnings("unchecked")
	@Loggable
	public void logFileAnalysis() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String expression = Constants.EXP_URI_START;
		PatternType patternType = PatternType.URI_START;
		boolean isInputParams = false;
		HashMap<String, Object> params = new HashMap<String, Object>();
		String startURI = "";
		String endURI = "";
		String line = "";

		while ((line = reader.readLine()) != null) {
			line = line.trim();
			Pattern pattern_uri = Pattern.compile(expression);
			Matcher matcher = pattern_uri.matcher(line);

			if (matcher.find()) {

				switch (patternType) {
					case URI_START :
						startURI = parseURI(line, matcher.start(), matcher.end());
						patternType = PatternType.INPUT_PARAMS;
						expression = Constants.EXP_PARAMS_START;
						break;
					case INPUT_PARAMS :
						if (!isInputParams) {
							expression = Constants.EXP_PARAMS_END;
							isInputParams = true;
						} else {
							expression = Constants.EXP_URI_END;
							patternType = PatternType.URI_END;
							isInputParams = false;
						}
						break;
					case URI_END :
						/**
						 * 로그파일에 정확히 START-Input Params-END 순으로 정확히 매칭되는 URI만 처리
						 */
						endURI = parseURI(line, matcher.start(), matcher.end());

						if (StringUtils.equals(startURI, endURI)) {
							HashSet<HashMap<String, Object>> currentDataSet = uriInfo.get(startURI);

							if (currentDataSet != null) {
								currentDataSet.add((HashMap<String, Object>) params.clone());
							} else {
								currentDataSet = new HashSet<HashMap<String, Object>>();
								currentDataSet.add((HashMap<String, Object>) params.clone());
							}

							uriInfo.put(startURI, currentDataSet);
						}

						expression = Constants.EXP_URI_START;
						patternType = PatternType.URI_START;
						params.clear();
						break;
				}
			} else {

				if (isInputParams) {
					String[] strArr = parseInputParams(line);
					params.put(strArr[0], strArr.length == 2 ? strArr[1] : null);
				}
			}
		}
	}

	private String parseURI(String str, int start, int end) {
		str = str.substring(start, end);
		str = str.replaceAll("RPC START", "");
		str = str.replaceAll("RPC END", "");
		str = str.replaceAll("\\[", "");
		str = str.replaceAll("\\]", "");
		str = str.replaceAll(" ", "").replaceAll(",", ".");
		return str;
	}

	private String[] parseInputParams(String str) {
		str = str.replaceAll(" ", "").replaceAll("'", "");
		str = str.substring(0, str.length() - 1);
		return str.split("=>");
	}
}
