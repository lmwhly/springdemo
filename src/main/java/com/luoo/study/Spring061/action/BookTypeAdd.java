package com.luoo.study.Spring061.action;

/**
 * @author Luoo2
 * @create 2016-08-14 0:35
 */

import com.luoo.study.Spring061.entities.BookType;
import com.luoo.study.Spring061.service.BookTypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/BookTypeAdd.do")
public class BookTypeAdd extends HttpServlet {
        private static final long serialVersionUID = 1L;

        BookTypeService bookTypeService;

@Override
public void init() throws ServletException {
        //从容器中获得bean
        bookTypeService=CtxUtil.getBean(BookTypeService.class);
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer=response.getWriter();
        BookType entity1=new BookType();
        entity1.setTypeName("量子力学");

        BookType entity2=new BookType();
        entity1.setTypeName("天体物理");  //请注意这是是entity1，entity2的typeName属性为空
        writer.print(bookTypeService.addDouble(entity1, entity2));
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        }

        }