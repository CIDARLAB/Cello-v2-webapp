// Generated from STL.g4 by ANTLR 4.5.3
// jshint ignore: start
var antlr4 = require('antlr4/index');
var STLListener = require('./STLListener').STLListener;
var grammarFileName = "STL.g4";

var serializedATN = ["\u0003\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd",
    "\u0003/\u00f8\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t",
    "\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004",
    "\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0003\u0002\u0003",
    "\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0006\u0002\u001d",
    "\n\u0002\r\u0002\u000e\u0002\u001e\u0003\u0002\u0003\u0002\u0003\u0002",
    "\u0003\u0002\u0006\u0002%\n\u0002\r\u0002\u000e\u0002&\u0003\u0002\u0003",
    "\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0003\u0007\u00032\n\u0003\f\u0003\u000e\u00035\u000b\u0003",
    "\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0003\u0003\u0003\u0003\u0007\u0003W\n\u0003\f\u0003\u000e\u0003Z\u000b",
    "\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0005\u0005v\n\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003",
    "\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005\u0095\n\u0005",
    "\f\u0005\u000e\u0005\u0098\u000b\u0005\u0003\u0006\u0003\u0006\u0003",
    "\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003",
    "\u0006\u0003\u0006\u0003\u0006\u0005\u0006\u00a5\n\u0006\u0003\u0006",
    "\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006",
    "\u0003\u0006\u0003\u0006\u0007\u0006\u00b0\n\u0006\f\u0006\u000e\u0006",
    "\u00b3\u000b\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0003",
    "\u0007\u0005\u0007\u00ba\n\u0007\u0003\b\u0003\b\u0003\b\u0003\b\u0003",
    "\b\u0007\b\u00c1\n\b\f\b\u000e\b\u00c4\u000b\b\u0003\b\u0003\b\u0003",
    "\t\u0003\t\u0003\t\u0005\t\u00cb\n\t\u0003\t\u0003\t\u0003\t\u0003\n",
    "\u0003\n\u0003\n\u0003\n\u0003\n\u0007\n\u00d5\n\n\f\n\u000e\n\u00d8",
    "\u000b\n\u0003\n\u0003\n\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b",
    "\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b",
    "\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b",
    "\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b",
    "\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0005\u000b\u00f6\n",
    "\u000b\u0003\u000b\u0002\u0005\u0004\b\n\f\u0002\u0004\u0006\b\n\f\u000e",
    "\u0010\u0012\u0014\u0002\u0007\u0004\u0002\n\n\u0015\u0015\u0003\u0002",
    "\u0016\u001b\u0003\u0002\u001c\u001d\u0003\u0002\u001e\u001f\u0004\u0002",
    "\r\r #\u010d\u0002\u0016\u0003\u0002\u0002\u0002\u0004+\u0003\u0002",
    "\u0002\u0002\u0006[\u0003\u0002\u0002\u0002\bu\u0003\u0002\u0002\u0002",
    "\n\u00a4\u0003\u0002\u0002\u0002\f\u00b9\u0003\u0002\u0002\u0002\u000e",
    "\u00bb\u0003\u0002\u0002\u0002\u0010\u00c7\u0003\u0002\u0002\u0002\u0012",
    "\u00cf\u0003\u0002\u0002\u0002\u0014\u00f5\u0003\u0002\u0002\u0002\u0016",
    "\u0017\u0005\u0004\u0003\u0002\u0017\u0018\u0007.\u0002\u0002\u0018",
    "\u001c\u0007.\u0002\u0002\u0019\u001a\u0005\u0006\u0004\u0002\u001a",
    "\u001b\u0007.\u0002\u0002\u001b\u001d\u0003\u0002\u0002\u0002\u001c",
    "\u0019\u0003\u0002\u0002\u0002\u001d\u001e\u0003\u0002\u0002\u0002\u001e",
    "\u001c\u0003\u0002\u0002\u0002\u001e\u001f\u0003\u0002\u0002\u0002\u001f",
    " \u0003\u0002\u0002\u0002 $\u0007.\u0002\u0002!\"\u0005\u000e\b\u0002",
    "\"#\u0007.\u0002\u0002#%\u0003\u0002\u0002\u0002$!\u0003\u0002\u0002",
    "\u0002%&\u0003\u0002\u0002\u0002&$\u0003\u0002\u0002\u0002&\'\u0003",
    "\u0002\u0002\u0002\'(\u0003\u0002\u0002\u0002()\u0005\u0012\n\u0002",
    ")*\u0007.\u0002\u0002*\u0003\u0003\u0002\u0002\u0002+,\b\u0003\u0001",
    "\u0002,-\u0007+\u0002\u0002-.\u0007\n\u0002\u0002.3\u0007+\u0002\u0002",
    "/0\u0007\u000b\u0002\u000202\u0007+\u0002\u00021/\u0003\u0002\u0002",
    "\u000225\u0003\u0002\u0002\u000231\u0003\u0002\u0002\u000234\u0003\u0002",
    "\u0002\u000246\u0003\u0002\u0002\u000253\u0003\u0002\u0002\u000267\u0007",
    "\f\u0002\u00027X\u0003\u0002\u0002\u000289\f\t\u0002\u00029:\u0007\u0003",
    "\u0002\u0002:;\u0007\u0004\u0002\u0002;<\u0007+\u0002\u0002<W\u0005",
    "\u0004\u0003\n=>\f\b\u0002\u0002>?\u0007\u0005\u0002\u0002?@\u0007\u0004",
    "\u0002\u0002@A\u0007+\u0002\u0002AW\u0005\u0004\u0003\tBC\f\u0007\u0002",
    "\u0002CD\u0007\u0006\u0002\u0002DE\u0007\u0004\u0002\u0002EF\u0007+",
    "\u0002\u0002FW\u0005\u0004\u0003\bGH\f\u0006\u0002\u0002HI\u0007\u0007",
    "\u0002\u0002IJ\u0007\u0004\u0002\u0002JK\u0007+\u0002\u0002KW\u0005",
    "\u0004\u0003\u0007LM\f\u0005\u0002\u0002MN\u0007\b\u0002\u0002NO\u0007",
    "\u0004\u0002\u0002OP\u0007+\u0002\u0002PW\u0005\u0004\u0003\u0006QR",
    "\f\u0004\u0002\u0002RS\u0007\t\u0002\u0002ST\u0007\u0004\u0002\u0002",
    "TU\u0007+\u0002\u0002UW\u0005\u0004\u0003\u0005V8\u0003\u0002\u0002",
    "\u0002V=\u0003\u0002\u0002\u0002VB\u0003\u0002\u0002\u0002VG\u0003\u0002",
    "\u0002\u0002VL\u0003\u0002\u0002\u0002VQ\u0003\u0002\u0002\u0002WZ\u0003",
    "\u0002\u0002\u0002XV\u0003\u0002\u0002\u0002XY\u0003\u0002\u0002\u0002",
    "Y\u0005\u0003\u0002\u0002\u0002ZX\u0003\u0002\u0002\u0002[\\\u0007+",
    "\u0002\u0002\\]\u0007\r\u0002\u0002]^\u0005\b\u0005\u0002^\u0007\u0003",
    "\u0002\u0002\u0002_`\b\u0005\u0001\u0002`a\u0007\n\u0002\u0002ab\u0005",
    "\b\u0005\u0002bc\u0007\f\u0002\u0002cv\u0003\u0002\u0002\u0002dv\u0005",
    "\f\u0007\u0002ef\u0007\u000e\u0002\u0002fv\u0005\b\u0005\rgh\u0007\u000f",
    "\u0002\u0002hi\u0007\u0010\u0002\u0002ij\u0007,\u0002\u0002jk\u0007",
    "\u000b\u0002\u0002kl\u0007,\u0002\u0002lm\u0007\u0011\u0002\u0002mv",
    "\u0005\b\u0005\fno\u0007\u0012\u0002\u0002op\u0007\u0010\u0002\u0002",
    "pq\u0007,\u0002\u0002qr\u0007\u000b\u0002\u0002rs\u0007,\u0002\u0002",
    "st\u0007\u0011\u0002\u0002tv\u0005\b\u0005\u000bu_\u0003\u0002\u0002",
    "\u0002ud\u0003\u0002\u0002\u0002ue\u0003\u0002\u0002\u0002ug\u0003\u0002",
    "\u0002\u0002un\u0003\u0002\u0002\u0002v\u0096\u0003\u0002\u0002\u0002",
    "wx\f\n\u0002\u0002xy\u0007\u0003\u0002\u0002y\u0095\u0005\b\u0005\u000b",
    "z{\f\t\u0002\u0002{|\u0007\u0005\u0002\u0002|\u0095\u0005\b\u0005\n",
    "}~\f\b\u0002\u0002~\u007f\u0007\u0006\u0002\u0002\u007f\u0095\u0005",
    "\b\u0005\t\u0080\u0081\f\u0007\u0002\u0002\u0081\u0082\u0007\u0007\u0002",
    "\u0002\u0082\u0095\u0005\b\u0005\b\u0083\u0084\f\u0006\u0002\u0002\u0084",
    "\u0085\u0007\b\u0002\u0002\u0085\u0095\u0005\b\u0005\u0007\u0086\u0087",
    "\f\u0005\u0002\u0002\u0087\u0088\u0007\t\u0002\u0002\u0088\u0095\u0005",
    "\b\u0005\u0006\u0089\u008a\f\u0004\u0002\u0002\u008a\u008b\u0007\u0013",
    "\u0002\u0002\u008b\u008c\u0007\u0010\u0002\u0002\u008c\u008d\u0007,",
    "\u0002\u0002\u008d\u008e\u0007\u000b\u0002\u0002\u008e\u008f\u0007,",
    "\u0002\u0002\u008f\u0090\u0007\u0011\u0002\u0002\u0090\u0095\u0005\b",
    "\u0005\u0005\u0091\u0092\f\u0003\u0002\u0002\u0092\u0093\u0007\u0014",
    "\u0002\u0002\u0093\u0095\u0005\b\u0005\u0004\u0094w\u0003\u0002\u0002",
    "\u0002\u0094z\u0003\u0002\u0002\u0002\u0094}\u0003\u0002\u0002\u0002",
    "\u0094\u0080\u0003\u0002\u0002\u0002\u0094\u0083\u0003\u0002\u0002\u0002",
    "\u0094\u0086\u0003\u0002\u0002\u0002\u0094\u0089\u0003\u0002\u0002\u0002",
    "\u0094\u0091\u0003\u0002\u0002\u0002\u0095\u0098\u0003\u0002\u0002\u0002",
    "\u0096\u0094\u0003\u0002\u0002\u0002\u0096\u0097\u0003\u0002\u0002\u0002",
    "\u0097\t\u0003\u0002\u0002\u0002\u0098\u0096\u0003\u0002\u0002\u0002",
    "\u0099\u009a\b\u0006\u0001\u0002\u009a\u009b\t\u0002\u0002\u0002\u009b",
    "\u009c\u0005\n\u0006\u0002\u009c\u009d\u0007\f\u0002\u0002\u009d\u00a5",
    "\u0003\u0002\u0002\u0002\u009e\u009f\t\u0003\u0002\u0002\u009f\u00a0",
    "\u0005\n\u0006\u0002\u00a0\u00a1\u0007\f\u0002\u0002\u00a1\u00a5\u0003",
    "\u0002\u0002\u0002\u00a2\u00a5\u0007,\u0002\u0002\u00a3\u00a5\u0007",
    "+\u0002\u0002\u00a4\u0099\u0003\u0002\u0002\u0002\u00a4\u009e\u0003",
    "\u0002\u0002\u0002\u00a4\u00a2\u0003\u0002\u0002\u0002\u00a4\u00a3\u0003",
    "\u0002\u0002\u0002\u00a5\u00b1\u0003\u0002\u0002\u0002\u00a6\u00a7\f",
    "\b\u0002\u0002\u00a7\u00a8\u0007\u0014\u0002\u0002\u00a8\u00b0\u0005",
    "\n\u0006\t\u00a9\u00aa\f\u0006\u0002\u0002\u00aa\u00ab\t\u0004\u0002",
    "\u0002\u00ab\u00b0\u0005\n\u0006\u0007\u00ac\u00ad\f\u0005\u0002\u0002",
    "\u00ad\u00ae\t\u0005\u0002\u0002\u00ae\u00b0\u0005\n\u0006\u0006\u00af",
    "\u00a6\u0003\u0002\u0002\u0002\u00af\u00a9\u0003\u0002\u0002\u0002\u00af",
    "\u00ac\u0003\u0002\u0002\u0002\u00b0\u00b3\u0003\u0002\u0002\u0002\u00b1",
    "\u00af\u0003\u0002\u0002\u0002\u00b1\u00b2\u0003\u0002\u0002\u0002\u00b2",
    "\u000b\u0003\u0002\u0002\u0002\u00b3\u00b1\u0003\u0002\u0002\u0002\u00b4",
    "\u00b5\u0005\n\u0006\u0002\u00b5\u00b6\t\u0006\u0002\u0002\u00b6\u00b7",
    "\u0005\n\u0006\u0002\u00b7\u00ba\u0003\u0002\u0002\u0002\u00b8\u00ba",
    "\u0007*\u0002\u0002\u00b9\u00b4\u0003\u0002\u0002\u0002\u00b9\u00b8",
    "\u0003\u0002\u0002\u0002\u00ba\r\u0003\u0002\u0002\u0002\u00bb\u00bc",
    "\u0007+\u0002\u0002\u00bc\u00bd\u0007$\u0002\u0002\u00bd\u00c2\u0005",
    "\u0010\t\u0002\u00be\u00bf\u0007\u000b\u0002\u0002\u00bf\u00c1\u0005",
    "\u0010\t\u0002\u00c0\u00be\u0003\u0002\u0002\u0002\u00c1\u00c4\u0003",
    "\u0002\u0002\u0002\u00c2\u00c0\u0003\u0002\u0002\u0002\u00c2\u00c3\u0003",
    "\u0002\u0002\u0002\u00c3\u00c5\u0003\u0002\u0002\u0002\u00c4\u00c2\u0003",
    "\u0002\u0002\u0002\u00c5\u00c6\u0007%\u0002\u0002\u00c6\u000f\u0003",
    "\u0002\u0002\u0002\u00c7\u00ca\u0007+\u0002\u0002\u00c8\u00c9\u0007",
    "&\u0002\u0002\u00c9\u00cb\u0007+\u0002\u0002\u00ca\u00c8\u0003\u0002",
    "\u0002\u0002\u00ca\u00cb\u0003\u0002\u0002\u0002\u00cb\u00cc\u0003\u0002",
    "\u0002\u0002\u00cc\u00cd\u0007\'\u0002\u0002\u00cd\u00ce\u0007+\u0002",
    "\u0002\u00ce\u0011\u0003\u0002\u0002\u0002\u00cf\u00d0\u0007+\u0002",
    "\u0002\u00d0\u00d1\u0007\u0010\u0002\u0002\u00d1\u00d6\u0005\u0014\u000b",
    "\u0002\u00d2\u00d3\u0007\u000b\u0002\u0002\u00d3\u00d5\u0005\u0014\u000b",
    "\u0002\u00d4\u00d2\u0003\u0002\u0002\u0002\u00d5\u00d8\u0003\u0002\u0002",
    "\u0002\u00d6\u00d4\u0003\u0002\u0002\u0002\u00d6\u00d7\u0003\u0002\u0002",
    "\u0002\u00d7\u00d9\u0003\u0002\u0002\u0002\u00d8\u00d6\u0003\u0002\u0002",
    "\u0002\u00d9\u00da\u0007\u0011\u0002\u0002\u00da\u0013\u0003\u0002\u0002",
    "\u0002\u00db\u00dc\u0007$\u0002\u0002\u00dc\u00dd\u0007+\u0002\u0002",
    "\u00dd\u00de\u0007\'\u0002\u0002\u00de\u00df\u0007$\u0002\u0002\u00df",
    "\u00e0\u0007(\u0002\u0002\u00e0\u00e1\u0007\'\u0002\u0002\u00e1\u00e2",
    "\u0007,\u0002\u0002\u00e2\u00e3\u0007\u000b\u0002\u0002\u00e3\u00e4",
    "\u0007)\u0002\u0002\u00e4\u00e5\u0007\'\u0002\u0002\u00e5\u00e6\u0007",
    ",\u0002\u0002\u00e6\u00e7\u0007%\u0002\u0002\u00e7\u00f6\u0007%\u0002",
    "\u0002\u00e8\u00e9\u0007$\u0002\u0002\u00e9\u00ea\u0007+\u0002\u0002",
    "\u00ea\u00eb\u0007\'\u0002\u0002\u00eb\u00ec\u0007$\u0002\u0002\u00ec",
    "\u00ed\u0007)\u0002\u0002\u00ed\u00ee\u0007\'\u0002\u0002\u00ee\u00ef",
    "\u0007,\u0002\u0002\u00ef\u00f0\u0007\u000b\u0002\u0002\u00f0\u00f1",
    "\u0007(\u0002\u0002\u00f1\u00f2\u0007\'\u0002\u0002\u00f2\u00f3\u0007",
    ",\u0002\u0002\u00f3\u00f4\u0007%\u0002\u0002\u00f4\u00f6\u0007%\u0002",
    "\u0002\u00f5\u00db\u0003\u0002\u0002\u0002\u00f5\u00e8\u0003\u0002\u0002",
    "\u0002\u00f6\u0015\u0003\u0002\u0002\u0002\u0012\u001e&3VXu\u0094\u0096",
    "\u00a4\u00af\u00b1\u00b9\u00c2\u00ca\u00d6\u00f5"].join("");


