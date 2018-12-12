package web.api.service;

import org.springframework.stereotype.Service;
import web.api.dto.component.MainPageDto;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.article.ArticleDto;
import web.api.dto.unit.article.ShortArticleDto;
import web.api.service.article.NewsArticleService;
import web.api.service.article.WomanArticleService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by oleht on 21.10.2018
 */
@Service
public class MainArticleServiceImpl implements MainArticleService {

    private NewsArticleService newsArticleService;
    private WomanArticleService womanArticleService;

    public MainArticleServiceImpl(NewsArticleService newsArticleService, WomanArticleService womanArticleService) {
        this.newsArticleService = newsArticleService;
        this.womanArticleService = womanArticleService;
    }

    @Override
    public MainPageDto getMainDto() {

        MainPageDto mainPageDto = new MainPageDto();
        setMainArticleWithItemsTo(mainPageDto);
        setRecommendedTo(mainPageDto);

        return mainPageDto;
    }

    private void setMainArticleWithItemsTo(MainPageDto dto) {
        Collection<ArticleDto> newsArticles = newsArticleService.getPage(0, 30).getItems();

        ArrayList<ArticleDto> mainArticles = new ArrayList<>();
        mainArticles.addAll(newsArticles);
        mainArticles.sort(Comparator.comparing(ArticleDto::getTimesVisited));

        ArticleDto mainArticle = mainArticles.get(0);
        mainArticles.remove(mainArticle);

        dto.setMainArticle(new ShortArticleDto<>(mainArticle.getId(), mainArticle.getHotContent(), ArticleCategoryDto.getNewsCategory(),
                mainArticle.getHashTags()));
        dto.setMainItems(mainArticles.stream()
                .map(e -> new ShortArticleDto(e.getId(), e.getHotContent(), ArticleCategoryDto.getNewsCategory(), e.getHashTags()))
                .collect(Collectors.toList()));
    }

    private void setRecommendedTo(MainPageDto dto) {
        Collection<ShortArticleDto> news = newsArticleService.getAdditionalArticles().getRecommended();
        Collection<ShortArticleDto> women = womanArticleService.getAdditionalArticles().getRecommended();

        dto.setRecommendedNews(news);
        dto.setRecommendedWomen(women);
    }
}
