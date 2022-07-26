package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TutorialDaoImpl;
import pojos.Tutorial;

/**
 * Servlet implementation class TutorialDetailsServlet
 */
/*
 * TutorialDetailsServlet method=GET doGet set cont type , pw get tut dao from
 * session get tut details from dao layer by it's name --- Tutorial update
 * visits send updated tut details to the clnt (pw) back link <a
 * href='tutorials?topic_id=' tut.getTopicId() >Back </a>
 * 
 * log out link <a href='logout'>Log Me Out</a>
 */
@WebServlet("/tut_details")
public class TutorialDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set cont type
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			// get HttpSession from WC
			HttpSession session = request.getSession();
			// get tut dao from session scope
			TutorialDaoImpl tutDao = (TutorialDaoImpl) session.getAttribute("tut_dao");
			if (tutDao != null) {
				// get selected tut name from req param
				String tutName = request.getParameter("tut_name");
				// updating visits for selected tut
				System.out.println(tutDao.updateVisits(tutName));
				// get updated tut details from dao layer
				Tutorial tutorial = tutDao.getTutorialDetailsByName(tutName);
				// send tut details to user as dyn resp
				pw.print("<h5> Title " + tutorial.getTutName() + "</h5>");
				pw.print("<h5> Author  " + tutorial.getAuthor() + "</h5>");
				pw.print("<h5> Published on  " + tutorial.getPublishDate() + "</h5>");
				pw.print("<h5> Visits " + tutorial.getVisits() + "</h5>");
				pw.print("<h5> Contents  " + tutorial.getContents() + "</h5>");
				// back link
				pw.print("<h5> <a href='tutorials?topic_id=" + tutorial.getTopicId() + "'>Back</a></h5>");
			} else
				pw.print("<h5> NO Cookies : Session Tracking Failed!!!!!!!!!!!!!!!</h5>");
			// logout link
			pw.print("<h5> <a href='logout'>Log Me Out</a></h5>");

		} catch (Exception e) {
			throw new ServletException("err in do-get of " + getClass(), e);
		}
	}

}
