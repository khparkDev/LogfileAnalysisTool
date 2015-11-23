package com.khpark.log.analysis.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khpark.log.analysis.service.impl.LogFileAnalysisServiceImpl;

public class LogFileAnalysisServiceTest {
	@Autowired
	private LogFileAnalysisServiceImpl logFileAnalysisService;

	@Before
	public void init() throws FileNotFoundException {
		String filepath = "D:\\sources_java\\logfile_analysis_tool\\resources\\";
		String filename = "billrpc-review-2014-12-16.log";

		//FileChannel cin = new FileInputStream(new File(filepath + filename)).getChannel();
		FileChannel cin = new FileInputStream(new File("D:\\sources\\FileSplit\\resources\\sample.log")).getChannel();

		//logFileAnalysisService.setFileInfo(filepath, filename);
	}

	@Test
	public void logFileAnalysisTest() throws IOException {
		logFileAnalysisService.logFileAnalysis();
	}
}
