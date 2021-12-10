package Servlets;

import java.io.IOException; 
import java.io.PrintWriter; 
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 


// Servlet Name 
@WebServlet("/MainServlet") 
public class MainServlet extends HttpServlet { 
	private static final long serialVersionUID = 1L; 

	protected void doPost(HttpServletRequest request, 
HttpServletResponse response) 
		throws ServletException, IOException 
	{ 
		try { 

			// Initialize the database 
			Connection con = DatabaseConnection.initializeDatabase(); 

			// Create a SQL query to insert data into demo table 
			// demo table consists of two columns, so two '?' is used 
			PreparedStatement all = con.prepareStatement("select * from mail");
			PreparedStatement ss = con.prepareStatement("select * from mail where Expediteur=?");
			PreparedStatement st = con.prepareStatement("insert into mail values(?, ? ,? ,? , ?)"); 
			PreparedStatement ps = con.prepareStatement("delete from mail where id=? and Expediteur=? and Destinataire=? AND Sujet=? AND Objet=?");

			// For the first parameter, 
			// get the data using request object 
			// sets the data to st pointer 
			String action = request.getParameter("action");
			if (action.equals("insert")){ 
				st.setInt(1, Integer.valueOf(request.getParameter("ID"))); 

				// Same for second parameter 
				st.setString(2, request.getParameter("exp")); 
				st.setString(3, request.getParameter("dest")); 
				st.setString(4, request.getParameter("sujet")); 
				st.setString(5, request.getParameter("objet")); 

				// Execute the insert command using executeUpdate() 
				// to make changes in database 
				st.executeUpdate(); 
				// Close all the connections 
				st.close(); 
				con.close(); 

				// Get a writer pointer 
				// to display the successful result 
				PrintWriter out = response.getWriter(); 
				out.println("Success");
			}
			else if (action.equals("delete")) {
				ps.setInt(1, Integer.valueOf(request.getParameter("IDdelete"))); 
		        ps.setString(2, request.getParameter("expdelete"));
		        ps.setString(3, request.getParameter("destdelete"));
		        ps.setString(4, request.getParameter("sujetdelete"));
		        ps.setString(5, request.getParameter("objetdelete"));

		        int i = ps.executeUpdate();

		        if(i > 0) {
		        	PrintWriter out = response.getWriter();
		            //out.println("User successfully removed...");
		        }
			}
			else if(action.equals("search")) {
		         PrintWriter out = response.getWriter();
				ss.setString(1,request.getParameter("expsearch"));
				 ResultSet rs=ss.executeQuery(); 
				 if(rs.next()) {
					 out.println("<html>");
					 out.println("<head></head>");
					 out.println("<body>");
				 Email Ne = new Email();
				 Ne.setId(rs.getInt(1));
				 Ne.setExpediteur(rs.getString(2));
				 Ne.setDestinataire(rs.getString(3));
				 Ne.setSujet(rs.getString(4));
				 Ne.setObjet(rs.getString(5));
				 out.println("ID : "+Ne.getId()+"<br>Expediteur : "+Ne.getExpediteur()+"<br>Destinataire : "+Ne.getDestinataire()+"<br>Sujet : "+Ne.getSujet()+"<br>Objet : "+Ne.getObjet());
				 out.println("</body>");
				 out.println("</html>");
				 }
			}
			else if(action.equals("fetch")) {
				PrintWriter out = response.getWriter();
				 ResultSet rsa=all.executeQuery(); 
				 out.println("<html>");
				 out.println("<head>");
				 out.println("</head>");
				 out.println("<body>");
				 out.println("<ol>");
				 while(rsa.next()) {
					 Email Ne = new Email();
					 Ne.setId(rsa.getInt(1));
					 Ne.setExpediteur(rsa.getString(2));
					 Ne.setDestinataire(rsa.getString(3));
					 Ne.setSujet(rsa.getString(4));
					 Ne.setObjet(rsa.getString(5));
					 out.println("<li>ID : "+Ne.getId()+"</li><ul><li>Expediteur : "+Ne.getExpediteur()+"</li><li>"+"Destinataire : "+Ne.getDestinataire()+"</li><li>"+"Sujet : "+Ne.getSujet()+"</li><li>"+"Objet : "+Ne.getObjet()+"</li></ul>");
				 }
				 out.println("</ol>");
				 out.println("</body>");
				 out.println("</html>");
			}
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

  response.setContentType("application/json;charset=UTF-8");
 try (PrintWriter out = response.getWriter()) {

        }
    }
}
