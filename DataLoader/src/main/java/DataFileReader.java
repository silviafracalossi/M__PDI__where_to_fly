
import java.io.*;
import java.util.*;

public class DataFileReader {
  static String parsed_data_path = "../parsed_data/";

	public List<String> ParseFile(String entity) {
		String file = parsed_data_path+entity+"/part-00000";

		String row;
		List<String> list = new ArrayList<String>();

			try {

				// Reading the file
				BufferedReader br = new BufferedReader(new FileReader(file));

				// Removing the header of the file
				String header = br.readLine();
				System.out.println("[INFO] Parsing file " +file);
				System.out.println("[INFO] Header of file is " +header);

				// Transforming the content of the file into a List<String>
				while ((row = br.readLine()) != null) {
					list.add(row);
				}

			} catch (Exception e) {
				System.out.println("[ERROR] Error with the airlines parsing");
				System.out.println(e);
			}

			return list;
		}
}
