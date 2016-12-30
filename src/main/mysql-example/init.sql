CREATE TABLE url_jobs (
  id int(11) NOT NULL auto_increment PRIMARY KEY,
  url VARCHAR(56) NOT NULL,
  method VARCHAR(10) NOT NULL,
  created_on DATETIME
  );
