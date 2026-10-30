# Provider picker correction

The existing home provider bottom sheet is the only selection list. Selecting a provider toggles its selection and keeps the sheet open. Apply commits the selection; Cancel leaves the provider selection unchanged. One provider uses the original single-provider route; multiple providers use the existing combined-home backend. None and Random remain exclusive choices. Pinning, language labels, media filters and provider settings remain in the original list. Selections are stored by provider identity so filtering or pinning does not transfer selections to other rows.

The extra multiple-source button and second dialog have been removed. This correction does not change device sharing, recommendations, the player, the launcher or Windows.

PR #3292 was closed by a maintainer for excessive scope and code quality. This correction addresses the duplicated picker only, and does not claim to resolve all feedback on that larger proposal. Do not reopen it automatically or describe it as accepted. A future replacement proposal must be reduced and reviewed as a whole.

Validation: see the final command result in the work log. Physical touch/D-pad checks remain pending.

AI assistance: OpenAI Codex was used for this correction and validation.
` :app:compilePrereleaseDebugKotlin` and `:app:testPrereleaseDebugUnitTest` passed on 2026-10-30. No physical-device validation was performed.
