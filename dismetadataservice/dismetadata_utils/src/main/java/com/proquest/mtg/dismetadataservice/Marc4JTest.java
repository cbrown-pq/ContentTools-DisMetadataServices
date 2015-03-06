package com.proquest.mtg.dismetadataservice;

import java.io.FileInputStream;
import java.io.InputStream;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcWriter;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.Record;

public class Marc4JTest {

    public static void main(String args[]) throws Exception {
        InputStream input = new FileInputStream("C:\\temp\\Marc\\MARC21RDA.txt");
        MarcReader reader = new MarcStreamReader(input);
        MarcWriter writer = new MarcXmlWriter(System.out, true);
        

        while (reader.hasNext()) {
            Record record = reader.next();
            writer.write(record);
        }

        writer.close();
	}
}
