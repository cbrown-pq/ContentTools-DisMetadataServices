package com.proquest.mtg.utils.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class StringWriter implements IWriter {

	private ByteArrayOutputStream byteArrayOs;
	private PrintWriter printWriter;
	
	public StringWriter() {
		resetInternal();
	}
	
	@Override
	public void reset() throws IOException {
		resetInternal();
	}

	@Override
	public void write(String x) throws IOException {
		printWriter.write(x);
		printWriter.flush();
	}

	@Override
	public void close() {
		printWriter.close();
	}

	public String toString() {
		try {
			return byteArrayOs.toString("utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private void resetInternal() {
		this.byteArrayOs = new ByteArrayOutputStream();
		this.printWriter = new PrintWriter(byteArrayOs);
	}

}
