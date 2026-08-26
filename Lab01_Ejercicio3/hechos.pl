% base de conocimientos: personajes, enemigos, armas y lugares

personaje(leon).
personaje(ashley).
personaje(ada).
personaje(luis).

profesion(leon, agente).
profesion(ashley, estudiante).
profesion(ada, espia).
profesion(luis, investigador).

edad(leon, 27).
edad(ashley, 20).
edad(ada, 26).
edad(luis, 32).

arma(leon, pistola).
arma(leon, cuchillo).
arma(ada, pistola).
arma(luis, cuchillo).

no_lleva_arma_fuego(luis).

enemigo(ganados).
enemigo(regeneradores).

infectado_por(ganados, las_plagas).
infectado_por(regeneradores, las_plagas).

aparece_en(ganados, pueblo).
aparece_en(ganados, castillo).
aparece_en(regeneradores, isla).

dificultad(pueblo, alta).
dificultad(castillo, alta).
dificultad(isla, muy_alta).

ubicacion(leon, pueblo).
ubicacion(luis, pueblo).
ubicacion(ada, pueblo).
ubicacion(ada, castillo).