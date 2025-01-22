package io.tintoll.indra


import nethru.indra.shard.ShardRef
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.DocValuesType
import org.apache.lucene.store.FSDirectory
import java.nio.file.Path
import kotlin.math.min

fun checkDocs(shardRootPath: String, shard: ShardRef) {
    println("------------------------------ check index")
    val path = Path.of(
        shardRootPath,
        shard.table.tenantId,
        shard.table.id,
        shard.id,
    )
    println("- shard: $path")

    val dir = FSDirectory.open(path)
    val indexReader = DirectoryReader.open(dir)

    println("- number of docs: ${indexReader.numDocs()}")

    println("- stored fields")
    val storedFields = indexReader.storedFields()
    val doc = storedFields.document(0)
    for (fieldName in doc.fields.map { it.name() }.toSet()) {
        println(fieldName)
    }

    println("- doc values fields")
    val leafReader = indexReader.leaves().map { it.reader() }.first()
    val docValuesFieldNames = leafReader.fieldInfos.filter {
        it.docValuesType != DocValuesType.NONE
    }.map { it.name }
    for (fieldName in docValuesFieldNames) {
        println(fieldName)
    }

    println("- documents")
    for (docID in 0 until min(indexReader.maxDoc(), 5)) {
        val document = indexReader.storedFields().document(docID)
        println("Document ID: $docID")
        for (field in document.fields) {
            println("  Field ${field.name()} : ${field.binaryValue().utf8ToString()}")
        }
    }

    indexReader.close()
}
