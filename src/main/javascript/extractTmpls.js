function fromHouseHolds(hhList, tmpls){
    for(var i=0;i<hhList.length;i++){
        var h = hhList[i];
        if(h.irsList){
            for(var j1=0; j1<h.irsList.length; j1++){
                var irs = h.irsList[j1];
                if(irs.serviceList){
                    for(var j2 = 0; j2 < irs.serviceList.length;j2++){
                        tmpls.push(irs.serviceList[j2].templateId);
                    }
                }
            }
        }
        if(h.singleServiseList){
            for(var j1=0; j1<h.singleServiseList.length; j1++){
                tmpls.push(h.singleServiseList[j1].templateId);
            }
        }
    }
}

data.tmpls = [];
data.counter = 0;

fromHouseHolds(data.res.householdList, data.tmpls);
data.tmplsLength = data.tmpls.length;