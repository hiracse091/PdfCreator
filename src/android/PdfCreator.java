package org.apache.cordova.pdfcreator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.dcastalia.main.R;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
/*import android.content.Context;
import android.content.ContextWrapper;*/
//import com.lowagie.*;

 
public class PdfCreator extends CordovaPlugin {
 
 @Override
 public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) {
  if (action.equals("create")) {
   cordova.getThreadPool().execute(new Runnable() {
    public void run() {
    	try{
  		  JSONObject params = args.getJSONObject(0);
  		  String jsonData = params.getString("jsonObj"); 
  	      if (jsonData != null && jsonData.length() > 0) { 
  	    	  try{
  	    		  //CreatePdfMain generatePdf = new CreatePdfMain();
  	    		  //generatePdf.createPDF();  
  	    		  
  	    		  createPDF(jsonData);
  		    	  Log.e("PhoneGapLog", "pdfCreator Plugin: Success: " + PluginResult.Status.OK);
  		    	   callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK,jsonData));
  		    	  // return true;
  	    	  }catch(Exception e){
  	    		  e.printStackTrace();
  	    		  Log.e("PhoneGapLog", "pdfCreator Plugin: Error 1 : " + PluginResult.Status.ERROR);
  		    	   callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
  		    	   //return false;
  	    	  }
  	      } else {
  	    	  Log.e("PhoneGapLog", "pdfCreator Plugin: Error:2 " + PluginResult.Status.ERROR);
  	    	   callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
  	    	  // return false;
  	      }
  	  }catch(JSONException e){
  		  e.printStackTrace();
  	      Log.e("PhoneGapLog", "pdfCreator Plugin: JSON_EXCEPTION -1: " + PluginResult.Status.JSON_EXCEPTION);
  	      callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
  	  }
    }
   });
   return true;
  }
  else {
	   Log.e("PhoneGapLog", "pdfCreator Plugin: INVALID_ACTION -2: " + PluginResult.Status.INVALID_ACTION);
	   callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
	   return false;
  }  
 }
 
 
 private void showToast(final String message, final String duration) {
  cordova.getActivity().runOnUiThread(new Runnable() {
   public void run() {
    Toast toast;
    if(duration.equals("long")) {
     toast = Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_LONG);
    } else {
     toast = Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_SHORT);
    }
    toast.show();
   }
  });
 }

