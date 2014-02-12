package com.proquest.mtg.dismetadataservice.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.metadata.TextNormalizer;

public class MakeCSVRecordFactory_Abstract_Tests {
	CSVRecordFactory factory;
	String header = "";
	DisPubMetaData metadata;

	@Before
	public void setUp() throws Exception {
		factory = new CSVRecordFactory();
		metadata = new DisPubMetaData();
		for (String curheader : factory.getHeaders()) {
			header += curheader + ",";
		}
	}

	@Test
	public void makeWithEmptyAbstract() throws Exception {
		String abstractString = null;
		metadata.setAbstract(abstractString);
		String expectedCSVData = header
				+ "\r\n,,,,,,,,,,,,,,,,,,,,,,,,,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

	@Test
	public void withAbstract() throws Exception {
		String abstractText = "<abstractText>Aim</italic>. The aim was to evaluate the results of endovascular aneurysm repair (EVAR) of abdominal aortic aneurysms (AAA) in terms of intra-AAA pressure and clinical outcomes. ^   <italic>Methods and results</italic>. 0.014-inch guide-wire-mounted tip-pressure sensors were used for direct intra-aneurysm sac pressure measurement (DISP) through translumbar puncture of the AAA. Mean Pressure Index was calculated (MPI) as the percentage of mean intra-aneurysm pressure relative to the simultaneous mean intra-aortic pressure. The pressure sensor was evaluated in vitro, in an aneurysm model, showing good agreement with model output (2 mm Hg). Measurements within aneurysm thrombus agreed well with the simultaneous ones from the model's lumen (1 mm Hg). Intra-observer variability of DISP in 15 patients undergoing double measurement, by separate punctures of the AAA, showed MPI median variability of 0% (r = .962, p &lt; .0001). Median MPI in patients with shrinking (n = 11), unchanged (n = 10) and expanding (n = 9) aneurysms without endoleaks at least 1 year after EVAR was 19%, 30% and 59%, respectively. Pulse pressure was also higher in expanding compared to the shrinking AAAs (10 vs. 2 mm Hg, p &lt; .0001). Seven of the 10 patients with unchanged AAAs underwent further imaging follow-up after DISP; 2 expanded (MPIs 47%&ndash;63%), 4 shrank (MPI 21%&ndash;30%) and I continued unchanged (MPI 14%). Type II endoleaks (6 patients, 7 DISP) seemed to be a varied entity with different degrees of AAA pressurization, even in the same patient at different occasions (range MPI 22%&ndash;92%). Successful endoleak embolization resulted in pressure reduction. Between 1998 and 2001, 168 consecutive patients with non-ruptured AAAs were treated by EVAR (n = 117) and open repair (OR) due to anatomical restrictions for EVAR (group A, n = 40) or young age with long life-expectancy (n = 11). Thirty-day mortality in EVAR (n = 117), OR group A and B was 2.6%, 15% and 0%, respectively. EVAR patients had higher ASA classifications (p &lt; .0001). Late survival was not different between the groups and late reinterventions, mainly endovascular, were more frequent in EVAR. ^   <italic>Conclusions</italic>. DISP is a reliable and reproducible technique for measuring intra-AAA pressure. It may become an important tool for EVAR evaluation by detecting EVAR outcomes early. High pressure is associated with AAA expansion, while low pressure with shrinkage. EVAR provides good results for AAA treatment even with the inclusion of high risk patients. The wide application of EVAR may affect the results of OR, since this tends to be performed in older patients with severe comorbidity and challenging anatomy. OR continues to the first-option for low-risk young patients. ^";
		TextNormalizer abstractNormalizer = new TextNormalizer();
		abstractText = abstractNormalizer.applyTo(abstractText);
		metadata.setAbstract(abstractText);
		String expectedCSVData = header + "\r\n" + ",,,,,,,,,,,,,,,,,,,,,,,"
				+ "\"" + abstractText + "\""
				+ ",,,,\"N\",,,,,,,,,,,,,,\"N\",,,";
		String csvData = factory.makeFrom(metadata);
		assertThat(csvData, is(expectedCSVData));
	}

}
