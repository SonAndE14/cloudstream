# Upstream contribution plan

All implemented features remain together in features/device-compatibility-and-home, based on the same upstream master used by the clean master branch. A small upstream PR is a review slice, not deletion of the other work. Keep dependencies, plugin APIs and existing fallback options compatible when preparing each slice.

| Slice | Included behavior | Publication / remaining validation |
|---|---|---|
| Runtime image compatibility | One software retry for recognizable hardware bitmap errors; preserve known-device workaround | Draft [upstream PR3287](https://github.com/recloudstream/cloudstream/pull/3287); build, JVM tests, ABI and Lint pass; physical-device retry still pending |
| Shared provider-cache observer | One application-lifetime listener instead of one retained listener per repository | [Upstream PR3288](https://github.com/recloudstream/cloudstream/pull/3288); regression reproduced with 200 extra observers before fix, two tests pass after fix; build/ABI/Lint pass |
| Image options and placeholders | Explicit software option, image fallback for absent/failed artwork | Fork implementation; separate UI slice; preserve missing-provider-metadata semantics |
| Local-first playback | Prefer complete/readable downloaded files, preserve streaming, casting and forced retry; keep Skip loading capability | Fork implementation and completeness tests; physical local/remote episode transition pending |
| Availability and retry | Short cache for unavailable metadata; explicit retry invalidates response; distinguish unavailable from future release | Fork implementation and cache-policy tests; real transient-provider scenarios pending |
| Multiple home sources | Explicit multi-select and All compatible, single-source mode retained, partial rows and bounded queries | Fork implementation and concurrency/order/cancellation tests; related request300; pagination and D-pad QA pending |
| Responsive featured loading | Publish rows first; bounded metadata loading per item/batch | Fork implementation; cold/warm latency measurement still required |
| Recommendations | Up to12 from selected sources, six option, all/per-source filters, duplicate/current-title exclusion | Fork implementation and selection tests; depends on source-selection slice; physical navigation pending |
| Home presentation | Configurable normal-card scaling and five featured items; reuse background artwork if provided | Fork implementation; hero dimensions unchanged; source-quality limitations respected; visual QA pending |
| TV rail | Hide during poster browsing; reveal at leading edge or Menu; preserve player keys and disable option | Fork implementation and edge-policy tests; real remote/focus/RTL QA pending |
| Navigation restore | Home/root entry by default and optional restoration; deep links/external-player returns preserved | Fork implementation; activity lifecycle/deep-link QA pending |
| Manual local handoff | Explicit send of title/episode/progress, temporary code, receiver confirmation; no history-wide sync | Experimental fork implementation and transport/payload tests; two Android devices and protocol/security review required before a ready-for-merge upstream proposal |
| Ratings | Preserve upstream optional/personal-rating behavior; no speculative defect claim | Original behavior restored; no standalone fix to submit without reproduction |

For each slice: branch from current upstream, include only relevant production code and regression tests, disclose AI assistance, state actual validation and limitations in English, run applicable ABI/build/test/Lint checks, and attach/update its PR. Do not present aggregate JVM tests from a larger branch as proof of a smaller patch's physical behavior.

Further optimization candidates are in PERFORMANCE-AUDIT.md. No blanket cache enlargement, buffer reduction, provider-link rewriting or invented metadata is justified without measurements and compatibility checks.
