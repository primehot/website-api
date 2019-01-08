package web.api.domain.view;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 08.01.2019
 */
@Entity
@Table(name = "news_article_view")
public class NewsArticleViewEntity extends AbstractViewEntity<Long> {

    private Long newsId;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
