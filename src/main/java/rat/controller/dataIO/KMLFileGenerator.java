package rat.controller.dataIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import rat.calculation.Project;
import rat.calculation.calculator.CalculationStepResult;

/**
 * This static holds the function and the template for generating a google maps
 * .kml file of the flight path.
 * 
 * @author Gerhard Mesch
 * 
 */
public class KMLFileGenerator {

	/**
	 * 
	 * @param file
	 * @param projectModel
	 * @throws IOException
	 */
	public static void generateKMLFile(File file, Project projectModel)
			throws IOException {
		// I like it quick an dirty! You too? Otherwise implement it nicely :)
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writeFileHeader(writer, projectModel.rocket.getName());
		writer.newLine();
		java.util.Iterator<CalculationStepResult> iter = projectModel
				.getCalculationResult().getCalculationResult().iterator();
		while (iter.hasNext()) {
			CalculationStepResult step = iter.next();
			writer.write(step.getKMLString());
		}
		if (writer != null) {
			writeFileEnding(writer);
			writer.flush();
			writer.close();
		}
	}

	private static void writeFileHeader(BufferedWriter writer, String RocketName)
			throws IOException {
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n "
				+ " <kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n "
				+ "<Document>\n 	<name>\n	"
				+ RocketName
				+ " flight path.kml\n	 </name>\n 	<Style id=\"sn_ylw-pushpin\">\n 	<IconStyle>\n			<scale>1.1</scale>\n "
				+ "	<Icon>\n	<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>\n 			</Icon>\n			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n "
				+ " </IconStyle>\n		<LineStyle>\n			<color>ff0000ff</color>\n			<width>25</width>\n		</LineStyle>\n "
				+ "	</Style>\n	<StyleMap id=\"msn_ylw-pushpin\">\n  		<Pair>\n 			<key>normal</key>\n 			<styleUrl>#sn_ylw-pushpin</styleUrl>\n"
				+ "		</Pair>\n		<Pair>\n			<key>highlight</key>\n			<styleUrl>#sh_ylw-pushpin</styleUrl>\n		</Pair>\n"
				+ "	</StyleMap>\n	<Style id=\"sh_ylw-pushpin\">\n		<IconStyle>\n			<scale>1.3</scale>\n			<Icon>\n"
				+ "				<href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>\n			</Icon>\n"
				+ "			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n		</IconStyle>\n"
				+ "		<LineStyle>\n			<color>ff0000ff</color>\n			<width>25</width>\n		</LineStyle>\n	</Style>\n"
				+ "	<Placemark>\n		<name>\n		 "
				+ RocketName
				+ " flight path\n		</name>\n		<styleUrl>#msn_ylw-pushpin</styleUrl>\n"
				+ "		<LineString>\n			<tessellate>1</tessellate>\n			<altitudeMode>absolute</altitudeMode>\n"
				+ "<coordinates>");
	}

	private static void writeFileEnding(BufferedWriter writer)
			throws IOException {
		writer.write("\n</coordinates>\n		</LineString>\n	</Placemark>\n</Document>\n</kml>\n");
	}

}
