package com.wisnu.tesandroiddev_orsela.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "orsela_table")
@Parcelize
data class Orsela(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val address: String?,
    val city: String?,
    val zip : Int?
) : Parcelable