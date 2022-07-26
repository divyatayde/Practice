package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TopicDaoImpl;
import pojos.Topic;
import pojos.User;

/**
 * Servlet implementation class TopicsServlet
 */
@WebServlet("/topics")
public class TopicsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// generate HTML form dynamically : to show avlable topics
		System.out.println("in do-get of " + getClass());
		// set cont type
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			pw.print("<h4> In Topics Page : </h4>");

			// 1. Get HttpSession from WC
			HttpSession session = request.getSession();
			// chk ofr new or existing session
			System.out.println("From topics page session is new " + session.isNew());// f iff cookies are accepted !
			System.out.println("session id " + session.getId());
			// 2.get user details from HttpSession's attribute map (i.e inner map)
			User user = (User) session.getAttribute("user_details");
			if (user != null) {
				// get topic dao from HttpSession scope
				TopicDaoImpl topicDao = (TopicDaoImpl) session.getAttribute("topic_dao");

				// display validated user details
				pw.print("<h5>  User Details  retrieved from a session " + user);
				// dyn form generation
				pw.print("<form action='tutorials'>");
				// <input type='radio' name='topic_id' value='actual topic id '/>lable : topic
				// name
				for (Topic t : topicDao.getAllTopics())
					pw.print("<input type='radio' name='topic_id' value='" + t.getTopicId() + "' required />" + t.getTopicName()
							+ "<br/>");
				pw.print("<input type='submit' value='Choose Topic'/>");
				pw.print("</form>");

			} else
				pw.print("<h5> NO Cookies : Session Tracking Failed!!!!!!!!!!!!!!!</h5>");

		} catch (Exception e) {
			throw new ServletException("err in do-get of " + getClass(), e);
		}
	}

}
