

function createBreadCrumbs(services) {
    var bc = {};
    for (var i = 0; i < services.length; i++) {
        var s = services[i];
        if (s.forbidden) {
            continue;
        } else {
            var obj = props2Obj(s.property);
            bc = {
                recipientName: {title: "COMPANY_NAME", value: obj.COMPANY, order: "1",
                    requisites: {
                        "okpo": {"title": "OKPO", "value": obj.COMPANY_OKPO, "order": "1"},
                        "mfo": {"title": "MFO", "value": obj.MFO, "order": "2"},
                        "acc": {"title": "PAY_ACC", "value": obj.ACCOUNT, "order": "3"}
                    }
                },
                "ls": {"title": "ACC", "value": getFirstProp(obj, ["ACCOUNT","pacc", "kod"]), "order": "2"},
                "division": {"title": "DIVISION_NAME", "value": "", "order": "4"},
                "service": {"title": "SERVICE_NAME", "value": s.name, "order": "5"},
                divisioID: s.divisionID
            };
            
        }
    }
    return bc;
}

function getFirstProp(obj, propList){
    for(var i=0; i<propList.length; i++){
        var ret = obj[propList[i]];
        if(ret) return ret;
    }
}

function props2Obj(props) {
    var ret = {};
    for (var i = 0; i < props.length; i++) {
        var p = props[i];
        ret[p.alias] = p.value;
    }
    return ret;
}

function addDivision(divisionID, bc, divisions){
    for(var i=0;i<divisions.length;i++){
        var d = divisions[i];
        if(d.id==divisionID){
            bc.division.value = d.name;
            break;
        }
    }
}


data.breadCrumbs = createBreadCrumbs(data.services.service);
if(data.breadCrumbs.divisioID && data.services.division){
    addDivision(data.breadCrumbs.divisioID, data.breadCrumbs, data.services.division);
}
