package com.example.diplomatest.model

import androidx.activity.result.ActivityResultLauncher

class DataSource {
    var id: String? = null
    var name: String? = null
    var info: String? = null
    var img: String? = null
    var city: String? = null
    var commis_date: String? = null
    var description: String? = null
    constructor(){}

    constructor(
        id: String?,
        name:String?,
        info:String?,
        img: String,
        city:String?,
        commis_date:String?,
        description:String?
    ){
        this.id = id
        this.name = name
        this.info = info
        this.img = img
        this.city = city
        this.commis_date = commis_date
        this.description = description
    }
}