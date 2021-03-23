package com.example.foodhub.models

import java.io.Serializable

class Product : Serializable {

    var id: Int = 0
    var name: String = ""
    var ratings: Int = 0
    var tags: String = ""
    var img_url: String = ""
    var url: String = ""

    override fun toString(): String {
        return "Product(id=$id, name='$name', ratings=$ratings, tags='$tags', img_url='$img_url', url='$url')"
    }

}