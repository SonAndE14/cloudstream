# Loading and optimization audit

Reviewed 2026-10-28. The integration branch is based on upstream master 5024ace2922a148d38df2a816b483b529789cf58. This is a static and automated-test audit, not a physical-device benchmark.

## Video and streaming

Already present upstream: links and subtitles cached for twenty minutes; duplicate URL filtering; next-episode link preloading; playback can start when an acceptable-priority link arrives; a Skip loading button is exposed when usable links exist; Media3 buffering and optional disk cache are configurable.

The fork adds complete/readable local-download selection before remote extraction, retains casting and forced remote retry, and propagates coroutine cancellation rather than treating it as an ordinary provider failure. Provider metadata availability and extractor/server availability remain separate from video decoder performance.

Found in the fork: LocalFirstGenerator set canSkipLoading=false even when it delegated to RepoLinkGenerator, which supports skipping the remaining link load. This hid the existing button while usable remote links could already be available. Preserve the delegated capability; local-file generation already completes immediately. Corrected in the integration branch. This restores the original interaction, not an assertion that every provider starts automatically faster.

Follow-up candidates requiring dedicated tests: the process-wide link-cache map has no global size eviction; cache entries do not distinguish casting from in-app extraction; in-flight requests for the same episode can overlap. Do not change TTLs or reuse signed URLs more aggressively without checking token expiry, source types, cancellation, seeking and casting. Do not reduce Media3 buffers globally to claim a speedup: a smaller buffer can increase rebuffering on slow Wi-Fi.

## Images

Already present: one shared Coil loader, disk/network cache, a default network client reused by requests, disposal when a view is rebound and ImageView-sized requests. Existing Allwinner detection uses software decoding and keeps strong memory references disabled.

The fork adds selective software retry for recognizable hardware-bitmap failures, an explicit software setting and placeholders for absent/failed images. Missing provider artwork or HTTP errors do not trigger an unlimited hardware-retry loop.

Checked the actual Coil3.3.0 source. maxSizePercent resets the earlier maxSizeBytes value; its percentage result is still bounded by Coil's default minimum/maximum (10MiB and250MiB). Therefore the existing sequential 512MiB/4% builder calls do not create an unbounded multi-gigabyte cache. No unverified cache-size increase is justified. Source: https://github.com/coil-kt/coil/blob/3.3.0/coil-core/src/commonMain/kotlin/coil3/disk/DiskCache.kt

Strong memory references are currently disabled, so images can require decoding again after garbage collection even when downloaded bytes remain in disk cache. Enabling a small strong LRU may improve repeated browsing, but it trades CPU/I/O against heap use. Measure on low-memory hardware before changing this choice or claiming a frame-time improvement. Source: https://github.com/coil-kt/coil/blob/3.3.0/coil-core/src/commonMain/kotlin/coil3/memory/MemoryCache.kt

## Catalogue and recommendations

The fork emits partial catalogue results, permits three concurrent catalogue calls, serializes each source and bounds batch time. It publishes catalogue rows before fetching hero metadata; hero and recommendation requests have separate limits of two concurrent calls, eight seconds per call and eighteen seconds per batch. Image/request headers and provider identities are preserved.

The provider repository previously registered an event observer for every short-lived repository instance, capturing that instance for the application lifetime. One observer for the shared cache removes that accumulation. A dedicated upstream contribution should verify that repeated repository construction adds no observers and that forced plugin reload still invalidates cached metadata.

## Measurement still required

Measure cold/warm time to first catalogue row, first usable link, first video frame and first complete poster; scroll frame-time and heap/GC during repeated browsing; next-episode transition and casting. Use the same lawful test media, device, provider and network between variants. Report missing metadata, failed HTTP, extractor errors and video decoder errors separately. Do not publish credentials, signed links or personal viewing history with traces.

## Recorded validation

The complete integration branch passed 45 JVM tests, APK assembly, Kotlin ABI checking and Android Lint (with warnings). The separate image PR passed18 tests; the separate observer PR passed10 tests. For the observer patch, the new constant-observer-count test failed on the unmodified baseline and passed after the fix. No physical-device latency or scrolling benchmark has been claimed.
