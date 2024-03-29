/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntn.controllers;

import com.ntn.pojo.Khuyenmai;
import com.ntn.service.KhuyenMaiService;
import com.ntn.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author THANH NHAN
 */
@Controller
@PropertySource("classpath:configs.properties")
public class PromotionController {

    @Autowired
    private KhuyenMaiService khuyenMaiSer;
    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/khuyenmais")
    public String Promotion(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("promotion", this.khuyenMaiSer.getKhuyenMais(params));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", this.userService.getUsersByUsername(authentication.getName()));
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.khuyenMaiSer.countPromotion();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        return "promotion";
    }
    
    @GetMapping("/admin/kmexpires")
    public String PromotionExpires(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("promotion", this.khuyenMaiSer.getKhuyenMaisExpires(params));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", this.userService.getUsersByUsername(authentication.getName()));
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.khuyenMaiSer.countPromotionExpires();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        model.addAttribute("flagPro", 1);
        return "promotion";
    }

    @GetMapping("/admin/addkhuyenmai")
    public String showAddForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", this.userService.getUsersByUsername(authentication.getName()));
        model.addAttribute("promotion", new Khuyenmai());
        return "addKhuyenMai"; // Thay đổi tên trang JSP thành "addKhuyenMai.jsp"
    }

    @GetMapping("/admin/addkhuyenmai/{id}")
    public String showUpdateFrom(Model model, @PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", this.userService.getUsersByUsername(authentication.getName()));
        model.addAttribute("promotion", khuyenMaiSer.getKhuyenMaiById(id));
        return "addKhuyenMai"; // Thay đổi tên trang JSP thành "addKhuyenMai.jsp"
    }

    @PostMapping("/admin/addkhuyenmai")
    public String add(@ModelAttribute(value = "promotion") @Valid Khuyenmai km, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (khuyenMaiSer.addOrUpdateKhuyenMai(km) == true) {
                return "redirect:/admin/khuyenmais";
            }
        }
        return "addKhuyenMai"; // Thay đổi tên trang JSP thành "addKhuyenMai.jsp"
    }

}
