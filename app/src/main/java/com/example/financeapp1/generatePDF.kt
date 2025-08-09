import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import com.example.financeapp1.models.ExpenseEntity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun generateExpensePdf(expenses: List<ExpenseEntity>): String? {

    val pageWidth = 595
    val pageHeight = 842

    val pdfDocument = PdfDocument()

    val titlePaint = Paint().apply {
        textSize = 18f
        isFakeBoldText = true
        color = Color.BLACK
    }

    val contentPaint = Paint().apply {
        textSize = 12f
        color = Color.BLACK
    }

    val marginLeft = 40f
    val topMargin = 50f
    val lineHeight = 20f
    val bottomMargin = 50f

    var pageNumber = 1
    var yPosition = topMargin

    var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
    var page = pdfDocument.startPage(pageInfo)
    var canvas = page.canvas

    fun drawHeader() {
        val title = "Expense Report"
        val titleWidth = titlePaint.measureText(title)
        val xTitle = (pageWidth - titleWidth) / 2f
        canvas.drawText(title, xTitle, yPosition, titlePaint)

        var headerY = yPosition + 30f

        canvas.drawText("Title", marginLeft, headerY, contentPaint)
        canvas.drawText("Category", marginLeft + 150f, headerY, contentPaint)
        canvas.drawText("Amount (₹)", marginLeft + 300f, headerY, contentPaint)
        canvas.drawText("Date", marginLeft + 420f, headerY, contentPaint)

        headerY += 20f
        canvas.drawLine(marginLeft, headerY, pageWidth - marginLeft, headerY, contentPaint)

        yPosition = headerY + 20f
    }

    drawHeader()

    for (expense in expenses) {
        if (yPosition > pageHeight - bottomMargin) {
            pdfDocument.finishPage(page)
            pageNumber++
            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            page = pdfDocument.startPage(pageInfo)
            canvas = page.canvas
            yPosition = topMargin
            drawHeader()
        }

        canvas.drawText(expense.title, marginLeft, yPosition, contentPaint)
        canvas.drawText(expense.category, marginLeft + 150f, yPosition, contentPaint)
        canvas.drawText("%.2f".format(expense.amount), marginLeft + 300f, yPosition, contentPaint)
        canvas.drawText(expense.date, marginLeft + 420f, yPosition, contentPaint)
        yPosition += lineHeight
    }

    pdfDocument.finishPage(page)

    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val fileName = "Expense_Report_${sdf.format(Date())}.pdf"

    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    if (!storageDir.exists()) storageDir.mkdirs()
    val file = File(storageDir, fileName)

    return try {
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
        file.absolutePath
    } catch (e: Exception) {
        Log.e("PDF", "Error writing PDF", e)
        pdfDocument.close()
        null
    }
}
