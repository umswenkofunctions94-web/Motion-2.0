package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.models.BookingRequest
import com.example.models.Creator
import com.example.models.Opportunity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class MotionViewModel : ViewModel() {

    private val _opportunities = MutableStateFlow<List<Opportunity>>(emptyList())
    val opportunities: StateFlow<List<Opportunity>> = _opportunities.asStateFlow()

    private val _creators = MutableStateFlow<List<Creator>>(emptyList())
    val creators: StateFlow<List<Creator>> = _creators.asStateFlow()

    private val _activeBookingRequest = MutableStateFlow<BookingRequest?>(null)
    val activeBookingRequest: StateFlow<BookingRequest?> = _activeBookingRequest.asStateFlow()

    private val _myProfile = MutableStateFlow<Creator?>(null)
    val myProfile: StateFlow<Creator?> = _myProfile.asStateFlow()

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        _opportunities.value = listOf(
            Opportunity(UUID.randomUUID().toString(), "DJ Needed", "R1,500", "Tonight, 8PM-1AM", true),
            Opportunity(UUID.randomUUID().toString(), "Photography Gig", "R2,000", "Tomorrow, 10AM-2PM", false),
            Opportunity(UUID.randomUUID().toString(), "Event Videographer", "R3,500", "Sat, 4PM-10PM", false),
            Opportunity(UUID.randomUUID().toString(), "Promoter Needed", "R800", "Tonight, 9PM-12AM", true)
        )

        _creators.value = listOf(
            Creator("c1", "DJ James", "DJ", 48, 4.9, 4, 100, 17),
            Creator("c2", "Sarah V.", "Photographer", 31, 4.8, 12, 98, 10),
            Creator("c3", "Mike Beats", "DJ", 112, 5.0, 2, 100, 45),
            Creator("c4", "Elena Shoots", "Videographer", 22, 4.7, 30, 95, 5)
        )

        _myProfile.value = _creators.value.first()
    }

    fun initiateBooking(creator: Creator) {
        _activeBookingRequest.value = BookingRequest(
            id = UUID.randomUUID().toString(),
            creatorId = creator.id,
            clientName = "Club Velocity",
            time = "Tonight, 8PM–1AM",
            offer = "R2,500"
        )
    }

    fun resolveBooking(accept: Boolean) {
        _activeBookingRequest.value = null
        // Real app would update backend and local state
    }
}
