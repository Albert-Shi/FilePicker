# FilePicker
此文件选择器属于Reader的一部分，由于觉得可以单独使用于是特别拿出来了
继承了AlertDialog.Builder类 因此 使用的时候需要使用create()方法和show()方法。 
已经包含了几个基础的文件图标 图标来自http://www.iconfont.cn/
文件夹点按后进入文件夹 文件点按后只是Log.v() 输出文件位置 没有返回具体的位置String
用的时候需要改为需要的动作