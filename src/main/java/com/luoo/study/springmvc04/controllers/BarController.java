package com.luoo.study.springmvc04.controllers;

import com.luoo.study.springmvc04.entities.Person;
import com.luoo.study.springmvc04.entities.Product;
import com.luoo.study.springmvc04.entities.ProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luoo2
 * @create 2016-08-15 0:11
 */
@Controller
@RequestMapping("/bar")
public class BarController {

    @RequestMapping("/action11")
    public String action11(Model model){
        //向模型中添加一个名为product的对象，用于渲染视图
//        model.addAttribute("product", new Product("Meizu note1", 999));
        model.addAttribute("product", new Product("Meizu note1<hr/>", 999));
        return "bar/action11";
    }


    @RequestMapping("/action21")
    public String action21(Model model){
        model.addAttribute("person", new Person());
        return "bar/action21";
    }

    @RequestMapping("/action22")
    @ResponseBody
    public Person action22(HttpServletResponse response, Person person){
        return person;
    }

    @RequestMapping("/action31")
    public String action31(Model model){
        Person person=new Person();
        person.setEducation("edu");
        model.addAttribute("person", person);
        return "bar/action31";
    }

   /* @RequestMapping("/action31")
    public String action31(Model model){
        model.addAttribute("person", new Person());
        return "bar/action31";
    }*/

    @RequestMapping("/action32")
    @ResponseBody
    public Person action32(HttpServletResponse response,Person person){
        return person;
    }

    @RequestMapping("/action41")
    public String action41(Model model){
        List<ProductType> productTypes = new ArrayList<ProductType>();
        productTypes.add(new ProductType(11, "数码电子"));
        productTypes.add(new ProductType(21, "鞋帽服饰"));
        productTypes.add(new ProductType(31, "图书音像"));
        productTypes.add(new ProductType(41, "五金家电"));
        productTypes.add(new ProductType(51, "生鲜水果"));
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("person", new Person());
        return "bar/action41";
    }

    @RequestMapping("/action42")
    @ResponseBody
    public Person action42(HttpServletResponse response,Person person){
        return person;
    }


    //option
    @RequestMapping("/action51")
    public String action51(Model model){
        model.addAttribute("person", new Person());
        return "bar/action51";
    }

    @RequestMapping("/action52")
    @ResponseBody
    public Person action52(HttpServletResponse response,Person person){
        return person;
    }


    @RequestMapping("/action61")
    public String action61(Model model){
        List<ProductType>  productTypes = new ArrayList<ProductType>();
        productTypes.add(new ProductType(11, "数码电子"));
        productTypes.add(new ProductType(21, "鞋帽服饰"));
        productTypes.add(new ProductType(31, "图书音像"));
        productTypes.add(new ProductType(41, "五金家电"));
        productTypes.add(new ProductType(51, "生鲜水果"));
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("person", new Person());
        return "bar/action61";
    }

    @RequestMapping("/action62")
    @ResponseBody
    public Person action62(HttpServletResponse response,Person person){
        return person;
    }


    @RequestMapping("/action81")
    public String action81(Model model) {
        List<ProductType> productTypes = new ArrayList<ProductType>();
        productTypes.add(new ProductType(11, "数码电子"));
        productTypes.add(new ProductType(21, "鞋帽服饰"));
        productTypes.add(new ProductType(31, "图书音像"));
        productTypes.add(new ProductType(41, "五金家电"));
        productTypes.add(new ProductType(51, "生鲜水果"));
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("person", new Person());
        return "bar/action81";
    }

    @RequestMapping("/action82")
    @ResponseBody
    public Person action82(HttpServletResponse response, Person person) {
        return person;
    }


}