private void createPDF(String resultKeys) throws DocumentException, JSONException
{
	JSONArray formData = null;
	JSONArray question = null;
	JSONObject Conclusion = null;
	JSONArray alternatives =  null;
	 Document doc = new Document();
	 Date date = new Date();
	 SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy h-mm-ss");
	 String formattedDate = sdf.format(date);
	 
	 String jsonString = "{\"Consultation\":\"Form Test\",\"Date\":\"2013-12-8 15:12:46\"" +
	 		",\"FormData\":[{\"name\":\"hira\",\"value\":\"3\"},{\"name\":\"hira\",\"value\":\"3\"}]" +
	 		",\"Questionnaire\":[{\"qsn\":\"This is a sample question though the question and answer is unknown. \"" +
	 		",\"ans\":\"No\"},{\"qsn\":\"This is another question though do not know answer at all. \",\"ans\":\"More Super\"}]" +
	 		",\"Conclusion\":{\"alternate\":\"C Alternative\",\"value\":0},\"Alternatives\":[{\"alternate\":\"Alternative A\",\"value\":0},{\"alternate\":\"Alternative 2\",\"value\":0}]}";
	 final JSONObject obj = new JSONObject(resultKeys);
	 Conclusion =  obj.getJSONObject("Conclusion");
	 alternatives = obj.getJSONArray("Alternatives");
	 formData = obj.getJSONArray("FormData");
	 question = obj.getJSONArray("Questionnaire");
	 try {
	  String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
	
	  File dir = new File(path);
	  if(!dir.exists())
	   dir.mkdirs();
	
	  Log.d("PDFCreator", "PDF Path: " + path);
	  String fileName = obj.getString("Consultation")+formattedDate+".pdf"; //
	  Log.e("PdfCreator ","filename "+fileName);
	  File file = new File(dir, fileName);
	  FileOutputStream fOut = new FileOutputStream(file);
	  //= PdfWriter.GetInstance(doc, new FileStream(pdfpath + "/Graphics.pdf", FileMode.Create));
	  PdfWriter writer   = PdfWriter.getInstance(doc, fOut);
	
	  //open the document
	  doc.open();
	 /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
	  Bitmap bitmap = BitmapFactory.decodeResource(cordova.getActivity().getResources(), R.drawable.logo);
	  bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
	  Image logo = Image.getInstance(stream.toByteArray());
	  logo.scaleAbsoluteHeight(20);
	  logo.scaleAbsoluteWidth(30);
	  logo.scalePercent(100);
	  Chunk chunk = new Chunk(logo, 30, 15);
	  HeaderFooter header = new HeaderFooter(new Phrase(chunk), false);
	  header.setAlignment(Element.ALIGN_CENTER);
	  header.setBorder(Rectangle.NO_BORDER);
	  doc.setHeader(header);*/
	  
	  Font paraFont= new Font(Font.HELVETICA);
	  paraFont.setSize(30);
	  Paragraph p1 = new Paragraph("Consultation: "+obj.getString("Consultation"),paraFont);		
	  /* Create Set Font and its Size */
	  
	  p1.setAlignment(Paragraph.ALIGN_LEFT);
	  //p1.setFont(paraFont);
	  doc.add(p1);
	  
	  /* Inserting Image in PDF */
	  ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
	  Bitmap bitmap2 = BitmapFactory.decodeResource(cordova.getActivity().getResources(), R.drawable.logo);
	  bitmap2.compress(Bitmap.CompressFormat.JPEG, 100 , stream2);
	  Image myImg = Image.getInstance(stream2.toByteArray());
	  myImg.setAlignment(Image.ALIGN_RIGHT);
	  myImg.scaleAbsolute(120, 60);	 
	  myImg.setAbsolutePosition(450,720);
	  
	  doc.add(myImg);
	 
	  Font smallFont= new Font(Font.TIMES_ROMAN);
	  smallFont.setSize(15);
	  
	  Paragraph date1 = new Paragraph("Date: "+obj.getString("Date"),smallFont);
	  doc.add(date1);
	  /*PdfContentByte cb = writer.getDirectContent();
	  cb.moveTo(doc.getPageSize().getWidth() / 2, doc.getPageSize().getHeight() / 2);
	  cb.lineTo(doc.getPageSize().getWidth() , doc.getPageSize().getHeight()/2);
	  cb.stroke();*/
	  /*PdfContentByte cb = writer.getDirectContent();
      cb.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, false), 24);
      cb.moveTo(30, 700);
      cb.lineTo(450, 700);
      
      cb.moveTo(30, 600);
      cb.lineTo(450, 600);
      cb.stroke();*/
      
      /*PdfTemplate template = cb.createTemplate(500, 200);
      template.setLineWidth(1f);
      template.rectangle(0.5f, 3.4f, 495f, 95f);
      template.stroke();
      template.setLineWidth(12f);
      cb.addTemplate(template, 0, 1, -1, 0, 500, 200);*/
      
      
	  Paragraph line = new Paragraph("-------------------------------------------------------------",smallFont);
	  doc.add(line);
	  for(int i=0;i<formData.length();i++){
	  		 JSONObject form = formData.getJSONObject(i);
	  		 String key = null;
	  		 String value = null;
	  		 Log.e("Pdf Creator plugin value for value", form.getString("value"));
	  		 //(color != null ? color : messageColor)
	  		 key = (form.getString("name")!= null && form.getString("name").length()>0 ? form.getString("name") : "Form Filed");
	  		 value = (form.getString("value")!= null && form.getString("value").length()>0 ? form.getString("value") : "Form Value");
	  		/* if(key.length()<=0){
	  			 key = "Form Filed";
	  			 value = "Form Value";
	  		 }*/
	  		 Paragraph formFiled = new Paragraph(key+" : "+value,smallFont);
	  		 formFiled.setFont(smallFont);
	  		 formFiled.setAlignment(Paragraph.ALIGN_LEFT);
	  		 doc.add(formFiled);
	  		
	  	  }
	  
	 Paragraph line2 = new Paragraph("-------------------------------------------------------------",smallFont);
	  doc.add(line2);
	  //Questionnaire
	  
	  Font paraFont3= new Font(Font.HELVETICA);
	  paraFont3.setSize(27);
	  Paragraph Questionnaire = new Paragraph("Questionnaire",paraFont3);
	  Questionnaire.setAlignment(Paragraph.ALIGN_LEFT);
	  doc.add(Questionnaire);
	  
	  for(int i=0;i<question.length();i++){
		 JSONObject q = question.getJSONObject(i);
		 Paragraph questionFiled = new Paragraph((i+1)+"."+q.getString("qsn")+"     ANS: "+q.getString("ans"),smallFont);
		 questionFiled.setFont(smallFont);
		 questionFiled.setAlignment(Paragraph.ALIGN_LEFT);
		 doc.add(questionFiled);
	  }
	 
	
	  
	  Paragraph BestAlternat = new Paragraph("Conclusion",paraFont3);
	  BestAlternat.setAlignment(Paragraph.ALIGN_LEFT);
	  doc.add(BestAlternat);
	  Paragraph best = new Paragraph(Conclusion.getString("alternate")+" = "+Conclusion.getString("value"));
	  best.setAlignment(Paragraph.ALIGN_LEFT);
	  doc.add(best);
	  
	  Font paraFont4= new Font(Font.HELVETICA);
	  paraFont4.setSize(20);
	  Paragraph alternate = new Paragraph("Alternatives: ",paraFont4);
	  alternate.setAlignment(Paragraph.ALIGN_LEFT);
	  doc.add(alternate);
	  
	  for(int i=0;i<alternatives.length();i++){
		  JSONObject altObj = alternatives.getJSONObject(i);
		  Paragraph alt = new Paragraph(altObj.getString("alternate")+" = "+altObj.getString("value"));
		  alt.setAlignment(Paragraph.ALIGN_LEFT);
		  doc.add(alt);
	  }
	 /* //set footer
	  Phrase footerText = new Phrase("This is an example of a footer");
	  HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
	  doc.setFooter(pdfFooter);*/
	  Log.e("PhoneGapLog", "pdfCreator Plugin: Success: Pdf created..");
	  showToast("Result is saved in PDF folder as "+fileName+" .","long" );
	  
	 } catch (DocumentException de) {
	  Log.e("PDFCreator", "DocumentException:" + de);
	 } catch (IOException e) {
	  Log.e("PDFCreator", "ioException:" + e);
	 } 
	 finally
	 {
	  doc.close();
	 }
}  
}
