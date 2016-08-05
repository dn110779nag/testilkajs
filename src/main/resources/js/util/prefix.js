var data = JSON.parse(dataStr); var console = {log: function(str){print(str);}}; function require(path){load(path);}
var data = JSON.parse(dataStr); var console = {log: function(str){print(str);}}; delete data.stack;
var _very_ugly_ret_ = {i: JSON.stringify(data)};
