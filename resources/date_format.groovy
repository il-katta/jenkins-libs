import java.text.SimpleDateFormat

// @ref: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html

String format_now(String dateFormatStr = "dd/MM/yyyy HH:mm")
{
    def date = new Date()
    return format(date, dateFormatStr)
}


String format(Date date, String dateFormatStr)
{
    def dateFormat = new SimpleDateFormat(dateFormatStr)
    return dateFormat.format(date)
}

return this