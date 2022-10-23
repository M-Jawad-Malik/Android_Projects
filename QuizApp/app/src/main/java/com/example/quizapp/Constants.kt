package com.example.quizapp


object Constants{
    const val USER_NAME:String="username"
    const val TOTAL_Questions:String="total_questions"
    const val CORRECT_ANSWERS:String="correct_answer"
    fun getQuestions():ArrayList<Question>{
        val questionList=ArrayList<Question>()
        val que1=Question(1,"What Country does this flag belong to?",
        R.drawable.ic_flag_of_argentina,
            "Pakistan",
            "Armenia",
            "Australia",
            "Argentina",
            4)
        questionList.add(que1)
        val que2=Question(2,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_pakistan,
            "Argentina",
            "Pakistan",
            "Australia",
            "Syria",
            2)
        questionList.add(que2)
        val que3=Question(3,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_india,
            "Pakistan",
            "Armenia",
            "India",
            "Argentina",
            3)
        questionList.add(que3)
        val que4=Question(4,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_china,
            "USA",
            "Armenia",
            "Australia",
            "China",
            4)
        questionList.add(que4)
        val que5=Question(5,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            "Afganistan",
            "Armenia",
            "Australia",
            "Argentina",
            3)
        questionList.add(que5)
        val que6=Question(6,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_egypt,
            "India",
            "Egypt",
            "Australia",
            "Iran",
            2)
        questionList.add(que6)
        val que7=Question(7,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_turkey,
            "Iraq",
            "Iran",
            "Syria",
            "Turkey",
            4)
        questionList.add(que7)
        val que8=Question(8,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_usa,
            "Australia",
            "USA",
            "UK",
            "France",
            2)
        questionList.add(que8)
        val que9=Question(9,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_saudi_arabia,
            "Pakistan",
            "Iran",
            "Saudi Arabia",
            "Syria",
            3)
        questionList.add(que9)
        val que10=Question(10,"What Country does this flag belong to?",
            R.drawable.ic_flag_of_united_kingdom,
            "UK",
            "USA",
            "Autralia",
            "Argentina",
            1)
        questionList.add(que10)
        return questionList
    }
}