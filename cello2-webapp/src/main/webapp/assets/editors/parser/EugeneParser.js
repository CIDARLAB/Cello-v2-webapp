// Generated from Eugene.g4 by ANTLR 4.5.3
// jshint ignore: start
var antlr4 = require('antlr4/index');
var EugeneListener = require('./EugeneListener').EugeneListener;
var grammarFileName = "Eugene.g4";

var serializedATN = ["\u0003\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd",
    "\u0003@9\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004",
    "\u0003\u0002\u0006\u0002\n\n\u0002\r\u0002\u000e\u0002\u000b\u0003\u0003",
    "\u0005\u0003\u000f\n\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0005\u0003\u0015\n\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0005\u0003\u001a\n\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003",
    "\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003",
    "\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003",
    "\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003",
    "\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0005",
    "\u00047\n\u0004\u0003\u0004\u0002\u0002\u0005\u0002\u0004\u0006\u0002",
    "\u001d\u0003\u0002\u0003\u0004\u0003\u0002\u0005\u0006\u0003\u0002\u0007",
    "\b\u0003\u0002\t\n\u0003\u0002\u000b\f\u0003\u0002\r\u000e\u0003\u0002",
    "\u000f\u0010\u0003\u0002\u0011\u0012\u0003\u0002\u0013\u0014\u0003\u0002",
    "\u0015\u0016\u0003\u0002\u0017\u0018\u0003\u0002\u0019\u001a\u0003\u0002",
    "\u001b\u001c\u0003\u0002\u001d\u001e\u0003\u0002\u001f \u0003\u0002",
    "!\"\u0003\u0002#$\u0003\u0002%&\u0003\u0002\'(\u0003\u0002)*\u0003\u0002",
    "+,\u0003\u0002-.\u0003\u0002/0\u0003\u000212\u0003\u000234\u0003\u0002",
    "56\u0003\u000278S\u0002\t\u0003\u0002\u0002\u0002\u0004\u0019\u0003",
    "\u0002\u0002\u0002\u00066\u0003\u0002\u0002\u0002\b\n\u0005\u0004\u0003",
    "\u0002\t\b\u0003\u0002\u0002\u0002\n\u000b\u0003\u0002\u0002\u0002\u000b",
    "\t\u0003\u0002\u0002\u0002\u000b\f\u0003\u0002\u0002\u0002\f\u0003\u0003",
    "\u0002\u0002\u0002\r\u000f\u0007;\u0002\u0002\u000e\r\u0003\u0002\u0002",
    "\u0002\u000e\u000f\u0003\u0002\u0002\u0002\u000f\u0010\u0003\u0002\u0002",
    "\u0002\u0010\u0011\u0005\u0006\u0004\u0002\u0011\u0012\u0007;\u0002",
    "\u0002\u0012\u001a\u0003\u0002\u0002\u0002\u0013\u0015\u0007;\u0002",
    "\u0002\u0014\u0013\u0003\u0002\u0002\u0002\u0014\u0015\u0003\u0002\u0002",
    "\u0002\u0015\u0016\u0003\u0002\u0002\u0002\u0016\u0017\u0005\u0006\u0004",
    "\u0002\u0017\u0018\u00079\u0002\u0002\u0018\u001a\u0003\u0002\u0002",
    "\u0002\u0019\u000e\u0003\u0002\u0002\u0002\u0019\u0014\u0003\u0002\u0002",
    "\u0002\u001a\u0005\u0003\u0002\u0002\u0002\u001b7\t\u0002\u0002\u0002",
    "\u001c7\t\u0003\u0002\u0002\u001d7\t\u0004\u0002\u0002\u001e7\t\u0005",
    "\u0002\u0002\u001f7\t\u0006\u0002\u0002 7\t\u0007\u0002\u0002!7\t\b",
    "\u0002\u0002\"7\t\t\u0002\u0002#7\t\n\u0002\u0002$7\t\u000b\u0002\u0002",
    "%7\t\f\u0002\u0002&7\t\r\u0002\u0002\'7\t\u000e\u0002\u0002(7\t\u000f",
    "\u0002\u0002)7\t\u0010\u0002\u0002*7\t\u0011\u0002\u0002+7\t\u0012\u0002",
    "\u0002,7\t\u0013\u0002\u0002-7\t\u0014\u0002\u0002.7\t\u0015\u0002\u0002",
    "/7\t\u0016\u0002\u000207\t\u0017\u0002\u000217\t\u0018\u0002\u00022",
    "7\t\u0019\u0002\u000237\t\u001a\u0002\u000247\t\u001b\u0002\u000257",
    "\t\u001c\u0002\u00026\u001b\u0003\u0002\u0002\u00026\u001c\u0003\u0002",
    "\u0002\u00026\u001d\u0003\u0002\u0002\u00026\u001e\u0003\u0002\u0002",
    "\u00026\u001f\u0003\u0002\u0002\u00026 \u0003\u0002\u0002\u00026!\u0003",
    "\u0002\u0002\u00026\"\u0003\u0002\u0002\u00026#\u0003\u0002\u0002\u0002",
    "6$\u0003\u0002\u0002\u00026%\u0003\u0002\u0002\u00026&\u0003\u0002\u0002",
    "\u00026\'\u0003\u0002\u0002\u00026(\u0003\u0002\u0002\u00026)\u0003",
    "\u0002\u0002\u00026*\u0003\u0002\u0002\u00026+\u0003\u0002\u0002\u0002",
    "6,\u0003\u0002\u0002\u00026-\u0003\u0002\u0002\u00026.\u0003\u0002\u0002",
    "\u00026/\u0003\u0002\u0002\u000260\u0003\u0002\u0002\u000261\u0003\u0002",
    "\u0002\u000262\u0003\u0002\u0002\u000263\u0003\u0002\u0002\u000264\u0003",
    "\u0002\u0002\u000265\u0003\u0002\u0002\u00027\u0007\u0003\u0002\u0002",
    "\u0002\u0007\u000b\u000e\u0014\u00196"].join("");


