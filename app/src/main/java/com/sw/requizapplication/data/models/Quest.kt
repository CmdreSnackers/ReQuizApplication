package com.sw.requizapplication.data.models

data class Quest(
    val id: String = "",
    val quest:String = "",
    val ans1: String =  "",
    val ans2: String =  "",
    val ans3: String =  "",
    val ans4: String =  "",
    val finalAns: String =  "",
)
{
    fun toHashMap(): HashMap<String, Any>
    {
        return hashMapOf(
            "id" to id,
            "quest" to quest,
            "ans1" to ans1,
            "ans2" to ans2,
            "ans3" to ans3,
            "ans4" to ans4,
            "finalAns" to finalAns
        )
    }
}