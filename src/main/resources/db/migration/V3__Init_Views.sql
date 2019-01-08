CREATE VIEW news_article_view AS
  SELECT
    ROW_NUMBER()
    OVER (
      ORDER BY na.creation_date DESC, na.times_visited DESC ) AS id,
    na.id                                                     AS news_id,
    na.title                                                  AS title,
    na.hot_content                                            AS hot_content
  FROM news_article na;

CREATE VIEW woman_article_view AS
  SELECT
    ROW_NUMBER()
    OVER (
      ORDER BY wa.creation_date DESC, wa.times_visited DESC ) AS id,
    wa.id                                                     AS woman_id,
    wa.title                                                  AS title,
    wa.hot_content                                            AS hot_content
  FROM woman_article wa;

CREATE VIEW dream_book_article_view AS
  SELECT
    ROW_NUMBER()
    OVER (
      ORDER BY dba.creation_date DESC, dba.times_visited DESC ) AS id,
    dba.id                                                     AS dream_book_id,
    dba.title                                                  AS title,
    dba.hot_content                                            AS hot_content
  FROM dream_book_article dba;