var atn = new antlr4.atn.ATNDeserializer().deserialize(serializedATN);

var decisionsToDFA = atn.decisionToState.map( function(ds, index) { return new antlr4.dfa.DFA(ds, index); });

var sharedContextCache = new antlr4.PredictionContextCache();

var literalNames = [ null, "'CONTAINS'", "'contains'", "'NOTCONTAINS'", 
                     "'notcontains'", "'NOTMORETHAN'", "'notmorethan'", 
                     "'EXACTLY'", "'exactly'", "'NOTEXACTLY'", "'notexactly'", 
                     "'MORETHAN'", "'morethan'", "'SAME_COUNT'", "'same_count'", 
                     "'STARTSWITH'", "'startswith'", "'BEFORE'", "'before'", 
                     "'ALWAYS_NEXTTO'", "'always_nextto'", "'NEXTTO'", "'nextto'", 
                     "'AFTER'", "'after'", "'SOME_BEFORE'", "'some_before'", 
                     "'ALL_BEFORE'", "'all_before'", "'SOME_AFTER'", "'some_after'", 
                     "'ALL_AFTER'", "'all_after'", "'ENDSWITH'", "'endswith'", 
                     "'FORWARD'", "'forward'", "'REVERSE'", "'reverse'", 
                     "'SAME_ORIENTATION'", "'same_orientation'", "'ALL_FORWARD'", 
                     "'all_forward'", "'ALL_REVERSE'", "'all_reverse'", 
                     "'ALL_SAME_ORIENTATION'", "'all_same_orientation'", 
                     "'REPRESSES'", "'represses'", "'INDUCES'", "'induces'", 
                     "'DRIVES'", "'drives'", "'BINDS'", "'binds'" ];

var symbolicNames = [ null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, "INT", "REAL", "ID", "WS", "NEWLINE", "SL_COMMENT", 
                      "ML_COMMENT", "STRING" ];

var ruleNames =  [ "specification", "assign", "relOp" ];

function EugeneParser (input) {
	antlr4.Parser.call(this, input);
    this._interp = new antlr4.atn.ParserATNSimulator(this, atn, decisionsToDFA, sharedContextCache);
    this.ruleNames = ruleNames;
    this.literalNames = literalNames;
    this.symbolicNames = symbolicNames;
    return this;
}

EugeneParser.prototype = Object.create(antlr4.Parser.prototype);
EugeneParser.prototype.constructor = EugeneParser;

Object.defineProperty(EugeneParser.prototype, "atn", {
	get : function() {
		return atn;
	}
});

