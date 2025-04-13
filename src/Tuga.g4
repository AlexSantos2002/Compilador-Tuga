grammar Tuga;

tuga : inst+ EOF;

inst : 'escreve' expr END_INST						# PrintInst ;

expr
  : LPAREN expr RPAREN								# ParenExpr
  | op=SUB expr										# ArithmeticNegateOp
  | op=NOT expr										# LogicNegateOp
  | expr op=(MLT|DIV|MOD) expr						# MultDivOp
  | expr op=(SUM|SUB) expr							# SumSubOp
  | expr op=(LESS|GREATER|LESS_EQ|GREATER_EQ) expr	# RelOp
  | expr op=(EQUALS|N_EQUALS) expr					# EqualsOp
  | expr op=AND expr								# AndOp
  | expr op=OR expr									# OrOp
  | DOUBLE											# DoubleLiteral
  | STRING											# StringLiteral
  | TRUE											# TrueLiteral
  | FALSE											# FalseLiteral
  ;

// Operators
END_INST	: ';' ;
LPAREN		: '(' ;
RPAREN		: ')' ;
SUM			: '+' ;
SUB			: '-' ;
MLT			: '*' ;
DIV			: '/' ;
MOD			: '%' ;
LESS		: '<' ;
GREATER		: '>' ;
LESS_EQ		: '<=' ;
GREATER_EQ	: '>=' ;
EQUALS		: 'igual' ;
N_EQUALS	: 'diferente' ;
AND			: 'e' ;
OR			: 'ou' ;
NOT			: 'nao' ;

// Literals
DOUBLE		: [0-9]+ '.' [0-9]+ ;
INT			: [0-9]+ ;
STRING		: '"' ~["]* '"' ;
TRUE		: 'verdadeiro' ;
FALSE		: 'falso' ;

// Whitespace and Comments
WS			: [ \t\r\n]+ -> skip ;
SL_COMMENT	: '//' .*? (EOF | '\n') -> skip ;
ML_COMMENT	: '/*' .*? '*/' -> skip ;
