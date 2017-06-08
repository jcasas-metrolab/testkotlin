package ArtifactID

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.google.gson.Gson
import java.io.File
import java.io.PrintWriter
import utils.downloadURL
import mu.KotlinLogging
import org.apache.http.HttpHost
import org.apache.http.entity.ContentType
import org.apache.http.nio.entity.NStringEntity
import org.elasticsearch.client.RestClient
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.xml.stream.XMLInputFactory
import objects.*
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by scas272 on 1/6/17.
 */

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {

    logger.info("Hello, World")

    for (a in args){
        println(a)
    }
    val name = if (args.size > 0) args[0] else "Kotlin"
    println("Hello, $name!")
    logger.debug("Hello, $name!")

    // Download file
    //downloadURL("http://opendata-ajuntament.barcelona.cat/resources/bcn/XMLOPENDATA.XML")


    // PUBLIC WORKS
    val file = File("/Users/scas272/Documents/data/civil_works/civil_works_20170601.xml")
    val writer = PrintWriter("/Users/scas272/Documents/data/civil_works/kotlin_xmlopendata.txt", "UTF-8")

    // Open ES rest client
    val restClient = RestClient.builder(HttpHost("127.0.0.1", 9200, "http")).build()

    val info = InputStreamReader(FileInputStream(file))
    val f = XMLInputFactory.newFactory()
    val sr = f.createXMLStreamReader(info)
    val mapper = XmlMapper()

    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
    mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
    mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    while (sr.hasNext()) {
        sr.next()

        if (sr.isStartElement) {
            if(sr.localName.equals("obra")) {
                val obra = mapper.readValue(sr, PWOObra::class.java)

                // Convert Java object to JSON
                val json = Gson().toJson(obra);

                // Write output to a file
                writer.println(json)
                println(json)

                // Write to ES index
                val entity = NStringEntity(json, ContentType.APPLICATION_JSON)
                val response = restClient.performRequest("POST", "/index/type/", emptyMap<String, String>(), entity)
            }
        }
    }
    info.close()
    writer.close()
    println("Process ended")

}