EugeneParser.EOF = antlr4.Token.EOF;
EugeneParser.T__0 = 1;
EugeneParser.T__1 = 2;
EugeneParser.T__2 = 3;
EugeneParser.T__3 = 4;
EugeneParser.T__4 = 5;
EugeneParser.T__5 = 6;
EugeneParser.T__6 = 7;
EugeneParser.T__7 = 8;
EugeneParser.T__8 = 9;
EugeneParser.T__9 = 10;
EugeneParser.T__10 = 11;
EugeneParser.T__11 = 12;
EugeneParser.T__12 = 13;
EugeneParser.T__13 = 14;
EugeneParser.T__14 = 15;
EugeneParser.T__15 = 16;
EugeneParser.T__16 = 17;
EugeneParser.T__17 = 18;
EugeneParser.T__18 = 19;
EugeneParser.T__19 = 20;
EugeneParser.T__20 = 21;
EugeneParser.T__21 = 22;
EugeneParser.T__22 = 23;
EugeneParser.T__23 = 24;
EugeneParser.T__24 = 25;
EugeneParser.T__25 = 26;
EugeneParser.T__26 = 27;
EugeneParser.T__27 = 28;
EugeneParser.T__28 = 29;
EugeneParser.T__29 = 30;
EugeneParser.T__30 = 31;
EugeneParser.T__31 = 32;
EugeneParser.T__32 = 33;
EugeneParser.T__33 = 34;
EugeneParser.T__34 = 35;
EugeneParser.T__35 = 36;
EugeneParser.T__36 = 37;
EugeneParser.T__37 = 38;
EugeneParser.T__38 = 39;
EugeneParser.T__39 = 40;
EugeneParser.T__40 = 41;
EugeneParser.T__41 = 42;
EugeneParser.T__42 = 43;
EugeneParser.T__43 = 44;
EugeneParser.T__44 = 45;
EugeneParser.T__45 = 46;
EugeneParser.T__46 = 47;
EugeneParser.T__47 = 48;
EugeneParser.T__48 = 49;
EugeneParser.T__49 = 50;
EugeneParser.T__50 = 51;
EugeneParser.T__51 = 52;
EugeneParser.T__52 = 53;
EugeneParser.T__53 = 54;
EugeneParser.INT = 55;
EugeneParser.REAL = 56;
EugeneParser.ID = 57;
EugeneParser.WS = 58;
EugeneParser.NEWLINE = 59;
EugeneParser.SL_COMMENT = 60;
EugeneParser.ML_COMMENT = 61;
EugeneParser.STRING = 62;

EugeneParser.RULE_specification = 0;
EugeneParser.RULE_assign = 1;
EugeneParser.RULE_relOp = 2;

function SpecificationContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = EugeneParser.RULE_specification;
    return this;
}

SpecificationContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SpecificationContext.prototype.constructor = SpecificationContext;

SpecificationContext.prototype.assign = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(AssignContext);
    } else {
        return this.getTypedRuleContext(AssignContext,i);
    }
};

SpecificationContext.prototype.enterRule = function(listener) {
    if(listener instanceof EugeneListener ) {
        listener.enterSpecification(this);
	}
};

SpecificationContext.prototype.exitRule = function(listener) {
    if(listener instanceof EugeneListener ) {
        listener.exitSpecification(this);
	}
};




EugeneParser.SpecificationContext = SpecificationContext;

