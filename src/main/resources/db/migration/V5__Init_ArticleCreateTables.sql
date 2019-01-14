-- Dream Book Article

DROP TABLE IF EXISTS dream_book_article_draft;

CREATE TABLE dream_book_article_draft (
  id                     SERIAL NOT NULL,
  creation_date          TIMESTAMP,
  last_modification_date TIMESTAMP,
  content                TEXT,
  hash_tags              VARCHAR(255),
  hot_content            VARCHAR(255),
  main_image             BYTEA,
  main                   BOOLEAN,
  approved               BOOLEAN,
  title                  VARCHAR(255),
  author                 VARCHAR(255),
  PRIMARY KEY (id)
);

-- News Article

DROP TABLE IF EXISTS news_article_draft;

CREATE TABLE news_article_draft (
  id                     SERIAL NOT NULL,
  creation_date          TIMESTAMP,
  last_modification_date TIMESTAMP,
  content                TEXT,
  hash_tags              VARCHAR(255),
  hot_content            VARCHAR(255),
  main_image             BYTEA,
  main                   BOOLEAN,
  approved               BOOLEAN,
  news_topic             INT4,
  title                  VARCHAR(255),
  author                 VARCHAR(255),
  PRIMARY KEY (id)
);

-- Woman Article

DROP TABLE IF EXISTS woman_article_draft;

CREATE TABLE woman_article_draft (
  id                     SERIAL NOT NULL,
  creation_date          TIMESTAMP,
  last_modification_date TIMESTAMP,
  content                TEXT,
  hash_tags              VARCHAR(255),
  hot_content            VARCHAR(255),
  main_image             BYTEA,
  main                   BOOLEAN,
  approved               BOOLEAN,
  woman_topic            INT4,
  title                  VARCHAR(255),
  author                 VARCHAR(255),
  PRIMARY KEY (id)
);
