package com.khpark.log.analysis.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class NioTest {
	@Test
	public void byteBufferTest() throws IOException {
		String filepath = "D:\\sources_java\\logfile_analysis_tool\\src\\test\\java\\com\\tmoncorp\\delivery\\test\\service\\";
		String filename = "test.txt";
		File srcFile = new File(filepath + filename);
		FileInputStream fis = new FileInputStream(srcFile);
		ByteBuffer buf = ByteBuffer.allocate(100);
		FileChannel cin = fis.getChannel();

		while (cin.read(buf) > -1) {
			// read작업에 의해 buf에 담기면서 position이 제일 마지막으로 위치하게 됨. 따라서 flip을 해줘야 버퍼 read가 가능
			buf.flip();

			while (buf.position() < buf.limit()) {
				//System.out.println("# bufinfo = " + buf);
				System.out.print((char) buf.get());
			}

			buf.flip();
		}

		fis.close();
	}
}
