package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.body.BodyService;
import ru.job4j.cars.service.category.CategoryService;
import ru.job4j.cars.service.engine.EngineService;
import ru.job4j.cars.service.model.ModelService;
import ru.job4j.cars.service.post.PostService;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final ModelService modelService;
    private final BodyService bodyService;
    private final EngineService engineService;

    @GetMapping("/list")
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    @GetMapping("/list/new")
    public String getWithPhoto(Model model) {
        model.addAttribute("posts", postService.findNew());
        return "posts/list";
    }

    @GetMapping("/list/last-day")
    public String getForLastDay(Model model) {
        model.addAttribute("posts", postService.findForLastDay());
        return "posts/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("models", modelService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("engines", engineService.findAll());
        return "posts/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute PostDto post,
                         @RequestParam MultipartFile file,
                         Model model,
                         @SessionAttribute User user) {
        try {
            postService.save(
                    post,
                    new FileDto(file.getOriginalFilename(), file.getBytes()),
                    user
            );
            return "redirect:/posts/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/one/{id:\\d+}")
    public String get(Model model, @PathVariable int id) {
        var post = postService.findById(id);
        if (post.isEmpty()) {
            model.addAttribute("message",
                    "Объявление с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("post", post.get());
        return "posts/one";
    }

    @GetMapping("/close/{id}")
    public String closePost(@PathVariable int id, Model model,
                            @SessionAttribute User user) {
        try {
            postService.closePost(id, user);
            return "redirect:/posts/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}
