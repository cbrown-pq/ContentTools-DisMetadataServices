package com.proquest.mtg.dismetadataservice.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.Record;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.exodus.IMarcProvider;
import com.proquest.mtg.dismetadataservice.marc.MarcParser;


public class MarcXmlFormat implements IMetaDataFormats {

	private final IMarcProvider marcDataProvider;
	public static final String kMarcRecordDelim = Character.toString(
			MarcParser.kMarcRecordDelimChar);
	
	@Inject
	public MarcXmlFormat(IMarcProvider marcDataProvider) {
		this.marcDataProvider = marcDataProvider;
	}

	public IMarcProvider getMarcDataProvider() {
		return marcDataProvider;
	}

	@Override
	public String makeFor(String pubNum) throws Exception {
		String marc21String =  getMarcDataProvider().
				getMarc21RDAResultFor(pubNum).toMarcString();
		if (null == marc21String || marc21String.isEmpty()) {
			throw new MarcException("Failed  to generate MARC21RDA for " +
					"publication :" + pubNum);
		}
		String marcRecordString =  Lists.newArrayList(
				marc21String.split(kMarcRecordDelim)).get(0);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream stream = new ByteArrayInputStream(
				marcRecordString.getBytes(StandardCharsets.UTF_8));
		MarcReader reader = new MarcStreamReader(stream, "UTF8");
		/* By default MarcXmlWriter takes UTF-8 encoding */
        MarcWriter writer = new MarcXmlWriter(baos, true);

        while (reader.hasNext()) {
            Record record = reader.next();
            writer.write(record);
        }
        writer.close();
        return new String(baos.toByteArray(), Charset.forName("UTF-8"));
	}
}
