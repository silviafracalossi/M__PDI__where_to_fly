import java.awt.Desktop;
import java.io.*;

class X_ShowGeneratedHtml {

  public X_ShowGeneratedHtml() {

    try {

      BufferedReader br = new BufferedReader(new FileReader("ex_out/part-00000"));

      File f = new File("source.htm");
      BufferedWriter bw = new BufferedWriter(new FileWriter(f));
      bw.write("<html>");
      bw.write("<body>");
      bw.write("<h1>ShowGeneratedHtml source</h1>");
      bw.write("<textarea cols=75 rows=30>");

      bw.write("textarea");

      String line;
      while ((line = br.readLine()) != null) {
          bw.write(line);
          bw.newLine();
      }

      bw.write("</text" + "area>");

      bw.write("<button type=\"button\" onclick=\"alert('Hello world!')\">Click Me!</button>");

      bw.write("</body>");
      bw.write("</html>");

      // br.close();
      bw.close();

      Desktop.getDesktop().browse(f.toURI());

    } catch(Exception e) {
      System.out.println(e);
      System.out.println("ciao");
    }


  }

}
