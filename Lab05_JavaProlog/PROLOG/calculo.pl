sumar(V, A+B, R) :- algebra(V, A+B, R).
restar(V, A-B, R) :- algebra(V, A-B, R).

algebra(V, E, R) :-
    terminos(V, E, T), member(t(N, _), T), N =\= 0, !,
    combinar(T, U), expresion(V, U, 0, R).

normalizar(V, E, R) :- terminos(V, E, T), !, combinar(T, U), expresion(V, U, 0, R).
normalizar(_, R, R).

terminos(_, N, [t(0, N)]) :- number(N), !.
terminos(V, V, [t(1, 1)]) :- !.
terminos(V, V^N, [t(N, 1)]) :- number(N), !.
terminos(V, A+B, T) :- !, terminos(V, A, L), terminos(V, B, M), append(L, M, T).
terminos(V, A-B, T) :- !, terminos(V, A, L), terminos(V, B, M), escalar(-1, M, S), append(L, S, T).
terminos(V, -A, T) :- !, terminos(V, A, L), escalar(-1, L, T).
terminos(V, K*A, T) :- number(K), !, terminos(V, A, L), escalar(K, L, T).
terminos(V, A*K, T) :- number(K), !, terminos(V, A, L), escalar(K, L, T).
terminos(V, A/K, T) :- number(K), K =\= 0, terminos(V, A, L), C is 1.0/K, escalar(C, L, T).

escalar(_, [], []).
escalar(K, [t(N, C)|T], [t(N, D)|R]) :- D is K*C, escalar(K, T, R).

combinar([], []).
combinar([t(N, C)|T], [t(N, S)|R]) :- extraer(N, T, C, S, U), combinar(U, R).

extraer(_, [], C, C, []).
extraer(N, [t(N, K)|T], C, S, R) :- !, D is C+K, extraer(N, T, D, S, R).
extraer(N, [H|T], C, S, [H|R]) :- extraer(N, T, C, S, R).

expresion(_, [], R, R).
expresion(V, [t(_, C)|T], A, R) :- C =:= 0, !, expresion(V, T, A, R).
expresion(V, [t(N, C)|T], 0, R) :- !, monomio(V, N, C, M), expresion(V, T, M, R).
expresion(V, [t(N, C)|T], A, R) :-
    K is abs(C), monomio(V, N, K, M),
    (C < 0 -> S = A-M ; S = A+M), expresion(V, T, S, R).

monomio(_, N, C, C) :- N =:= 0, !.
monomio(_, _, C, 0) :- C =:= 0, !.
monomio(V, N, C, R) :- N =:= 1, !, coeficiente(C, V, R).
monomio(V, N, C, R) :- coeficiente(C, V^N, R).
coeficiente(C, T, T) :- C =:= 1, !.
coeficiente(C, T, -T) :- C =:= -1, !.
coeficiente(C, T, C*T).

d(V, E, R) :- derivar(V, E, D), normalizar(V, D, R).
derivar(_, K, 0) :- number(K), !.
derivar(V, V, 1) :- !.
derivar(V, V^N, R) :- number(N), !, M is N-1, monomio(V, M, N, R).
derivar(V, K*A, K*D) :- number(K), !, derivar(V, A, D).
derivar(V, A+B, L+M) :- !, derivar(V, A, L), derivar(V, B, M).
derivar(V, A-B, L-M) :- !, derivar(V, A, L), derivar(V, B, M).
derivar(V, -A, -D) :- !, derivar(V, A, D).
derivar(V, log(V), 1/V).
derivar(V, A^V, A^V*log(A)) :- number(A), A > 0.

s(V, E, R) :- integrar(V, E, I), normalizar(V, I, R).
integrar(V, K, K*V) :- number(K), !.
integrar(V, V, 0.5*V^2) :- !.
integrar(V, V^N, C*V^M) :- number(N), N =\= -1, !, M is N+1, C is 1.0/M.
integrar(V, V^(-1), log(V)) :- !.
integrar(V, K*A, K*I) :- number(K), !, integrar(V, A, I).
integrar(V, A+B, L+M) :- !, integrar(V, A, L), integrar(V, B, M).
integrar(V, A-B, L-M) :- !, integrar(V, A, L), integrar(V, B, M).
integrar(V, -A, -I) :- !, integrar(V, A, I).
integrar(V, A^V, A^V/log(A)) :- number(A), A > 0, A =\= 1.
