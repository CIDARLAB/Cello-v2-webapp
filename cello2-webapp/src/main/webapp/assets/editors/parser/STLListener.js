// Generated from STL.g4 by ANTLR 4.5.3
// jshint ignore: start
var antlr4 = require('antlr4/index');

// This class defines a complete listener for a parse tree produced by STLParser.
function STLListener() {
	antlr4.tree.ParseTreeListener.call(this);
	return this;
}

STLListener.prototype = Object.create(antlr4.tree.ParseTreeListener.prototype);
STLListener.prototype.constructor = STLListener;

// Enter a parse tree produced by STLParser#specification.
STLListener.prototype.enterSpecification = function(ctx) {
};

// Exit a parse tree produced by STLParser#specification.
STLListener.prototype.exitSpecification = function(ctx) {
};


// Enter a parse tree produced by STLParser#moduleLeaf.
STLListener.prototype.enterModuleLeaf = function(ctx) {
};

// Exit a parse tree produced by STLParser#moduleLeaf.
STLListener.prototype.exitModuleLeaf = function(ctx) {
};


// Enter a parse tree produced by STLParser#moduleOp.
STLListener.prototype.enterModuleOp = function(ctx) {
};

// Exit a parse tree produced by STLParser#moduleOp.
STLListener.prototype.exitModuleOp = function(ctx) {
};


// Enter a parse tree produced by STLParser#moduleDescription.
STLListener.prototype.enterModuleDescription = function(ctx) {
};

// Exit a parse tree produced by STLParser#moduleDescription.
STLListener.prototype.exitModuleDescription = function(ctx) {
};


// Enter a parse tree produced by STLParser#booleanPred.
STLListener.prototype.enterBooleanPred = function(ctx) {
};

// Exit a parse tree produced by STLParser#booleanPred.
STLListener.prototype.exitBooleanPred = function(ctx) {
};


// Enter a parse tree produced by STLParser#formula.
STLListener.prototype.enterFormula = function(ctx) {
};

// Exit a parse tree produced by STLParser#formula.
STLListener.prototype.exitFormula = function(ctx) {
};


// Enter a parse tree produced by STLParser#parprop.
STLListener.prototype.enterParprop = function(ctx) {
};

// Exit a parse tree produced by STLParser#parprop.
STLListener.prototype.exitParprop = function(ctx) {
};


// Enter a parse tree produced by STLParser#expr.
STLListener.prototype.enterExpr = function(ctx) {
};

// Exit a parse tree produced by STLParser#expr.
STLListener.prototype.exitExpr = function(ctx) {
};


// Enter a parse tree produced by STLParser#booleanExpr.
STLListener.prototype.enterBooleanExpr = function(ctx) {
};

// Exit a parse tree produced by STLParser#booleanExpr.
STLListener.prototype.exitBooleanExpr = function(ctx) {
};


// Enter a parse tree produced by STLParser#translationMap.
STLListener.prototype.enterTranslationMap = function(ctx) {
};

// Exit a parse tree produced by STLParser#translationMap.
STLListener.prototype.exitTranslationMap = function(ctx) {
};


// Enter a parse tree produced by STLParser#translationPair.
STLListener.prototype.enterTranslationPair = function(ctx) {
};

// Exit a parse tree produced by STLParser#translationPair.
STLListener.prototype.exitTranslationPair = function(ctx) {
};


// Enter a parse tree produced by STLParser#limitMap.
STLListener.prototype.enterLimitMap = function(ctx) {
};

// Exit a parse tree produced by STLParser#limitMap.
STLListener.prototype.exitLimitMap = function(ctx) {
};


// Enter a parse tree produced by STLParser#limitPair.
STLListener.prototype.enterLimitPair = function(ctx) {
};

// Exit a parse tree produced by STLParser#limitPair.
STLListener.prototype.exitLimitPair = function(ctx) {
};



exports.STLListener = STLListener;