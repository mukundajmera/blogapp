package com.example.blogapp.articles;

import com.example.blogapp.users.UserEntitiy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @GetMapping("")
    String getArticles(){
        return "Articles;";
    }

    @GetMapping("/{id}")
    String getArticleById(@PathVariable Long id){
        return "get article by id: "+id;
    }

    @PostMapping("")
    String createArticle(@AuthenticationPrincipal UserEntitiy user){
        return "create article " + user.getUsername();
    }

}
