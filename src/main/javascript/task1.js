d = data.data;

if(d && d.listHOS){
    for(var i=0; i<d.listHOS.length;i++){
        var h = d.listHOS[i];
        if(h.mapOflistProviderService && h.mapOflistProviderService.mapOfListProvidersService){
            for(var k in h.mapOflistProviderService.mapOfListProvidersService){
                var serv = h.mapOflistProviderService.mapOfListProvidersService[k];
                
                
                    var a = serv.providerServices.filter(function(s){
                        s=""+s.provideId;
                        var st = s.match(/^\d*$/g);
                        var ret = !s || s==="null" || st!==null;
//                        print(s+"|"+ret)
                        return ret;
                    });
//                    print("a.length="+a.length);
                    serv.providerServices = a;
            }
        }
    }
}

