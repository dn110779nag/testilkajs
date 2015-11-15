

function copyInvoices(src, dst) {
    
    var lst = src.listInvoises.listInvoises;
    dst.invoiseList = new Array();
    debug("lst="+lst)
    if(!lst) return;
    for (var i = 0; i < lst.length; i++) {
        var inv = lst[i];
        dst.invoiseList.push({
            invName: inv.invName,
            validDate: long2StringDate(inv.invValid),
            sum: inv.allSum,
            cur: inv.currency
        });
    }
}
var deb = false;
function debug(str) {
    if (deb)
        print(str);
}

function copyDH(src, dst) {
    dst.householdList = [];
    debug("src.listHOS.length=" + src.listHOS.length);
    for (var i = 0; i < src.listHOS.length; i++) {
        var h = src.listHOS[i];
        debug("h.hos=" + h.hos);
        var ho = {
            addressHousehold: formatAdr((h.addressMessage)?h.addressMessage.listNodeAddress:null),
            hos: h.hos,
            irsList: [],
            singleServiseList: []
        };
        dst.householdList.push(ho);
        if (h.mapOflistProviderService && h.mapOflistProviderService.mapOfListProvidersService) {
            debug("in=" + JSON.stringify(h.mapOflistProviderService));
            var irsMap = {};
            for (var k in h.mapOflistProviderService.mapOfListProvidersService) {
                debug("k=" + k);
                var t = h.mapOflistProviderService.mapOfListProvidersService["" + k];

                if (k !== "49" && k !== "147") { //single
                    debug("t=" + JSON.stringify(t));
                    if (t && t.providerServices) {
                        for (var j = 0; j < t.providerServices.length; j++) {
                            var s = t.providerServices[j];
                            if (accept(s)) {
                                ho.singleServiseList.push({
                                    templateId: s.templateId,
                                    organizationId: s.organizationId,
                                    orgName: s.organizationName,
                                    idBP: s.idBP,
                                    serviceName: s.serviceName
                                });
                            }
                        }

                    }

                } else if (k === "49" || k === "147") { //irs
                    if (t && t.providerServices) {
                        for (var j = 0; j < t.providerServices.length; j++) {
                            var s = t.providerServices[j];
                            if (accept(s)) {
                                var key = s.sp_hash + "_" + s.provideId;
                                debug("key=" + key);
                                var tmp = irsMap[key];
                                if (!tmp) {
                                    tmp = [];
                                    irsMap[key] = tmp;
                                }

                                tmp.push({
                                    templateId: s.templateId,
                                    organizationId: s.organizationId,
                                    orgName: s.organizationName,
                                    idBP: s.idBP,
                                    sp_hash: s.sp_hash,
                                    provideId: s.provideId,
                                    serviceName: s.serviceName,
                                    p_timestamp: s.p_timestamp
                                });
                            }
                        }
                    }
                }
            }
            debug("k=" + k + ";irsMap=" + JSON.stringify(irsMap));
            ho.irsList = mergeIrs(irsMap);
            debug("ho.irsList =" + JSON.stringify(ho.irsList));
        }


    }
}

function long2StringDate(lng) {
    var d = new Date(lng);
    //dd.mm.yyyy
    return (d.getDate() < 10 ? "0" : "") + d.getDate() + "."
            + (d.getMonth() < 10 ? "0" : "") + d.getMonth() + "."
            + d.getFullYear();
}


function mergeIrs(irsMap) {
    var ret = [];
    debug("irsMap=" + JSON.stringify(irsMap));
    
    for (var k in irsMap) {
        var arr = irsMap[k];
        var irs;
        var map2 = {};
        debug("arr=" + JSON.stringify(arr));
        for (var i = 0; i < arr.length; i++) {
            var t = arr[i];
            debug("t=" + JSON.stringify(t));
            if (i === 0) {
                var org = getOrg(t.organizationId);
                debug("org=" + JSON.stringify(org));
                irs = {
                    singleTicket: org.st,
                    organizationId: t.organizationId,
                    orgName: org.name,
                    idBP: null,
                    provideId: t.provideId,
                    sp_hash: t.sp_hash,
                    serviceList: []
                };
                ret.push(irs);
            }
            var key = t.sp_hash + "_" + t.idBP;
            var prev = map2[key];
            if (prev) {

                if (prev.p_timestamp < t.p_timestamp) {
                    //t.index = map2[key].index;
                    map2[key] = t;
                    irs.serviceList[t.index] = {
                        templateId: t.templateId,
                        serviceName: t.serviceName
                    };
                }
                irs.idBP = map2[key].idBP;
            } else {
                map2[key] = t;
                t.index = irs.serviceList.length;
                irs.serviceList.push({
                    templateId: t.templateId,
                    serviceName: t.serviceName
                });
            }

        }

    }
    debug("irsList1=" + JSON.stringify(ret));
    return ret;
}

function getOrg(organizationId) {
    var defs = {
        "2058899": "МоЦ",
        "2155351": "Нова Ком",
        "899677": "ГИВЦ переплата"
    };
    var orgName = defs[organizationId];
    var st = orgName === null;

    return {
        st: st,
        name: orgName
    };
}

function accept(s) {
    s = "" + s.templateId;
    var st = s.match(/^\d*$/g);
    var ret = !s || s === "null" || st !== null;
    return ret;
}



function formatAdr(arr) {
    if (!arr)
        return;
    var has4 = false;
    arr.sort(function (a, b) {
        if (a.nodeOrder === 4 || b.nodeOrder === 4) {
            has4 = true;
        }
        return a.nodeOrder > b.nodeOrder;
    });
    var ret = "";
    var coma = ", ";
    for (var i = 0; i < arr.length; i++) {
        var n = arr[i];
        if (n.nodeOrder === 1)
            continue;
        var str = n.typeName + " " + n.nodeName;
        if (n.nodeOrder === 2 && has4) {
            continue;
        }
        ret += (ret.length === 0 ? "" : coma) + str;
    }
    return ret;
}

function sortHos(hos){
    hos.sort(function(a,b){
        if(!a.hos) return 1;
        else if(!b.hos) return -1;
        else if(a.irsList && a.irsList.length) return 1;
        else if(b.irsList && b.irsList.length) return -1;
        else return 0;
    });
}

var dst = {
};

try {
    copyInvoices(data.data, dst);
    copyDH(data.data, dst);
    sortHos(dst.householdList);
} catch (ex) {
    data.stack = ""+ex.message + " - "+ex.stack;
    //print("ex=" + JSON.stringify(ex) + "|" + ex.stack);
}
data.res = dst;
//data = {res: dst, in: data};