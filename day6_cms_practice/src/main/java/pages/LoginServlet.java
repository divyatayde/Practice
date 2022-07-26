package pages;

import static utils.DBUtils.closeConnection;
import static utils.DBUtils.openConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TopicDaoImpl;
import dao.TutorialDaoImpl;
import dao.UserDaoImpl;
import pojos.User;

/**
 * Servlet implementation class LoginServlet
 */
//Entry in request mapping Key /validate Value : pages.LoginServlet
//@WebServlet(value = "/validate", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDaoImpl userDao;
	private TopicDaoImpl topicDao;
	private TutorialDaoImpl tutorialDao;

	public LoginServlet() {
	     System.out.println("in def ctor  of" + getClass() + "config=" + getServletConfig());
       //System.out.println("ctx="+getServletContext());
	}

	public LoginServlet(String mesg) {
		System.out.println("in def ctor  of" + getClass() + "config=" + getServletConfig());
	}

	/**
	 * @see Servlet#init()
	 */
	@Override
	public void init() throws ServletException {
		try {
			ServletConfig config = getServletConfig();
			System.out.println("config=" + config);
			
			openConnection(config.getInitParameter("url"),config.getInitParameter("user_name"),config.getInitParameter("pwd"));
			userDao = new UserDaoImpl();
			topicDao = new TopicDaoImpl();
			tutorialDao = new TutorialDaoImpl();
		} catch (Exception e) {
			// System.out.println(+" err "+e);
			// ServletException(String mesg, Throwable rootCause)
			throw new ServletException("err in init of " + getClass(), e);
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	@Override
	public void destroy() {
		try {
			userDao.cleanUp();
			topicDao.cleanUp();
			tutorialDao.cleanUp();
			closeConnection();
		} catch (Exception e) {
			// System.out.println("err in destroy of "+getClass()+" err "+e); reco.
			throw new RuntimeException("err in destroy " + getClass(), e);
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in do-post of " + getClass());
		// set cont type
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			String email = request.getParameter("em");
			String password = request.getParameter("pass");
			// invoke user dao's method for authentication
			User user = userDao.authenticateUser(email, password);
			if (user == null) // => failed login --- send retry link ---Client Pull I
				pw.print("<h4>Invalid Login , Please <a href='login.html'>Retry</a></h4>");
			else // login success --

			{
				// 1. Get HttpSession from WC
				HttpSession hs = request.getSession();
				// chk if it's new
				System.out.println("From login page : session is new " + hs.isNew());// t
				System.out.println("session id " + hs.getId());
				System.out.println("Session creation time " + new Date(hs.getCreationTime()));
				// 2. save validated user details , under session scope , as server side session
				// scope attr.
				hs.setAttribute("user_details", user);
				// add dao instances in the session scope(i.e in the inner session scoped attr
				// map) , so that other servlets can share the same.
				hs.setAttribute("topic_dao", topicDao);
				hs.setAttribute("tut_dao", tutorialDao);
				// auto navigate the clnt to topics page : in the NEXT request
				// coming from clnt browser
				// API of HttpServletResponse
				// public void sendRedirect(String redirectLocationURL) throws IOExc
				// pw.print("<h4> Successful Login : sending from login page .....</h4>");
				// pw.flush();//WC throws IllegalStateExc --can not call sendRedirect after
				// committing the resp !!!!!!!!!!!!
				response.sendRedirect("topics");
				// WC sends temp redirect resp
				// SC 302 | headers Location : topics , Set-Cookie : JSESSIONID : abc12345 |
				// body : EMPTY
			}

		} catch (Exception e) {
			throw new ServletException("err in do-post of " + getClass(), e);
		}
	}

}
