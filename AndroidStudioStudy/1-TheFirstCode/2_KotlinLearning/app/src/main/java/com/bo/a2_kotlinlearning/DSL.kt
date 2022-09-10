package com.bo.a2_kotlinlearning


class Dependency{
    val libraries=ArrayList<String>()
    //libraries中添加依赖库

    fun implementation(lib:String){
        libraries.add(lib)
    }
}

fun dependencies(block: Dependency.()->Unit):List<String>{
    val dependency=Dependency()
    dependency.block()
    return dependency.libraries
}

fun main(){
    val libs=dependencies {
        implementation("com.example.de1")
        implementation("com.example.de2")
    }

    for (lib in libs)
        println(lib)
}