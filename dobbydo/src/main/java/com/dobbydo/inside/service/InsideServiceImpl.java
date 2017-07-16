package com.dobbydo.inside.service;

import java.util.List;

import com.dobbydo.atestpage.entity.Article;

public interface InsideServiceImpl {
	Article getArticleById(int articleId);
    
	/*
     List<Article> getAllArticles();
     boolean createArticle(Article article);
     void updateArticle(Article article);
     void deleteArticle(int articleId);
     */
}
