CREATE TABLE user_detail (
  id       SERIAL       NOT NULL,
  name     VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  email    VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE role (
  id   SERIAL       NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user_roles (
  user_id integer NOT NULL,
  role_id integer NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user_detail(id),
  FOREIGN KEY (role_id) REFERENCES role(id),
  UNIQUE (user_id, role_id)
);

INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_PM');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');