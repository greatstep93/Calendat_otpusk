package ru.greatstep.spring_sqlite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.greatstep.spring_sqlite.excel.UserExcelExporter;
import ru.greatstep.spring_sqlite.models.User;
import ru.greatstep.spring_sqlite.repositories.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "")
public class AdminController {
    UserRepository userRepository;
    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String snowAdminPanel() {
        return "admin";
    }


    @GetMapping()
    public String home() {
        return "redirect:/admin";
    }

    @GetMapping("/admin/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "users_"+currentDateTime+".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        List<User> listUsers = userRepository.findAll();
        System.out.println(listUsers);
        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
        excelExporter.export(response);

    }
}
