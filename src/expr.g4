grammar expr;

program : (statement)* EOF ;

statement : 'escreve' expr ';' ;

// Prioridades conforme a Seccao 2.2 do enunciado
expr
    : '(' expr ')'                             # ParentExpr
    | '-' expr                                 # UnaryMinusExpr
    | 'nao' expr                               # UnaryNotExpr
    | expr op=('*' | '/' | '%') expr           # MulDivModExpr
    | expr op=('+' | '-') expr                 # AddSubExpr
    | expr op=('<' | '>' | '<=' | '>=') expr   # RelationalExpr
    | expr op=('igual' | 'diferente') expr     # EqualityExpr
    | expr 'e' expr                            # AndExpr
    | expr 'ou' expr                           # OrExpr
    | expr '+' expr                            # ConcatExpr
    | INT                                      # IntLiteral
    | DOUBLE                                   # DoubleLiteral
    | STRING                                   # StringLiteral
    | 'verdadeiro'                             # TrueLiteral
    | 'falso'                                  # FalseLiteral
    ;

// Tokens
INT     : [0-9]+ ;
DOUBLE  : [0-9]+ '.' [0-9]+ ;
STRING  : '"' (~[\r\n"])* '"' ;

// Coisas a ignorar
WS : [ \t\r\n]+ -> skip ; //ignorar espacos
LINHA_DE_COMENTARIO : '//' .*? ('\n' | EOF) -> skip ;
BLOCO_DE_COMENTARIO : '/*' .*? '*/' -> skip ;
