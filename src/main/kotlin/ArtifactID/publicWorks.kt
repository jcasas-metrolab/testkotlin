package ArtifactID

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.File
import javax.xml.stream.XMLInputFactory
import java.io.InputStreamReader
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.Gson
import org.apache.http.HttpHost
import org.apache.http.entity.ContentType
import org.apache.http.nio.entity.NStringEntity
import org.elasticsearch.client.RestClient
import java.io.FileInputStream
import java.io.PrintWriter

@JsonIgnoreProperties(ignoreUnknown = true)
data class PWObra(
        @JsonProperty("card")
        val card: PWCard?)

data class PWCard(
        @JsonProperty("lang")
        val lang: String = "",
        @JsonProperty("title")
        val title: String = "",
        @JsonProperty("description")
        val description: String = "",
        @JsonProperty("issued")
        val issued: String = "",
        @JsonProperty("address")
        val address: PWAddress?,
        @JsonProperty("worktype")
        val worktype: String = "",
        @JsonProperty("period")
        val period: String = "",
        @JsonProperty("status")
        val status: String = "",
        @JsonProperty("developer-company")
        val developerCompany: String = "",
        @JsonProperty("construction-company")
        val constructionCompany: String = "",
        @JsonProperty("media")
        val objMedia: PWMedia?,
        @JsonProperty("milestones")
        val milestones: List<PWMilestone>?,
        @JsonProperty("dtlastpub")
        val dtlastpub: String = "")


data class PWAddress(
        @JsonProperty("zone")
        val zone: PWZone?,
        @JsonProperty("geo")
        val geo: PWGeo?,
        @JsonProperty("object")
        val obj: PWObject?,
        @JsonProperty("comments")
        val comments: String? = ""
)

data class PWMedia(
        @JsonProperty("object")
        val obj: PWMediaObject?
)

data class PWObject(
        @JsonProperty("src")
        val src: String? = "",
        @JsonProperty("type")
        val type: String? = "",
        @JsonProperty("alternate")
        val alternate: String? = ""
)

data class PWMediaObject(
        @JsonProperty("src")
        val src: String? = "",
        @JsonProperty("type")
        val type: String? = "",
        @JsonProperty(" ")
        val noIdentified: String? = ""
)

data class PWGeo(
        @JsonProperty("latitude")
        val latitude: String? = "",
        @JsonProperty("longitude")
        val longitude: String? = "")

data class PWZone(
        @JsonProperty("district")
        val district: String? = "",
        @JsonProperty("neighborhood")
        val neighborhood: String? = "")

data class PWMilestone(
        @JsonProperty("title")
        val title: String? = "",
        @JsonProperty("description")
        val description: String? = "",
        @JsonProperty("dtstart")
        val dtstart: String? = "",
        @JsonProperty("dtend")
        val dtend: String? = "",
        @JsonProperty("affectations")
        val affectations: List<PWAffectation>?
)

data class PWAffectation(
        @JsonProperty("affectation")
        val type: String? = "",
        @JsonProperty("title")
        val title: String? = "",
        @JsonProperty("description")
        val description: String? = ""
)


fun main(args: Array<String>) {

    // PUBLIC WORKS
    val file = File("/Users/scas272/Documents/data/civil_works/civil_works_20170601_clean.xml")
    val writer = PrintWriter("/Users/scas272/Documents/data/civil_works/kotlin_xmlopendata.txt", "UTF-8")

    // Open ES rest client
    val restClient = RestClient.builder(HttpHost("127.0.0.1", 9200, "http")).build()

    val info = InputStreamReader(FileInputStream(file))
    val f = XMLInputFactory.newFactory()
    val sr = f.createXMLStreamReader(info)
    val mapper = XmlMapper()

    while (sr.hasNext()) {
        sr.next()

        if (sr.isStartElement) {
            if(sr.localName.equals("obra")) {
                val obra = mapper.readValue(sr, PWObra::class.java)

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