EugeneParser.prototype.specification = function() {

    var localctx = new SpecificationContext(this, this._ctx, this.state);
    this.enterRule(localctx, 0, EugeneParser.RULE_specification);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 7; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 6;
            this.assign();
            this.state = 9; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << EugeneParser.T__0) | (1 << EugeneParser.T__1) | (1 << EugeneParser.T__2) | (1 << EugeneParser.T__3) | (1 << EugeneParser.T__4) | (1 << EugeneParser.T__5) | (1 << EugeneParser.T__6) | (1 << EugeneParser.T__7) | (1 << EugeneParser.T__8) | (1 << EugeneParser.T__9) | (1 << EugeneParser.T__10) | (1 << EugeneParser.T__11) | (1 << EugeneParser.T__12) | (1 << EugeneParser.T__13) | (1 << EugeneParser.T__14) | (1 << EugeneParser.T__15) | (1 << EugeneParser.T__16) | (1 << EugeneParser.T__17) | (1 << EugeneParser.T__18) | (1 << EugeneParser.T__19) | (1 << EugeneParser.T__20) | (1 << EugeneParser.T__21) | (1 << EugeneParser.T__22) | (1 << EugeneParser.T__23) | (1 << EugeneParser.T__24) | (1 << EugeneParser.T__25) | (1 << EugeneParser.T__26) | (1 << EugeneParser.T__27) | (1 << EugeneParser.T__28) | (1 << EugeneParser.T__29) | (1 << EugeneParser.T__30))) !== 0) || ((((_la - 32)) & ~0x1f) == 0 && ((1 << (_la - 32)) & ((1 << (EugeneParser.T__31 - 32)) | (1 << (EugeneParser.T__32 - 32)) | (1 << (EugeneParser.T__33 - 32)) | (1 << (EugeneParser.T__34 - 32)) | (1 << (EugeneParser.T__35 - 32)) | (1 << (EugeneParser.T__36 - 32)) | (1 << (EugeneParser.T__37 - 32)) | (1 << (EugeneParser.T__38 - 32)) | (1 << (EugeneParser.T__39 - 32)) | (1 << (EugeneParser.T__40 - 32)) | (1 << (EugeneParser.T__41 - 32)) | (1 << (EugeneParser.T__42 - 32)) | (1 << (EugeneParser.T__43 - 32)) | (1 << (EugeneParser.T__44 - 32)) | (1 << (EugeneParser.T__45 - 32)) | (1 << (EugeneParser.T__46 - 32)) | (1 << (EugeneParser.T__47 - 32)) | (1 << (EugeneParser.T__48 - 32)) | (1 << (EugeneParser.T__49 - 32)) | (1 << (EugeneParser.T__50 - 32)) | (1 << (EugeneParser.T__51 - 32)) | (1 << (EugeneParser.T__52 - 32)) | (1 << (EugeneParser.T__53 - 32)) | (1 << (EugeneParser.ID - 32)))) !== 0));
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function AssignContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = EugeneParser.RULE_assign;
    return this;
}

AssignContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
AssignContext.prototype.constructor = AssignContext;

AssignContext.prototype.relOp = function() {
    return this.getTypedRuleContext(RelOpContext,0);
};

AssignContext.prototype.ID = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(EugeneParser.ID);
    } else {
        return this.getToken(EugeneParser.ID, i);
    }
};


AssignContext.prototype.INT = function() {
    return this.getToken(EugeneParser.INT, 0);
};

AssignContext.prototype.enterRule = function(listener) {
    if(listener instanceof EugeneListener ) {
        listener.enterAssign(this);
	}
};

AssignContext.prototype.exitRule = function(listener) {
    if(listener instanceof EugeneListener ) {
        listener.exitAssign(this);
	}
};




EugeneParser.AssignContext = AssignContext;

EugeneParser.prototype.assign = function() {

    var localctx = new AssignContext(this, this._ctx, this.state);
    this.enterRule(localctx, 2, EugeneParser.RULE_assign);
    var _la = 0; // Token type
    try {
        this.state = 23;
        this._errHandler.sync(this);
        var la_ = this._interp.adaptivePredict(this._input,3,this._ctx);
        switch(la_) {
        case 1:
            this.enterOuterAlt(localctx, 1);
            this.state = 12;
            _la = this._input.LA(1);
            if(_la===EugeneParser.ID) {
                this.state = 11;
                this.match(EugeneParser.ID);
            }

            this.state = 14;
            this.relOp();
            this.state = 15;
            this.match(EugeneParser.ID);
            break;

        case 2:
            this.enterOuterAlt(localctx, 2);
            this.state = 18;
            _la = this._input.LA(1);
            if(_la===EugeneParser.ID) {
                this.state = 17;
                this.match(EugeneParser.ID);
            }

            this.state = 20;
            this.relOp();
            this.state = 21;
            this.match(EugeneParser.INT);
            break;

        }
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};

function RelOpContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = EugeneParser.RULE_relOp;
    return this;
}

RelOpContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
RelOpContext.prototype.constructor = RelOpContext;


RelOpContext.prototype.enterRule = function(listener) {
    if(listener instanceof EugeneListener ) {
        listener.enterRelOp(this);
	}
};

RelOpContext.prototype.exitRule = function(listener) {
    if(listener instanceof EugeneListener ) {
        listener.exitRelOp(this);
	}
};




EugeneParser.RelOpContext = RelOpContext;

