

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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		out.println("<html><body style='background-color:green;'><div style='margin:15%;'>");
		
		
		
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
		
		Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Create a statement out of connection
		
		ResultSet rst = stmt.executeQuery("select * from users where email='abdurehimsermolo002@gmail.com' || email='hajaebakemal@gmail.com' limit 1"); // Fetch the data from DB
		
		//Iterate through the Resultset Data
		while(rst.next())
		{
				if(rst.getString("email") != null)
				{
				response.sendRedirect("flightlist");
				}
				else
				{
					out.println(rst.getInt("id")+","+rst.getString("email")+"<br>"); // Print the rows in the Users table
				}
		}
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
