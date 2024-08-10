package com.example.diplomatest.model

import android.graphics.drawable.Drawable


class User {
    var userId: String? = null
    var username: String? = null
    var email: String? = null
    var phone: String? = null
    var imageUrl: String? = null
    constructor(){}

    constructor(
        userId:String?,
        username:String?,
        email:String?,
        phone:String?,
        imageUrl: String?
    ){
        this.userId = userId
        this.username = username
        this.email = email
        this.phone = phone
        this.imageUrl = imageUrl
    }
}