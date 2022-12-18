package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.models.Tovaradd;
import com.example.demo.models.User;
import com.example.demo.repo.TovarRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class BlogController  {
    @Autowired
    private TovarRepository tovarRepository;
    private PostRepository postRepository;



    private final UserRepository userRepository;
    public BlogController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @GetMapping("/index")
    public String skladTovar( Model model)
    {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Iterable<Tovaradd> tovars = tovarRepository.findAll();
        model.addAttribute("tovars", tovars);
        //
        Iterable<User> users = userRepository.findAll();



        model.addAttribute("tovars", tovars);
        //
        model.addAttribute("User", users);
        model.addAttribute("isUser", authentication.getAuthorities().toString().contains("USER"));

        return "sklad-tovar";
    }


    @GetMapping("/index/buy")
    public String Buy( Model model)
    {
        return "buy";
    }

    @GetMapping("/index/buy/{id}")
    public String Buy1(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Tovaradd> tovar = tovarRepository.findById(id);
        ArrayList<Tovaradd> res1 = new ArrayList<>();
        tovar.ifPresent(res1::add);
        model.addAttribute("tovar", res1);
        if(!tovarRepository.existsById(id)){
            return "redirect:/index";
        }
        return "buy";
    }


    @GetMapping("/blog/add")
    public String blogAdd(Model model)
    {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model)
    {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog/add";
    }






    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> result = postRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "blog-filter";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        return "blog-details";
    }

    @GetMapping("/reclam/{id}")
    public String reclam(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id)){
            return "redirect:/reclam";
        }
        return "reclam";
    }


    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id") long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text, Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(@PathVariable("id") long id, Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }



   @GetMapping("/index/{id}/profiledit")
    public String profiledit(@PathVariable(value = "id") long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("roles", Role.values());
        return "profil-edit";
    }


    @PostMapping("/index")
    public String profileditsave(@RequestParam String username,@RequestParam String imea,@RequestParam String familiya,@RequestParam String password,
                           @RequestParam("userId") User user){
        user.setUsername(username);
        user.setImea(imea);
        user.setFamiliya(familiya);
        user.setPassword(password);

        userRepository.save(user);

        return "redirect:/index";
    }
}
