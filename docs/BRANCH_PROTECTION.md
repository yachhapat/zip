# Branch Protection and Merge Rules

These settings are configured in GitHub (not in code). Follow these steps to satisfy the exercise requirements.

## 1. Open branch protection

1. Go to your repo on GitHub.
2. **Settings** → **Branches**.
3. Under **Branch protection rules**, click **Add rule** (or edit the rule for `master`).

## 2. Apply these settings

| Requirement | Where to set it |
|-------------|-----------------|
| **Branch name pattern** | Set to `master`. |
| **Require status checks before merging** | ✅ Enable. Then under "Status checks that are required", add: **Lint & Test** (the name of the GitHub Actions job). |
| **Require 1 approval** | ✅ Enable "Require a pull request before merging", set "Required approvals" to **1**. |
| **Squash and merge only** | In **Settings** → **General** → scroll to **Pull Requests**. Uncheck "Allow merge commits" and "Allow rebase merging". Leave **Allow squash merging** checked. Optionally enable "Default to squash merging". |

## 3. Summary

- **Checks:** The "Lint & Test" job must pass (Checkstyle + JUnit 5).
- **Review:** At least one approval is required.
- **Merge method:** Only squash merge is allowed (and optionally set as default).

After this, PRs cannot be merged until the CI job passes and one reviewer approves, and only via squash merge.
