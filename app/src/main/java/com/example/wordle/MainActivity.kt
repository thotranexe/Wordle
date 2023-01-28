package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import com.example.wordle.FourLetterWordList.FourLetterWordList
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.core.text.color
class MainActivity : AppCompatActivity() {
    var wordToGuess = FourLetterWordList.getRandomFourLetterWord()

    private fun checkGuess(guess: String) : String {
        var result=""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) { //letter correct and correct spot
                result+="0"
            }
            else if (guess[i] in wordToGuess) { // correct spot
                result+="-"
            }
            else { // not in word
                result += "."
            }
        }
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val reset = findViewById<Button>(R.id.button2)
        reset.visibility=View.INVISIBLE
        //declare guesses
        var g1=findViewById<TextView>(R.id.guess1)
        var g2=findViewById<TextView>(R.id.guess2)
        var g3=findViewById<TextView>(R.id.guess3)
        val counter=findViewById<TextView>(R.id.counter)
        var num=3
        button.setOnClickListener {
            //get given text
            var guess = findViewById<EditText>(R.id.editText)
            var text =guess.text.toString().uppercase()
            //check
            var correctness=checkGuess(text)
            //turn into spannable
            val s = SpannableStringBuilder()
            for (i in 0..3){
                if (correctness[i]=='0'){
                    s.color(Color.GREEN,{append(text[i])})
                }
                if (correctness[i]=='-'){
                    s.color(Color.BLUE,{append(text[i])})
                }
                if (correctness[i]=='.'){
                    s.bold { append(text[i]) }
                }
            }
            //funtionality
            println("Guess:$text Correctnes:$correctness Word:$wordToGuess")
            if (text==wordToGuess){
                Toast.makeText(it.context,"Correct!",Toast.LENGTH_SHORT).show()
                //reset
                button.visibility= View.INVISIBLE
                reset.visibility=View.VISIBLE
                reset.setOnClickListener {
                    wordToGuess=FourLetterWordList.getRandomFourLetterWord()
                    num=3
                    g1.setText("")
                    g2.setText("")
                    g3.setText("")
                    reset.visibility=View.INVISIBLE
                    button.visibility=View.VISIBLE
                }
            }
            if(num==3){
                g1.setText(s, TextView.BufferType.SPANNABLE)
                num--
            }else if (num==2){
                g2.setText(s, TextView.BufferType.SPANNABLE)
                num--
            }else if (num==1){
                g3.setText(s, TextView.BufferType.SPANNABLE)
                num--
            }
            if(num==0){
                g3.append("\nThe word was: " + wordToGuess)
                Toast.makeText(it.context,"No more guesses left!",Toast.LENGTH_SHORT).show()
                button.visibility= View.INVISIBLE
                reset.visibility=View.VISIBLE
                reset.setOnClickListener {
                    wordToGuess=FourLetterWordList.getRandomFourLetterWord()
                    num=3
                    g1.setText("")
                    g2.setText("")
                    g3.setText("")
                    reset.visibility=View.INVISIBLE
                    button.visibility=View.VISIBLE
                }
            }
            counter.setText("Guesses Left: "+ num.toString())

        }
    }
}