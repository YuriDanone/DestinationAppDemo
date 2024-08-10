package com.example.diplomatest.model

class WritingTest {
    var student: String? = null
    var studentId: String? = null
    var id: String? = null
    var part: String? = null
    var img1: String? = null
    var img2: String? = null
    var q1: String? = null
    var q2: String? = null
    var wordCount1: String? = null
    var wordCount2: String? = null
    var feedback1: String? = null
    var feedback2: String? = null


    constructor(){}

    constructor(
        student: String?,
        studentId: String?,
        id: String?,
        part: String?,
        img1: String,
        img2: String,
        q1: String?,
        q2: String?,
        wordCount1: String?,
        wordCount2: String?,
        feedback1: String?,
        feedback2: String?
    ){
        this.student = student
        this.studentId = studentId
        this.id = id
        this.part = part
        this.img1 = img1
        this.img2 = img2
        this.q1 = q1
        this.q2 = q2
        this.wordCount1 = wordCount1
        this.wordCount2 = wordCount2
        this.feedback1 = feedback1
        this.feedback2 = feedback2
    }
}