var atn = new antlr4.atn.ATNDeserializer().deserialize(serializedATN);

var decisionsToDFA = atn.decisionToState.map( function(ds, index) { return new antlr4.dfa.DFA(ds, index); });

var sharedContextCache = new antlr4.PredictionContextCache();

var literalNames = [ null, "'=>'", "'_'", "'&&'", "'||'", "'<<'", "'>>'", 
                     "'#'", "'('", "','", "')'", "'='", "'!'", "'F'", "'['", 
                     "']'", "'G'", "'U'", "'^'", "'-('", "'sqrt('", "'log('", 
                     "'ln('", "'abs('", "'der('", "'int('", "'*'", "'/'", 
                     "'+'", "'-'", "'<'", "'<='", "'>='", "'>'", "'{'", 
                     "'}'", "'@'", "':'", "'max'", "'min'" ];

var symbolicNames = [ null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, "BOOLEAN", "VARIABLE", "RATIONAL", 
                      "WS", "NEWLINE", "SL_COMMENT" ];

var ruleNames =  [ "specification", "module", "moduleDescription", "property", 
                   "expr", "booleanExpr", "translationMap", "translationPair", 
                   "limitMap", "limitPair" ];

function STLParser (input) {
	antlr4.Parser.call(this, input);
    this._interp = new antlr4.atn.ParserATNSimulator(this, atn, decisionsToDFA, sharedContextCache);
    this.ruleNames = ruleNames;
    this.literalNames = literalNames;
    this.symbolicNames = symbolicNames;
    return this;
}

