package web.api.service;

import org.springframework.stereotype.Service;
import web.api.dto.AbstractArticleDto;
import web.api.dto.MainDto;
import web.api.dto.ShortArticleDto;
import web.api.dto.news.NewsArticleDto;

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
    public MainDto getMainDto() {

        MainDto mainDto = new MainDto();
        setMainArticleWithItemsTo(mainDto);
        setRecommendedTo(mainDto);

        return mainDto;
    }

    private void setMainArticleWithItemsTo(MainDto dto) {
        Collection<NewsArticleDto> newsArticles = newsArticleService.getPage(0, 30).getItems();

        ArrayList<AbstractArticleDto> mainArticles = new ArrayList<>();
        mainArticles.addAll(newsArticles);
        mainArticles.sort(Comparator.comparing(AbstractArticleDto::getTimesVisited));

        AbstractArticleDto mainArticle = mainArticles.get(0);
        mainArticles.remove(mainArticle);

        dto.setMainArticle(new ShortArticleDto<>(mainArticle.getId(), mainArticle.getHotContent()));
        dto.setMainItems(mainArticles.stream().map(e -> new ShortArticleDto<>(e.getId(), e.getHotContent())).collect(Collectors.toList()));
    }

    private void setRecommendedTo(MainDto dto) {
        Collection<ShortArticleDto> news = newsArticleService.getRecommended();
        Collection<ShortArticleDto> women = womanArticleService.getRecommended();

        dto.setRecommendedNews(news);
        dto.setRecommendedWomen(women);
    }
}
