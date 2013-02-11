package sdslabs.echo.indexing
import nl.siegmann.epublib.epub.EpubReader
import java.io.File
import java.io.FileInputStream
import nl.siegmann.epublib.domain.Book
import java.io.FileWriter
import java.io.PrintWriter
import org.jsoup._
import java.io.File
import scala.collection.immutable.HashMap
import com.mongodb.Mongo
import com.mongodb.DB
import com.mongodb.DBCollection
import sdslabs.echo.utils._
import com.mongodb.BasicDBObject
import java.util.UUID
import java.io.Writer
import java.io.BufferedWriter
import java.io.FileWriter
import com.mongodb.DBCursor
import java.io.PrintWriter
import sdslabs.echo.utils._
import java.io.InputStream
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.io.DataInputStream
import sdslabs.echo.mongo.EchoMongo
import java.net.HttpURLConnection
import java.io.OutputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream


object IndexingMain {
  
  def downloadFile(str : String, name : String) : File = {
    var out : OutputStream = null
    var in : InputStream = null
    try {
		var url : URL = new URL(str)
        val uc = url.openConnection()
        val connection = uc.asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        val buffer : Array[Byte] = new Array[Byte](1024)
        var numRead : Int = 0
        in = connection.getInputStream()
        var localFileName = "/home/neeraj/Desktop/" + name
        out = new BufferedOutputStream(new FileOutputStream(localFileName))
		Iterator.continually(in.read(buffer)).takeWhile(_ != -1).foreach(n => out.write(buffer,0,n))
	} catch {
        case e:Exception => println(e.printStackTrace())
     }
	out.close()
	in.close()
	var file : File = new File("/home/neeraj/Desktop/" + name)
	file
  }
  
  
  def main(args : Array[String]) {
    var counter : Int = 1
    var a : EchoExtraction = new EchoExtraction()
    var b : EchoIndexing = new EchoIndexing()
    var c : EchoMongo = new EchoMongo()
    var map : Map[String, String] = new HashMap[String, String]()
    var outfile = new java.io.FileOutputStream(Settings.getErrorFile())
    var out_stream = new java.io.PrintStream(outfile)
/*    var directory : File = new File(Settings.getBooksDir())
    println(directory)
    for(file <- directory.listFiles()) {
      
      var path : String = directory + "/" + file.getName()
      println(path)
      var book : EchoFileInfo = new EchoFileInfo(path)
      if(book.getExtension().compareTo("pdf") == 0 || book.getExtension().compareTo("epub") == 0 ) {
	      try {
	    		map = a.getInfo(book)
	    		println(map)
	    		println(map.get("uuid").get)
	    		var doc : EchoDocument = new EchoDocument(new EchoFileInfo(path), map.get("uuid").get, map)
	    		b.indexDocument(doc)
	    		//store in mongo (pending)
	    	
	   } catch {
	   	  case e : Exception => {
	       out_stream.println(counter + " :: " + book.getBookName() + "   ==>   " + e.getStackTrace())
	   }
	   }
	   
   } else {
     println("djvu file")
   }
    println(counter)
    counter += 1
    } */
      var fstream : FileInputStream = new FileInputStream("/home/neeraj/Desktop/EchoBooks.txt")
      var in : DataInputStream = new DataInputStream(fstream)
      var br :BufferedReader = new BufferedReader(new InputStreamReader(in))
      var strLine : String = ""
      while ({strLine = br.readLine(); strLine != null})   {
        println("========================================")
         System.out.println (strLine)
         if(strLine.split('.').last.compareTo("pdf") == 0 || strLine.split('.').last.compareTo("epub") == 0) {
        	 var url = "http://192.168.208.198/ebook/books/" + strLine
        	 var file : File = downloadFile(url, strLine)
        	 var book : EchoFileInfo = new EchoFileInfo(file)
        	 try {
        		 map = a.getInfo(book)
        		 println(map)
        		 var doc : EchoDocument = new EchoDocument(book, map.get("uuid").get, map)
        		 b.indexDocument(doc)
        		 c.storeStatus( map.get("name").get, true)    
        		 
        	 } catch {
        	 	case e : Exception => {
        	 		out_stream.println(counter + " :: " + book.getBookName() + "   ==>   " + e.getStackTrace())
        	    }
             }
        	 file.delete()
         } else {
           c.storeStatus(strLine, false)
         }
       println("========================================")  
      }
  }
}