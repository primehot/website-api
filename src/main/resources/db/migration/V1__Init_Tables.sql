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

CREATE INDEX IF NOT EXISTS dream_book_tsv_idx
  ON dream_book USING GIST (document_tokens);

-- Dream Book Article

DROP TABLE IF EXISTS dream_book_article;

CREATE TABLE dream_book_article (
  id                     SERIAL       NOT NULL,
  creation_date          TIMESTAMP    NOT NULL,
  last_modification_date TIMESTAMP    NOT NULL,
  times_visited          BIGINT       NOT NULL,
  content                TEXT         NOT NULL,
  hash_tags              VARCHAR(255) NOT NULL,
  hot_content            VARCHAR(255) NOT NULL,
  main                   BOOLEAN      NOT NULL,
  title                  VARCHAR(255) NOT NULL,
  document_tokens        TSVECTOR,
  author                 VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS dream_book_article_tsv_idx
  ON dream_book_article USING GIST (document_tokens);

-- News Article

DROP TABLE IF EXISTS news_article;

CREATE TABLE news_article (
  id                     SERIAL       NOT NULL,
  creation_date          TIMESTAMP    NOT NULL,
  last_modification_date TIMESTAMP    NOT NULL,
  times_visited          BIGINT       NOT NULL,
  content                TEXT         NOT NULL,
  hash_tags              VARCHAR(255) NOT NULL,
  hot_content            VARCHAR(255) NOT NULL,
  main                   BOOLEAN,
  news_topic             INT4         NOT NULL,
  title                  VARCHAR(255) NOT NULL,
  document_tokens        TSVECTOR,
  author                 VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS news_article_tsv_idx
  ON news_article USING GIST (document_tokens);

-- Woman Article

DROP TABLE IF EXISTS woman_article;

CREATE TABLE woman_article (
  id                     SERIAL       NOT NULL,
  creation_date          TIMESTAMP    NOT NULL,
  last_modification_date TIMESTAMP    NOT NULL,
  times_visited          BIGINT       NOT NULL,
  content                TEXT         NOT NULL,
  hash_tags              VARCHAR(255) NOT NULL,
  hot_content            VARCHAR(255) NOT NULL,
  main                   BOOLEAN,
  title                  VARCHAR(255) NOT NULL,
  woman_topic            INT4         NOT NULL,
  document_tokens        TSVECTOR     NOT NULL,
  author                 VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS woman_article_tsv_idx
  ON woman_article USING GIST (document_tokens);

-- Image
DROP TABLE IF EXISTS image;

CREATE TABLE image (
  id                     SERIAL    NOT NULL,
  creation_date          TIMESTAMP NOT NULL,
  last_modification_date TIMESTAMP NOT NULL,
  image                  BYTEA     NOT NULL,
  author                 VARCHAR(255),
  paragraph              INT4,

  PRIMARY KEY (id)
);

CREATE TABLE news_article_images (
  news_article_id INTEGER NOT NULL,
  image_id        INTEGER NOT NULL,
  FOREIGN KEY (news_article_id) REFERENCES news_article (id),
  FOREIGN KEY (image_id) REFERENCES image (id),
  UNIQUE (news_article_id, image_id)
);

CREATE TABLE woman_article_images (
  woman_article_id INTEGER NOT NULL,
  image_id         INTEGER NOT NULL,
  FOREIGN KEY (woman_article_id) REFERENCES woman_article (id),
  FOREIGN KEY (image_id) REFERENCES image (id),
  UNIQUE (woman_article_id, image_id)
);

CREATE TABLE dream_book_article_images (
  dream_book_article_id INTEGER NOT NULL,
  image_id              INTEGER NOT NULL,
  FOREIGN KEY (dream_book_article_id) REFERENCES dream_book_article (id),
  FOREIGN KEY (image_id) REFERENCES image (id),
  UNIQUE (dream_book_article_id, image_id)
);

CREATE TABLE dream_book_images (
  dream_book_id INTEGER NOT NULL,
  image_id      INTEGER NOT NULL,
  FOREIGN KEY (dream_book_id) REFERENCES dream_book (id),
  FOREIGN KEY (image_id) REFERENCES image (id),
  UNIQUE (dream_book_id, image_id)
);
