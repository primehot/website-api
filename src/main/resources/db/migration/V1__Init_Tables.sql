-- Dream Book

DROP TABLE IF EXISTS dream_book;

CREATE TABLE dream_book (
  id                     SERIAL       NOT NULL,
  creation_date          TIMESTAMP    NOT NULL,
  last_modification_date TIMESTAMP    NOT NULL,
  times_visited          INT8         NOT NULL,
  author                 VARCHAR(255) NOT NULL,
  content                TEXT         NOT NULL,
  title                  VARCHAR(255) NOT NULL,
  document_tokens        TSVECTOR,
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS dream_book_tsv_idx ON dream_book USING GIST (document_tokens);

-- Dream Book Article

DROP TABLE IF EXISTS dream_book_article;

CREATE TABLE dream_book_article (
  id                     SERIAL NOT NULL,
  creation_date          TIMESTAMP,
  last_modification_date TIMESTAMP,
  times_visited          BIGINT,
  content                TEXT,
  hash_tags              VARCHAR(255),
  hot_content            VARCHAR(255),
  main_image                  BYTEA,
  main                   BOOLEAN,
  title                  VARCHAR(255),
  document_tokens        TSVECTOR,
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS dream_book_article_tsv_idx ON dream_book_article USING GIST (document_tokens);

-- News Article

DROP TABLE IF EXISTS news_article;

CREATE TABLE news_article (
  id                     SERIAL NOT NULL,
  creation_date          TIMESTAMP,
  last_modification_date TIMESTAMP,
  times_visited          BIGINT,
  content                TEXT,
  hash_tags              VARCHAR(255),
  hot_content            VARCHAR(255),
  main_image                  BYTEA,
  main                   BOOLEAN,
  news_topic             INT4,
  title                  VARCHAR(255),
  document_tokens        TSVECTOR,
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS news_article_tsv_idx ON news_article USING GIST (document_tokens);

-- Woman Article

DROP TABLE IF EXISTS woman_article;

CREATE TABLE woman_article (
  id                     SERIAL NOT NULL,
  creation_date          TIMESTAMP,
  last_modification_date TIMESTAMP,
  times_visited          BIGINT,
  content                TEXT,
  hash_tags              VARCHAR(255),
  hot_content            VARCHAR(255),
  main_image                  BYTEA,
  main                   BOOLEAN,
  title                  VARCHAR(255),
  woman_topic            INT4,
  document_tokens        TSVECTOR,
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS woman_article_tsv_idx ON woman_article USING GIST (document_tokens);