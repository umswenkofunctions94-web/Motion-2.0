package com.example.models

data class Opportunity(
    val id: String,
    val role: String,
    val price: String,
    val time: String,
    val isUrgent: Boolean
)

data class Creator(
    val id: String,
    val name: String,
    val role: String,
    val completedBookings: Int,
    val rating: Double,
    val responseTimeMin: Int,
    val attendancePercent: Int,
    val repeatClients: Int,
    val isAvailableNow: Boolean = true
)

data class BookingRequest(
    val id: String,
    val creatorId: String,
    val clientName: String,
    val time: String,
    val offer: String,
    val isPending: Boolean = true
)
