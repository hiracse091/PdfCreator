/**
 * Created with JetBrains PhpStorm.
 * User: Naher
 * Date: 10/22/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
 /*function Downloader() {}

 Downloader.prototype.downloadFile = function(fileUrl, params, win, fail) {
 //Make params hash optional.
 if (!fail) win = params;
 PhoneGap.exec(win, fail, "Downloader", "downloadFile", [fileUrl, params]);
 };

 PhoneGap.addConstructor(function() {
     PhoneGap.addPlugin("downloader", new Downloader());
     PluginManager.addService("Downloader", "com.phonegap.plugins.downloader.Downloader");
 });

 if(!window.plugins) {
    window.plugins = {};
 }
 if (!window.plugins.downloader) {
    window.plugins.downloader = new Downloader();
 }*/
cordova.define("cordova/plugin/pdfcreator", function (require, exports, module) {
    var exec = require("cordova/exec");
    module.exports = {
        create: function (message, win, fail) {
            exec(win, fail, "PdfCreator", "create", [message]);
        }
    };
});

/*var errorpath =''

function download(url, overwrite,dir) {
    var fileTransfer = new FileTransfer();
    var downloader = cordova.require("cordova/plugin/downloader");
    downloader.get({url: url, overwrite: overwrite, dir:dir},
        function() {
            console.log("PhoneGap Plugin: Downloader: callback success");
            downLoad.finishLoading++
            console.log('DEBUG - queue length = '+downLoad.queue.length+' , finishLoading - '+downLoad.finishLoading)
            if(downLoad.finishLoading ==  downLoad.queue.length){
                $('.loadingDiv').html('Image download finished.')
                downLoad.downloadComplete = true
                downLoad.downLoadVideo()
                setStorage('fileNotFound',JSON.stringify(heDb.fileNotFound))
            }
        },
        function() {
            path = window.appRootDir.fullPath+'/' + getFileNameFromUrl(url)
            heDb.fileNotFound.push(path)
            errorpath+=',   '+ url
            //alert("PhoneGap Plugin: Downloader: callback error for "+url);
            downLoad.finishLoading++
            console.log('DEBUG - queue length = '+downLoad.queue.length+' , finishLoading - '+downLoad.finishLoading)
            if(downLoad.finishLoading ==  downLoad.queue.length){
                $('.loadingDiv').html('Image download finished.')
                downLoad.downloadComplete = true
                downLoad.downLoadVideo() // download video files
                setStorage('fileNotFound',JSON.stringify(heDb.fileNotFound))
            }
        }
    );
}*/


createPdf = function(jsonData){
    var pdfCreator = cordova.require("cordova/plugin/pdfcreator");
    pdfCreator.create({jsonObj:jsonData },
        function(data) {
            console.log("Successfully created pdf.")
        },
        function() {
            alert("Error in creating Results Pdf . Try from beginning. ")
        }
    );
}
writeAccesslog =  function(jsonData){
    function gotFS(fileSystem) {
        fileSystem.root.getFile("Android/data/HeData/accesslog.txt", {create: true, exclusive: false}, gotFileEntry, fail);
    }

    function gotFileEntry(fileEntry) {
        fileEntry.createWriter(gotFileWriter, fail);
    }

    function gotFileWriter(writer) {
        writer.onwriteend = function(evt) {
            console.log("contents of file now 'json data'");
            //writer.truncate(11);  
           /* writer.onwriteend = function(evt) {
                console.log("contents of file now 'some sample'");
                //writer.seek(4);
                writer.write(" different text");
                writer.onwriteend = function(evt){
                    console.log("contents of file now 'some different text'");
                }
            };*/
        };
        writer.seek(writer.length+2)
        writer.write(jsonData);
    }

    function fail(error) {
        console.log(error.code);
    }
   window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, gotFS, fail);

}


