grammar Directives;

value: NUMBER | STRING | BYTE_SIZE | TIME_DURATION;

fragment BYTE_UNIT: [Bb] | [Kk][Bb] | [Mm][Bb] | [Gg][Bb] | [Tt][Bb];
fragment TIME_UNIT: [Mm][Ss] | [Ss] | [Mm][Ii][Nn];

BYTE_SIZE: [0-9]+ ('.' [0-9]+)? BYTE_UNIT;
TIME_DURATION: [0-9]+ ('.' [0-9]+)? TIME_UNIT;

NUMBER: [0-9]+;
STRING: '"' ~["]* '"';