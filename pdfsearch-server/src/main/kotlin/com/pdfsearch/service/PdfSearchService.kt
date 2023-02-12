import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException

@Service
class PdfSearchService {

    fun searchInPdfs(directory: String, searchTerm: String): Map<String, List<Int>> {
        val result = mutableMapOf<String, List<Int>>()
        val directoryFile = File(directory)
        if (directoryFile.isDirectory) {
            directoryFile.listFiles()
                    .filter { it.extension == "pdf" }
                    .forEach { file ->
                        val pdfFile = File(file.absolutePath)
                        try {
                            val pdfText = pdfFile.readText()
                            val pageNumbers = getPageNumbersOfTerm(pdfText, searchTerm)
                            if (pageNumbers.isNotEmpty()) {
                                result[pdfFile.name] = pageNumbers
                            }
                        } catch (e: IOException) {
                            println("Error reading file: ${pdfFile.name}")
                        }
                    }
        }
        return result
    }

    private fun getPageNumbersOfTerm(pdfText: String, searchTerm: String): List<Int> {
        val pageNumbers = mutableListOf<Int>()
        val lines = pdfText.split("\n")
        var pageNumber = 1
        lines.forEach { line ->
            if (line.contains(searchTerm)) {
                pageNumbers.add(pageNumber)
            }
            if (line.trim().endsWith("-")) {
                pageNumber++
            }
        }
        return pageNumbers
    }
}
