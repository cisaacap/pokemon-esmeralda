create database pokemon_esmeralda_in4bm;

use pokemon_esmeralda_in4bm;

create table Usuarios(

	nickname varchar(35) not null,
    email varchar(60) not null,
    passwrd varchar(35) not null,
    pokemons int default 1,
    pokedex int default 1,
    primary key(nickname, email)

);

create table Pokemons(

	id_pokemon int primary key not null,
    nombre_pokemon varchar(60) not null,
    primary_type varchar(10) not null,
    second_type varchar(10),
    base_damage double default 10.00

);

create table Usuario_Pokemon(

	id_pokemon_usuario int auto_increment primary key,
    nickname varchar(35) not null,
    id_pokemon int not null,
    mote varchar(20),
    health double default 100.00

);

INSERT INTO Pokemons (id_pokemon, nombre_pokemon, primary_type, second_type, base_damage) VALUES
-- Línea Bulbasaur (3 etapas)
(1, 'Bulbasaur', 'Grass', 'Poison', 10.00),
(2, 'Ivysaur', 'Grass', 'Poison', 15.00),
(3, 'Venusaur', 'Grass', 'Poison', 20.00),

-- Línea Charmander (3 etapas)
(4, 'Charmander', 'Fire', NULL, 10.00),
(5, 'Charmeleon', 'Fire', NULL, 15.00),
(6, 'Charizard', 'Fire', 'Flying', 20.00),

-- Línea Squirtle (3 etapas)
(7, 'Squirtle', 'Water', NULL, 10.00),
(8, 'Wartortle', 'Water', NULL, 15.00),
(9, 'Blastoise', 'Water', NULL, 20.00),

-- Línea Caterpie (3 etapas)
(10, 'Caterpie', 'Bug', NULL, 10.00),
(11, 'Metapod', 'Bug', NULL, 15.00),
(12, 'Butterfree', 'Bug', 'Flying', 20.00),

-- Línea Weedle (3 etapas)
(13, 'Weedle', 'Bug', 'Poison', 10.00),
(14, 'Kakuna', 'Bug', 'Poison', 15.00),
(15, 'Beedrill', 'Bug', 'Poison', 20.00),

-- Línea Pidgey (3 etapas)
(16, 'Pidgey', 'Normal', 'Flying', 10.00),
(17, 'Pidgeotto', 'Normal', 'Flying', 15.00),
(18, 'Pidgeot', 'Normal', 'Flying', 20.00),

-- Línea Rattata (2 etapas)
(19, 'Rattata', 'Normal', NULL, 15.00),
(20, 'Raticate', 'Normal', NULL, 20.00),

-- Línea Spearow (2 etapas)
(21, 'Spearow', 'Normal', 'Flying', 15.00),
(22, 'Fearow', 'Normal', 'Flying', 20.00),

-- Línea Ekans (2 etapas)
(23, 'Ekans', 'Poison', NULL, 15.00),
(24, 'Arbok', 'Poison', NULL, 20.00),

-- Línea Pikachu (2 etapas en Gen 1)
(25, 'Pikachu', 'Electric', NULL, 15.00),
(26, 'Raichu', 'Electric', NULL, 20.00),

-- Línea Sandshrew (2 etapas)
(27, 'Sandshrew', 'Ground', NULL, 15.00),
(28, 'Sandslash', 'Ground', NULL, 20.00),

-- Líneas Nidoran (3 etapas en Gen 1: Nidoran -> Nidorina/Nidorino -> Nidoqueen/Nidoking)
(29, 'Nidoran♀', 'Poison', NULL, 10.00),
(30, 'Nidorina', 'Poison', NULL, 15.00),
(31, 'Nidoqueen', 'Poison', 'Ground', 20.00),
(32, 'Nidoran♂', 'Poison', NULL, 10.00),
(33, 'Nidorino', 'Poison', NULL, 15.00),
(34, 'Nidoking', 'Poison', 'Ground', 20.00),

-- Línea Clefairy (2 etapas en Gen 1)
(35, 'Clefairy', 'Fairy', NULL, 15.00),
(36, 'Clefable', 'Fairy', NULL, 20.00),

-- Línea Vulpix (2 etapas)
(37, 'Vulpix', 'Fire', NULL, 15.00),
(38, 'Ninetales', 'Fire', NULL, 20.00),

-- Línea Jigglypuff (2 etapas en Gen 1)
(39, 'Jigglypuff', 'Normal', 'Fairy', 15.00),
(40, 'Wigglytuff', 'Normal', 'Fairy', 20.00),

-- Línea Zubat (2 etapas en Gen 1)
(41, 'Zubat', 'Poison', 'Flying', 15.00),
(42, 'Golbat', 'Poison', 'Flying', 20.00),

-- Línea Oddish (3 etapas)
(43, 'Oddish', 'Grass', 'Poison', 10.00),
(44, 'Gloom', 'Grass', 'Poison', 15.00),
(45, 'Vileplume', 'Grass', 'Poison', 20.00),

-- Línea Paras (2 etapas)
(46, 'Paras', 'Bug', 'Grass', 15.00),
(47, 'Parasect', 'Bug', 'Grass', 20.00),

-- Línea Venonat (2 etapas)
(48, 'Venonat', 'Bug', 'Poison', 15.00),
(49, 'Venomoth', 'Bug', 'Poison', 20.00),

-- Línea Diglett (2 etapas)
(50, 'Diglett', 'Ground', NULL, 15.00),
(51, 'Dugtrio', 'Ground', NULL, 20.00);

select * from Usuarios;
select * from Pokemons;
select * from Usuario_Pokemon;
