package alo.android.multitests.ui.json

import com.beust.klaxon.*
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.StringReader
import java.util.regex.Pattern
import kotlin.reflect.KClass

class JsonTest {
    
    @Test
    fun givenProduct_whenSerialize_thenGetJsonString() {
        val product = Product("HDD")
        val result = Klaxon().toJsonString(product)
        
        assertThat(result).isEqualTo("""{"name" : "HDD"}""")
    }
    
    @Test
    fun givenJsonString_whenDeserialize_thenGetProduct() {
        val result = Klaxon().parse<Product>("""{"name" : "RAM"}""")
        
        assertThat(result?.name).isEqualTo("RAM")
    }
    
    @Test
    fun givenCustomProduct_whenSerialize_thenGetJsonString() {
        val product = CustomProduct("HDD", 1)
        val result = Klaxon().toJsonString(product)
        
        assertThat(result).isEqualTo("""{"productName" : "HDD"}""")
    }
    
    @Test
    fun givenFlashCard_whenSerialized_thenGetJsonString() {
        val flashCard = arrayListOf(
                FlashCard("Vanguard Samurai?",
                          "Kensei",
                          listOf("video games",
                                 "fighting game",
                                 "ubisoft"),
                          mapOf("createDate" to "13/06/2020",
                                "author" to "Papa"))
        )
        
        val expectedJsonString = """[{"question" : "Vanguard Samurai?", "answer" : "Kensei", "tags" : ["video games", "fighting game", "ubisoft"], "properties" : {"createDate": "13/06/2020", "author": "Papa"}}]"""
        
        val result = Klaxon().toJsonString(flashCard)
        
        assertThat(result).isEqualTo(expectedJsonString)
    }
    
    @Test
    fun givenJsonArray_whenStreaming_thenGetFlashCardArray() {
        val jsonArray = """[
            {
                "question" : "Vanguard Samurai?",
                "answer" : "Kensei",
                "tags" : [ "video games", "fighting game", "ubisoft" ],
                "properties" : {
                    "createDate" : "13/06/2020",
                    "author" : "Papa"
                }
            }
        ]"""
        
        val expectedArray = arrayListOf(
                FlashCard("Vanguard Samurai?",
                          "Kensei",
                          listOf("video games",
                                 "fighting game",
                                 "ubisoft"),
                          mapOf("createDate" to "13/06/2020",
                                "author" to "Papa"))
        )
        
        val klaxon = Klaxon()
        val flashCardArray = arrayListOf<FlashCard>()
        
        JsonReader(StringReader(jsonArray)).use { reader ->
            reader.beginArray {
                while (reader.hasNext()) {
                    val product = klaxon.parse<FlashCard>(reader)
                    flashCardArray.add(product!!)
                }
            }
        }
        
        assertThat(flashCardArray).isEqualTo(expectedArray)
    }
    
    @Test
    fun givenJsonString_whenDeserialize_thenGetCustomProduct() {
        val result = Klaxon().parse<CustomProduct>("""{"productName" : "RAM", "id" : "1"}""")
        
        assertThat(result?.name).isEqualTo("RAM")
        assertThat(result?.id).isEqualTo(1)
    }
    
    @Test
    fun givenJsonArray_whenStreaming_thenGetProductArray() {
        val jsonArray = """[
            { "name" : "HDD", "capacityInGb" : 512 },
            { "name" : "RAM", "capacityInGb" : 16 }
        ]"""
        
        val expectedArray = arrayListOf(
                ProductData("HDD", 512),
                ProductData("RAM", 16)
        )
        
        val klaxon = Klaxon()
        val productArray = arrayListOf<ProductData>()
        
        JsonReader(StringReader(jsonArray)).use { reader ->
            reader.beginArray {
                while (reader.hasNext()) {
                    val product = klaxon.parse<ProductData>(reader)
                    productArray.add(product!!)
                }
            }
        }
        
        assertThat(productArray).isEqualTo(expectedArray)
    }
    
