DROP TABLE IF EXISTS link_statistic;
DROP TABLE IF EXISTS links;

CREATE TABLE links (
  link_id BIGSERIAL NOT NULL ,
  active BOOLEAN DEFAULT TRUE NOT NULL,
  created_date TIMESTAMP DEFAULT now() NOT NULL,
  end_date TIMESTAMP NOT NULL,
  short_link VARCHAR(255) NOT NULL,
  link VARCHAR(2048) NOT NULL,
  PRIMARY KEY (link_id),
  UNIQUE (short_link, link)
);

CREATE TABLE link_statistic (
  statistic_id BIGSERIAL NOT NULL,
  ip_address VARCHAR(255),
  browser VARCHAR(255),
  follow_date TIMESTAMP,
  refferer VARCHAR(255),
  link_id BIGINT NOT NULL,
  PRIMARY KEY (statistic_id),
  FOREIGN KEY (link_id) REFERENCES links ON DELETE CASCADE
);
