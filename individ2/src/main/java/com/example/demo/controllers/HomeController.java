package com.example.demo.controllers;

import com.example.demo.models.Marka;
import com.example.demo.models.Role;
import com.example.demo.models.Tovaradd;
import com.example.demo.models.User;
import com.example.demo.repo.TovarRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.SalesExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller

public class HomeController {
    @Autowired
    private TovarRepository tovarRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    private RuntimeException runtimeException;
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }




    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {



        String role =  authResult.getAuthorities().toString();




        if(role.contains("ADMIN")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin" ));
        }
        else if(role.contains("USER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index" ));
        }
        else if(role.contains("PRODA")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/proda"));
        }
        else if(role.contains("SKLAD")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/sklad"));
        }
        else if(role.contains("KADRI")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/kadri"));
        }
    }


    @GetMapping("/kadri")
    public String kadrovik( Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "kadri";
    }
    @RequestMapping("/kadri")
    @PostMapping
    public String userSave(@RequestParam String username,@RequestParam String imea,@RequestParam String familiya,@RequestParam(name="roles[]", required = false) String[] roles,
                           @RequestParam("userId") User user){
        user.setUsername(username);
        user.setImea(imea);
        user.setFamiliya(familiya);
        user.getRoles().clear();
        if(roles!=null)
        {
            Arrays.stream(roles).forEach(r->user.getRoles().add(Role.valueOf(r)));
        }
        userRepository.save(user);

        return "redirect:/kadri";
    }


    @GetMapping("kadri/{id}/kadriEdit")
    public String userEdit(@PathVariable(value = "id") long id, Model model){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("imea", res);
        model.addAttribute("familiya", res);
        model.addAttribute("roles", Role.values());
        return "kadriEdit";
    }

    @PostMapping("/kadri/{id}/remove")
    public String kadriRemove(@PathVariable("id") long id, Model model)
    {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/kadri";
    }


    @GetMapping("/kadri/sotrudnikAdd")
    public String NewSotrudnik(Model model)
    {

        return "sotrudnikAdd";
    }

    @PostMapping("/kadri/sotrudnikAdd")
    public String NewSotru(User user, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if(userFromDb != null)
        {
            model.addAttribute("message", "Сотрудник с таким логином уже зарегистрирован");
            return "sotrudnikAdd";
        }
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/kadri";
    }


    @GetMapping("/proda")
    public String proda ()
    {

        return "proda-view";
    }


    @GetMapping("/sklad")
    public String skladov (Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Iterable<Tovaradd> tovari = tovarRepository.findAll();
        model.addAttribute("tovari", tovari);
        //
        Iterable<User> users = userRepository.findAll();



        model.addAttribute("tovari", tovari);
        //
        model.addAttribute("User", users);
        model.addAttribute("isUser", authentication.getAuthorities().toString().contains("USER"));

        return "sklad-view";
    }

    @PostMapping("/sklad/{id}/remove")
    public String TovarRemove(@PathVariable("id") long id, Model model)
    {
        Tovaradd tovaradd = tovarRepository.findById(id).orElseThrow();
        tovarRepository.delete(tovaradd);
        return "redirect:/sklad";
    }

    @GetMapping("/main")
    public String main ()
    {

        return "main";
    }

    @GetMapping("/sklad/tovari-add")
    public String tovariAdd(Model model)
    {
        //Iterable<Post> posts = TovarRepository.findAll();

        return "tovar-add";
    }


    @PostMapping("/sklad/tovari-add")
    public String tovariPostAdd(String opisanie,
                                String cena,
                                String kolichestvo,
                                @RequestParam("file")MultipartFile file)
            throws IOException {
        Tovaradd tovarAdd = new Tovaradd(opisanie, cena, kolichestvo);
        if (file !=null) {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

           String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            //String Filek = file.getOriginalFilename();
            //String resultFilename = "/C:/Users/sasha/IdeaProjects/individ2/src/main/resources/static/images/" + file.getOriginalFilename();
           file.transferTo(new File(uploadPath + "/" + resultFilename));
            //file.transferTo(new File(uploadPath + "/" + Filek));
        tovarAdd.setFilename(resultFilename);
        }
        tovarRepository.save(tovarAdd);
        return "redirect:/sklad/tovari-add";


    }

   

    @GetMapping("/report")
    public ResponseEntity<Resource> getReport() throws IOException {

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = HttpHeaders.CONTENT_DISPOSITION;
        String headerValue = "attachment; filename=sales_" + currentDateTime + ".xlsx";

        List<Tovaradd> listSales = (List<Tovaradd>) tovarRepository.findAll();

        SalesExcelExporter excelExporter = new SalesExcelExporter(listSales);

        excelExporter.export(currentDateTime);

        try {
            String uri = Paths.get("load/sales_" + currentDateTime + ".xlsx").toUri().toString();
            org.springframework.core.io.Resource resource = resourceLoader.getResource(uri);
            ResponseEntity<org.springframework.core.io.Resource> body = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .contentLength(resource.contentLength())
                    .body(resource);
            return body;
        } catch (IOException e) {
            throw runtimeException;
        }

    }

}