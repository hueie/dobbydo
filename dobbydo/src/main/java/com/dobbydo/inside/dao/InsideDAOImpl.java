package com.dobbydo.inside.dao;
import java.util.List;

import com.dobbydo.atestpage.entity.Article;
public interface InsideDAOImpl {
	Article getArticleById(int articleId);
    /*
    List<Article> getAllArticles();
    void createArticle(Article article);
    void updateArticle(Article article);
    void deleteArticle(int articleId);
    boolean articleExists(String title, String category);
    */
}
 