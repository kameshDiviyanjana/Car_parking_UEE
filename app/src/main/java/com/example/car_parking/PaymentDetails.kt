package com.example.car_parking

data class PaymentDetails(
    val cardName: String, // Name on the card
    val cvv: String,      // Card CVV
    val cardNumber: String, // Card Number
    val expDate: String    // Expiry Date (you might want to use a Date type)
)
