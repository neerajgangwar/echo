package sdslabs.echo.utils
import scala.AnyVal
import java.io.File
import java.io.FileInputStream
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.apache.pdfbox.pdmodel.PDDocument
import nl.siegmann.epublib.epub.EpubReader
import nl.siegmann.epublib.domain.Book
import org.jsoup._

object WhichParser {
  
  def getText(file : EchoFileInfo) : String = {
    var extension : String = file.getExtension()
    println("which parser " + extension)
    var myfile : File = file.getFile()
    val fi: FileInputStream  = new FileInputStream(myfile)
    var text : String = ""
    if(extension.toLowerCase().compareTo("pdf") == 0) {
      var parser =  new PDFParser(fi)
      parser.parse()  
      val cd: COSDocument = parser.getDocument()  
      val stripper: PDFTextStripper = new PDFTextStripper()  
      text = ""
      text  = stripper.getText(new PDDocument(cd))  
      cd.close
    }
    
    else if(extension.toLowerCase().compareTo("epub") == 0) {
      var epubReader : EpubReader  = new EpubReader()
      var book : Book = epubReader.readEpub(fi)
      text= ""
      for(i <- 0 until book.getContents().size()) {
    	  if(book.getContents().get(i).getMediaType().toString().compareTo("application/xhtml+xml") == 0) {
    		var data : String = new String(book.getContents().get(i).getData())
    		data = Jsoup.parse(data).text()
     		text += " " + data 
    	}
      }
    }
    else if(extension.toLowerCase().compareTo("djvu") == 0) {
      
    }
    return text
     
  }

}