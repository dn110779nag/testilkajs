var data = JSON.parse(dataStr); var console = {log: function(str){print(str);}}; function include(name){load("lib/"+name);}
var data = JSON.parse(dataStr); var console = {log: function(str){print(str);}}; delete data.stack;
var ret = {i: JSON.stringify(data)};
