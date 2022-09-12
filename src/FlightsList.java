

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyaway.DBConnection;

/**
 * Servlet implementation class FlightsList
 */
@WebServlet("/FlightsList")
public class FlightsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightsList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
		PrintWriter out=response.getWriter();
		out.println("<html><body style='background-image: url(img/bg.jpg);background-repeat: no-repeat;background-position: center;background-size: cover; background-color:eeeee;'><div style='margin:10%;'>");
				
		
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");// load db connection details and connection files
		Properties props = new Properties();
		props.load(in); //load the db connection data onto properties
		
		DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
		//out.println("DB Connection Initialized. <br>");// Above line actually established the connection
		
		
		String dbemail;
		String formemail;
		String email1=request.getParameter("email");
		String password1= request.getParameter("password");
		
		formemail=email1;
		
out.println("<h2 style='padding: 8px;padding-left: 40%;'>List Of Flights</h2>");
		
		out.println("<head>");
		out.println("<style>");
		
		
		
		out.println("table {");
		out.println("font-family: arial, sans-serif;");
		out.println("width: 100%;");
		out.println("padding-left: 0%;");
		out.println("}");

		out.println("td, th {");
		out.println(" border: 1px solid #dddddd;");
		out.println("text-align: left;");
		out.println("  padding: 8px;");
		  
		out.println("}");

		out.println("tr:nth-child(even) {");
		out.println(" background-color: #dddddd;");
		out.println("}");
		
		out.println("</style>");
		out.println("</head>");
		
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>Flight Number</th>");
		out.println("<th>From</th>");
		out.println("<th>To</th>");
		out.println("<th>Departure Date</th>");
		out.println("<th>Departure Time</th>");    
		out.println("<th>Type</th>");
		out.println("<th>Price</th>");
		out.println("<th>Action</th>");
		out.println("</tr>"); 
		
		Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Create a statement out of connection
		
		ResultSet rst = stmt.executeQuery("select * from flights_list limit 100"); // Fetch the data from DB
		
		//Iterate through the Resultset Data
		
		while(rst.next())
		{
		out.println("<tr>");
		out.println("<td>"+rst.getInt("flight_id")+"</td>");
		out.println("<td>"+rst.getString("from1")+"</td>");
		out.println("<td>"+rst.getString("To1")+"</td>");
		out.println("<td>"+rst.getString("dep_date")+"</td>");
		out.println("<td>"+rst.getString("dep_time")+"</td>");   
		out.println("<td>"+rst.getString("type")+"</td>");  
		out.println("<td>$"+rst.getInt("price")+"</td>");  
		out.println("<td>"+"<a href=selectflight?flightid="+rst.getInt("flight_id")+">Select Flight</a>"+"</td>"); 
		out.println("</tr>");
		}
		out.println("</table>"); 	
		stmt.close();
		out.println("</body></html>");
		conn.closeConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
