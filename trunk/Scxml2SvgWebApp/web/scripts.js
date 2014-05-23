function checkFileType(){
    var str = document.getElementById("uploadFile").value;
    var suffix = ".scxml";
    var suffix2 = ".xml";    
    
    if(!(str.indexOf(suffix, str.length - suffix.length) !== -1 ||
            str.indexOf(suffix2, str.length - suffix2.length) !== -1)) {
        alert("File type is not allowed!\nAllowed file types: *.scxml,*.xml");
        document.getElementById("uploadFile").value="";
    }
}