STLParser.prototype = Object.create(antlr4.Parser.prototype);
STLParser.prototype.constructor = STLParser;

Object.defineProperty(STLParser.prototype, "atn", {
	get : function() {
		return atn;
	}
});

STLParser.EOF = antlr4.Token.EOF;
STLParser.T__0 = 1;
STLParser.T__1 = 2;
STLParser.T__2 = 3;
STLParser.T__3 = 4;
STLParser.T__4 = 5;
STLParser.T__5 = 6;
STLParser.T__6 = 7;
STLParser.T__7 = 8;
STLParser.T__8 = 9;
STLParser.T__9 = 10;
STLParser.T__10 = 11;
STLParser.T__11 = 12;
STLParser.T__12 = 13;
STLParser.T__13 = 14;
STLParser.T__14 = 15;
STLParser.T__15 = 16;
STLParser.T__16 = 17;
STLParser.T__17 = 18;
STLParser.T__18 = 19;
STLParser.T__19 = 20;
STLParser.T__20 = 21;
STLParser.T__21 = 22;
STLParser.T__22 = 23;
STLParser.T__23 = 24;
STLParser.T__24 = 25;
STLParser.T__25 = 26;
STLParser.T__26 = 27;
STLParser.T__27 = 28;
STLParser.T__28 = 29;
STLParser.T__29 = 30;
STLParser.T__30 = 31;
STLParser.T__31 = 32;
STLParser.T__32 = 33;
STLParser.T__33 = 34;
STLParser.T__34 = 35;
STLParser.T__35 = 36;
STLParser.T__36 = 37;
STLParser.T__37 = 38;
STLParser.T__38 = 39;
STLParser.BOOLEAN = 40;
STLParser.VARIABLE = 41;
STLParser.RATIONAL = 42;
STLParser.WS = 43;
STLParser.NEWLINE = 44;
STLParser.SL_COMMENT = 45;

