var data = JSON.parse(dataStr); var console = {log: function(str){print(str);}}; function include(name){load("lib/"+name);}
var ret = {i: JSON.stringify(data)};