    @Test
    fun givenDiskInventory_whenRegexMatches_thenGetTypes() {
        val jsonString = """{
            "inventory" : {
                "disks" : [
                    {
                        "type" : "HDD",
                        "sizeInGb" : 1000
                    },
                    {
                        "type" : "SDD",
                        "sizeInGb" : 512
                    }
                ]
            }
        }"""
        
        val pathMatcher = object : PathMatcher {
            override fun pathMatches(path: String)
                    = Pattern.matches(".*inventory.*disks.*type.*", path)
            
            override fun onMatch(path: String, value: Any) {
                when (path) {
                    "$.inventory.disks[0].type"
                    -> assertThat(value).isEqualTo("HDD")
                    "$.inventory.disks[1].type"
                    -> assertThat(value).isEqualTo("SDD")
                }
            }
        }
        
        Klaxon().pathMatcher(pathMatcher)
                .parseJsonObject(StringReader(jsonString))
    }
    
    @Test
    fun givenJsonString_whenParser_thenGetJsonObject() {
        val jsonString = StringBuilder("""{
                "name" : "HDD",
                "capacityInGb" : 512,
                "sizeInInch" : 2.5
            }""")
        
        val parser = Parser.default()
        val json = parser.parse(jsonString) as JsonObject
        
        assertThat(json).hasSize(3)
        assertThat(json).containsEntry("name", "HDD")
        assertThat(json).containsEntry("capacityInGb", 512)
        assertThat(json).containsEntry("sizeInInch", 2.5)
    }
    
    @Test
    fun givenJsonStringArray_whenParser_thenGetJsonArray() {
        val jsonString = StringBuilder("""[
                { "name" : "SDD" },
                { "madeIn" : "Taiwan" },
                { "warrantyInYears" : 5 }
            ]""")
        
        val parser = Parser.default()
        
        @Suppress("UNCHECKED_CAST")
        val json = parser.parse(jsonString) as JsonArray<JsonObject>
        
        assertThat(json).hasSize(3)
        assertThat(json[0]["name"]).isEqualTo("SDD")
        assertThat(json[1]["madeIn"]).isEqualTo("Taiwan")
        assertThat(json[2]["warrantyInYears"]).isEqualTo(5)
    }
    
    /**
     * test from Klaxon:
     * url = "https://github.com/cbeust/klaxon/blob/master/klaxon/src/test/kotlin/com/beust/klaxon/Issue231Test.kt"
     */
    @Test
    fun issue231() {
        val json = """{
            "threshold": { "type": "Threshold", "value":0.4 },
            "monitoringTime" : {"type": "MonitoringTime", "value":4}
        }"""
        
        val r = Klaxon().parse<Info>(json)
        Truth.assertThat(r!!.threshold.value).isEqualTo(0.4)
        Truth.assertThat(r.monitoringTime.value).isEqualTo(4)
    }
    
    /**
     * test from Klaxon:
     * url = "https://github.com/cbeust/klaxon/blob/master/klaxon/src/test/kotlin/com/beust/klaxon/SpecialTypesTest.kt"
     */
    @Test
    fun map() {
        val o = Klaxon().parse<Map<String, Any>>("""
            {
               "bar": "def"
               "entity": {
                   "bar": "isBar"
               }
            }
        """)
        
        assertThat(o!!.keys.size).isEqualTo(2)
        assertThat(o["bar"]).isEqualTo("def")
        assertThat((o["entity"] as JsonObject)["bar"]).isEqualTo("isBar")
    }
}

class Product(val name: String)

class CustomProduct(
        @Json(name = "productName")
        val name: String,
        @Json(ignored = true)
        val id: Int = 1)

data class ProductData(val name: String, val capacityInGb: Int)

@TypeFor(field = "type", adapter = SettingValueAdapter::class)
open class SettingValue(val type: String)
data class MonitoringTime(val value: Int) : SettingValue("MonitoringTime")
data class Threshold(val value: Double) : SettingValue("Threshold")

data class Info(val threshold: Threshold, val monitoringTime: MonitoringTime)

class SettingValueAdapter : TypeAdapter<SettingValue> {
    override fun classFor(type: Any): KClass<out SettingValue> = when (type as String) {
        "MonitoringTime" -> MonitoringTime::class
        "Threshold" -> Threshold::class
        else -> throw IllegalArgumentException("Unknown type: $type")
    }
}

data class FlashCard (@Json(index = 1) var question : String,
                      @Json(index = 2) var answer : String,
                      @Json(index = 3) var tags : List<String>? = null,
                      @Json(index = 4) var properties : Map<String, String>? = null)
