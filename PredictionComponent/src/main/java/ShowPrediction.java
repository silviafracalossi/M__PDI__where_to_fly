import java.awt.Desktop;
import java.io.*;

class ShowPrediction {

    public ShowPrediction() {

        try {

            // Useful variables for the file
            String line;
            String file_path = "../ex_out/prediction/part-00000";
            String results_path = "../data/final_flight_results.txt";
            File f = new File("source.htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            BufferedReader display_file_br = new BufferedReader(new FileReader(file_path));
            BufferedReader results_br = new BufferedReader(new FileReader(results_path));

            // HTML file header
            bw.write("<html>");
            bw.write("<body>");
            bw.write("<h1><center>Show Prediction Source</center></h1>");
            bw.write("<h3><center>Correct answers: 29/34</center></h3>");
            bw.write("<br>");

            // Creation of the prediction table
            bw.write("<table border=1px>");
            bw.write("<tr><th>Training Data</th><th>Expected Result</th><th>Correctness</th></tr>");
            bw.write("<col width=\"400\"><col width=\"150\"><col width=\"150\">");

            while ((line = display_file_br.readLine()) != null) {

                // Preparing data
                String result_row = results_br.readLine().split("/")[1];
                int result = (result_row.equals("left") ? 5 : 2);
                int prediction = Integer.parseInt(line.substring(line.length()-4, line.length()-3));

                // Printing data in html
                bw.write("<tr>");
                bw.write("<td>"+line+"</td>");
                bw.write("<td><center>"+result+"</center></td>");
                bw.write("<td align=\"right\"><center>");
                bw.write((result == prediction) ? "Correct!" : "Wrong.");
                bw.write("</center></td>");
                bw.write("</tr>");
            }

            display_file_br.close();
            results_br.close();

            bw.write("</table>");
            bw.write("</body>");
            bw.write("</html>");
            bw.close();

            Desktop.getDesktop().browse(f.toURI());

        } catch(Exception e) {
            System.out.println(e);
        }


    }
}
