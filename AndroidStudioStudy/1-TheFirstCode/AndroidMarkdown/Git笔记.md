---
title: Git使用
author: Bob
top: true
toc: true
mathjax: true
categories: 工具
tags:
  - Git
abbrlink: 
summary: Git的使用
date: 2022-02-12 00:00:00
---


```git
git config --global user.name "Tony" 设定用户名

git config --global user.email "tony@gmail.com"  设定邮箱

git init  初始化git仓库

ls -al 命令查看当前文件夹下所有的文件和文件夹

git add .  把想要提交的文件加到更改里面

git commit .  -m  "Msg"   提交到本地仓库  -m后面是本次提交的信息

.gitignore文件     配置忽略文件

git status     查看有哪些文件被修改过了

git diff  A.kt   查看A.kt这个文件被修改了什么

git checkout A.kt   撤销A.kt中修改了但是还未提交的内容(add 过了就不能checkout了)

git reset A.kt   已经add过了的A.kt中的修改(commit 过了的不能reset)，可以撤回添加，然后再checkout撤销修改

git log 查看历史提交信息

git log id   可以查看指定id 的 记录

git log -1   查看最后一次的提交记录


分支的用法
在已有的代码基础上开辟一个分岔口，使得代码可以在主干线和分支线上同时进行开发(多用于版本控制)

git branch version1.0 在已有分支上开辟一个version1.0的分支

git checkout version1.0  从main分支切换到version1.0分支上

git merge version1.0  把version1.0分支合并到main分支上

git branch -D version1.0 不需要version1.0这个分支的时候把version1.0分支删除

git clone http://github.com/example/test.git  把远程库克隆到本地

git push origin master  把本地代码同步到master分支上

git fetch origin master  把远程库上master分支上的修改同步到本地

git diff origin/master   查看远程库中的修改

git pull origin master   相当于把fetch和merge这两个命令一起执行，它可以从远程版本库上获取最新的代码并且合并到本地
```