STLParser.RULE_specification = 0;
STLParser.RULE_module = 1;
STLParser.RULE_moduleDescription = 2;
STLParser.RULE_property = 3;
STLParser.RULE_expr = 4;
STLParser.RULE_booleanExpr = 5;
STLParser.RULE_translationMap = 6;
STLParser.RULE_translationPair = 7;
STLParser.RULE_limitMap = 8;
STLParser.RULE_limitPair = 9;

function SpecificationContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_specification;
    this.spec = null; // ModuleContext
    return this;
}

SpecificationContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
SpecificationContext.prototype.constructor = SpecificationContext;

SpecificationContext.prototype.NEWLINE = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(STLParser.NEWLINE);
    } else {
        return this.getToken(STLParser.NEWLINE, i);
    }
};


SpecificationContext.prototype.module = function() {
    return this.getTypedRuleContext(ModuleContext,0);
};

SpecificationContext.prototype.limitMap = function() {
    return this.getTypedRuleContext(LimitMapContext,0);
};

SpecificationContext.prototype.moduleDescription = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ModuleDescriptionContext);
    } else {
        return this.getTypedRuleContext(ModuleDescriptionContext,i);
    }
};

SpecificationContext.prototype.translationMap = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(TranslationMapContext);
    } else {
        return this.getTypedRuleContext(TranslationMapContext,i);
    }
};

SpecificationContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterSpecification(this);
	}
};

SpecificationContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitSpecification(this);
	}
};




STLParser.SpecificationContext = SpecificationContext;

STLParser.prototype.specification = function() {

    var localctx = new SpecificationContext(this, this._ctx, this.state);
    this.enterRule(localctx, 0, STLParser.RULE_specification);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 20;
        localctx.spec = this.module(0);
        this.state = 21;
        this.match(STLParser.NEWLINE);
        this.state = 22;
        this.match(STLParser.NEWLINE);
        this.state = 26; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 23;
            this.moduleDescription();
            this.state = 24;
            this.match(STLParser.NEWLINE);
            this.state = 28; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while(_la===STLParser.VARIABLE);
        this.state = 30;
        this.match(STLParser.NEWLINE);
        this.state = 34; 
        this._errHandler.sync(this);
        var _alt = 1;
        do {
        	switch (_alt) {
        	case 1:
        		this.state = 31;
        		this.translationMap();
        		this.state = 32;
        		this.match(STLParser.NEWLINE);
        		break;
        	default:
        		throw new antlr4.error.NoViableAltException(this);
        	}
        	this.state = 36; 
        	this._errHandler.sync(this);
        	_alt = this._interp.adaptivePredict(this._input,1, this._ctx);
        } while ( _alt!=2 && _alt!=antlr4.atn.ATN.INVALID_ALT_NUMBER );

        this.state = 38;
        this.limitMap();
        this.state = 39;
        this.match(STLParser.NEWLINE);
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

function ModuleContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_module;
    return this;
}

ModuleContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ModuleContext.prototype.constructor = ModuleContext;


 
ModuleContext.prototype.copyFrom = function(ctx) {
    antlr4.ParserRuleContext.prototype.copyFrom.call(this, ctx);
};

function ModuleLeafContext(parser, ctx) {
	ModuleContext.call(this, parser);
    this.moduleName = null; // Token;
    ModuleContext.prototype.copyFrom.call(this, ctx);
    return this;
}

ModuleLeafContext.prototype = Object.create(ModuleContext.prototype);
ModuleLeafContext.prototype.constructor = ModuleLeafContext;

STLParser.ModuleLeafContext = ModuleLeafContext;

ModuleLeafContext.prototype.VARIABLE = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(STLParser.VARIABLE);
    } else {
        return this.getToken(STLParser.VARIABLE, i);
    }
};

ModuleLeafContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterModuleLeaf(this);
	}
};

ModuleLeafContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitModuleLeaf(this);
	}
};


function ModuleOpContext(parser, ctx) {
	ModuleContext.call(this, parser);
    this.left = null; // ModuleContext;
    this.op = null; // Token;
    this.tmap = null; // Token;
    this.right = null; // ModuleContext;
    ModuleContext.prototype.copyFrom.call(this, ctx);
    return this;
}

ModuleOpContext.prototype = Object.create(ModuleContext.prototype);
ModuleOpContext.prototype.constructor = ModuleOpContext;

STLParser.ModuleOpContext = ModuleOpContext;

ModuleOpContext.prototype.module = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ModuleContext);
    } else {
        return this.getTypedRuleContext(ModuleContext,i);
    }
};

ModuleOpContext.prototype.VARIABLE = function() {
    return this.getToken(STLParser.VARIABLE, 0);
};
ModuleOpContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterModuleOp(this);
	}
};

ModuleOpContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitModuleOp(this);
	}
};



