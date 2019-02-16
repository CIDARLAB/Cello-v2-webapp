grammar Eugene;

// @header {
// /**
//  * Copyright (C) 2015  Cristian Ioan Vasile <cvasile@bu.edu>
//  * Hybrid and Networked Systems (HyNeSs) Group, BU Robotics Lab, Boston University
//  * See license.txt file for license information.
//  */
// package hyness.stl.grammar.flat;
// }

specification:   (assign)+;

assign
	: ID? relOp ID
	| ID? relOp INT
	;
relOp // user-defined relational operators
    :   ('CONTAINS'|'contains')
    |   ('NOTCONTAINS'|'notcontains')
    |   ('NOTMORETHAN'|'notmorethan')
    |   ('EXACTLY'|'exactly')
    |   ('NOTEXACTLY'|'notexactly')
    |   ('MORETHAN'|'morethan')
    |   ('SAME_COUNT'|'same_count')
    |	('STARTSWITH'|'startswith')
    |   ('BEFORE'|'before')
    |   ('ALWAYS_NEXTTO'|'always_nextto')
    |   ('NEXTTO'|'nextto')
    |   ('AFTER'|'after')
    |   ('SOME_BEFORE'|'some_before')
    |   ('ALL_BEFORE'|'all_before')
    |   ('SOME_AFTER'|'some_after')
    |   ('ALL_AFTER'|'all_after')
    |   ('ENDSWITH'|'endswith')
    |   ('FORWARD'|'forward')
    |   ('REVERSE'|'reverse')
    |   ('SAME_ORIENTATION'|'same_orientation')
    |   ('ALL_FORWARD'|'all_forward')
    |   ('ALL_REVERSE'|'all_reverse')
    |   ('ALL_SAME_ORIENTATION'|'all_same_orientation')
    |   ('REPRESSES'|'represses')
    |   ('INDUCES'|'induces')
    |   ('DRIVES'|'drives')
    |   ('BINDS'|'binds')
    ;

// ruleOperators
// 	:	('WITH'|'with')
// 	|	('THEN'|'then')
// 	|	('ALL_NEXTTO'|'all_nextto')
// 	|	('SOME_NEXTTO'|'some_nextto')
// 	|	('EQUALS'|'equals')
// 	|	('MATCHES'|'matches')
// 	|	('SOME_FORWARD'|'some_forward')
// 	|	('SOME_REVERSE'|'some_reverse')
// 	|	('SOME_SAME_ORIENTATION'|'some_same_orientation')
//     |   ('TEMPLATE'|'template')
//     |   ('SEQUENCE'|'sequence')
// 	|	('ALTERNATE_ORIENTATION'|'alternate_orientation')
// 	|	('NOTWITH'|'notwith')
// 	|	('NOTTHEN'|'notthen')
// 	|	('NOTEQUALS'|'notequals')
// 	|	('NOTMATCHES'|'notmatches')
// 	;

// expr
//     :   ('NOT'|'not')? expr
//     |   expr ('or')? ('not')? (expr)?
//     ;


// PLUS: '+' ;
// MINUS: '-' ;
// MULT: '*' ;
// DIV: '/' ;
// LEFTP: '(';
// RIGHTP: ')';
// LEFTSBR: '[';
// RIGHTSBR: ']';
// LEFTCUR: '{';
// RIGHTCUR: '}';
// DOLLAR: '$';
// EQUALS: '=';
// UNDERS: '_';
// SEMIC: ';';
// COLON: ':';
// COMMA: ',';
// DOT: '.';
// DOTDOT: '..';
// PIPE: '|';
// NUM: 'num';
// BOOL: 'bool';
// BOOLEAN: 'boolean';
// IMAGE: 'Image';
// PROPERTY: 'Property';
// TYPE: 'Type';
// PART_TYPE: 'PartType';
// PART: 'Part';
// DEVICE: 'Device';
// RULE: 'Rule';
// TXT: 'txt';
// ADDPROPS: 'addProperties';
// AMP: '&';
// REF: 'ref';
// NEQUAL: '!=';
// LTHAN: '<';
// GTHAN: '>';
// LEQUAL: '<=';
// GEQUAL: '>=';
// UC_AND: 'AND';
// LC_AND: 'and';
// LOG_AND: '/\\';
// UC_OR: 'OR';
// LC_OR: 'or';
// LOG_OR: '\\/';
// UC_NOT: 'NOT';
// LC_NOT: 'not';
// OP_NOT: '!';
// ASSERT: 'Assert';
// NOTE: 'Note';
// LC_IF: 'if';
// UC_IF: 'IF';
// LC_ELSEIF: 'elseif';
// UC_ELSEIF: 'ELSEIF';	
// LC_ELSE: 'else';
// UC_ELSE: 'ELSE';
// UC_ON: 'ON';
// LC_ON: 'on';
// TRUE_LC: 'true';
// TRUE_UC: 'TRUE';
// FALSE_LC: 'false';
// FALSE_UC: 'FALSE';
// STRICT: 'strict';
// FLEXIBLE: 'flexible';
// COLLECTION: 'Collection';
// RULE_BUILDER: 'RuleBuilder';
// ARRAY: 'Array';
// SBOL: 'SBOL';
// GENBANK: 'Genbank';
// REGISTRY: 'Registry';

