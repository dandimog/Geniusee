create table ticket (id int primary key, movie_id int, price int, seat_number int);
alter table ticket ADD CONSTRAINT fk_movie_id FOREIGN KEY (movie_id) REFERENCES movie(id) ON UPDATE CASCADE ON DELETE CASCADE;
