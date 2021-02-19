
import java.io.*;
import java.util.*;

public class DataFileReader {
  static String parsed_data_path = "../parsed_data/";

	public List<String> ParseFile(String entity, Boolean has_header) {
		String directory_path = parsed_data_path+entity+"/part-";

		int i = 0;
		String file_number = "";
		String row;
		List<String> list = new ArrayList<String>();

			try {

				// Preparing the number of the file, starting from part-00000
				file_number = i + "";
				while (file_number.length() != 5) {
					file_number = "0" + file_number;
				}

				// Checking if exists file
				String file_path = directory_path + file_number;
				while (new File(file_path).exists()) {

					//Reading file
					System.out.println("[INFO] Parsing file " +file_path);
					BufferedReader file_br = new BufferedReader(new FileReader(file_path));

					// Removing the header of the file
					if (has_header) {
						String header = file_br.readLine();
						System.out.println("[INFO] Header of file is " +header);
					}

					// Reading each line of the file
					while ((row = file_br.readLine()) != null) {
						list.add(row);
					}
					file_br.close();

					// Preparing the number of the file
					i = i + 1;
					file_number = i + "";
					while (file_number.length() != 5) {
						file_number = "0" + file_number;
					}
					file_path = directory_path + file_number;
				}

			} catch (Exception e) {
				System.out.println("[ERROR] Error with the parsing of " + entity);
				System.out.println(e);
			}

			return list;
		}
}
