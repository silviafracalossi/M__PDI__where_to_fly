
 import java.util.*;

import org.hibernate.Session;
 import java.awt.Desktop;
 import java.io.*;

	/**
	* Class that implements the concrete access to mysql database
	*/
   public class AnalyticsServiceMysql implements Serializable {
       public Session session;

     public AnalyticsServiceMysql (Session session) {
         this.session = session;
     }
     
     public void displayAnalytics() {
    	 
    	 
    	 try {
    		 File f = new File("source.htm");
			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException e) {
			System.out.println("No file found");
			e.printStackTrace();
		}
     }
     
     // Shows the analytics
     public void generateVisualization () {

		 try {
		
		   // Opening file
		   File f = new File("source.htm");
		   Object[] row;
		   long flights_no;
		   BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		   
		   // First header tags
		   bw.write("<html>");
		   bw.write("<head><title>WhereToFly</title></head>");
		   bw.write("<body>");
		   bw.write("<h1><center>WhereToFly Data Analytics</centrer></h1>");
		   
		   // Leaflet library
		   bw.write("<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.5.1/dist/leaflet.css\"\r\n" + 
			   		"   integrity=\"sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==\"\r\n" + 
			   		"   crossorigin=\"\"/>");
		   bw.write("<!-- Make sure you put this AFTER Leaflet's CSS -->\r\n" + 
			   		" <script src=\"https://unpkg.com/leaflet@1.5.1/dist/leaflet.js\"\r\n" + 
			   		"   integrity=\"sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og==\"\r\n" + 
			   		"   crossorigin=\"\"></script>");
		   
		   // --------------------------------------------First query-------------------------------------------- 
		   bw.write("<h2> Question 1: What are the most expensive routes?</h2>");
		   bw.write("<div id=\"map1\" style=\"height: 450px; width: 1450\"></div>");
		   
		   // Script for the map
		   bw.write("<script>");
		   bw.write("var map = L.map('map1').setView([39.341097, -99.614587], 13).setZoom(4);");
		   bw.write("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\r\n" + 
		   		"    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\r\n" + 
		   		"}).addTo(map);");		   
		   
		   
		   // Get query result
		   List expensive_routes = getExpensiveRoutes();
			for (int i = 0; i<expensive_routes.size(); i++) {
				row = (Object[]) expensive_routes.get(i);
				float cost = (float) row[0];
				Route route = (Route) row[1];
				
				// Insert markers
				bw.write("L.marker(["+route.get_origin_airport().get_lat()+", "+route.get_origin_airport().get_lng() +"]).addTo(map)" + 
						"    .bindPopup('"+route.get_origin_airport().get_city()+"');");
				bw.write("L.marker(["+route.get_destination_airport().get_lat()+", "+route.get_destination_airport().get_lng() +"]).addTo(map)" + 
						"    .bindPopup('"+route.get_destination_airport().get_city()+"');");
				
				// Connect them with line
				bw.write("var pointA = new L.LatLng("
				                +"parseFloat("+route.get_origin_airport().get_lat()+"), " 
				                +"parseFloat("+route.get_origin_airport().get_lng() +"));");
				bw.write("var pointB = new L.LatLng(" 
						+"parseFloat("+route.get_destination_airport().get_lat()+"), " 
		                +"parseFloat("+route.get_destination_airport().get_lng() +"));");
				bw.write("var pointList = [pointA, pointB];");
				bw.write("var firstpolyline = new L.Polyline(pointList, {\r\n" + 
						"			    color: 'red',\r\n" + 
						"			    weight: 3,\r\n" + 
						"			    opacity: 0.5,\r\n" + 
						"			    smoothFactor: 1\r\n" + 
						"			});\r\n" + 
						"			firstpolyline.bindPopup('<b>"+ route.get_origin_airport().get_city() +
						" - "+ route.get_destination_airport().get_city() + "</b>: "+cost+"$ per passenger').addTo(map);");
				
			}
						
			bw.write("</script>");
			bw.write("<br>");
			bw.write("<br>");
			
			
			
			// --------------------------------------------Second query-------------------------------------------- 
		   bw.write("<h2> Question 2: What are the airports with the highest number of flights?</h2>");
		   bw.write("<div id=\"map2\" style=\"height: 450px; width: 1450\"></div>");
		   
		   // Script for the map
		   bw.write("<script>");
		   bw.write("var map = L.map('map2').setView([39.341097, -99.614587], 13).setZoom(4);");
		   bw.write("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\r\n" + 
		   		"    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\r\n" + 
		   		"}).addTo(map);");		   
		   
		   
		    // Get query result 
			List most_flights = getMostFlights();
	        for (int i = 0; i<most_flights.size(); i++) {
				row = (Object[]) most_flights.get(i);
				flights_no = (long) row[0];
				Airport airport = (Airport) row[1];
							
				// Insert markers
				bw.write("L.marker(["+airport.get_lat()+", "+airport.get_lng() +"]).addTo(map)" + 
						"    .bindPopup('<dl><dt><b>" + airport.get_name().replace("'", "\\'") +" in "+ airport.get_city() +", "+ airport.get_state() 
						+"</b></dt><dd>with "+ flights_no +" flights</dd></dl>');");
			}
						
			bw.write("</script>");
			bw.write("<br>");
			bw.write("<br>");
		   
		   
		   
		    // --------------------------------------------Third query-------------------------------------------- 
		   bw.write("<h2> Question 3: What are the two most connected airports?</h2>");
		   bw.write("<div id=\"map3\" style=\"height: 450px; width: 1450\"></div>");
		   
		   // Script for the map
		   bw.write("<script>");
		   bw.write("var map = L.map('map3').setView([36.612000, -119.674157], 13).setZoom(6);");
		   bw.write("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\r\n" + 
		   		"    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\r\n" + 
		   		"}).addTo(map);");		   
		   
		   
		   // Get query result
	       List connected_airports = getConnectedAirports();
	       row = (Object[]) connected_airports.get(0);
	       
	       flights_no = (long) row[0];
	       Route route = (Route) row[1];     
	        				
			// Insert markers
			bw.write("L.marker(["+route.get_origin_airport().get_lat()+", "+route.get_origin_airport().get_lng() +"]).addTo(map)" + 
					"    .bindPopup('"+route.get_origin_airport().get_city()+"');");
			bw.write("L.marker(["+route.get_destination_airport().get_lat()+", "+route.get_destination_airport().get_lng() +"]).addTo(map)" + 
					"    .bindPopup('"+route.get_destination_airport().get_city()+"');");
			
			// Connect them with line
			bw.write("var pointA = new L.LatLng("
			                +"parseFloat("+route.get_origin_airport().get_lat()+"), " 
			                +"parseFloat("+route.get_origin_airport().get_lng() +"));");
			bw.write("var pointB = new L.LatLng(" 
					+"parseFloat("+route.get_destination_airport().get_lat()+"), " 
	                +"parseFloat("+route.get_destination_airport().get_lng() +"));");
			bw.write("var pointList = [pointA, pointB];");
			bw.write("var firstpolyline = new L.Polyline(pointList, {\r\n" + 
					"			    color: 'red',\r\n" + 
					"			    weight: 3,\r\n" + 
					"			    opacity: 0.5,\r\n" + 
					"			    smoothFactor: 1\r\n" + 
					"			});\r\n" + 
					"			firstpolyline.bindPopup('<b>"+ 
					    route.get_origin_airport().get_city() +", "+route.get_origin_airport().get_state()+
					" - "+ route.get_destination_airport().get_city()  +", "+route.get_origin_airport().get_state()+
					"</b></dt><dd>with "+ flights_no +" flights</dd></dl>').addTo(map);");
						
			bw.write("</script>");
			bw.write("<br>");
			bw.write("<br>");
		   

		   
		   // --------------------------------------------Forth query-------------------------------------------- 
		   bw.write("<h2> Question 4: What are the airlines which serve the lowest number of flights?</h2>");
		      
		   bw.write("<table border=1px>");
		   bw.write("<tr><th>Airline Name</th><th>IATA code</th><th>Flights Offered</th></tr>");
		   bw.write("<col width=\"250\"><col width=\"150\"><col width=\"150\">");
		   List lowest_airline = getLowestFlightsAirline();
	       for (int i = 0; i<lowest_airline.size(); i++) {
				row = (Object[]) lowest_airline.get(i);
				flights_no = (long) row[0];
				Airline airline = (Airline) row[1];
				bw.write("<tr>");
		        bw.write("<td>"+airline.get_name()+"</td>");
		        bw.write("<td><center>"+airline.get_iata_code()+"</center></td>");
		        bw.write("<td align=\"right\">"+flights_no+"</td>");
		        bw.write("</tr>");
	        }
		   
	        bw.write("</table>");
	        bw.write("<br>");
	        bw.write("<br>");
		   
		   
			// --------------------------------------------Fifth query-------------------------------------------- 
			
           bw.write("<h2> Question 5: What are the airports causing the greatest departure delay per flight?</h2>");
		   bw.write("<div id=\"map5\" style=\"height: 450px; width: 1450\"></div>");
		   
		   // Script for the map
		   bw.write("<script>");
		   bw.write("var map = L.map('map5').setView([39.341097, -99.614587], 13).setZoom(4);");
		   bw.write("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\r\n" + 
		   		"    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\r\n" + 
		   		"}).addTo(map);");		   
		   
		   
		   // Get query result
		   List greatest_delay = getGreatestDelayAirport();
	        for (int i = 0; i<greatest_delay.size(); i++) {
				row = (Object[]) greatest_delay.get(i);
				double delay = (double) row[0];
				Airport airport = (Airport) row[1];
		        System.out.println("AVG delay of "+ row[0] +" minutes for airport "+ row[1]);

				// Insert markers
				bw.write("L.marker(["+airport.get_lat()+", "+airport.get_lng() +"]).addTo(map)" + 
						"    .bindPopup('<b><dl><dt>"+airport.get_city()+", "+airport.get_state()+
						"</b></dt><dd>with "+ delay + " minutes of delay per flight</dd></dl>');");
	        }
			
						
			bw.write("</script>");
			bw.write("<br>");
			bw.write("<br>");
	       
		
		   // End of file
		   bw.write("</body>");
		   bw.write("</html>");
		   bw.close();
		   Desktop.getDesktop().browse(f.toURI());
		
		 } catch(Exception e) {
		   System.out.println(e);
		 }

     }

	  // 1) What are the most expensive routes?
	  public List getExpensiveRoutes () {
	    return session.createQuery(
	        "select max(price), f.route from Fare f group by f.route order by max(price) desc")
	    	.setMaxResults(10)
	        .list();
	  }
	 
	  // 2) What are the airports with the highest number of flights?
	  public List getMostFlights () {
	    return session.createQuery(
	        "select count(*), f.route_code.origin_airport from Flight f group by f.route_code.origin_airport order by count(*) desc")
	    	.setMaxResults(10)
	        .list();
	  }
		      
	  // 3) What are the two most connected airports?
	  public List getConnectedAirports () {
	      return session.createQuery(
	          "select count(*), f.route_code from Flight f group by f.route_code order by count(*) desc")
	      	  .setMaxResults(1)
	          .list();
	}
	  
	  // 4) What are the airlines which serve the lowest number of flights?
	  public List getLowestFlightsAirline () {
	      return session.createQuery(
	          "select count(*), f.airline_iata from Flight f group by f.airline_iata order by count(*) asc")
	      	  .setMaxResults(10)
	          .list();
	}
	  
	  // 5) What are the airports causing the greatest departure delay per flight?
	  public List getGreatestDelayAirport () {
	      return session.createQuery(
	          "select AVG(f.departure_time-f.scheduled_departure)/100, f.route_code.origin_airport "
	          + "from Flight f "
	          + "group by f.route_code.origin_airport order by AVG(f.departure_time-f.scheduled_departure)/100 desc")
	      	  .setMaxResults(10)
	          .list();
	}
	        

 }
