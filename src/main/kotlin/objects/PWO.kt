package objects

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

/**
 * Created by scas272 on 8/6/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class PWOObra(
        @JacksonXmlProperty(localName="card") val card: PWOCard?)


data class PWOCard(
        @JacksonXmlProperty(localName="lang") val lang: String = "",
        @JacksonXmlProperty(localName="title") val title: String = "",
        @JacksonXmlProperty(localName="description") val description: String = "",
        @JacksonXmlProperty(localName="issued") val issued: String = "",
        @JacksonXmlProperty(localName="address") val address: PWOAddress?,
        @JacksonXmlProperty(localName="worktype") val worktype: String = "",
        @JacksonXmlProperty(localName="period") val period: String = "",
        @JacksonXmlProperty(localName="status") val status: String = "",
        @JacksonXmlProperty(localName="developer-company") val developerCompany: String = "",
        @JacksonXmlProperty(localName="construction-company") val constructionCompany: String = "",
        @JacksonXmlProperty(localName="media") val objMedia: PWOMedia?,
        @JacksonXmlProperty(localName="milestones") val milestones: List<PWOMilestone>?,
        @JacksonXmlProperty(localName="dtlastpub") val dtlastpub: String = "")

data class PWOAddress(
        @JacksonXmlProperty(localName="zone") val zone: PWOZone?,
        @JacksonXmlProperty(localName="geo") val geo: PWOGeo?,
        @JacksonXmlProperty(localName="object") val obj: PWOObject?,
        @JacksonXmlProperty(localName="comments") val comments: String? = "")

data class PWOMedia(
        @JacksonXmlProperty(localName="object") val obj: List<PWOMediaObject>?)

data class PWOObject(
        @JacksonXmlProperty(localName="src") val src: String? = "",
        @JacksonXmlProperty(localName="type") val type: String? = "",
        @JacksonXmlProperty(localName="alternate") val alternate: String? = "")

data class PWOMediaObject(
        @JacksonXmlProperty(localName="src") val src: String? = "",
        @JacksonXmlProperty(localName="type") val type: String? = "")

data class PWOGeo(
        @JacksonXmlProperty(localName="latitude") val latitude: String? = "",
        @JacksonXmlProperty(localName="longitude") val longitude: String? = "")

data class PWOZone(
        @JacksonXmlProperty(localName="district") val district: String? = "",
        @JacksonXmlProperty(localName="neighborhood") val neighborhood: String? = "")

data class PWOMilestone(
        @JacksonXmlProperty(localName="title") val title: String? = "",
        @JacksonXmlProperty(localName="description") val description: String? = "",
        @JacksonXmlProperty(localName="dtstart") val dtstart: String? = "",
        @JacksonXmlProperty(localName="dtend") val dtend: String? = "",
        @JacksonXmlProperty(localName="affectations") val affectations: List<PWOAffectation>? = emptyList())

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PWOAffectation(
        @JacksonXmlProperty(localName="affectation") val type: String? = "",
        @JacksonXmlProperty(localName="title") val title: String? = "",
        @JacksonXmlProperty(localName="description") val description: String? = "")