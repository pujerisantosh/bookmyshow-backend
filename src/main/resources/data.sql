INSERT INTO movies (id, title, duration) VALUES (1, 'Inception', 148);

INSERT INTO theatres (id, name, city) VALUES (1, 'PVR Orion', 'Bangalore');

INSERT INTO screens (id, name, theatre_id) VALUES (1, 'Screen 1', 1);

INSERT INTO shows (id, start_time, movie_id, screen_id)
VALUES (1, '2026-02-21 10:00:00', 1, 1);