### `assets	res/raw	res/drawable`
|获取资源方式|	路径+文件名	R.raw.xxx|	R.drawable.xxx|
|:---:|:---:|:---:|
|是否被压缩|	NO|	NO|	YES(失真压缩)|
|能否获取子目录资源|	YES	|NO|	NO|

### `raw`与`assets`比较
- **相同点**
    - 两者目录下的文件在打包后会原封不动的保存在`apk`中，不会被编译成二进制。
- **不同点**
    - `raw`: Android会自动为目录所有制源文件生成`一个ID`。使用`ID`访问速度快
    - `assets`:不会生成`ID`，只能通过`AssetManager`访问，`xml`中不能访问，访问速度慢，操作方便。目录下可以再建文件夹。

### 读取文件资源

```java
res/raw
InputStream is = getResource().openRawResource(R.id.filename);


assets
private Bitmap getImageFromAssetsFile(String filename) {
        Bitmap bitmap = null;
        AssetManager manager = getResources().getAssets();
        try {
            InputStream is = manager.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
```