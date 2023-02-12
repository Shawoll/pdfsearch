import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
class PdfSearchController {
    @GetMapping("/search")
    fun search(@RequestParam folder: String, @RequestParam searchText: String): List<String> {
        val result = mutableListOf<String>()
        val pdfFolder = File(folder)

        pdfFolder.listFiles().forEach { file ->
            if (file.extension == "pdf") {
                PDDocument.load(file).use { document ->
                    val pdfText = PDFTextStripper().getText(document)
                    if (pdfText.contains(searchText)) {
                        result.add(file.name)
                    }
                }
            }
        }

        return result
    }
}