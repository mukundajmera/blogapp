package com.example.blogapp.articles;

import com.example.blogapp.articles.dtos.CreateArticleRequest;
import com.example.blogapp.articles.dtos.UpdateArticleRequest;
import com.example.blogapp.users.UsersRepository;
import com.example.blogapp.users.UsersService;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;

    public ArticleService(ArticlesRepository articlesRepository, UsersRepository usersRepository){
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles(){
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article =  articlesRepository.findBySlug(slug);
        if(article == null){
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest request, Long authorId){
        var author = usersRepository.findById(authorId).orElseThrow( () -> new UsersService.UserNotFoundException(authorId));

        return articlesRepository.save(ArticleEntity.builder()
                .title(request.getTitle())
                .body(request.getBody())
                //TODO: create proper function for slug
                .slug(request.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                .subtitle(request.getSubtitle())
                .author(author)
                .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest request){
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if(request.getTitle() != null){
            article.setTitle(request.getTitle());
            article.setSlug(request.getTitle().toLowerCase().replaceAll("\\s+","-"));
        }
        if(request.getBody() != null){
            article.setTitle(request.getBody());
        }
        if(request.getSubtitle() != null){
            article.setTitle(request.getSubtitle());
        }
        return articlesRepository.save(article);
    }

    static class ArticleNotFoundException extends IllegalArgumentException{
        public ArticleNotFoundException(String slug){
            super("Article with slug: " + slug + " not found");
        }

        public ArticleNotFoundException(Long id){
            super("Article with Id: " + id + " not found");
        }
    }
}
