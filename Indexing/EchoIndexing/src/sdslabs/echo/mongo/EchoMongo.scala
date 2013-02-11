package sdslabs.echo.mongo

import com.mongodb.Mongo
import sdslabs.echo.utils._
import com.mongodb.DB
import org.json.simple.JSONObject
import org.json.simple.JSONArray
import scala.collection.mutable.HashMap
import org.json.simple.JSONValue
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.BasicDBObject
import com.mongodb.DBCursor
import org.jsoup.Jsoup
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.BufferedWriter

class EchoMongo {
  
  val mongodb : Mongo = new Mongo()
  val db : DB = mongodb.getDB(Settings.getDBName())
  
  def storeInfo(map : Map[String, String]) {
    var coll : DBCollection = db.getCollection(Settings.getBookCollection())
    var doc : BasicDBObject = new BasicDBObject()
    var uuid : String = map.get("uuid").get
    var temp_key : String = ""
    var temp_value : String = ""
    map.foreach( m => {      
      temp_key = m._1 
      temp_value = m._2
      if(temp_key.equals("mainCategory")) {        
    	var list = List[java.lang.String]()
    	var temp_str = ""
        temp_value.replaceAll("\"", "").split("/").foreach( entry => {
          temp_str = entry.trim()
          list ++= List(temp_str)
        })
        doc.put(temp_key, list.toArray)
      }
      
      else if(temp_key.equals("categories")) {        
    	var list = List[String]()
        var category_array : JSONArray = JSONValue.parse(temp_value).asInstanceOf[JSONArray]
        var size = category_array.size()
        var temp_str = ""
        for(i <- 1 until size+1) {
          var category = category_array.get(i-1).toString()
              category.replaceAll("\"", "").split("/").foreach( entry => {
                temp_str = entry.trim()
                list ++= List(temp_str)
                storeCategory(temp_str)
                storeByCategory(temp_str, uuid)
              })
        }
    	doc.put(temp_key, list.toArray)
      }
      
      else if(temp_key.equals("authors")) {
        var author_array : JSONArray = JSONValue.parse(temp_value).asInstanceOf[JSONArray]
        var size = author_array.size()
        var temp_str : String = null
        for(i <- 1 until size + 1) {
          if(i == 1)
        	temp_str = author_array.get(i-1).toString()
          else
            temp_str = temp_str + ", " + author_array.get(i-1).toString()
        }
        doc.put(temp_key, temp_str.toString())
      }
      
      else if(temp_key.equals("description")) {
        var temp_str = Jsoup.parse(temp_value).text()
        doc.put(temp_key, temp_str)
      }
      
      else {
        var temp_str = temp_value.trim()
        doc.put(temp_key, temp_str)
      }
    })
    coll.insert(doc)
  }
  
  def storeByCategory(str : String, uuid : String) {
    var coll : DBCollection = db.getCollection(Settings.getCategoryCollection())
    var query : BasicDBObject = new BasicDBObject()
    query.put("id", str.toLowerCase())
    var cursor : DBCursor = coll.find(query)
    if(!cursor.hasNext())
      coll.insert(query)
    coll.update(query, new BasicDBObject("$addToSet", new BasicDBObject("list", uuid)))
  }
  
  def storeCategory(str : String) {
    var len : Int = 0
    var coll : DBCollection = db.getCollection(Settings.getCategoryListCollection())
    str.toLowerCase().split(" ").foreach( string => {
      len = string.length()
	    for(i <- 3 until len+1) {
	      for(j <- 0 until len-i+1) {
	        var temp_str : String = string.substring(j, j+i)
	        var query : BasicDBObject = new BasicDBObject()
	        query.put("id", temp_str)
	        var cursor = coll.find(query)
	        if(!cursor.hasNext()) 
	          coll.insert(query)
	        coll.update(query, new BasicDBObject("$addToSet", new BasicDBObject("list", str)))
	      }
	    }
    })
  }
  
  def storeStatus(uuid : String, status : Boolean) {
    var coll = db.getCollection("booksIndexStatus")
    if(status == true) {            
      var query : BasicDBObject = new BasicDBObject()
      query.put("id", "success")
      var cursor = coll.find(query)
      if(!cursor.hasNext())
        coll.insert(query)
      coll.update(query, new BasicDBObject("$addToSet", new BasicDBObject("list", uuid)))
    } else {
      var query : BasicDBObject = new BasicDBObject()
      query.put("id", "not indexed")
      var cursor = coll.find(query)
      if(!cursor.hasNext())
        coll.insert(query)
      coll.update(query, new BasicDBObject("$addToSet", new BasicDBObject("list", uuid)))
    }
  }
  
  def saveImages(str : String, uuid : String) {
    println("image link starting")
    var out : OutputStream = null
    var in : InputStream = null
    try {
    	println(str)
		var url : URL = new URL(str)
        val uc = url.openConnection()
        val connection = uc.asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        val buffer : Array[Byte] = new Array[Byte](1024)
        var numRead : Int = 0
        in = connection.getInputStream()
        var localFileName = Settings.getImageDir() + uuid
        out = new BufferedOutputStream(new FileOutputStream(localFileName))
		println("image link working")
		Iterator.continually(in.read(buffer)).takeWhile(_ != -1).foreach(n => out.write(buffer,0,n))
	} catch {
        case e:Exception => println(e.printStackTrace())
     }
	out.close()
	in.close()
   /* var file : File = new File("/home/neeraj/Desktop/imageInfo.txt")
    var fileWritter = new FileWriter(file.getName(),true)
    var bufferWritter = new BufferedWriter(fileWritter)
    bufferWritter.write(uuid + " " + str)
    bufferWritter.close()*/
   }
    
   
}