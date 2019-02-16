importScripts("./bower_components/ace-worker/worker.js");
importScripts("./bower_components/ace-builds/src-noconflict/ace.js");
importScripts("./bower_components/ace-worker/mirror.js");

ace.define('ace/worker/stl-worker',["require","exports","module","ace/lib/oop","ace/worker/mirror"], function(require, exports, module) {
  "use strict";

  var oop = require("ace/lib/oop");
  var Mirror = require("ace/worker/mirror").Mirror;

  var stlWorker = function(sender) {
    Mirror.call(this, sender);
    this.setTimeout(200);
    this.$dialect = null;
  };

  oop.inherits(stlWorker, Mirror);

  // load nodejs compatible require
  var ace_require = require;
  window.require = undefined; // prevent error: "Honey: 'require' already defined in global scope"
  var Honey = { 'requirePath': ['..'] }; // walk up to js folder, see Honey docs
  importScripts("./javascript/require.js");
  var antlr4_require = window.require;
  window.require = require = ace_require;

  // load antlr4 and stlLanguage
  var antlr4, STLLexer, STLParser;
  try {
    window.require = antlr4_require;
    antlr4 = antlr4_require('antlr4/index');
    STLLexer = antlr4_require('./parser/STLLexer').STLLexer;
    STLParser = antlr4_require('./parser/STLParser').STLParser;
    // STLListener = antlr4_require('./parser/STLListener').STLListener;
  } finally {
    window.require = ace_require;
  }

  // class for gathering errors and posting them to ACE editor
  var AnnotatingErrorListener = function(annotations) {
    antlr4.error.ErrorListener.call(this);
    this.annotations = annotations;
    return this;
  };

  AnnotatingErrorListener.prototype = Object.create(antlr4.error.ErrorListener.prototype);
  AnnotatingErrorListener.prototype.constructor = AnnotatingErrorListener;

  AnnotatingErrorListener.prototype.syntaxError = function(recognizer, offendingSymbol, line, column, msg, e) {
    this.annotations.push({
      row: line - 1,
      column: column,
      text: msg,
      type: "error"
    });
  };

  function validate(input) {
    var stream = new antlr4.InputStream(input);
    var lexer = new STLLexer(stream);
    var tokens = new antlr4.CommonTokenStream(lexer);
    var parser = new STLParser(tokens);
    var annotations = [];
    var listener = new AnnotatingErrorListener(annotations);
    parser.removeErrorListeners();
    parser.addErrorListener(listener);
    parser.property();
    return annotations;
  }

  (function() {

    this.onUpdate = function() {
      var value = this.doc.getValue();
      var annotations = validate(value);
      this.sender.emit("annotate", annotations);
    };

  }).call(stlWorker.prototype);

  exports.stlWorker = stlWorker;
});