EugeneParser.prototype.relOp = function() {

    var localctx = new RelOpContext(this, this._ctx, this.state);
    this.enterRule(localctx, 4, EugeneParser.RULE_relOp);
    var _la = 0; // Token type
    try {
        this.state = 52;
        switch(this._input.LA(1)) {
        case EugeneParser.T__0:
        case EugeneParser.T__1:
            this.enterOuterAlt(localctx, 1);
            this.state = 25;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__0 || _la===EugeneParser.T__1)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__2:
        case EugeneParser.T__3:
            this.enterOuterAlt(localctx, 2);
            this.state = 26;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__2 || _la===EugeneParser.T__3)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__4:
        case EugeneParser.T__5:
            this.enterOuterAlt(localctx, 3);
            this.state = 27;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__4 || _la===EugeneParser.T__5)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__6:
        case EugeneParser.T__7:
            this.enterOuterAlt(localctx, 4);
            this.state = 28;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__6 || _la===EugeneParser.T__7)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__8:
        case EugeneParser.T__9:
            this.enterOuterAlt(localctx, 5);
            this.state = 29;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__8 || _la===EugeneParser.T__9)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__10:
        case EugeneParser.T__11:
            this.enterOuterAlt(localctx, 6);
            this.state = 30;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__10 || _la===EugeneParser.T__11)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__12:
        case EugeneParser.T__13:
            this.enterOuterAlt(localctx, 7);
            this.state = 31;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__12 || _la===EugeneParser.T__13)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__14:
        case EugeneParser.T__15:
            this.enterOuterAlt(localctx, 8);
            this.state = 32;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__14 || _la===EugeneParser.T__15)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__16:
        case EugeneParser.T__17:
            this.enterOuterAlt(localctx, 9);
            this.state = 33;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__16 || _la===EugeneParser.T__17)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__18:
        case EugeneParser.T__19:
            this.enterOuterAlt(localctx, 10);
            this.state = 34;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__18 || _la===EugeneParser.T__19)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__20:
        case EugeneParser.T__21:
            this.enterOuterAlt(localctx, 11);
            this.state = 35;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__20 || _la===EugeneParser.T__21)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__22:
        case EugeneParser.T__23:
            this.enterOuterAlt(localctx, 12);
            this.state = 36;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__22 || _la===EugeneParser.T__23)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__24:
        case EugeneParser.T__25:
            this.enterOuterAlt(localctx, 13);
            this.state = 37;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__24 || _la===EugeneParser.T__25)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__26:
        case EugeneParser.T__27:
            this.enterOuterAlt(localctx, 14);
            this.state = 38;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__26 || _la===EugeneParser.T__27)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__28:
        case EugeneParser.T__29:
            this.enterOuterAlt(localctx, 15);
            this.state = 39;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__28 || _la===EugeneParser.T__29)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__30:
        case EugeneParser.T__31:
            this.enterOuterAlt(localctx, 16);
            this.state = 40;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__30 || _la===EugeneParser.T__31)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__32:
        case EugeneParser.T__33:
            this.enterOuterAlt(localctx, 17);
            this.state = 41;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__32 || _la===EugeneParser.T__33)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__34:
        case EugeneParser.T__35:
            this.enterOuterAlt(localctx, 18);
            this.state = 42;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__34 || _la===EugeneParser.T__35)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__36:
        case EugeneParser.T__37:
            this.enterOuterAlt(localctx, 19);
            this.state = 43;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__36 || _la===EugeneParser.T__37)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__38:
        case EugeneParser.T__39:
            this.enterOuterAlt(localctx, 20);
            this.state = 44;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__38 || _la===EugeneParser.T__39)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__40:
        case EugeneParser.T__41:
            this.enterOuterAlt(localctx, 21);
            this.state = 45;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__40 || _la===EugeneParser.T__41)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__42:
        case EugeneParser.T__43:
            this.enterOuterAlt(localctx, 22);
            this.state = 46;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__42 || _la===EugeneParser.T__43)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__44:
        case EugeneParser.T__45:
            this.enterOuterAlt(localctx, 23);
            this.state = 47;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__44 || _la===EugeneParser.T__45)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__46:
        case EugeneParser.T__47:
            this.enterOuterAlt(localctx, 24);
            this.state = 48;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__46 || _la===EugeneParser.T__47)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__48:
        case EugeneParser.T__49:
            this.enterOuterAlt(localctx, 25);
            this.state = 49;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__48 || _la===EugeneParser.T__49)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__50:
        case EugeneParser.T__51:
            this.enterOuterAlt(localctx, 26);
            this.state = 50;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__50 || _la===EugeneParser.T__51)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        case EugeneParser.T__52:
        case EugeneParser.T__53:
            this.enterOuterAlt(localctx, 27);
            this.state = 51;
            _la = this._input.LA(1);
            if(!(_la===EugeneParser.T__52 || _la===EugeneParser.T__53)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


exports.EugeneParser = EugeneParser;
