package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Aranzman;
import dao.AranzmanDAO;

/**
 * Servlet implementation class AranzmanServlet
 */
@WebServlet("/AranzmanServlet")
public class AranzmanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AranzmanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		ServletContext ctx = getServletContext();
		ctx.setAttribute("aranzmani", new AranzmanDAO());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String id_aranzmana = request.getParameter("ID"); 
		if(id_aranzmana!=null) {
			ServletContext ctx = getServletContext();
			AranzmanDAO aranzmani = (AranzmanDAO) ctx.getAttribute("aranzmani");
			aranzmani.confirmAranzman(id_aranzmana);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/aranzmaniOverview.jsp");
			dispatcher.forward(request, response);
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/aranzmani.jsp");
			dispatcher.forward(request, response);			
		}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		String id_aranzmana = request.getParameter("id_aranzmana");
		String full_name = request.getParameter("full_name");
		String passport_number = request.getParameter("passport_number");
		String destination_name = request.getParameter("destination_name");
		String vacation_type = request.getParameter("vacation_type");
		String price = request.getParameter("price");			
		ServletContext ctx = getServletContext();
		AranzmanDAO aranzmani = (AranzmanDAO) ctx.getAttribute("aranzmani");
		
		if(id_aranzmana.matches("[0-9][0-9]-[0-9][0-9][0-9]")) { //ovo ne radi
			response.sendRedirect("/JSP/aranzmani.jsp");			
			return;
		}		
		aranzmani.addAranzman(new Aranzman(id_aranzmana,full_name,passport_number,destination_name,vacation_type,price));
		
		
		System.out.println("kreirao i dodao novi aranzman");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/aranzmaniOverview.jsp");
		System.out.println("OVDE OVDE SAM");
		
		
		
		System.out.println("pre dispatcher forward");
		dispatcher.forward(request, response);
		System.out.println("odradio dispatcher forward");
		
	}

}
