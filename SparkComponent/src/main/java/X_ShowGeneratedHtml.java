import java.awt.Desktop;
import java.io.*;

class X_ShowGeneratedHtml {

  public X_ShowGeneratedHtml(String entity) {

    try {

      File f = new File("source.htm");
      BufferedWriter bw = new BufferedWriter(new FileWriter(f));
      bw.write("<html>");
      bw.write("<body>");
      bw.write("<h1>ShowGeneratedHtml source</h1>");
      bw.write("<textarea cols=300 rows=30>");

      int i = 0;
      String a = "";
      String line;

      // Preparing the number of the file
      a = i + "";
      while (a.length() != 5) {
        a = "0" + a;
      }

      // Checking if exists file
      String file_path = "../parsed_data/"+entity+"/part-"+a;
      while (new File(file_path).exists()) {

        // Reading the file
        System.out.println("Reading file " +file_path);
        BufferedReader display_file_br = new BufferedReader(new FileReader(file_path));
        while ((line = display_file_br.readLine()) != null) {
          bw.write(line);
          bw.newLine();
        }
        display_file_br.close();

        // Preparing the number of the file
        i = i + 1;
        a = i + "";
        while (a.length() != 5) {
          a = "0" + a;
        }
        file_path = "../parsed_data/"+entity+"/part-"+a;
      }

      bw.write("</text" + "area>");

      bw.write("<button type=\"button\" onclick=\"alert('Hello world!')\">Click Me!</button>");

      bw.write("</body>");
      bw.write("</html>");

      bw.close();

      Desktop.getDesktop().browse(f.toURI());

    } catch(Exception e) {
      System.out.println(e);
    }


  }
}
