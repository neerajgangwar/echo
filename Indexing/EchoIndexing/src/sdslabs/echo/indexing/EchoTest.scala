package sdslabs.echo.indexing
import sdslabs.echo.mongo.EchoMongo
import sdslabs.echo.utils.Settings
import sdslabs.echo.utils.EchoFileInfo
import java.io.FileInputStream
import java.io.DataInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileWriter
import java.io.BufferedWriter

object EchoTest {
  
  def main(args : Array[String]) {
 //   val c : EchoSearching = new EchoSearching()
  //  println(c.search("new headway"))
    var d : EchoMongo = new EchoMongo()
    d.saveImages("http://bks7.books.google.com/books?id=jL_yT-AZK-8C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", "neeraj")
  /*  val a : EchoExtraction = new EchoExtraction()
    var fstream : FileInputStream = new FileInputStream("/home/neeraj/Desktop/EchoBooks.txt")
    var in : DataInputStream = new DataInputStream(fstream);
    var br : BufferedReader = new BufferedReader(new InputStreamReader(in));
    var strLine : String = new String()
    var fstream1 : FileWriter = new FileWriter("/home/neeraj/Desktop/EchoBookDetails.txt")
    var out : BufferedWriter = new BufferedWriter(fstream1)
    while ({strLine = br.readLine(); strLine != null})   {
      var query = a.getFromSQL(strLine)
      out.write(strLine + " " +  query + "\n")      
    }
    out.close() 
    a.getFromSQL("004f6257f3f0b33ed6fc92b243f63bb2.pdf")*/
  //  val map = a.searchGoogle("php")004f6257f3f0b33ed6fc92b243f63bb2.pdf 
  /*  a.searchGoogle("c++")
    a.searchGoogle("ruby")
    a.searchGoogle("php") */
  //  val b = new EchoMongo()
 //   val c : EchoSearching = new EchoSearching()
 //   println(c.search("I have been so-many too-many persons, life unlike syntax allows one more than three, and at last somewhere the striking of a clock, twelve chimes, release"))
 //   println(c.search("midnight childern"))
 //   println(c.search("information theory"))
    
  //  val d : EchoMongo = new EchoMongo()
  //  d.saveImages("http://bks1.books.google.com/books?id=N--YV7uyDI8C&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE72JAtn82JnVVJV_w0M_5wI7VKRU66mh7tOM8QG3Oq1S5RRSC68wv-BfQSnpq-iVc953yFhMqIf4Xls1esBVb4QHV118aAgLt5f9p8k78-4g-2F5gWw&source=gbs_api&key=AIzaSyDfRHedKz25GNoOlmyLMf2mECWxMTAa4tE", "984y7q8eruh")
    
 //   b.storeCategory("computer programming")
 //   b.storeCategory("programming")
 //   b.storeCategory("computer")
 //   b.storeByCategory("programming", "book1")
 //   b.storeByCategory("programming", "book4")
 //   b.storeByCategory("programming", "book3")
 //   b.storeByCategory("programming", "book2")
  // b.saveImages("http://bks1.books.google.com/books?id=2KQNAAAAQAAJ&printsec=frontcover&img=1&zoom=3&edge=curl&imgtk=AFLRE72uNOt1fHB7JAyA7ZAYQx9iWiDko_j-sjXlkDCXPcbYMWSnvMgIp239MEzrMqbzD9FAgnV5cgphUA04PjBacDxqRlVyvQ&source=gbs_api"); 
  }
}