STLParser.prototype.module = function(_p) {
	if(_p===undefined) {
	    _p = 0;
	}
    var _parentctx = this._ctx;
    var _parentState = this.state;
    var localctx = new ModuleContext(this, this._ctx, _parentState);
    var _prevctx = localctx;
    var _startState = 2;
    this.enterRecursionRule(localctx, 2, STLParser.RULE_module, _p);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        localctx = new ModuleLeafContext(this, localctx);
        this._ctx = localctx;
        _prevctx = localctx;

        this.state = 42;
        localctx.moduleName = this.match(STLParser.VARIABLE);
        this.state = 43;
        this.match(STLParser.T__7);
        this.state = 44;
        this.match(STLParser.VARIABLE);
        this.state = 49;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===STLParser.T__8) {
            this.state = 45;
            this.match(STLParser.T__8);
            this.state = 46;
            this.match(STLParser.VARIABLE);
            this.state = 51;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 52;
        this.match(STLParser.T__9);
        this._ctx.stop = this._input.LT(-1);
        this.state = 86;
        this._errHandler.sync(this);
        var _alt = this._interp.adaptivePredict(this._input,4,this._ctx)
        while(_alt!=2 && _alt!=antlr4.atn.ATN.INVALID_ALT_NUMBER) {
            if(_alt===1) {
                if(this._parseListeners!==null) {
                    this.triggerExitRuleEvent();
                }
                _prevctx = localctx;
                this.state = 84;
                this._errHandler.sync(this);
                var la_ = this._interp.adaptivePredict(this._input,3,this._ctx);
                switch(la_) {
                case 1:
                    localctx = new ModuleOpContext(this, new ModuleContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_module);
                    this.state = 54;
                    if (!( this.precpred(this._ctx, 7))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 7)");
                    }
                    this.state = 55;
                    localctx.op = this.match(STLParser.T__0);
                    this.state = 56;
                    this.match(STLParser.T__1);
                    this.state = 57;
                    localctx.tmap = this.match(STLParser.VARIABLE);
                    this.state = 58;
                    localctx.right = this.module(8);
                    break;

                case 2:
                    localctx = new ModuleOpContext(this, new ModuleContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_module);
                    this.state = 59;
                    if (!( this.precpred(this._ctx, 6))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 6)");
                    }
                    this.state = 60;
                    localctx.op = this.match(STLParser.T__2);
                    this.state = 61;
                    this.match(STLParser.T__1);
                    this.state = 62;
                    localctx.tmap = this.match(STLParser.VARIABLE);
                    this.state = 63;
                    localctx.right = this.module(7);
                    break;

                case 3:
                    localctx = new ModuleOpContext(this, new ModuleContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_module);
                    this.state = 64;
                    if (!( this.precpred(this._ctx, 5))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 5)");
                    }
                    this.state = 65;
                    localctx.op = this.match(STLParser.T__3);
                    this.state = 66;
                    this.match(STLParser.T__1);
                    this.state = 67;
                    localctx.tmap = this.match(STLParser.VARIABLE);
                    this.state = 68;
                    localctx.right = this.module(6);
                    break;

                case 4:
                    localctx = new ModuleOpContext(this, new ModuleContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_module);
                    this.state = 69;
                    if (!( this.precpred(this._ctx, 4))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 4)");
                    }
                    this.state = 70;
                    localctx.op = this.match(STLParser.T__4);
                    this.state = 71;
                    this.match(STLParser.T__1);
                    this.state = 72;
                    localctx.tmap = this.match(STLParser.VARIABLE);
                    this.state = 73;
                    localctx.right = this.module(5);
                    break;

                case 5:
                    localctx = new ModuleOpContext(this, new ModuleContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_module);
                    this.state = 74;
                    if (!( this.precpred(this._ctx, 3))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 3)");
                    }
                    this.state = 75;
                    localctx.op = this.match(STLParser.T__5);
                    this.state = 76;
                    this.match(STLParser.T__1);
                    this.state = 77;
                    localctx.tmap = this.match(STLParser.VARIABLE);
                    this.state = 78;
                    localctx.right = this.module(4);
                    break;

                case 6:
                    localctx = new ModuleOpContext(this, new ModuleContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_module);
                    this.state = 79;
                    if (!( this.precpred(this._ctx, 2))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 2)");
                    }
                    this.state = 80;
                    localctx.op = this.match(STLParser.T__6);
                    this.state = 81;
                    this.match(STLParser.T__1);
                    this.state = 82;
                    localctx.tmap = this.match(STLParser.VARIABLE);
                    this.state = 83;
                    localctx.right = this.module(3);
                    break;

                } 
            }
            this.state = 88;
            this._errHandler.sync(this);
            _alt = this._interp.adaptivePredict(this._input,4,this._ctx);
        }

    } catch( error) {
        if(error instanceof antlr4.error.RecognitionException) {
	        localctx.exception = error;
	        this._errHandler.reportError(this, error);
	        this._errHandler.recover(this, error);
	    } else {
	    	throw error;
	    }
    } finally {
        this.unrollRecursionContexts(_parentctx)
    }
    return localctx;
};

function ModuleDescriptionContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_moduleDescription;
    this.moduleName = null; // Token
    this.moduleFormula = null; // PropertyContext
    return this;
}

ModuleDescriptionContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ModuleDescriptionContext.prototype.constructor = ModuleDescriptionContext;

ModuleDescriptionContext.prototype.VARIABLE = function() {
    return this.getToken(STLParser.VARIABLE, 0);
};

ModuleDescriptionContext.prototype.property = function() {
    return this.getTypedRuleContext(PropertyContext,0);
};

ModuleDescriptionContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterModuleDescription(this);
	}
};

ModuleDescriptionContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitModuleDescription(this);
	}
};




STLParser.ModuleDescriptionContext = ModuleDescriptionContext;

STLParser.prototype.moduleDescription = function() {

    var localctx = new ModuleDescriptionContext(this, this._ctx, this.state);
    this.enterRule(localctx, 4, STLParser.RULE_moduleDescription);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 89;
        localctx.moduleName = this.match(STLParser.VARIABLE);
        this.state = 90;
        this.match(STLParser.T__10);
        this.state = 91;
        localctx.moduleFormula = this.property(0);
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

function PropertyContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_property;
    return this;
}

PropertyContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
PropertyContext.prototype.constructor = PropertyContext;


 
PropertyContext.prototype.copyFrom = function(ctx) {
    antlr4.ParserRuleContext.prototype.copyFrom.call(this, ctx);
};

function BooleanPredContext(parser, ctx) {
	PropertyContext.call(this, parser);
    PropertyContext.prototype.copyFrom.call(this, ctx);
    return this;
}

BooleanPredContext.prototype = Object.create(PropertyContext.prototype);
BooleanPredContext.prototype.constructor = BooleanPredContext;

STLParser.BooleanPredContext = BooleanPredContext;

BooleanPredContext.prototype.booleanExpr = function() {
    return this.getTypedRuleContext(BooleanExprContext,0);
};
BooleanPredContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterBooleanPred(this);
	}
};

BooleanPredContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitBooleanPred(this);
	}
};


function FormulaContext(parser, ctx) {
	PropertyContext.call(this, parser);
    this.left = null; // PropertyContext;
    this.op = null; // Token;
    this.child = null; // PropertyContext;
    this.low = null; // Token;
    this.high = null; // Token;
    this.right = null; // PropertyContext;
    PropertyContext.prototype.copyFrom.call(this, ctx);
    return this;
}

FormulaContext.prototype = Object.create(PropertyContext.prototype);
FormulaContext.prototype.constructor = FormulaContext;

STLParser.FormulaContext = FormulaContext;

FormulaContext.prototype.property = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(PropertyContext);
    } else {
        return this.getTypedRuleContext(PropertyContext,i);
    }
};

FormulaContext.prototype.RATIONAL = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(STLParser.RATIONAL);
    } else {
        return this.getToken(STLParser.RATIONAL, i);
    }
};

FormulaContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterFormula(this);
	}
};

FormulaContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitFormula(this);
	}
};


function ParpropContext(parser, ctx) {
	PropertyContext.call(this, parser);
    this.child = null; // PropertyContext;
    PropertyContext.prototype.copyFrom.call(this, ctx);
    return this;
}

ParpropContext.prototype = Object.create(PropertyContext.prototype);
ParpropContext.prototype.constructor = ParpropContext;

STLParser.ParpropContext = ParpropContext;

ParpropContext.prototype.property = function() {
    return this.getTypedRuleContext(PropertyContext,0);
};
ParpropContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterParprop(this);
	}
};

ParpropContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitParprop(this);
	}
};



