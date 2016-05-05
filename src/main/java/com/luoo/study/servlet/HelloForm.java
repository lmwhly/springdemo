package com.luoo.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * cookies test
 *
 * @author Luoo2
 * @create 2016-04-19 19:41
 */

public class HelloForm extends HttpServlet {

    public static void main(String[] args) throws ServletException {
        UploadFile uploadFile = new UploadFile();
        uploadFile.init();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie firstName = new Cookie("first_name", req.getParameter("first_name"));
        Cookie lastName = new Cookie("last_name", req.getParameter("last_name"));

        firstName.setMaxAge(60 * 60 * 24);
        lastName.setMaxAge(60 * 60 * 24);

        resp.addCookie(firstName);
        resp.addCookie(lastName);


        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String title = "Setting Cookies Example";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " +
                        "transitional//en\">\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>First Name</b>: "
                + req.getParameter("first_name") + "\n" +
                "  <li><b>Last Name</b>: "
                + req.getParameter("last_name") + "\n" +
                "</ul>\n" +
                "</body></html>");

    }

}