// INTERACTION: 'Interaction';
// UC_REPRESSES: 'REPRESSES';
// LC_REPRESSES: 'represses';
// UC_INDUCES: 'INDUCES';
// LC_INDUCES: 'induces';
// HASHMARK: '#';	

// LC_FORALL: 'forall';
// UC_FORALL: 'FORALL';
// LC_FOR: 'for';
// UC_FOR: 'FOR';
// LC_WHILE: 'while';
// UC_WHILE: 'WHILE';

// ARROW: '-->';
// GRAMMAR: 'Grammar';

// RETURN_UC: 'RETURN';
// RETURN_LC: 'return';

// /* ------------------------------------- 
//     * RESERVED WORDS FOR BUILT-IN FUNCTIONS
//     * -------------------------------------*/

// UC_PERMUTE: 'PERMUTE';
// LC_PERMUTE: 'permute';
// UC_PRODUCT: 'PRODUCT';
// LC_PRODUCT: 'product';

// INCLUDE_LC: 'include';
// INCLUDE_UC: 'INCLUDE';

// IMPORT_LC: 'import';
// IMPORT_UC: 'IMPORT';

// EXPORT_LC: 'export';
// EXPORT_UC: 'EXPORT';

// PRINT_LC: 'print';
// PRINT_UC: 'PRINT';
// PRINTLN_LC: 'println';
// PRINTLN_UC: 'PRINTLN';

// SIZEOF_LC: 'sizeof';
// SIZEOF_UC: 'SIZEOF';
// SIZE_OF_LC: 'size_of';
// SIZE_OF_UC: 'SIZE_OF';

// SIZE_LC: 'size';
// SIZE_UC: 'SIZE';

// RANDOM_LC: 'random';
// RANDOM_UC: 'RANDOM';

// SAVE_LC: 'save';
// SAVE_UC: 'SAVE';
// STORE_LC: 'store';
// STORE_UC: 'STORE';

// VISUALIZE_LC: 'visualize';
// VISUALIZE_UC: 'VISUALIZE';

// LC_SEQUENCE_OF: 'sequence_of';
// UC_SEQUENCE_OF: 'SEQUENCE_OF';

// EXIT_UC: 'EXIT';
// EXIT_LC: 'exit';

// /*   -----
// *   Functions to interact with LMS
// *   ----- */
// CREATE_UC: 'CREATE';
// CREATE_LC: 'create';

// QUERY_UC: 'QUERY';
// QUERY_LC: 'query';

// READ_UC: 'READ';
// READ_LC: 'read';

// UPDATE_UC: 'UPDATE';
// UPDATE_LC: 'update';

// DELETE_UC: 'DELETE';
// DELETE_LC: 'delete';
 			
// relationalOperators
// 	:	EQUALS EQUALS
// 	|	NEQUAL
// 	|	LTHAN
// 	|	GTHAN
// 	|	LEQUAL
// 	|	GEQUAL
// 	|	('CONTAINS'|'contains')
// 	|	('NOTCONTAINS'|'notcontains')
// 	|	('MATCHES'|'matches')
// 	|	('NOTMATCHES'|'notmatches')
// 	|	('STARTSWITH'|'startswith')
// 	|	('ENDSWITH'|'endswith')
// 	|	('EQUALS'|'equals')
// 	|	('NOTEQUALS'|'notequals')
// 	|	('SOUNDSLIKE'|'soundslike')
// 	;


// grammarDeclaration[boolean defer]
// 	:
// 		GRAMMAR n=ID LEFTP list_of_production_rules[defer] RIGHTP
// 	;

// list_of_production_rules[boolean defer]
// 	:	production_rule[defer] SEMIC (list_of_production_rules[defer])?	
// 	;	

// production_rule[boolean defer]	
// 	:	lhs=ID {	
// 	}	ARROW right_hand_side[defer] 
// 	;

// right_hand_side[boolean defer]
// 	:	i=ID {
// 	}	(COMMA right_hand_side[defer])?
// 	;	
	
 
INT	:	[0-9]+ ;

REAL:	INT '.' INT ;

ID	:	('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')* ;

// WS 	:	( '\t' | ' ' | '\u000C' | '\r' )+ -> skip ;
WS	:	[ \t\n\r]+ -> channel(HIDDEN) ;

NEWLINE
	:	'\r' ? '\n' -> channel(HIDDEN) ;

SL_COMMENT
	:	'//' .*? '\r'? '\n' -> channel(HIDDEN) ;

ML_COMMENT
	:	'/*' ? '*/' -> channel(HIDDEN) ;

fragment DIGIT
	:	'0'..'9' ;

STRING
	:	'"' * '"' ;