STLParser.prototype.property = function(_p) {
	if(_p===undefined) {
	    _p = 0;
	}
    var _parentctx = this._ctx;
    var _parentState = this.state;
    var localctx = new PropertyContext(this, this._ctx, _parentState);
    var _prevctx = localctx;
    var _startState = 6;
    this.enterRecursionRule(localctx, 6, STLParser.RULE_property, _p);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 115;
        this._errHandler.sync(this);
        var la_ = this._interp.adaptivePredict(this._input,5,this._ctx);
        switch(la_) {
        case 1:
            localctx = new ParpropContext(this, localctx);
            this._ctx = localctx;
            _prevctx = localctx;

            this.state = 94;
            this.match(STLParser.T__7);
            this.state = 95;
            localctx.child = this.property(0);
            this.state = 96;
            this.match(STLParser.T__9);
            break;

        case 2:
            localctx = new BooleanPredContext(this, localctx);
            this._ctx = localctx;
            _prevctx = localctx;
            this.state = 98;
            this.booleanExpr();
            break;

        case 3:
            localctx = new FormulaContext(this, localctx);
            this._ctx = localctx;
            _prevctx = localctx;
            this.state = 99;
            localctx.op = this.match(STLParser.T__11);
            this.state = 100;
            localctx.child = this.property(11);
            break;

        case 4:
            localctx = new FormulaContext(this, localctx);
            this._ctx = localctx;
            _prevctx = localctx;
            this.state = 101;
            localctx.op = this.match(STLParser.T__12);
            this.state = 102;
            this.match(STLParser.T__13);
            this.state = 103;
            localctx.low = this.match(STLParser.RATIONAL);
            this.state = 104;
            this.match(STLParser.T__8);
            this.state = 105;
            localctx.high = this.match(STLParser.RATIONAL);
            this.state = 106;
            this.match(STLParser.T__14);
            this.state = 107;
            localctx.child = this.property(10);
            break;

        case 5:
            localctx = new FormulaContext(this, localctx);
            this._ctx = localctx;
            _prevctx = localctx;
            this.state = 108;
            localctx.op = this.match(STLParser.T__15);
            this.state = 109;
            this.match(STLParser.T__13);
            this.state = 110;
            localctx.low = this.match(STLParser.RATIONAL);
            this.state = 111;
            this.match(STLParser.T__8);
            this.state = 112;
            localctx.high = this.match(STLParser.RATIONAL);
            this.state = 113;
            this.match(STLParser.T__14);
            this.state = 114;
            localctx.child = this.property(9);
            break;

        }
        this._ctx.stop = this._input.LT(-1);
        this.state = 148;
        this._errHandler.sync(this);
        var _alt = this._interp.adaptivePredict(this._input,7,this._ctx)
        while(_alt!=2 && _alt!=antlr4.atn.ATN.INVALID_ALT_NUMBER) {
            if(_alt===1) {
                if(this._parseListeners!==null) {
                    this.triggerExitRuleEvent();
                }
                _prevctx = localctx;
                this.state = 146;
                this._errHandler.sync(this);
                var la_ = this._interp.adaptivePredict(this._input,6,this._ctx);
                switch(la_) {
                case 1:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 117;
                    if (!( this.precpred(this._ctx, 8))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 8)");
                    }
                    this.state = 118;
                    localctx.op = this.match(STLParser.T__0);
                    this.state = 119;
                    localctx.right = this.property(9);
                    break;

                case 2:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 120;
                    if (!( this.precpred(this._ctx, 7))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 7)");
                    }
                    this.state = 121;
                    localctx.op = this.match(STLParser.T__2);
                    this.state = 122;
                    localctx.right = this.property(8);
                    break;

                case 3:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 123;
                    if (!( this.precpred(this._ctx, 6))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 6)");
                    }
                    this.state = 124;
                    localctx.op = this.match(STLParser.T__3);
                    this.state = 125;
                    localctx.right = this.property(7);
                    break;

                case 4:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 126;
                    if (!( this.precpred(this._ctx, 5))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 5)");
                    }
                    this.state = 127;
                    localctx.op = this.match(STLParser.T__4);
                    this.state = 128;
                    localctx.right = this.property(6);
                    break;

                case 5:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 129;
                    if (!( this.precpred(this._ctx, 4))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 4)");
                    }
                    this.state = 130;
                    localctx.op = this.match(STLParser.T__5);
                    this.state = 131;
                    localctx.right = this.property(5);
                    break;

                case 6:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 132;
                    if (!( this.precpred(this._ctx, 3))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 3)");
                    }
                    this.state = 133;
                    localctx.op = this.match(STLParser.T__6);
                    this.state = 134;
                    localctx.right = this.property(4);
                    break;

                case 7:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 135;
                    if (!( this.precpred(this._ctx, 2))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 2)");
                    }
                    this.state = 136;
                    localctx.op = this.match(STLParser.T__16);
                    this.state = 137;
                    this.match(STLParser.T__13);
                    this.state = 138;
                    localctx.low = this.match(STLParser.RATIONAL);
                    this.state = 139;
                    this.match(STLParser.T__8);
                    this.state = 140;
                    localctx.high = this.match(STLParser.RATIONAL);
                    this.state = 141;
                    this.match(STLParser.T__14);
                    this.state = 142;
                    localctx.right = this.property(3);
                    break;

                case 8:
                    localctx = new FormulaContext(this, new PropertyContext(this, _parentctx, _parentState));
                    localctx.left = _prevctx;
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_property);
                    this.state = 143;
                    if (!( this.precpred(this._ctx, 1))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 1)");
                    }
                    this.state = 144;
                    localctx.op = this.match(STLParser.T__17);
                    this.state = 145;
                    localctx.right = this.property(2);
                    break;

                } 
            }
            this.state = 150;
            this._errHandler.sync(this);
            _alt = this._interp.adaptivePredict(this._input,7,this._ctx);
        }

    } catch( error) {
        if(error instanceof antlr4.error.RecognitionException) {
	        localctx.exception = error;
	        this._errHandler.reportError(this, error);
	        this._errHandler.recover(this, error);
	    } else {
	    	throw error;
	    }
    } finally {
        this.unrollRecursionContexts(_parentctx)
    }
    return localctx;
};

function ExprContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_expr;
    return this;
}

ExprContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ExprContext.prototype.constructor = ExprContext;

ExprContext.prototype.expr = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ExprContext);
    } else {
        return this.getTypedRuleContext(ExprContext,i);
    }
};

ExprContext.prototype.RATIONAL = function() {
    return this.getToken(STLParser.RATIONAL, 0);
};

ExprContext.prototype.VARIABLE = function() {
    return this.getToken(STLParser.VARIABLE, 0);
};

ExprContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterExpr(this);
	}
};

ExprContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitExpr(this);
	}
};



