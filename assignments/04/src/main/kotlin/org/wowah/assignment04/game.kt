package org.wowah.assignment04

import java.util.Random

fun playerTurn(length: Int): String {
    val a = readLine()
    if (a == null || length != a.length) {
        println("Sorry. You enter incorrect word. Word length must be " + length.toString() + ". Please, try again")
        return ""
    }
    var isAlpha = true
    a.forEach { isAlpha = isAlpha && it.isLetter() }
    if (!isAlpha) {
        println("Sorry. You enter incorrect word. A word can contain only letters. Please, try again")
        return ""
    }
    return a.toString()
}

val wordList: MutableList<String> = mutableListOf()

fun getRandomWordFromDictionary(): String {
    if (!wordList.isNotEmpty()) {
        val input = object {}.javaClass.getResource("/dictionary.txt").readText().split("\r\n")
        for (x in input) {
            wordList.add(x)
        }
    }
    return wordList[Random().nextInt(wordList.size)]
}

fun isContinuePlay(): Boolean {
    println("Wanna play again? y/n")
    var answer = readLine()
    while (answer == null || (answer.toLowerCase() != "y" && answer.toLowerCase() != "n")) {
        println("Excuse me. I do not understand. Can you repeat?")
        answer = readLine()
    }
    if (answer.toLowerCase() == "n") {
        return false
    }
    return true
}

fun game() {
    println("Hello! Welcome to Bulls and Cows game!")
    println("You have only 10 attempts to guess the word that I made.")
    println("Good luck!")
    var isContinue = true
    while (isContinue) {
        val myString = getRandomWordFromDictionary().toLowerCase()
        println("My words consist of " + myString.length.toString() + " letters")
        for (i in 1..10) {
            var playerString = ""
            while (playerString == "") {
                playerString = playerTurn(myString.length)
            }
            playerString = playerString.toLowerCase()
            if (playerString == myString) {
                println("Congratulations! You won!")
                isContinue = isContinuePlay()
                break
            }
            val bulls = playerString.foldIndexed(0) { index, acc, c ->
                if (myString[index] == c) acc + 1 else acc
            }
            val cows = playerString.foldIndexed(0) { index, acc, c ->
                if (myString.contains(c) && myString[index] != c) acc + 1 else acc
            }
            println("Bulls: " + bulls.toString())
            println("Cows: " + cows.toString())
            if (i != 10) {
                println((10 - i).toString() + " attempts left")
            } else {
                println("Sorry. You lose:(. My word: $myString")
                isContinue = isContinuePlay()
            }
        }
    }
    println("Goodbye!")
}

fun main(args: Array<String>) {
    game()
}