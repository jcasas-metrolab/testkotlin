package utils

import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.URL
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import mu.KotlinLogging
/**
 * Created by scas272 on 8/6/17.
 */
private val logger = KotlinLogging.logger {}

/**
 * Function to save content from URL to a file
 */
fun downloadURL(datasourceURL: String) {
    val url = URL(datasourceURL)
    val file = File("/tmp/file.txt")
    try {
        FileUtils.copyURLToFile(url, file)
        println("Saved correctly")
        logger.debug("Saved correctly")
    }
    catch(e: IOException){
        println("Error saving URL to a file")
        logger.debug("Error saving URL to a file")
    }

}