package web.api.application.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import web.api.application.domain.HashTag;
import web.api.application.domain.ImageEntity;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;
import web.api.application.domain.entity.arcticle.dream.DreamBookArticleEntity;
import web.api.application.domain.entity.arcticle.news.NewsArticleEntity;
import web.api.application.domain.NewsTopic;
import web.api.application.domain.entity.arcticle.woman.WomanArticleEntity;
import web.api.application.domain.WomanTopic;
import web.api.application.domain.entity.dream_book.DreamBookEntity;
import web.api.application.dto.unit.article_draft.ParagraphDto;
import web.api.application.dto.unit.article_draft.ParagraphType;
import web.api.application.repository.ImageRepository;
import web.api.application.repository.article.DreamBookArticleRepository;
import web.api.application.repository.article.DreamBookRepository;
import web.api.application.repository.article.NewsArticleRepository;
import web.api.application.repository.article.WomanArticleRepository;
import web.api.application.util.ArticleUtil;
import web.api.application.util.ImageUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by oleh.tsyupa.
 */
@Log
@Component
public class TestDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vitae imperdiet nibh. Suspendisse potenti. Pellentesque lacus mi, tempus ac risus quis, pulvinar lobortis est. In augue sem, placerat sit amet odio at, sagittis finibus enim. Sed lacinia nisi id quam interdum varius. Aenean molestie felis et neque interdum, non dapibus purus gravida. Nulla a arcu sapien. Vivamus nec gravida leo. Aliquam libero ligula, tincidunt vitae cursus sit amet, pellentesque sed eros. Cras posuere, augue vitae convallis gravida, turpis purus consequat eros, et lobortis lectus arcu nec urna. Nulla aliquet facilisis lectus eu placerat. Etiam ultricies magna nisi.\n" +
            "\n" +
            "In hac habitasse platea dictumst. Fusce porta in dui tincidunt tempus. Sed quis lacus mattis enim vulputate suscipit nec quis felis. Morbi sodales sodales purus ac maximus. Pellentesque ac odio vitae tortor vehicula laoreet vitae eu sem. Ut sagittis venenatis risus, quis pellentesque nisi lacinia sagittis. Fusce pellentesque sed felis eu laoreet. Aliquam consectetur id risus vel elementum. Aenean hendrerit risus pellentesque nunc pharetra, non rutrum ex accumsan. Pellentesque mollis sapien lacus, sed volutpat nulla rhoncus eu. Suspendisse et fringilla tellus. Aliquam ut lectus sit amet nulla iaculis pretium. Sed porttitor magna at neque commodo ornare.\n" +
            "\n" +
            "Nunc vel diam sodales, consequat erat non, sodales ante. Ut bibendum justo in diam facilisis volutpat. Morbi congue massa placerat efficitur maximus. Vivamus faucibus rutrum tortor. Nulla bibendum ex a tempus eleifend. Morbi vitae sagittis ligula. Morbi leo lectus, semper vel quam ut, sodales pretium dolor. Sed pharetra, ante id tincidunt malesuada, ipsum turpis auctor diam, quis lacinia ligula nisi sit amet arcu. Aenean vehicula accumsan sem, quis aliquet est suscipit nec. Mauris ornare pharetra nulla, id semper erat eleifend eget. Morbi quis sodales diam. Donec mattis varius elit vitae semper. Cras non lacinia libero. Morbi condimentum dui laoreet, pulvinar augue sit amet, fermentum arcu. Phasellus pellentesque nisi risus.\n" +
            "\n" +
            "In eu massa eu dui pharetra ultrices vel non erat. Integer nisl magna, tristique eget interdum vitae, euismod feugiat nisi. Suspendisse quis dui rutrum, interdum orci pulvinar, sodales erat. Proin et nunc tellus. Fusce porta et nibh ut sagittis. In faucibus tempus felis, maximus congue augue mollis sed. Ut erat mauris, eleifend vulputate tempor a, facilisis ac ante. Pellentesque cursus auctor leo vel tincidunt. Fusce vel neque ac augue blandit iaculis.\n" +
            "\n" +
            "Praesent cursus tincidunt tincidunt. Sed et erat posuere, semper arcu quis, feugiat purus. Cras fermentum et mi bibendum accumsan. Phasellus semper lobortis ligula et volutpat. Aenean fermentum enim leo, vitae gravida lacus bibendum vel. Suspendisse semper convallis velit ac finibus. Integer eget tempor mauris. Cras feugiat iaculis quam rhoncus vestibulum. Praesent at aliquam diam. Mauris nec nulla tellus.";

    private NewsArticleRepository newsArticleRepository;
    private WomanArticleRepository womanArticleRepository;
    private DreamBookRepository dreamBookRepository;
    private DreamBookArticleRepository dreamBookArticleRepository;
    private ImageRepository imageRepository;
    private ObjectMapper objectMapper;

    public TestDataBootstrap(NewsArticleRepository newsArticleRepository, WomanArticleRepository womanArticleRepository, DreamBookRepository dreamBookRepository, DreamBookArticleRepository dreamBookArticleRepository, ImageRepository imageRepository, ObjectMapper objectMapper) {
        this.newsArticleRepository = newsArticleRepository;
        this.womanArticleRepository = womanArticleRepository;
        this.dreamBookRepository = dreamBookRepository;
        this.dreamBookArticleRepository = dreamBookArticleRepository;
        this.imageRepository = imageRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.log(Level.CONFIG,"Loading Bootstrap Data");
        addBasicNewsArticle();
        addBasicMainNewsArticle();
        addBasicWomanArticle();
        addBasicMainWomanArticle();
        addBasicDreamBookArticle();
        addDreamBook();
    }

    private void addBasicNewsArticle() {
        List<NewsArticleEntity> news = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NewsArticleEntity entity = new NewsArticleEntity();
            entity.setTitle("News number " + i);
            entity.setNewsTopic(NewsTopic.POLITICS);
            entity.addHashTag(HashTag.INSTAGRAM);
            entity.addHashTag(HashTag.RELIGY);
            try {
                entity.setContent(objectMapper.writeValueAsString(Collections.singleton(ParagraphDto.of(0,lorem, ParagraphType.NO_IMAGE))));
            } catch (JsonProcessingException e) {
                log.severe("Cannot convert test data");
            }
            entity.setHotContent("HOT " + i);
            addImage(entity, "classpath:pictures/news/article.jpg");

            news.add(entity);
        }

        newsArticleRepository.saveAll(news);
    }

    private void addBasicMainNewsArticle() {
        List<NewsArticleEntity> news = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            NewsArticleEntity entity = new NewsArticleEntity();
            entity.setTitle("Main News number " + i);
            entity.setNewsTopic(NewsTopic.TECHNOLOGY);
            entity.addHashTag(HashTag.INSTAGRAM);
            entity.addHashTag(HashTag.MURDER);
            try {
                entity.setContent(objectMapper.writeValueAsString(Collections.singleton(ParagraphDto.of(0,lorem, ParagraphType.NO_IMAGE))));
            } catch (JsonProcessingException e) {
                log.severe("Cannot convert test data");
            }
            entity.setHotContent("HOT " + i);
            entity.setMain(true);
            addImage(entity, "classpath:pictures/news/main.jpg");

            news.add(entity);
        }

        newsArticleRepository.saveAll(news);
    }

    private void addBasicWomanArticle() {
        List<WomanArticleEntity> womanArticles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            WomanArticleEntity entity = new WomanArticleEntity();
            entity.setTitle("News number " + i);
            entity.setWomanTopic(WomanTopic.SEX);
            try {
                entity.setContent(objectMapper.writeValueAsString(Collections.singleton(ParagraphDto.of(0,lorem, ParagraphType.NO_IMAGE))));
            } catch (JsonProcessingException e) {
                log.severe("Cannot convert test data");
            }
            entity.setHotContent("HOT " + i);
            entity.addHashTag(HashTag.MURDER);
            addImage(entity, "classpath:pictures/woman/article.jpg");

            womanArticles.add(entity);
        }

        womanArticleRepository.saveAll(womanArticles);
    }

    private void addBasicMainWomanArticle() {
        List<WomanArticleEntity> womanArticles = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            WomanArticleEntity entity = new WomanArticleEntity();
            entity.setTitle("News number " + i);
            entity.setWomanTopic(WomanTopic.HOLIDAY);
            entity.setContent(lorem);
            entity.setHotContent("HOT " + i);
            entity.setMain(true);
            entity.addHashTag(HashTag.INSTAGRAM);
            entity.addHashTag(HashTag.RELIGY);
            addImage(entity, "classpath:pictures/woman/main.jpg");

            womanArticles.add(entity);
        }

        womanArticleRepository.saveAll(womanArticles);
    }

    private void addBasicDreamBookArticle() {
        List<DreamBookArticleEntity> womanArticles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DreamBookArticleEntity entity = new DreamBookArticleEntity();
            entity.setTitle("News number " + i);
            try {
                entity.setContent(objectMapper.writeValueAsString(Collections.singleton(ParagraphDto.of(0,lorem, ParagraphType.NO_IMAGE))));
            } catch (JsonProcessingException e) {
                log.severe("Cannot convert test data");
            }
            entity.setHotContent("HOT " + i);
            entity.setMain(true);
            entity.addHashTag(HashTag.INSTAGRAM);
            entity.addHashTag(HashTag.RELIGY);
            addImage(entity, "classpath:pictures/dreambook/article.jpg");

            womanArticles.add(entity);
        }

        dreamBookArticleRepository.saveAll(womanArticles);
    }

    private void addImage(AbstractArticleEntity entity, String classpath) {
        try {
            File file = ResourceUtils.getFile(classpath);
            byte[] res = Files.readAllBytes(file.toPath());
            Byte[] image = ImageUtil.convertBytes(res);
            ImageEntity e = new ImageEntity();
            e.setImage(image);
            e = imageRepository.save(e);
            entity.setImages(Collections.singletonList(e));
        } catch (IOException e) {
            log.log(Level.CONFIG,"Error during loading mainImage: " + classpath);
        }
    }

    private void addDreamBook() {
        List<DreamBookEntity> dreamBookEntities = new ArrayList<>();
        addDreamToList("Любовь", "Великий мудрец Олег Цюпа", "Пристилось то приснилось", dreamBookEntities);
        addDreamToList("Жена", "Мудрец Олег Цюпа", "Жена хорошая", dreamBookEntities);
        addDreamToList("Дети", "Буддист Олег Цюпа", "Значить будут", dreamBookEntities);
        addDreamToList("Сем'я", "Олег Цюпа Аристократ", "Все отлично, не парся", dreamBookEntities);
        addDreamToList("Щастье", "Ум Олега", "Щастя бивает разное", dreamBookEntities);
        addDreamToList("Дом", "Олег Цюпа Супер", "Дом любим", dreamBookEntities);
        addDreamToList("Теплота", "Олег Цюпа Вперед", "Будет дождь", dreamBookEntities);
        addDreamToList("Душа", "Олег Цюпа Красавчик", "Летела на лужами", dreamBookEntities);
        addDreamToList("Ум", "Олег Цюпа Все знает", "Значить не тупой", dreamBookEntities);

        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);
        addDreamToList("Деньги", "Олег Цюпа", ArticleUtil.cutShortContent(lorem), dreamBookEntities);


        this.dreamBookRepository.saveAll(dreamBookEntities);
    }

    private void addDreamToList(String title, String author, String content, List<DreamBookEntity> dreamBookEntities) {
        int a = 0; // Начальное значение диапазона - "от"
        int b = 10; // Конечное значение диапазона - "до"

        long r = a + (long) (Math.random() * b); // Генерация 1-го числа

        DreamBookEntity e = new DreamBookEntity();
        e.setTitle(title);
        e.setAuthor(author);
        e.setContent(content);
        e.setTimesVisited(r);
        dreamBookEntities.add(e);
    }
}
