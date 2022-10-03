%{
import EtapaLexico.Semantica.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
%}

%token ID CTE CADENA IF THEN ELSE ENDIF OUT FUN RETURN BREAK WHEN WHILE MAYOR_IGUAL MENOR_IGUAL ASIGNACION DISTINTO

%start program

%%
program :

%%
