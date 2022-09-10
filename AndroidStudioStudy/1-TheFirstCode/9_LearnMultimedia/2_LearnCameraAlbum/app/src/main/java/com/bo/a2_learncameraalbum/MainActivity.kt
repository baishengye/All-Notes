package com.bo.a2_learncameraalbum

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.bo.a2_learncameraalbum.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val takePhoto=1
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    val formAlbum=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //点击拍照触发事件，拍完照之后会在Activity上显示这张照片
        binding.takePhotoBtn.setOnClickListener {
            /*创建File对象，储存拍照后的照片，拍摄的照片存放在手机SD卡的应用关联缓存目录下
            应用关联缓存目录:SD卡中专门用于存放当前应用缓存数据的位置
            File(第一个参数,第二个参数)
            第一个参数:externalCacheDir-----具体的路径时/sdcard/Android/data/<package name>/cache
            第二个参数:文件名称
            这两个参数会被拼接成一个字符串路径，然后解析成内容Uri,存放在这个路径下
            为什么用关联缓存目录来存放图片?:
            Android6.0开始读写SD卡就成了危险权限,如果放在其他目录就会要申请权限，而再这个目录下就不需要
            Android10.0开始公有的SD卡目录已经不再能被应用程序直接访问了，而是要用作用域存储才行
            作用域存储详情要去gl大大的微信公众号看看*/
            outputImage= File(externalCacheDir,"output_image.jpg")

            //必须检查以免出错，检查一下这个文件存不存在，存在就替换掉
            if(outputImage.exists()){
                outputImage.delete()
            }
            outputImage.createNewFile()

            /*如果当前的Android版本低于7.0就可以直接调用Uri的fromFile()方法，把File对象转化成Uri对象
            此时的Uri对象对应的时这个文件的本地真实路径
            如果不低于的话就需要用FileProvider类的getUriForFile(第一个参数,第二个参数,第三个参数)方法将一个File对象转化成封装过的Uri对象
                    第一个参数:context上下文
                    第二个参数：任意一个字符串，但是还是用项目的authority最好(保持唯一性)
                    第三个参数:需要转化的File对象*/
            /*那为什么要这样if-else呢?
                因为从Android7.0开始直接使用真实本地地址的Uri被认为是不安全的
                会抛出一个FileUriExposedException异常
                而FileProvider是一种特殊的ContentProvider，它使用和ContentProvider相似的机制来保护数据*/
            imageUri=if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                FileProvider.getUriForFile(this,"com.bo.a2_learncameraalbum.fileprovider",outputImage)
            }else{
                Uri.fromFile(outputImage)
            }
            //启动相机程序(隐式intent)
            //启动相机的动作是"android.media.action.IMAGE_CAPTURE"
            val intent=Intent("android.media.action.IMAGE_CAPTURE")
            //通过intent的附加信息来指定相机拍摄的照片输出(MediaStore.EXTRA_OUTPUT)的位置(imageUri)
            //位置是一个Uri对象来制定的
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
            //开启一个活动(这个方法开启的活动运行完了之后会回调onActivityResult(),结果返回到onActivityResult()的data中)
            startActivityForResult(intent,takePhoto)
        }

        binding.fromAlbumBtn.setOnClickListener {
            //打开文件管理器
            val intent=Intent(Intent.ACTION_OPEN_DOCUMENT)//指定意图去打开文件
            intent.addCategory(Intent.CATEGORY_OPENABLE)//过滤:只打开能打开的文件

            //指定显示图片
            intent.type="image/*"//只能显示image目录下的图片
            startActivityForResult(intent,formAlbum)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            takePhoto->{
                if(resultCode== Activity.RESULT_OK){
                    //如果运行的结果是成功的话就显示一下图片
                    /*还记得吗？现在的Android要跨程序获取数据就要借助contentResolver类
                    这里通过contentResolver开辟一个文件输入流，通过路径找到文件读入
                    再然后通过位图工厂把字节流形式的数据转化成一个位图*/
                    val bitmap=BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    binding.imageView.setImageBitmap(rotateRequired(bitmap))
                }
            }
            formAlbum->{
                if(resultCode==Activity.RESULT_OK&&data!=null){
                    data?.data.let { uri->
                        val bitmap= uri?.let { getBitmapFromUri(it) }
                        binding.imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        //通过contentResolver用只读的方式加载文件
        return contentResolver.openFileDescriptor(uri,"r")?.use{
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
                //然后把文件转成位图
            //use可以在使用完这个文件之后自动关上
        }
    }

    //手机拍摄的时候图片可能拍成歪的，这个函数就是把他扶正
    private fun rotateRequired(bitmap: Bitmap): Bitmap {
        val exif=ExifInterface(outputImage.path)
        val organization=exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL)
        return when(organization){
            ExifInterface.ORIENTATION_ROTATE_90->rotateBitmap(bitmap,90)
            ExifInterface.ORIENTATION_ROTATE_180->rotateBitmap(bitmap,180)
            ExifInterface.ORIENTATION_ROTATE_270->rotateBitmap(bitmap,270)
            else->bitmap
        }
    }
    //具体扶正的逻辑
    private fun rotateBitmap(bitmap: Bitmap,degree:Int):Bitmap{
        val matrix=Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
        bitmap.recycle()//将不需要的Bitmap对象回收
        return rotatedBitmap
    }
}