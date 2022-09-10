## **创建版本库**

* 工作区：文件夹
* 暂存区：`add`后的内容
* 版本库：`commit`后的内容

![](https://pic3.zhimg.com/80/v2-701e02bf0b2d71ab51b7e2b6aa973d52_1440w.jpg)


### **创建仓库**

在某个目录下创建Git仓库

```git
 git init
```

### **添加修改**

将工作区的内容添加到**暂存区**
```git
 git add <fileName>
```

### **提交修改**
将暂存区的内容提交到当前分支
```git
 git commit <fileName> -m'描述信息'
```

### **追加提交**
**含义：**将这次的提交和上次的提交合并，只保留一次`commit`记录（`commit`信息可以自己编辑）

**应用场景：**进行一次提交后，发现还有内容需要修改，但又不想多一次无用的`commit`信息
```git
 git commit <fileName> --amend
```
此时会进行`vim`编辑界面，可以修改上一次的`commit message`。

### **操作提交记录**
**含义：**对过去的若干个`commit`进行修改，可以进行的操作包括：

- 将多个`commit`合并
- 修改`commit`信息
- 删除某个更改
```git
 git rebase -i <起始版本> [终点版本]
```

是一个**前开后闭区间**，即起始版本是不显示在操作界面的。终点版本缺省则表示`HEAD`。

执行命令后会进行交互式界面，可以对每个`commit`做修改（修改这次提交前对应的字母），注释中列出了可用的操作方法：

- `p`：保留这次提交
- `r`：保留这次提交，但编辑它的`commit`信息
- `s`：保留提交，但和上一次提交合并（只显示上一次的提交信息）。提交列表中的第一条不能向前合并
- `d`：丢弃这次提交

```git
 pick 171d248 update:第二次提交
 pick 82ac586 update:第三次提交
 ​

# Rebase 6feb8b0..171d248 onto 6feb8b0 (1 command)

#

# Commands

# p, pick <commit> = use commit

# r, reword <commit> = use commit, but edit the commit message

# e, edit <commit> = use commit, but stop for amending

# s, squash <commit> = use commit, but meld into previous commit

# f, fixup <commit> = like "squash", but discard this commit's log message

# x, exec <command> = run command (the rest of the line) using shell

# b, break = stop here (continue rebase later with 'git rebase --continue')

# d, drop <commit> = remove commit

# l, label <label> = label current HEAD with a name

# t, reset <label> = reset HEAD to a label
```

`注：vim的基本操作`
- 进入交互界面时按i进入编辑模式。编辑结束后按Esc键结束编辑
- 输入:wq保存并退出

### **`Commit Message`规范**
此部分的参考
- 阮一峰网络日志
- 阿里技术知乎专栏
严格来讲，提交描述信息分为三部分：`Header、Body、Footer`。不过一般来讲一个清晰的Header足矣。

`Header`的格式如下：
```git
<Type>([scope]): <subject>
```

#### **`Type`**

描述更改的**类型**，必须是以下几种：

- `feat`：新功能（`feature`）
- `fix`：修`bug`
- `docs`：文档
- `style`：格式（不影响代码运行结果）
- `refactor`：重构（不是修bug也不是新功能）
- `test`：增加测试
- `chore`：构建过程或辅助工具的变动
- `merge`：代码合并
- `revert`：回滚版本
- `build`: 影响构建系统或外部依赖项的更改

#### **`scope`**

描述修改的影响范围，比如数据层、控制层、视图层等等，视项目不同而不同

#### **`subject`**

描述修改的**目的**

## **版本管理**

### **查看仓库状态**
```git
 git status
```
用于查看：

- 有哪些文件修改了还没有`add`
- 有哪些文件已经`add`，待提交`commit`
- 哪些文件是还没有被`add`的，也就是`Untracked files`
- 若所有修改都已提交，则会显示`nothing to commit, working tree clean`

### **查看修改内容**
```git
 git diff
```
- 当内容进行了修改，还未`add`时，会输出此次修改的内容
- 如果仓库内没有修改但未`add`的内容，则输出为空

### **查看提交历史**
```git
 git log [--pretty=oneline]
```
会输出历史的所有提交，包括：

- **唯一的十六进制版本号**
- **`commit message`**
- 作者
- 提交日期

### **回溯版本**

#### **回溯到某个特定版本**
```git
 git reset --hard <版本号>
```
- `HEAD`表示当前版本，`HEAD^`表示上一个版本，依次类推，用^表示前一个版本
- `HEAD~4`表示往上4个版本，等价于`HEAD^^^^`
- 版本号也可以是某次特定提交的版本号，则跳转到该版本。该方法可以向前或向后跳转

### **查看历史操作**
```git
 git reflog
```
如果使用`HEAD^`回退到了上一版本B，同时又没有记录下原本的版本号A，此时就无法直接通过版本号跳回版本A

使用`git reflog`可以查看历史操作的所有命令，也就可以找到每次提交的版本号
```git
 275d57a (HEAD -> master) HEAD@{0}: reset: moving to 275d57afc3dafa331e3af
 62fc97d HEAD@{1}: reset: moving to HEAD^
 275d57a (HEAD -> master) HEAD@{2}: commit: change content
 62fc97d HEAD@{3}: commit (initial): init
```
### **丢弃工作区的修改**
```git
 git checkout -- <fileName>
```
- 若文件的修改还没有提交到暂存区，则恢复到版本库中的状态
- 若文件的修改提交到暂存区后，又做了修改，则恢复暂存区中的状态

也就是，恢复到上一次`commit`后或`add`后的状态

### **丢弃暂存区的修改**
#### **不丢弃工作区**
```git
 git reset HEAD [fileName]
```
撤销已经`add`但还未`commit`的内容：

- 文件内容不会改变（工作区不变）
- 暂存区中丢弃已经`add`的内容，该部分成为`Unstaged changes`

#### **同时丢弃工作区**
```git
 git reset --hard HEAD
```
`HEAD`表示版本库中的当前版本，重置为当前版本即丢弃所有未提交到版本库的内容，包括工作区和暂存区

## **分支管理**
### **查看分支**
```git
 git branch
```
### **创建分支**
```git
 git branch <分支名>
```
### **删除分支**
```git
 git branch -d <分支名>
```
### **切换分支**
```
 git checkout [-b] <分支名>
```
- 不带参数时，表示切换分支
- 带了`-b`参数时，表示创建新分支并切换到该分支，等价于：
```git
 git branch <分支名>
 git checkout <分支名>
```
为避免和撤销修改的`checkout`命令产生歧义，`Git`提供了`switch`命令：

#### **切换分支：**
```
 git switch <分支名>
```
#### **创建并切换：**
```
 git switch -c <分支名>
```

### **合并某分支到当前分支**
```git
 git merge <另一分支名>
```
在`merge`之前如果修改了本分支的内容，必须先将修改`commit`，否则会报错

### **快速合并（` Fast-forward`）**
当前分支的内容和另一分支没有产生冲突，则会进行快速合并。

如果希望保留分支合并的痕迹，则需要禁用快速合并，这样会在合并时产生一个新的`commit`：
```git
 git merge --no-ff -m "commit信息" <分支名>
```
此次合并会创建一个新的`commit`，因此需要用`-m`参数传入`commit message`

### **解决合并冲突**

合并到当前分支会产生冲突的情况，需要自己手动解决冲突（冲突内容是保留本分支内容还是另一分支）。

如，`dev`分支内容为`123`，切换到`master`分支后，内容修改为`456`并`commit`，此时`git merge dev`

文件内容会变为：
```git
<<<<<<< HEAD
 456
 =======

 123
 >>>>>>> dev
```
- `<<<<<<<`表示当前分支
- `=======`用于分隔两个分支的内容
- `>>>>>>`表示合并进来的分支
手动处理冲突后（修改文本或是用编辑器的快捷处理），提交一个`commit`，至此分支合并完成。

### **分支管理思路**
- `master`分支：应该是稳定的，只用来发布版本，不用于开发
- `dev`分支：用于开发，代码修改汇总到该分支，并在发布版本时将`dev`合并到`master`
- 个人分支：每个开发者应有自己的分支，完成自己一定的任务后，往`dev`分支进行合并

### **Bug分支**
#### **现场保存**
当一个分支中的任务进行到一半时需要切换分支，可以临时保存工作区的修改：
```git
 git stash
```
此时工作区的修改会被清空，可以进行分支切换。

#### **查看保留的现场列表：**
```git
 git stash list
```
当切回该分支，需要还原之前保留的现场时：

- 切回上一个现场（并删除保存记录）
 `git stash pop`
- 切回某个特定现场（不会删除记录）
 `git stash apply <记录编号>`

注意：使用`vscode`带的命令行或`Windows PowerShell` 时可能会报一个`error unknown switch` \`e‘的错误，原因是花括号在`PowerShell` 中被认为是代码块执行标识符。可以用\`对花括号进行转义。

### **复制特定提交到当前分支**
含义：将一次提交的修改内容，而不是整个分支，合并到当前分支
```git
 git cherry-pick <commit版本号>
```
如果产生合并冲突，处理方式和分支合并时相同

### **变基**
假设有两人A和B在对同一分支进行修改：

1. `A、B`分别拉取`init`状态的仓库内容
2. `B`首先完成了修改`change1`、`change2`，并`push`到远程仓库
3. `A`完成修改`change3`，`push`时会报错，因为已有他人对仓库进行了推送
4. 此时`A`应先执行`git pull`，解决冲突后再提交一个`commit`

此时，提交历史如图：
  ![](https://pic3.zhimg.com/80/v2-317a18d8829bfbf03f6b90eb70c44f12_1440w.jpg)
注意：此时的最新提交`Merge branch...`实际和`change3`的内容一样，因为

此时如直接`push`，不会影响使用，但提交历史分叉了，影响美观。

此时执行变基：
```git
 git rebase
```
得到的结果：
![](https://pic3.zhimg.com/80/v2-e8ae6672f906fd018e9fe2999b52b71a_1440w.jpg)
即：将基于`init`状态而来的`change3`，改为基于`change2`而来，提交历史也就成为了一条直线

## **远程仓库**
### **添加仓库**
```git
 git reomte add <仓库名称> <URL>
```
仓库名称可以自定义，只是通常取为`origin`

### **查看远程仓库情况**
```git
 git remote -v
```

### **解除关联仓库**
```git
 git remote rm <仓库名称>
```
只是解除了关联，远程仓库本身并没有改动

### **将本地内容推送至仓库**
#### **主分支推送**
##### **首次：**
```git
 git push -u <仓库名称> master
```
参数`-u`会将本地的`master`分支和远程的`master`分支关联起来。后续可以简化命令。

##### **后续：**
```git
 git push
```

### **本地创建新的分支并推送到远程仓库**

1. 首先，创建并切换至新分支`dev`
```git
 git checkout -b dev
```
1. 进行修改，并`commit`
2. 和远程仓库的分支进行绑定并提交
```git
 git push --set-upstream origin dev
```

### **克隆远程仓库**
```git
 git clone <URL>
```
只会拉取默认的`master`分支。

若需要拉取远程仓库的其他分支，则需要在本地创建对应的分支，并将其和远程分支关联：
```git
 git checkout -b <分支名> origin/<分支名>
```

## **标签管理**

**标签的意义：**是版本库的快照，指向某一次commit的指针。

**和版本号的区别：**版本号不便于分辨

### **创建标签**
```git
 git tag <标签名> [版本号]
```
- 省略版本号时，默认在最新一次提交上创建标签
- 提供版本号时，则在特定的提交上创建

### **查看标签**
#### **查看所有标签：**
```git
 git tag
```
不是按时间顺序列出，而是按字母排序 。

#### **查看标签信息（也就是那次提交的信息）**：
```git
 git show <标签名>
```

### **推送标签**
创建的标签默认只存在本地，要提交到仓库可以执行：
```git
 git push origin <标签名>
```
或提交所有标签：
```git
 git push origin --tags
```

### **删除标签**
#### **删除本地标签：**
```git
 git tag -d <标签名>
```
#### **删除远程仓库标签：**

- 首先删除本地标签
- 用push提交删除的修改
```git
 git push origin :refs/tags/<标签名>
```

## **忽略特殊文件**
在仓库根目录下创建`.gitignore`文件，说明某些文件不会进行跟踪。

- 具体文件名：直接写文件名或目录名，
- 某一类文件名：
    - `*.class`：排除所有`.class`文件
    - `.*`：排除所有`.`开头的文件

- 排除特殊文件（如忽略所有.开头的文件，但不忽略`.gitignore`）：`!.gitignore`

gitignore的配置推荐：[github](https://github.com/github/gitig)
