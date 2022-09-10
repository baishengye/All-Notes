package com.bo.a2_learnnetwork

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class ContentHandler :DefaultHandler(){
    private var nodeName=""
    private lateinit var id:StringBuilder
    private lateinit var name:StringBuilder
    private lateinit var version:StringBuilder

    override fun startDocument() {
        id=StringBuilder()
        name= StringBuilder()
        version= StringBuilder()
    }

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        //记录当前节点名
        if (localName != null) {
            nodeName=localName
        }
        Log.d("ContentHandler","uri is $uri")
        Log.d("ContentHandler","localName is $localName")
        Log.d("ContentHandler","qName is $qName")
        Log.d("ContentHandler","attributes is $attributes")
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        when(nodeName){
            "id"->id.append(ch,start,length)
            "name"->name.append(ch,start,length)
            "version"->version.append(ch,start,length)
        }
    }

    override fun endDocument() {
        super.endDocument()
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if("app"==localName){
            Log.d("ContentHandler","id is ${id.toString().trim()}")
            Log.d("ContentHandler","name is ${name.toString().trim()}")
            Log.d("ContentHandler","version is ${version.toString().trim()}")

            /*一个节点解析完了要把StringBuilder清空*/
            id.setLength(0)
            name.setLength(0)
            version.setLength(0)
        }
    }
}