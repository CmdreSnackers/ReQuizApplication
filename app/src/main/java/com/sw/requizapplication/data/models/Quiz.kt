package com.sw.requizapplication.data.models

data class Quiz(
    val id: String ="",
    val QuizId: String = "",
    val title: String = "",
    val csv: String = "",
    val finalAnswer: Int = 0,
    val total: Int = 0,
    val questionTitles: List<String>,
    val options: List<String>,
    val answers: List<String>,
    val creator: String = "",
    val time: Long
)
{
    fun toHashMap():HashMap<String, Any>
    {
        return hashMapOf(
            "id" to id,
            "QuizId" to QuizId,
            "title" to title,
            "csv" to csv,
            "finalAnswer" to finalAnswer,
            "totalQuestion" to total,
            "questionTitles" to questionTitles,
            "options" to options,
            "answers" to answers,
            "creator" to creator,
            "time" to time

        )
    }
    companion object
    {
        fun fromHashMap(hash: Map<String, Any>): Quiz
        {
            return Quiz(
                id = hash["id"].toString(),
                QuizId = hash["QuizId"].toString(),
                title = hash["title"].toString(),
                csv = hash["csv"].toString(),
                questionTitles = (hash["questionTitles"] as ArrayList<*>?)?.map
                {
                    it.toString()
                }?.toList() ?: emptyList(),
                options = (hash["options"] as ArrayList<*>?)?.map
                {
                    it.toString()
                }?.toList() ?: emptyList(),
                answers = (hash["answers"] as ArrayList<*>?)?.map
                {
                    it.toString()
                }?.toList() ?: emptyList(),
                creator = hash["creator"].toString(),
                time = hash["time"]?.toString()?.toLong() ?: -1
            )
        }
    }
}