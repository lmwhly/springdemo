package com.luoo.study.springmvc03.controller;

import com.luoo.study.springmvc03.entities.Product;
import com.luoo.study.springmvc03.entities.ProductList;
import com.luoo.study.springmvc03.entities.ProductMap;
import com.luoo.study.springmvc03.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * @author Luoo2
 * @create 2016-08-14 21:42
 */

@Controller
@RequestMapping("/foo")
public class ProductController {

    @RequestMapping("/action01")
    public String action01(Model model, Product product) {
        model.addAttribute("message", product);
        return "foo/index";
    }

    @RequestMapping("/action02")
    public String action02(Model model, User user) {
        model.addAttribute("message", user.getUsername() + "," + user.getProduct().getName());
        return "foo/index";
    }

    @RequestMapping("/action03")
    public String action03(Model model, ProductList products) {
        model.addAttribute("message", products.getItems().get(0) + "<br/>" + products.getItems().get(1));
        return "foo/index";
    }

    // Map类型
    @RequestMapping("/action04")
    public String action04(Model model, ProductMap map) {
        model.addAttribute("message", map.getItems().get("p1") + "<br/>" + map.getItems().get("p2"));
        return "foo/index";
    }


    @RequestMapping("/action21")
    public void action21(@RequestBody List<Product> products, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(Arrays.deepToString(products.toArray()));
        response.getWriter().write("添加成功");
    }

    @RequestMapping("/action32")
    @ResponseBody
    public String action32() {
        return "not <b>path</b>,but <b>content</b>";
    }

    @RequestMapping("/action33")
    public void action33() {
    }


    @RequestMapping("/action34")
    public void action34(HttpServletResponse response) throws IOException {
        response.getWriter().write("<h2>void method</h2>");
    }

    @RequestMapping("/action37")
    public Integer action37()
    {
        return 9527;
    }

    @RequestMapping("/action38")
    @ResponseBody
    public int action38()
    {
        return 9527;
    }

    @RequestMapping("/action39")
    @ResponseBody
    public Product action39()
    {
        return new Product(1,"iPhone",1980.5);
    }

    @RequestMapping("/action40")
    public Model action40(Model model)
    {
        model.addAttribute("message", "返回类型为org.springframework.ui.Model");
        return model;
    }


    @RequestMapping("/action41")
    @ResponseBody
    public String action41(HttpServletResponse response)
    {
        response.setHeader("Content-type","application/octet-stream");
        response.setHeader("Content-Disposition","attachment; filename=table.xlsx");
        return "<table><tr><td>Hello</td><td>Excel</td></tr></table>";
    }


}
