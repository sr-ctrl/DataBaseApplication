package com.example.mydatabaseapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Register_users_table")
data class RegisterEntity(

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String,

    @ColumnInfo(name = "last_name")
    var lastName: String,

    @ColumnInfo(name = "user_name")
    var userName: String,

    @ColumnInfo(name = "password_text")
    var passwrd: String,

    @ColumnInfo(name = "email_id")
    var email_id: String,

    @ColumnInfo(name = "phone_number")
    var phone_number: String,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "birth_year")
    var birthYear: String,

    @ColumnInfo(name = "birth_month")
    var birthMonth: String,

    @ColumnInfo(name = "birth_day")
    var birthday: String

)