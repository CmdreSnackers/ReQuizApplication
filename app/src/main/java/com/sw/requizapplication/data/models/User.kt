package com.sw.requizapplication.data.models

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val profPic: String? = "",
    val role: String
)
{
    fun toHashMap(): HashMap<String, String?>
    {
        return hashMapOf(
            "name" to name,
            "email" to email,
            "profPic" to profPic,
            "role" to role
        )
    }
    companion object
    {
        fun fromHashMap(hash: Map<String, Any>): User
        {
            return User(
                id = hash["id"].toString(),
                name = hash["name"].toString(),
                email = hash["email"].toString(),
                profPic = hash["profPic"].toString(),
                role = hash["role"].toString()
            )
        }


    }
}