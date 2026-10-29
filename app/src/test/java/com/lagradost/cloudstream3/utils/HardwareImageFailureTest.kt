package com.lagradost.cloudstream3.utils

import java.io.IOException
import java.util.concurrent.CancellationException
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HardwareImageFailureTest {
    @Test fun recoversAllocationFailureWithoutModelDetection() {
        assertTrue(HardwareImageFailure.isRecoverable(IllegalStateException("failed to allocate hardware Bitmap!")))
    }

    @Test fun recoversWrappedSoftwareCanvasFailure() {
        assertTrue(HardwareImageFailure.isRecoverable(IOException("decode failed",
            IllegalArgumentException("Software rendering does not support hardware bitmaps"))))
    }

    @Test fun doesNotRetryHttpFailuresAsDecodeFailures() {
        assertFalse(HardwareImageFailure.isRecoverable(IOException("HTTP 403")))
    }

    @Test fun doesNotRetryOutOfMemoryEvenWhenWrappedByHardwareError() {
        assertFalse(HardwareImageFailure.isRecoverable(IllegalStateException("hardware bitmap",
            OutOfMemoryError("heap exhausted"))))
    }

    @Test fun doesNotRetryCancellation() {
        assertFalse(HardwareImageFailure.isRecoverable(CancellationException("hardware bitmap request cancelled")))
    }
}
