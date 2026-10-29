# Submitted contributions

All implemented changes have been submitted to recloudstream/cloudstream. This records submission, not approval or completion of the physical-device checks. No further implemented feature is being held back from submission.

| Scope | Pull request | Local JVM tests | Publication |
|---|---|---|---|
| Runtime hardware bitmap recovery | [#3287](https://github.com/recloudstream/cloudstream/pull/3287) | 18 | Draft, limitations disclosed |
| Shared provider-cache observer lifetime | [#3288](https://github.com/recloudstream/cloudstream/pull/3288) | 10 | Open for review |
| feat: add software image decoding option and poster fallbacks | [#3289](https://github.com/recloudstream/cloudstream/pull/3289) | 8 | Draft, limitations disclosed |
| feat: prefer complete local downloads before streaming extraction | [#3290](https://github.com/recloudstream/cloudstream/pull/3290) | 12 | Draft, limitations disclosed |
| fix: refresh transient availability metadata on explicit retry | [#3291](https://github.com/recloudstream/cloudstream/pull/3291) | 10 | Draft, limitations disclosed |
| feat: browse selected home sources with bounded recommendations | [#3292](https://github.com/recloudstream/cloudstream/pull/3292) | 15 | Draft, limitations disclosed |
| feat: configure home card scale and featured title count | [#3293](https://github.com/recloudstream/cloudstream/pull/3293) | 8 | Draft, limitations disclosed |
| feat: add optional TV rail hiding and navigation restoration | [#3294](https://github.com/recloudstream/cloudstream/pull/3294) | 10 | Draft, limitations disclosed |
| feat: prototype manual local title and progress handoff | [#3295](https://github.com/recloudstream/cloudstream/pull/3295) | 18 | Draft, limitations disclosed |

Every branch passed its local JVM tests, APK assembly and library ABI check before publication. The image-options branch also passed local Lint. The other new slices leave Lint to their GitHub workflow; the complete integration branch had previously passed Lint. No result is represented as physical-device testing.

All PR descriptions are in English and disclose OpenAI Codex assistance. Known-device software decoding, provider identity, backwards-compatible options and existing rating semantics are preserved. The launcher is excluded.

The implemented Kotlin/manifest/layout/settings files and new option resource IDs were checked against the submitted branches: no missing feature files or resource IDs. The runtime-retry and explicit-software preference interceptors were deliberately separated for independent review. See submission-coverage.json.

The full integration remains in features/device-compatibility-and-home. The clean master branch remains a mirror of upstream. PRs are left open; the owner requested completing submission without waiting for acceptance.
