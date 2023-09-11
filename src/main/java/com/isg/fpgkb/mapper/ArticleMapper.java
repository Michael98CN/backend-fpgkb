package com.isg.fpgkb.mapper;

import com.isg.fpgkb.dto.Article;
import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer pmid);

    int insert(Article record);

    Article selectByPrimaryKey(Integer pmid);

    List<Article> selectAll();

    int updateByPrimaryKey(Article record);
}