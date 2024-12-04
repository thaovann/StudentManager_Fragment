package com.example.studentman3
data class Student(val name: String, val id: String) {
    override fun toString(): String = "$name ($id)"
}
