import java.awt.Desktop;
import java.io.*;

class X_ShowGeneratedHtml {

  public X_ShowGeneratedHtml(String entity) {

    try {

      BufferedReader display_file_br = new BufferedReader(new FileReader("../parsed_data/"+entity+"/part-00000"));

      File f = new File("source.htm");
      BufferedWriter bw = new BufferedWriter(new FileWriter(f));
      bw.write("<html>");
      bw.write("<body>");
      bw.write("<h1>ShowGeneratedHtml source</h1>");
      bw.write("<textarea cols=75 rows=30>");

      String line;
      while ((line = display_file_br.readLine()) != null) {
          bw.write(line);
          bw.newLine();
      }

      bw.write("</text" + "area>");

      bw.write("<button type=\"button\" onclick=\"alert('Hello world!')\">Click Me!</button>");

      bw.write("</body>");
      bw.write("</html>");

      display_file_br.close();
      bw.close();

      Desktop.getDesktop().browse(f.toURI());

    } catch(Exception e) {
      System.out.println(e);
    }


  }
}
