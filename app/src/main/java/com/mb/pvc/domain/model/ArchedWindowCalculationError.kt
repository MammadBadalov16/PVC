package com.mb.pvc.domain.model



sealed class ArchedWindowCalculationError(message: String) : Throwable(message) {
    class InvalidHeight : ArchedWindowCalculationError("Hündürlük düzgün deyil")
    class ArchedWindowTooHigh : ArchedWindowCalculationError("Framuqanın hündürlüyü normadan artıqdır")
    class InvalidNumber : ArchedWindowCalculationError("Düzgün ədəd daxil edin")
}