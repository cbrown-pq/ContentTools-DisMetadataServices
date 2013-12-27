package com.proquest.mtg.dismetadataservice.format;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.utils.writer.IWriter;

public class USMarcFormat implements IMetaDataFormats {
	
	private final IMarcProvider marcDataProvider;
	private final IWriter marcDataWriter;
	
	@Inject
	public USMarcFormat(IMarcProvider marcDataProvider, IWriter marcDataWriter) {
		this.marcDataProvider = marcDataProvider;
		this.marcDataWriter = marcDataWriter;
	}

	public IMarcProvider getMarcDataProvider() {
		return marcDataProvider;
	}

	public IWriter getMarcDataWriter() {
		return marcDataWriter;
	}

	@Override
	public String makeFor(String pubNum) throws Exception {
		return getMarcDataProvider().getMarcResultFor(pubNum);
	}

}
