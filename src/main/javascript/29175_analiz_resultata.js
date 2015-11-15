function convert(str){
    return "01."+str.substring(4)+"."+str.substring(0,4);
}

function format(d){
    return (d.getDate() < 10 ? "0" : "") + d.getDate() + "."
            + (d.getMonth() < 10 ? "0" : "") + d.getMonth() + "."
            + d.getFullYear();
}

function date112to105(str){
    if(!str) return str;
    else return str.substring(8,10)+"."+str.substring(5,7)+"."+str.substring(0,4);
}

for(var i=0; i<data.services.length; i++){
    var v = data.services[i];
    
    v.date_pay = date112to105(v.date_pay);
    if(!v.sum) v.sum=0;
    else v.sum = -v.sum;
    
    if(v.periodTo) v.periodTo = convert(v.periodTo);
    else v.periodTo = format(new Date());
}

