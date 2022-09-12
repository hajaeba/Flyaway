

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
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
 * Servlet implementation class FinishFlight
 */
@WebServlet("/FinishFlight")
public class FinishFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinishFlight() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
    int flt_id;
    String from1;
	 String To1;
	 String dep_date;
	 String dep_time;
	 String type;
	 int price;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		try
		{
		PrintWriter out=response.getWriter();
				
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");// load db connection details and connection files
		Properties props = new Properties();
		props.load(in); //load the db connection data onto properties
		
		DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
		//out.println("DB Connection Initialized. <br>");// Above line actually established the connection
		
		
		int flightid;
		flightid = Integer.parseInt(request.getParameter("flightid"));
		//out.println("Flight Number: "+flightid);
		
		Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Create a statement out of connection
		
		ResultSet rst = stmt.executeQuery("select * from flights_list"); // Fetch the data from DB
		
		//Iterate through the Resultset Data
	
		while(rst.next())
		{
			if (rst.getInt("flight_id")==flightid)
			{
			flt_id=rst.getInt("flight_id");
			 from1=rst.getString("from1");
			 To1=rst.getString("To1");
			 dep_date=rst.getString("dep_date");
			 dep_time=rst.getString("dep_time");
			 type=rst.getString("type");
			 price=rst.getInt("price");			
			}
		}
		
		
		String prepInsert = "INSERT INTO book(flight_id,from1,To1,dep_date,dep_time,type,price,status,passenger) VALUES(?,?,?,?,?,?,?,1,'Abdurehim_Sermolo')";
		//String prepInsert = "INSERT INTO book(flight_id,from1,To1,dep_date,dep_time,type,status,passenger,price) VALUES(?,'Addis Ababa','Cairo','2022-09-08','04:36:00','Economy',1,'Abdurehim_Sermolo',1150)";
		//String prepInsert = "INSERT INTO flights_list(from1,To1,dep_date,dep_time,type,status,price) VALUES(?,?,?,?,?,?,1,1150)";
		PreparedStatement pstmt = conn.getConnection().prepareStatement(prepInsert);
		pstmt.setInt(1, flt_id);
		pstmt.setString(2, from1);
		pstmt.setString(3, To1);
		pstmt.setString(4, dep_date);
		pstmt.setString(5, dep_time);
		pstmt.setString(6, type);
		pstmt.setInt(7, price);
		
		int count = pstmt.executeUpdate();
		response.sendRedirect("viewticket");
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
