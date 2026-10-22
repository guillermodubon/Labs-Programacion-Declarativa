package org.example;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Theory;
import alice.tuprolog.Var;
import java.io.InputStream;

public class Connection {
    private final Prolog engine = new Prolog();

    public Connection() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/calculo.pl")) {
            if (in == null) throw new IllegalStateException("No se encontró calculo.pl");
            engine.setTheory(new Theory(in));
        }
    }

    public String consultar(String predicado, String expresion) throws Exception {
        Term funcion = engine.toTerm(expresion);
        if (!funcion.isGround()) throw new IllegalArgumentException("Usá la variable x en minúscula.");
        SolveInfo info = engine.solve(new Struct(predicado, new Struct("x"), funcion, new Var("R")));
        return info.isSuccess() ? engine.toString(engine.toTerm(info.getTerm("R").toString())).replace(" ", "")
                : "Sin solución para esa expresión";
    }
}
