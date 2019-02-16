module.exports = {
  literals: {
    'keyword.control': ['G', 'F', 'U'],
    'keyword.operator': [ '=>', '_', '&&', '||', '<<', '>>', '#', '=',
                          '!', , '^', '*', '/', '+', '-', '<=', '<',
                          '>=', '>', '@', 'E', 'E-'],
    'paren.lparen': ['-(', '(', '{', '['],
    'paren.rparen': [')', '}', ']'],
    'punctuation.operator': [',', ';', '.', ':'],
    // 'storage.type': ['float', 'int', 'void','const','let'],
    'constant.language': ['true', 'false'],
    'support.function': [ 'sqrt(', 'log(', 'ln(', 'abs(', 'der(', 'int(',
                          'max', 'min'],
  },
  symbols: {
    'constant.language': 'BOOLEAN',
    'identifier' : 'VARIABLE',
    'constant.numeric' : 'RATIONAL',
    'comment.line' : 'SL_COMMENT',
  }
};