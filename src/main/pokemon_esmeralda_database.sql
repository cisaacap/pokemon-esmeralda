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

select * from Usuarios;
select * from Pokemons;
select * from Usuario_Pokemon;
