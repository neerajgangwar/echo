package sdslabs.echo.utils

object Settings {
  
	private var indexDirName : String = "/home/neeraj/Desktop/Echo/IndexDirectoryName/"
	private var indexDirDetails : String = "/home/neeraj/Desktop/Echo/IndexDirectoryDetails/"
    private var errorFile : String = "/home/neeraj/Desktop/Echo/error.txt"
    private var booksDir : String = "/home/neeraj/Desktop/Books/"
    private var db : String = "echo"
    private var url : String = "https://www.googleapis.com/books/v1/volumes?q="
    private var key : String = "AIzaSyDfRHedKz25GNoOlmyLMf2mECWxMTAa4tE"
    private var path : String = "/home/neeraj/Desktop/Echo/BooksIndex"
    private var bookColl : String = "books"
    private var catColl : String = "categories"
    private var catListColl : String = "categoryList"
    private var imageDir : String = "/home/neeraj/Desktop/"
    
    def getImageDir() : String = imageDir
	def getErrorFile() : String = errorFile
	def getIndexDirName() : String = indexDirName
	def getIndexDirDetails() : String = indexDirDetails
	def getBooksDir() : String = booksDir
	def getDBName() : String = db
	def getGoogleURL(str : String) : String = url + str + "&key=" + key
	def getKey() : String = key
	def getURL(str : String) : String = str + "?key=" + key
	def getLocalFileName(str : String) : String = path + "/" + str
	def getBookCollection() : String = bookColl
	def getCategoryListCollection() : String = catListColl
	def getCategoryCollection() : String = catColl
	 

}