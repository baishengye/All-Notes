`ConstraintLayout`的优点：

`ConstraintLayout`测量次数比较多，优势是减少布局的层级，减少过度绘制，提升帧数。

复杂界面的话，`ConstraintLayout`可以减少布局嵌套深度，简单的界面`ConstraintLayout`和其它布局都差不多。

但如果布局比较简单还是用原来的布局好点儿。

结论：布局嵌套不多的情况下，就算是使用`relativeLayout`布局也比`ConstraintLayout`性能高，`ConstraintLayout`实用对嵌套布局层次比较深的修改。