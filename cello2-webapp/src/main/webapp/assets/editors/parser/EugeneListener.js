// Generated from Eugene.g4 by ANTLR 4.5.3
// jshint ignore: start
var antlr4 = require('antlr4/index');

// This class defines a complete listener for a parse tree produced by EugeneParser.
function EugeneListener() {
	antlr4.tree.ParseTreeListener.call(this);
	return this;
}

EugeneListener.prototype = Object.create(antlr4.tree.ParseTreeListener.prototype);
EugeneListener.prototype.constructor = EugeneListener;

// Enter a parse tree produced by EugeneParser#specification.
EugeneListener.prototype.enterSpecification = function(ctx) {
};

// Exit a parse tree produced by EugeneParser#specification.
EugeneListener.prototype.exitSpecification = function(ctx) {
};


// Enter a parse tree produced by EugeneParser#assign.
EugeneListener.prototype.enterAssign = function(ctx) {
};

// Exit a parse tree produced by EugeneParser#assign.
EugeneListener.prototype.exitAssign = function(ctx) {
};


// Enter a parse tree produced by EugeneParser#relOp.
EugeneListener.prototype.enterRelOp = function(ctx) {
};

// Exit a parse tree produced by EugeneParser#relOp.
EugeneListener.prototype.exitRelOp = function(ctx) {
};



exports.EugeneListener = EugeneListener;