STLParser.prototype.expr = function(_p) {
	if(_p===undefined) {
	    _p = 0;
	}
    var _parentctx = this._ctx;
    var _parentState = this.state;
    var localctx = new ExprContext(this, this._ctx, _parentState);
    var _prevctx = localctx;
    var _startState = 8;
    this.enterRecursionRule(localctx, 8, STLParser.RULE_expr, _p);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 162;
        switch(this._input.LA(1)) {
        case STLParser.T__7:
        case STLParser.T__18:
            this.state = 152;
            _la = this._input.LA(1);
            if(!(_la===STLParser.T__7 || _la===STLParser.T__18)) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            this.state = 153;
            this.expr(0);
            this.state = 154;
            this.match(STLParser.T__9);
            break;
        case STLParser.T__19:
        case STLParser.T__20:
        case STLParser.T__21:
        case STLParser.T__22:
        case STLParser.T__23:
        case STLParser.T__24:
            this.state = 156;
            _la = this._input.LA(1);
            if(!((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << STLParser.T__19) | (1 << STLParser.T__20) | (1 << STLParser.T__21) | (1 << STLParser.T__22) | (1 << STLParser.T__23) | (1 << STLParser.T__24))) !== 0))) {
            this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            this.state = 157;
            this.expr(0);
            this.state = 158;
            this.match(STLParser.T__9);
            break;
        case STLParser.RATIONAL:
            this.state = 160;
            this.match(STLParser.RATIONAL);
            break;
        case STLParser.VARIABLE:
            this.state = 161;
            this.match(STLParser.VARIABLE);
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
        this._ctx.stop = this._input.LT(-1);
        this.state = 175;
        this._errHandler.sync(this);
        var _alt = this._interp.adaptivePredict(this._input,10,this._ctx)
        while(_alt!=2 && _alt!=antlr4.atn.ATN.INVALID_ALT_NUMBER) {
            if(_alt===1) {
                if(this._parseListeners!==null) {
                    this.triggerExitRuleEvent();
                }
                _prevctx = localctx;
                this.state = 173;
                this._errHandler.sync(this);
                var la_ = this._interp.adaptivePredict(this._input,9,this._ctx);
                switch(la_) {
                case 1:
                    localctx = new ExprContext(this, _parentctx, _parentState);
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_expr);
                    this.state = 164;
                    if (!( this.precpred(this._ctx, 6))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 6)");
                    }
                    this.state = 165;
                    this.match(STLParser.T__17);
                    this.state = 166;
                    this.expr(7);
                    break;

                case 2:
                    localctx = new ExprContext(this, _parentctx, _parentState);
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_expr);
                    this.state = 167;
                    if (!( this.precpred(this._ctx, 4))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 4)");
                    }
                    this.state = 168;
                    _la = this._input.LA(1);
                    if(!(_la===STLParser.T__25 || _la===STLParser.T__26)) {
                    this._errHandler.recoverInline(this);
                    }
                    else {
                        this.consume();
                    }
                    this.state = 169;
                    this.expr(5);
                    break;

                case 3:
                    localctx = new ExprContext(this, _parentctx, _parentState);
                    this.pushNewRecursionContext(localctx, _startState, STLParser.RULE_expr);
                    this.state = 170;
                    if (!( this.precpred(this._ctx, 3))) {
                        throw new antlr4.error.FailedPredicateException(this, "this.precpred(this._ctx, 3)");
                    }
                    this.state = 171;
                    _la = this._input.LA(1);
                    if(!(_la===STLParser.T__27 || _la===STLParser.T__28)) {
                    this._errHandler.recoverInline(this);
                    }
                    else {
                        this.consume();
                    }
                    this.state = 172;
                    this.expr(4);
                    break;

                } 
            }
            this.state = 177;
            this._errHandler.sync(this);
            _alt = this._interp.adaptivePredict(this._input,10,this._ctx);
        }

    } catch( error) {
        if(error instanceof antlr4.error.RecognitionException) {
	        localctx.exception = error;
	        this._errHandler.reportError(this, error);
	        this._errHandler.recover(this, error);
	    } else {
	    	throw error;
	    }
    } finally {
        this.unrollRecursionContexts(_parentctx)
    }
    return localctx;
};

function BooleanExprContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_booleanExpr;
    this.left = null; // ExprContext
    this.op = null; // Token
    this.right = null; // ExprContext
    return this;
}

BooleanExprContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
BooleanExprContext.prototype.constructor = BooleanExprContext;

BooleanExprContext.prototype.expr = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ExprContext);
    } else {
        return this.getTypedRuleContext(ExprContext,i);
    }
};

BooleanExprContext.prototype.BOOLEAN = function() {
    return this.getToken(STLParser.BOOLEAN, 0);
};

BooleanExprContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterBooleanExpr(this);
	}
};

BooleanExprContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitBooleanExpr(this);
	}
};




STLParser.BooleanExprContext = BooleanExprContext;

STLParser.prototype.booleanExpr = function() {

    var localctx = new BooleanExprContext(this, this._ctx, this.state);
    this.enterRule(localctx, 10, STLParser.RULE_booleanExpr);
    var _la = 0; // Token type
    try {
        this.state = 183;
        switch(this._input.LA(1)) {
        case STLParser.T__7:
        case STLParser.T__18:
        case STLParser.T__19:
        case STLParser.T__20:
        case STLParser.T__21:
        case STLParser.T__22:
        case STLParser.T__23:
        case STLParser.T__24:
        case STLParser.VARIABLE:
        case STLParser.RATIONAL:
            this.enterOuterAlt(localctx, 1);
            this.state = 178;
            localctx.left = this.expr(0);
            this.state = 179;
            localctx.op = this._input.LT(1);
            _la = this._input.LA(1);
            if(!(((((_la - 11)) & ~0x1f) == 0 && ((1 << (_la - 11)) & ((1 << (STLParser.T__10 - 11)) | (1 << (STLParser.T__29 - 11)) | (1 << (STLParser.T__30 - 11)) | (1 << (STLParser.T__31 - 11)) | (1 << (STLParser.T__32 - 11)))) !== 0))) {
                localctx.op = this._errHandler.recoverInline(this);
            }
            else {
                this.consume();
            }
            this.state = 180;
            localctx.right = this.expr(0);
            break;
        case STLParser.BOOLEAN:
            this.enterOuterAlt(localctx, 2);
            this.state = 182;
            localctx.op = this.match(STLParser.BOOLEAN);
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

function TranslationMapContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_translationMap;
    this.tmapName = null; // Token
    return this;
}

TranslationMapContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
TranslationMapContext.prototype.constructor = TranslationMapContext;

TranslationMapContext.prototype.translationPair = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(TranslationPairContext);
    } else {
        return this.getTypedRuleContext(TranslationPairContext,i);
    }
};

TranslationMapContext.prototype.VARIABLE = function() {
    return this.getToken(STLParser.VARIABLE, 0);
};

TranslationMapContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterTranslationMap(this);
	}
};

TranslationMapContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitTranslationMap(this);
	}
};




STLParser.TranslationMapContext = TranslationMapContext;

STLParser.prototype.translationMap = function() {

    var localctx = new TranslationMapContext(this, this._ctx, this.state);
    this.enterRule(localctx, 12, STLParser.RULE_translationMap);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 185;
        localctx.tmapName = this.match(STLParser.VARIABLE);
        this.state = 186;
        this.match(STLParser.T__33);
        this.state = 187;
        this.translationPair();
        this.state = 192;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===STLParser.T__8) {
            this.state = 188;
            this.match(STLParser.T__8);
            this.state = 189;
            this.translationPair();
            this.state = 194;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 195;
        this.match(STLParser.T__34);
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

function TranslationPairContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_translationPair;
    this.key = null; // Token
    this.moduleName = null; // Token
    this.value = null; // Token
    return this;
}

TranslationPairContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
TranslationPairContext.prototype.constructor = TranslationPairContext;

TranslationPairContext.prototype.VARIABLE = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(STLParser.VARIABLE);
    } else {
        return this.getToken(STLParser.VARIABLE, i);
    }
};


TranslationPairContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterTranslationPair(this);
	}
};

TranslationPairContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitTranslationPair(this);
	}
};




STLParser.TranslationPairContext = TranslationPairContext;

STLParser.prototype.translationPair = function() {

    var localctx = new TranslationPairContext(this, this._ctx, this.state);
    this.enterRule(localctx, 14, STLParser.RULE_translationPair);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 197;
        localctx.key = this.match(STLParser.VARIABLE);
        this.state = 200;
        _la = this._input.LA(1);
        if(_la===STLParser.T__35) {
            this.state = 198;
            this.match(STLParser.T__35);
            this.state = 199;
            localctx.moduleName = this.match(STLParser.VARIABLE);
        }

        this.state = 202;
        this.match(STLParser.T__36);
        this.state = 203;
        localctx.value = this.match(STLParser.VARIABLE);
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

function LimitMapContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_limitMap;
    this.lmapName = null; // Token
    return this;
}

LimitMapContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
LimitMapContext.prototype.constructor = LimitMapContext;

LimitMapContext.prototype.limitPair = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(LimitPairContext);
    } else {
        return this.getTypedRuleContext(LimitPairContext,i);
    }
};

LimitMapContext.prototype.VARIABLE = function() {
    return this.getToken(STLParser.VARIABLE, 0);
};

LimitMapContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterLimitMap(this);
	}
};

LimitMapContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitLimitMap(this);
	}
};




STLParser.LimitMapContext = LimitMapContext;

STLParser.prototype.limitMap = function() {

    var localctx = new LimitMapContext(this, this._ctx, this.state);
    this.enterRule(localctx, 16, STLParser.RULE_limitMap);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 205;
        localctx.lmapName = this.match(STLParser.VARIABLE);
        this.state = 206;
        this.match(STLParser.T__13);
        this.state = 207;
        this.limitPair();
        this.state = 212;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===STLParser.T__8) {
            this.state = 208;
            this.match(STLParser.T__8);
            this.state = 209;
            this.limitPair();
            this.state = 214;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 215;
        this.match(STLParser.T__14);
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

function LimitPairContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = STLParser.RULE_limitPair;
    this.sigName = null; // Token
    this.maxValue = null; // Token
    this.minValue = null; // Token
    return this;
}

LimitPairContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
LimitPairContext.prototype.constructor = LimitPairContext;

LimitPairContext.prototype.VARIABLE = function() {
    return this.getToken(STLParser.VARIABLE, 0);
};

LimitPairContext.prototype.RATIONAL = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(STLParser.RATIONAL);
    } else {
        return this.getToken(STLParser.RATIONAL, i);
    }
};


LimitPairContext.prototype.enterRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.enterLimitPair(this);
	}
};

LimitPairContext.prototype.exitRule = function(listener) {
    if(listener instanceof STLListener ) {
        listener.exitLimitPair(this);
	}
};




STLParser.LimitPairContext = LimitPairContext;

STLParser.prototype.limitPair = function() {

    var localctx = new LimitPairContext(this, this._ctx, this.state);
    this.enterRule(localctx, 18, STLParser.RULE_limitPair);
    try {
        this.state = 243;
        this._errHandler.sync(this);
        var la_ = this._interp.adaptivePredict(this._input,15,this._ctx);
        switch(la_) {
        case 1:
            this.enterOuterAlt(localctx, 1);
            this.state = 217;
            this.match(STLParser.T__33);
            this.state = 218;
            localctx.sigName = this.match(STLParser.VARIABLE);
            this.state = 219;
            this.match(STLParser.T__36);
            this.state = 220;
            this.match(STLParser.T__33);
            this.state = 221;
            this.match(STLParser.T__37);
            this.state = 222;
            this.match(STLParser.T__36);
            this.state = 223;
            localctx.maxValue = this.match(STLParser.RATIONAL);
            this.state = 224;
            this.match(STLParser.T__8);
            this.state = 225;
            this.match(STLParser.T__38);
            this.state = 226;
            this.match(STLParser.T__36);
            this.state = 227;
            localctx.minValue = this.match(STLParser.RATIONAL);
            this.state = 228;
            this.match(STLParser.T__34);
            this.state = 229;
            this.match(STLParser.T__34);
            break;

        case 2:
            this.enterOuterAlt(localctx, 2);
            this.state = 230;
            this.match(STLParser.T__33);
            this.state = 231;
            localctx.sigName = this.match(STLParser.VARIABLE);
            this.state = 232;
            this.match(STLParser.T__36);
            this.state = 233;
            this.match(STLParser.T__33);
            this.state = 234;
            this.match(STLParser.T__38);
            this.state = 235;
            this.match(STLParser.T__36);
            this.state = 236;
            localctx.minValue = this.match(STLParser.RATIONAL);
            this.state = 237;
            this.match(STLParser.T__8);
            this.state = 238;
            this.match(STLParser.T__37);
            this.state = 239;
            this.match(STLParser.T__36);
            this.state = 240;
            localctx.maxValue = this.match(STLParser.RATIONAL);
            this.state = 241;
            this.match(STLParser.T__34);
            this.state = 242;
            this.match(STLParser.T__34);
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


STLParser.prototype.sempred = function(localctx, ruleIndex, predIndex) {
	switch(ruleIndex) {
	case 1:
			return this.module_sempred(localctx, predIndex);
	case 3:
			return this.property_sempred(localctx, predIndex);
	case 4:
			return this.expr_sempred(localctx, predIndex);
    default:
        throw "No predicate with index:" + ruleIndex;
   }
};

STLParser.prototype.module_sempred = function(localctx, predIndex) {
	switch(predIndex) {
		case 0:
			return this.precpred(this._ctx, 7);
		case 1:
			return this.precpred(this._ctx, 6);
		case 2:
			return this.precpred(this._ctx, 5);
		case 3:
			return this.precpred(this._ctx, 4);
		case 4:
			return this.precpred(this._ctx, 3);
		case 5:
			return this.precpred(this._ctx, 2);
		default:
			throw "No predicate with index:" + predIndex;
	}
};

STLParser.prototype.property_sempred = function(localctx, predIndex) {
	switch(predIndex) {
		case 6:
			return this.precpred(this._ctx, 8);
		case 7:
			return this.precpred(this._ctx, 7);
		case 8:
			return this.precpred(this._ctx, 6);
		case 9:
			return this.precpred(this._ctx, 5);
		case 10:
			return this.precpred(this._ctx, 4);
		case 11:
			return this.precpred(this._ctx, 3);
		case 12:
			return this.precpred(this._ctx, 2);
		case 13:
			return this.precpred(this._ctx, 1);
		default:
			throw "No predicate with index:" + predIndex;
	}
};

STLParser.prototype.expr_sempred = function(localctx, predIndex) {
	switch(predIndex) {
		case 14:
			return this.precpred(this._ctx, 6);
		case 15:
			return this.precpred(this._ctx, 4);
		case 16:
			return this.precpred(this._ctx, 3);
		default:
			throw "No predicate with index:" + predIndex;
	}
};


exports.STLParser = STLParser;
