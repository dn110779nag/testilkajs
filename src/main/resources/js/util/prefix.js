var data = JSON.parse(dataStr); var console = {log: function(str){print(str);}}; function require(path){load(path);}
var data = JSON.parse(dataStr); var console = {log: function(str){print(str);}}; delete data.stack;
var ret = {i: JSON.stringify(data)};
