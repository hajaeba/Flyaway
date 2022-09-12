

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
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
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
		int contact;
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String cpassword=request.getParameter("cpassword");
		String address=request.getParameter("address");
		contact=Integer.parseInt(request.getParameter("contact"));
		
		//insert statement
		
		//Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);//create statement of connection
		//stmt.executeUpdate("insert into users (fname,lname,email,password,cpassword,address,contact,role,date,status)"
			//	+ " values(fname,lname,email,password,cpassword,address,contact,'User', now(),1)");//insert some data
		//out.println("Data Inserted SuccessFully. <br>");
		
		String prepInsert = "INSERT INTO users(fname,lname,email,password,cpassword,address,contact,role,date,status) VALUES(?,?,?,?,?,?,?,'User', now(),1)";

		PreparedStatement pstmt = conn.getConnection().prepareStatement(prepInsert);
		pstmt.setString(1, fname);
		pstmt.setString(2, lname);
		pstmt.setString(3, email);
		pstmt.setString(4, password);
		pstmt.setString(5, cpassword);
		pstmt.setString(6, address);
		pstmt.setInt(7, contact);

		int count = pstmt.executeUpdate();
		out.println("<h3 style='color:white;'>The Account has been Successfully Created!!!</h3><br>");
		out.println(count+"<br>");
		out.println("<a href='login.html' style='color:White;font-size:20;'>Login</a><br>");
		out.println("</div></body></html>");
		
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
