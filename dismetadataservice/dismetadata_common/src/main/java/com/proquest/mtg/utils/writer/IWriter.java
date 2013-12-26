package com.proquest.mtg.utils.writer;

import java.io.IOException;

public interface IWriter {
	void reset() throws IOException;
	void write(String x) throws IOException;
	void close() throws IOException;
}
