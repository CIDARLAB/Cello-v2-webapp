// Generated from STL.g4 by ANTLR 4.5.3
// jshint ignore: start
var antlr4 = require('antlr4/index');

// This class defines a complete generic visitor for a parse tree produced by STLParser.

function STLVisitor() {
	antlr4.tree.ParseTreeVisitor.call(this);
	return this;
}

STLVisitor.prototype = Object.create(antlr4.tree.ParseTreeVisitor.prototype);
STLVisitor.prototype.constructor = STLVisitor;

// Visit a parse tree produced by STLParser#specification.
STLVisitor.prototype.visitSpecification = function(ctx) {
};


// Visit a parse tree produced by STLParser#moduleLeaf.
STLVisitor.prototype.visitModuleLeaf = function(ctx) {
};


// Visit a parse tree produced by STLParser#moduleOp.
STLVisitor.prototype.visitModuleOp = function(ctx) {
};


// Visit a parse tree produced by STLParser#moduleDescription.
STLVisitor.prototype.visitModuleDescription = function(ctx) {
};


// Visit a parse tree produced by STLParser#booleanPred.
STLVisitor.prototype.visitBooleanPred = function(ctx) {
};


// Visit a parse tree produced by STLParser#formula.
STLVisitor.prototype.visitFormula = function(ctx) {
};


// Visit a parse tree produced by STLParser#parprop.
STLVisitor.prototype.visitParprop = function(ctx) {
};


// Visit a parse tree produced by STLParser#expr.
STLVisitor.prototype.visitExpr = function(ctx) {
};


// Visit a parse tree produced by STLParser#booleanExpr.
STLVisitor.prototype.visitBooleanExpr = function(ctx) {
};


// Visit a parse tree produced by STLParser#translationMap.
STLVisitor.prototype.visitTranslationMap = function(ctx) {
};


// Visit a parse tree produced by STLParser#translationPair.
STLVisitor.prototype.visitTranslationPair = function(ctx) {
};


// Visit a parse tree produced by STLParser#limitMap.
STLVisitor.prototype.visitLimitMap = function(ctx) {
};


// Visit a parse tree produced by STLParser#limitPair.
STLVisitor.prototype.visitLimitPair = function(ctx) {
};



exports.STLVisitor = STLVisitor;