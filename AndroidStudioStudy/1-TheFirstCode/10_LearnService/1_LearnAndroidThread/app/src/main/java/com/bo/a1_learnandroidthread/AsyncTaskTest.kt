package com.bo.a1_learnandroidthread

import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import java.lang.Exception

/*
class AsyncTaskTest() : AsyncTask<Unit, Int, Boolean>() {
    */
/*第一个参数是Unit说明不需要传入参数给后台任务
    第二个参数是Int说明进度单位是Int
    第三个参数是Boolean说明用布尔型数据反馈执行结果*//*

    override fun doInBackground(vararg p0: Unit?): Boolean =try{
        while (true){
            val down=doDown()//这是一个虚构的方法
            onProgressUpdate(down)
            if (down>=100){//下载进度到达100
                break
            }
        }
        true
        }catch (e:Exception){
            e.printStackTrace()
            false
        }

    override fun onPreExecute() {
        progressDialog.show()//显示进度条
    }

    override fun onPostExecute(result: Boolean?) {
        progressDialog.dismess()//关闭对话框
    }

    override fun onProgressUpdate(vararg values: Int?) {
        //在这里进行下载
        progressDialog.setMessage("Download ${values[0]}%")
        //在这里显示下载结果
        if(result){
            Toast.makeText(context,"下载成功",Toast.LENGTH_SHORT)
        }else{
            Toast.makeText(context,"下载失败",Toast.LENGTH_SHORT)
        }

    }
}*/
