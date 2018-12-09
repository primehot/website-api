-- Dream Book

CREATE OR REPLACE FUNCTION make_dream_boook_tsvector(title TEXT, content TEXT, author TEXT)
  RETURNS TSVECTOR AS $$
BEGIN
  RETURN (setweight(to_tsvector('russian', title), 'A') ||
          setweight(to_tsvector('russian', content), 'B') ||
          setweight(to_tsvector('russian', author), 'B'));
END
$$
LANGUAGE 'plpgsql' IMMUTABLE;

CREATE OR REPLACE FUNCTION dream_boook_trigger()
  RETURNS TRIGGER AS $$
BEGIN
  new.document_tokens := make_dream_boook_tsvector(NEW.title, NEW.content, NEW.author);
  RETURN new;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS upd_dream_boook_tsvector
ON dream_book;
CREATE TRIGGER upd_dream_boook_tsvector
BEFORE INSERT OR UPDATE
  ON dream_book
FOR EACH ROW EXECUTE PROCEDURE dream_boook_trigger();



-- Dream Book Article

CREATE OR REPLACE FUNCTION make_dream_boook_article_tsvector(title TEXT, hot_content TEXT, content TEXT)
  RETURNS TSVECTOR AS $$
BEGIN
  RETURN (setweight(to_tsvector('russian', title), 'A') ||
          setweight(to_tsvector('russian', hot_content), 'B') ||
          setweight(to_tsvector('russian', content), 'C'));
END
$$
LANGUAGE 'plpgsql' IMMUTABLE;

CREATE OR REPLACE FUNCTION dream_boook_article_trigger()
  RETURNS TRIGGER AS $$
BEGIN
  new.document_tokens := make_dream_boook_article_tsvector(NEW.title, NEW.hot_content, NEW.content);
  RETURN new;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS upd_dream_boook_article_tsvector
ON dream_book_article;
CREATE TRIGGER upd_dream_boook_article_tsvector
BEFORE INSERT OR UPDATE
  ON dream_book_article
FOR EACH ROW EXECUTE PROCEDURE dream_boook_article_trigger();



-- News Article

CREATE OR REPLACE FUNCTION make_news_article_tsvector(title TEXT, hot_content TEXT, content TEXT)
  RETURNS TSVECTOR AS $$
BEGIN
  RETURN (setweight(to_tsvector('russian', title), 'A') ||
          setweight(to_tsvector('russian', hot_content), 'B') ||
          setweight(to_tsvector('russian', content), 'C'));
END
$$
LANGUAGE 'plpgsql' IMMUTABLE;

CREATE OR REPLACE FUNCTION news_article_trigger()
  RETURNS TRIGGER AS $$
BEGIN
  new.document_tokens := make_news_article_tsvector(NEW.title, NEW.hot_content, NEW.content);
  RETURN new;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS upd_news_article_tsvector
ON news_article;
CREATE TRIGGER upd_news_article_tsvector
BEFORE INSERT OR UPDATE
  ON news_article
FOR EACH ROW EXECUTE PROCEDURE news_article_trigger();



-- Woman Article

CREATE OR REPLACE FUNCTION make_woman_article_tsvector(title TEXT, hot_content TEXT, content TEXT)
  RETURNS TSVECTOR AS $$
BEGIN
  RETURN (setweight(to_tsvector('russian', title), 'A') ||
          setweight(to_tsvector('russian', hot_content), 'B') ||
          setweight(to_tsvector('russian', content), 'C'));
END
$$
LANGUAGE 'plpgsql' IMMUTABLE;

CREATE OR REPLACE FUNCTION woman_article_trigger()
  RETURNS TRIGGER AS $$
BEGIN
  new.document_tokens := make_woman_article_tsvector(NEW.title, NEW.hot_content, NEW.content);
  RETURN new;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS upd_woman_article_tsvector
ON woman_article;
CREATE TRIGGER upd_woman_article_tsvector
BEFORE INSERT OR UPDATE
  ON woman_article
FOR EACH ROW EXECUTE PROCEDURE woman_article_trigger();