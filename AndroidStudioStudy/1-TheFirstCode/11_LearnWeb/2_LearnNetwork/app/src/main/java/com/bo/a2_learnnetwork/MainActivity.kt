package com.bo.a2_learnnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bo.a2_learnnetwork.databinding.ActivityMainBinding
import java.lang.Exception
import android.util.Log
import com.alibaba.fastjson.JSON.parseArray
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendRequestBtn.setOnClickListener {
            Toast.makeText(this,"点击了发送请求",Toast.LENGTH_SHORT).show()
            /*sendRequestWithHttpURLConnection()*/
            val address="http://www.baidu.com"
            var responseData:String?=null
            HttpUtil.sendOkHttpRequest(address, object : Callback {
                override fun onFailure(call: Call, e: IOException) {//其实这个方法还是在子线程中运行的
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    responseData=response.body?.string()
                    Log.d("MainActivity", responseData!!)
                }
            })
            /*HttpUtil.sendHttpRequest(address, object : HttpCallbackListener {
                override fun onFinish(response: String) {
                    responseData=response
                    Log.d("MainActivity", responseData!!)
                }

                override fun onError(e: Exception) {
                    e.printStackTrace()
                }
            })*/
        }
    }

    /*private fun sendRequestWithOkHttp(){
        thread {
            try {
                val client= OkHttpClient()
                val request=Request.Builder()
                    .url("http://10.0.2.2/get_data.json")
                    .build()
                val response=client.newCall(request).execute()
                val responseData=response.body?.string()
                if (responseData!=null)
                    parseJSONWithFastJson(responseData)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }*/

    private fun parseJSONWithFastJson(jsonData: String) {
        try {
            val apps= parseArray(jsonData,App::class.java)
            for (app in apps) {
                Log.d("MainActivity","id is ${app.id}")
                Log.d("MainActivity","name is ${app.name}")
                Log.d("MainActivity","version is ${app.version}")
                Log.d("MainActivity","=======================================")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

   /* private fun parseJSONWithJackson(jsonData: String) {
        val mapper = ObjectMapper()
        var apps: ArrayList<App>? = null
        try {
            val typeOf = mapper.typeFactory.constructParametricType(
                List::class.java,
                App::class.java
            )
            apps = mapper.readValue(jsonData,typeOf)
            if (apps != null) {
                for (app in apps) {
                    Log.d("MainActivity","id is ${app.id}")
                    Log.d("MainActivity","name is ${app.name}")
                    Log.d("MainActivity","version is ${app.version}")
                    Log.d("MainActivity","=======================================")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

    /*private fun parseJSONWithGSON(jsonData: String) {
        try{
            val gson= Gson()
            val typeOf=object:TypeToken<List<App>>(){}.type//利用TypeToken获取到期望解析成的数据的类型
            val apps=gson.fromJson<List<App>>(jsonData,typeOf)//把jsonData解析成期望类型
            for (i in apps.indices){
                Log.d("MainActivity","id is ${apps.getOrNull(i)?.id}")
                Log.d("MainActivity","name is ${apps.getOrNull(i)?.name}")
                Log.d("MainActivity","version is ${apps.getOrNull(i)?.version}")
                Log.d("MainActivity","=======================================")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }*/

    /*private fun parseJSONWithJSONObject(jsonData: String) {
        try {
            val jsonArray=JSONArray(jsonData)//获取的数据会根据{}分组,一个{}是一组数据(也就是一个jsonObject)
            for (i in 0 until jsonArray.length()){
                val jsonObject=jsonArray.getJSONObject(i)//获取到每个jsonObject
                val id=jsonObject.getString("id")//再根据键来获取值
                val name=jsonObject.getString("name")
                val version=jsonObject.getString("version")
                Log.d("MainActivity","id is $id")
                Log.d("MainActivity","name is $name")
                Log.d("MainActivity","version is $version")
                Log.d("MainActivity","=======================================")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }*/

    /*private fun parseXMLWithDom(xmlData: String) {
        val factory = DocumentBuilderFactory.newInstance()
        try {
            val builder = factory.newDocumentBuilder();
            val dom = builder.parse(InputSource(StringReader(xmlData)))
            val root = dom.documentElement;
            val items = root.getElementsByTagName("app");//查找所有app节点
            for (i in 0 until items.length) {//遍历所有的app节点
                val appNode = items.item(i) as Element
                val childNodes = appNode.childNodes//获得app节点的子节点
                for (j in 0 until childNodes.length) {//遍历app节点的子节点
                    val node =  childNodes.item(j)
                    if(node.nodeType == Node.ELEMENT_NODE){//判断这个节点是不是元素节点
                        val e = node as Element//是元素节点就把节点转型成元素
                        when(e.nodeName){//判断元素的名字
                            "id"->Log.d("MainActivity","id is ${e.firstChild.nodeValue}")//
                            "name"->Log.d("MainActivity","name is ${e.firstChild.nodeValue}")
                            "version"->Log.d("MainActivity","version is ${e.firstChild.nodeValue}")
                        }
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }*/

    /*private fun parseXMLWithSAX(xmlData: String) {
        try {
            val factory=SAXParserFactory.newInstance()
            val xmlReader=factory.newSAXParser().xmlReader
            val handler=ContentHandler()

            //将ContentHandler的实例设置到xmlReader中
            xmlReader.contentHandler=handler
            //开始解析
            xmlReader.parse(InputSource(StringReader(xmlData)))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }*/


    /*private fun showResponse(response:String){
        runOnUiThread{//这个函数是使用异步消息处理机制来对UI操作，这个函数自己已经封装了异步消息处理机制
            binding.responseText.text=response
        }
    }
    private fun parseXMLWithPull(xmlData: String) {//这里传过来的是xml中的所有数据
        try {
            val factory = XmlPullParserFactory.newInstance()//获取一个Xml抽取分割工厂的实例
            val xmlPullParser=factory.newPullParser()//通过Xml拉取分割工厂对象new一个Xml抽取解析器
            xmlPullParser.setInput(StringReader(xmlData))//StringReader把xml数据字符串转化成字符串流，然后设置到解析器中
            var eventType=xmlPullParser.eventType//获取解析事件
            var id=""
            var name=""
            var version=""
            while (eventType!=XmlPullParser.END_DOCUMENT){//只要解析实践还没有到数据文档的结束就继续解析
                val nodeName=xmlPullParser.name//获取到当前节点的名字
                when (eventType){//判断解析实践到了节点的哪个地方
                    //开始解析某个节点
                    XmlPullParser.START_TAG->{
                        when(nodeName){//判断一下
                            "id"->id=xmlPullParser.nextText()
                            "name"->name=xmlPullParser.nextText()
                            "version"->version=xmlPullParser.nextText()//xml抽取解析器用nextText()获得节点里的具体内容
                        }
                    }
                    //完成解析某个节点
                    XmlPullParser.END_TAG->{
                        if("app"==nodeName){//完成一个节点的解析就一下节点里的信息
                            Log.d("MainActivity","id is $id")
                            Log.d("MainActivity","name is $name")
                            Log.d("MainActivity","version is $version")
                            Log.d("MainActivity","===========================")
                        }
                    }
                }
                eventType=xmlPullParser.next()//一个解析事件完了就解析下一个解析时间
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }*/


    /*private fun sendRequestWithOkHttp(){
        thread {
            try {
                val client= OkHttpClient()
                val request=Request.Builder()
                    .url("https://www.baidu.com")
                    .build()
                val response=client.newCall(request).execute()
                val responseData=response.body?.string()
                if (responseData!=null)
                    showResponse(responseData)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }*/
    /*private fun sendRequestWithHttpURLConnection(){
        //开启线程发起网络请求
        thread {
            var connection:HttpURLConnection?=null
            try {
                var response=StringBuilder()//创建一个StringBuilder来存储返回的数据
                val url= URL("https://www.baidu.com")
                val connection=url.openConnection() as HttpURLConnection
                connection.connectTimeout=8000
                connection.readTimeout=8000
                val input = connection.inputStream

                //对获取到的输入流进行读取
                val reader=BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                connection?.disconnect()
            }
        }
    }*/
}