import java.util.ArrayList;
import java.util.List;

public class Main {


	public static void main(String[] args) {

		// Object of useful classes
		AirlineController airline_controller = new AirlineController();
		DataFileReader dfr = new DataFileReader();

		List<String> content;

		// Airlines parser
		content = dfr.ParseFile("airlines");
		airline_controller.createMultipleAirlines(content);





		// X_AirlinesParser ap = new X_AirlinesParser();
		//X_ShowGeneratedHtml sgh = new X_ShowGeneratedHtml();
	}

}
