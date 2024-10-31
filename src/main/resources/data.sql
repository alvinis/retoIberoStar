-- Asegúrate de que no haya comentarios problemáticos
CREATE TABLE IF NOT EXISTS spaceship (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
    series VARCHAR(255) NOT NULL,
    movie VARCHAR(255) NOT NULL
    );

INSERT INTO spaceship (name, series, movie) VALUES
                                                ('Millennium Falcon', 'Star Wars', 'Star Wars: Episode IV - A New Hope'),
                                                ('USS Enterprise', 'Star Trek', 'Star Trek: The Motion Picture'),
                                                ('TARDIS', 'Doctor Who', 'Doctor Who: The Time of the Doctor'),
                                                ('B-wing', 'Star Wars', 'Star Wars: Return of the Jedi'),
                                                ('Serenity', 'Firefly', 'Serenity'),
                                                ('Nostromo', 'Alien', 'Alien'),
                                                ('Discovery One', '2001: A Space Odyssey', '2001: A Space Odyssey'),
                                                ('Galactica', 'Battlestar Galactica', 'Battlestar Galactica'),
                                                ('Event Horizon', 'Event Horizon', 'Event Horizon');
