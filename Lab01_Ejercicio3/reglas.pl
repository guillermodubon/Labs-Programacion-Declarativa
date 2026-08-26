:- consult('hechos.pl').

% un personaje es adulto si tiene 18 o mas
es_adulto(Personaje) :-
    personaje(Personaje),
    edad(Personaje, Edad),
    Edad >= 18.

% un personaje esta armado si tiene pistola o cuchillo
esta_armado(Personaje) :-
    arma(Personaje, pistola)
    ;
    arma(Personaje, cuchillo).

% un personaje esta en una zona peligrosa si esta en un lugar de dificultad alta o muy alta
esta_en_zona_peligrosa(Personaje) :-
    ubicacion(Personaje, Lugar),
    (dificultad(Lugar, alta) ; dificultad(Lugar, muy_alta)).

% un personaje puede enfrentar a un enemigo si ambos aparecen en el mismo lugar y el personaje tiene alguna arma
puede_enfrentar(Personaje, Enemigo) :-
    ubicacion(Personaje, Lugar),
    aparece_en(Enemigo, Lugar),
    enemigo(Enemigo),
    arma(Personaje, _).

% un enemigo es peligroso si esta infectado por las plagas y aparece en un lugar de dificultad alta o muy alta.
enemigo_peligroso(Enemigo) :-
    enemigo(Enemigo),
    infectado_por(Enemigo, las_plagas),
    aparece_en(Enemigo, Lugar),
    (dificultad(Lugar, alta) ; dificultad(Lugar, muy_alta)).