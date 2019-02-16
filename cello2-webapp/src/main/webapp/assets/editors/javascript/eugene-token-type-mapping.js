module.exports = {
  literals: {
    'keyword.control': ['CONTAINS', 'contains', 'EXACTLY', 'exactly',
                        'NOTEXACTLY', 'notexactly', 'MORETHAN', 'morethan',
                        'LESSTHAN', 'lessthan', 'NOTMORETHAN', 'notmorethan',
                        'NOTLESSTHAN', 'notlessthan', 'SAME_COUNT', 'same_count',
                        'STARTSWITH', 'startswith', 'BEFORE', 'before',
                        'ALWAYS_NEXTTO', 'always_nextto', 'NEXTTO', 'nextto', 
                        'AFTER', 'after', 'SOME_BEFORE', 'some_before', 
                        'ALL_BEFORE', 'all_before',  'SOME_AFTER', 'some_after', 
                        'ALL_AFTER', 'all_after', 'ENDSWITH', 'endswith', 
                        'FORWARD', 'forward', 'REVERSE', 'reverse', 
                        'SAME_ORIENTATION', 'same_orientation', 'ALL_FORWARD', 'all_forward', 
                        'ALL_REVERSE', 'all_reverse', 'ALL_SAME_ORIENTATION', 'all_same_orientation', 
                        'REPRESSES', 'represses', 'INDUCES', 'induces', 
                        'DRIVES', 'drives', 'BINDS', 'binds'],
    'constant.language': ['NOT','not','OR','or']
  },
  symbols: {
    'identifier' : 'ID',
    'constant.numeric' : 'INT' | 'REAL',
    'comment.line' : 'SL_COMMENT',
    'comment.block': 'ML_COMMENT'
  }
};