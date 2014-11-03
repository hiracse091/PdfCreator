var pdfcreator = {
    get: function(parmas,successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'PdfCreator', // mapped to our native Java class called "BMSHAHashing"
            'create', // with this action name
            [parmas]
        ); 
     }
}
module.exports = pdfcreator;



