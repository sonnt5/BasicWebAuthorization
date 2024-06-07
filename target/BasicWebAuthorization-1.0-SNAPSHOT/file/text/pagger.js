/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function pagger(pageindex,totalpage,gap,currentpage,param)
{
    var div_paggers = document.getElementsByClassName("pagger");
    for (var p = 0; p< div_paggers.length;p++)
    {
        var pager = div_paggers[p];
        if(pageindex - gap > 1)
        {
            var linkFirst = document.createElement("a");
            linkFirst.href = currentpage+"?page=1"+(param.length>0?"&"+param:"");
            linkFirst.innerHTML="first";
            pager.appendChild(linkFirst);
        }
        for (var i = pageindex - gap; i <= pageindex -1; i++) {
            if(i>=1)
            {
                var linkNum = document.createElement("a");
                linkNum.href = currentpage+"?page=" + i+(param.length>0?"&"+param:"");
                linkNum.innerHTML= i;
                pager.appendChild(linkNum);
            }
        }
        var span = document.createElement("span");
            span.innerHTML="["+pageindex+"]";
            pager.appendChild(span);
            
        for (var i = pageindex +1; i <= pageindex + gap; i++) {
            if(i<=totalpage)
            {
                var linkNum = document.createElement("a");
                linkNum.href = currentpage+"?page=" + i+(param.length>0?"&"+param:"");
                linkNum.innerHTML= i;
                pager.appendChild(linkNum);
            }
        }    
        if(pageindex + gap < totalpage)
        {
            var linkFirst = document.createElement("a");
            linkFirst.href = currentpage+"?page=" + totalpage+(param.length>0?"&"+param:"");
            linkFirst.innerHTML="last";
            pager.appendChild(linkFirst);
        }
    }
}

