# Upstream contribution plan

Submission completed: all implemented features are now represented in nine upstream PRs. See [UPSTREAM-SUBMISSIONS.md](UPSTREAM-SUBMISSIONS.md) for the complete verified list.

All implemented features remain together in features/device-compatibility-and-home, based on the same upstream master used by the clean master branch. A small upstream PR is a review slice, not deletion of the other work. Keep dependencies, plugin APIs and existing fallback options compatible when preparing each slice.

| Slice | Included behavior | Publication / remaining validation |
|---|---|---|
| Runtime image compatibility | One software retry for recognizable hardware bitmap errors; preserve known-device workaround | Draft [upstream PR3287](https://github.com/recloudstream/cloudstream/pull/3287); build, JVM tests, ABI and Lint pass; physical-device retry still pending |
| Shared provider-cache observer | One application-lifetime listener instead of one retained listener per repository | [Upstream PR3288](https://github.com/recloudstream/cloudstream/pull/3288); regression reproduced with 200 extra observers before fix, two tests pass after fix; build/ABI/Lint pass |
| Image options and placeholders | Explicit software option, image fallback for absent/failed artwork | Submitted in draft [PR3289](https://github.com/recloudstream/cloudstream/pull/3289); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Local-first playback | Prefer complete/readable downloaded files, preserve streaming, casting and forced retry; keep Skip loading capability | Submitted in draft [PR3290](https://github.com/recloudstream/cloudstream/pull/3290); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Availability and retry | Short cache for unavailable metadata; explicit retry invalidates response; distinguish unavailable from future release | Submitted in draft [PR3291](https://github.com/recloudstream/cloudstream/pull/3291); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Multiple home sources | Explicit multi-select and All compatible, single-source mode retained, partial rows and bounded queries | Submitted in draft [PR3292](https://github.com/recloudstream/cloudstream/pull/3292); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Responsive featured loading | Publish rows first; bounded metadata loading per item/batch | Submitted in draft [PR3292](https://github.com/recloudstream/cloudstream/pull/3292); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Recommendations | Up to12 from selected sources, six option, all/per-source filters, duplicate/current-title exclusion | Submitted in draft [PR3292](https://github.com/recloudstream/cloudstream/pull/3292); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Home presentation | Configurable normal-card scaling and five featured items; reuse background artwork if provided | Submitted in draft [PR3293](https://github.com/recloudstream/cloudstream/pull/3293); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| TV rail | Hide during poster browsing; reveal at leading edge or Menu; preserve player keys and disable option | Submitted in draft [PR3294](https://github.com/recloudstream/cloudstream/pull/3294); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Navigation restore | Home/root entry by default and optional restoration; deep links/external-player returns preserved | Submitted in draft [PR3294](https://github.com/recloudstream/cloudstream/pull/3294); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Manual local handoff | Explicit send of title/episode/progress, temporary code, receiver confirmation; no history-wide sync | Submitted in draft [PR3295](https://github.com/recloudstream/cloudstream/pull/3295); branch build/tests/ABI passed; physical checks and limitations disclosed. |
| Ratings | Preserve upstream optional/personal-rating behavior; no speculative defect claim | Original behavior restored; no standalone fix to submit without reproduction |

For each slice: branch from current upstream, include only relevant production code and regression tests, disclose AI assistance, state actual validation and limitations in English, run applicable ABI/build/test/Lint checks, and attach/update its PR. Do not present aggregate JVM tests from a larger branch as proof of a smaller patch's physical behavior.

Further optimization candidates are in PERFORMANCE-AUDIT.md. No blanket cache enlargement, buffer reduction, provider-link rewriting or invented metadata is justified without measurements and compatibility checks.
