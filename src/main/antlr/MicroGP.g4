grammar MicroGP;

// Lexer rules
TRUE    : 'true' ;
FALSE   : 'false' ;
IF      : 'if' ;
ELSE    : 'else' ;
WHILE   : 'while' ;
PRINT   : 'print' ;
READ    : 'read' ;
ID      : [a-zA-Z]+ ;
NUMBER  : [0-9]+ ;
ADD     : '+' ;
SUB     : '-' ;
MUL     : '*' ;
DIV     : '/' ;
AND     : '&&' ;
OR      : '||' ;
NOT     : '!' ;
EQUAL   : '==' ;
NOTEQUAL: '!=' ;
GT      : '>' ;
LT      : '<' ;
GTE     : '>=' ;
LTE     : '<=' ;
ASSIGN  : '=' ;
LPAREN  : '(' ;
RPAREN  : ')' ;
LBRACE  : '{' ;
RBRACE  : '}' ;
WS      : [ \t\r\n]+ -> skip ;

// Parser rules
program : statement* ;

statement : assignment
          | ifStatement
          | whileLoop
          | printStatement
          | readStatement
          | block ;

assignment : ID ASSIGN expression ';' ;

ifStatement : IF condition block (ELSE block)?;

whileLoop : WHILE condition block ;

printStatement : PRINT expression ';' ;

readStatement : READ ID ';' ;

block : LBRACE statement* RBRACE ;

condition : expression (EQUAL | NOTEQUAL | GT | LT | GTE | LTE) expression
          | expression (AND | OR) expression
          | NOT condition
          | LPAREN condition RPAREN ;

expression : term ((ADD | SUB) term)* ;

term : factor ((MUL | DIV) factor)* ;

factor : NUMBER
       | TRUE
       | FALSE
       | ID
       | LPAREN expression RPAREN ;