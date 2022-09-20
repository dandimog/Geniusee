create table ticket (id int primary key, movieId int, name varchar(255));
alter table ticket ADD CONSTRAINT fk_movie_id FOREIGN KEY (movieId) REFERENCES movie(id) ON UPDATE CASCADE ON DELETE CASCADE;
