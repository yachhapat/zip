# CI Pipeline: Process, Failure Modes, Notifications & Stakeholders

This document describes the CI pipeline implemented for this repository, how it can fail, what notifications it produces, and who should be involved as the process evolves. It is intended to be exported to PDF for submission.

---

## 1. The Process

### 1.1 Overview

The pipeline uses **GitHub Actions** as the CI tool. It runs two checks on every relevant event:

1. **Linter:** Checkstyle over the Java source in `src/main/java`.
2. **Unit tests:** JUnit 5 (via Maven Surefire) over the test suite in `src/test/java`.

Both must pass for the run to be considered successful.

### 1.2 When the Pipeline Runs

| Event | What runs |
|-------|------------|
| **Pull request** opened or updated (target branch: `master`) | Full CI (lint + tests) on the PR head commit. |
| **Push to `master`** | Full CI (lint + tests) on the new commit. |

So: every PR sees the same checks before merge, and every merge to `master` is validated again on push.

### 1.3 Merge Requirements (Branch Protection)

Merge into `master` is only allowed when **all** of the following are true:

1. **All CI checks pass** — The "Lint & Test" job must complete successfully.
2. **At least one PR review approval** — One designated reviewer (or any collaborator with write access, depending on org settings) must approve the PR.
3. **Squash and rebase behaviour** — The repository is configured to **allow only squash merging** (and optionally "Default to squash merging"). So PRs are merged as a single squashed commit; the requirement is effectively "squash (and optionally rebase before squash) for PR merging."

Branch protection and merge options are set in **GitHub → Repository → Settings → Branches** and **Settings → General → Pull Requests**. See `docs/BRANCH_PROTECTION.md` for step-by-step configuration.

### 1.4 End-to-End Flow

1. Developer opens a PR from a feature branch into `master`.
2. GitHub Actions triggers the CI workflow (checkout → install → lint → test).
3. If lint or tests fail, the PR shows a failing status; the author (and reviewers) see the failure and fix the code.
4. When CI is green, a reviewer approves the PR.
5. The PR is merged using **Squash and merge** only.
6. The push to `master` triggers CI again on the new commit; if it fails, the team is notified (see Notifications below).

---

## 2. Ways This Process Can Fail

### 2.1 CI Failures (Lint or Tests)

- **Linter failure:** New or changed code violates Checkstyle rules (e.g. style, braces, unused imports). Merge is blocked until the author fixes the issues.
- **Unit test failure:** A test fails or times out. Merge is blocked until the author fixes the code or updates the tests appropriately.
- **Flaky tests:** Intermittent failures can block or delay merges and erode trust in CI. Mitigation: fix or quarantine flaky tests and add retries or better isolation if needed.

### 2.2 Infrastructure and Environment

- **GitHub Actions outage or degradation:** Workflows may not start, may queue for a long time, or may fail for platform reasons. Merge is blocked until GitHub recovers or you switch CI provider.
- **Broken or changed dependencies:** Maven dependency resolution can fail due to repository issues, removed artifacts, or version conflicts. Mitigation: pin plugin/dependency versions in `pom.xml`, use a private repository mirror if needed, and periodic updates.
- **Wrong JDK or runner image:** If the workflow or base image changes (e.g. JDK version), the build or tests can fail. Keep the JDK version in the workflow and `pom.xml` aligned with team standards.

### 2.3 Configuration and Permissions

- **Branch protection misconfiguration:** If the required status check name doesn’t match the job name (e.g. "Lint & Test"), or the rule is disabled, PRs might merge without CI passing. Double-check **Settings → Branches** and the exact job name in the workflow.
- **Insufficient permissions:** The GitHub token or repo permissions might prevent install/run steps (e.g. private packages). Adjust workflow or repository permissions as needed.
- **Secrets or env missing:** If you later add steps that need secrets (e.g. API keys) and they’re not set in the repo, those steps will fail. Document required secrets and env vars.

### 2.4 Human and Process

- **Merge without waiting for CI:** If someone merges via a path that bypasses branch protection (e.g. direct push to `master` by an admin, or protection not applied), broken code can reach `master`. Enforce protection for everyone, including admins, and avoid direct pushes to `master`.
- **Approval from the author:** Some setups allow the PR author to approve their own PR. If the requirement is “someone else must approve,” configure “Dismiss stale pull request approvals when new commits are pushed” and restrict who can approve (e.g. code owners).
- **Squash merge not used:** If merge commits or rebase merge are still allowed, the “squash only” policy can be bypassed. Disable other merge types in **Settings → General → Pull Requests**.

### 2.5 Post-Merge (Push to master)

- **CI fails on `master` after merge:** The squashed commit might differ from what was tested on the PR (e.g. merge-order issues, or rare race conditions). CI runs again on push to `master`; if it fails, treat it as a broken mainline and fix or revert immediately. Notifications (below) help surface this.

---

## 3. Notifications: What Is Sent and Where

### 3.1 GitHub UI and Email

- **PR page:** The PR shows the status of the "Lint & Test" job (pass/fail/pending). No extra configuration needed.
- **Email (default):** GitHub can send email when:
  - A workflow run fails or succeeds (depends on **Notification** settings).
  - Someone is requested as a reviewer or when a review is submitted.
- **Location:** Inbox of the GitHub account (and any linked email). Users control this under **GitHub → Settings → Notifications**.

### 3.2 Repository and Org Level

- **Actions tab:** **Repository → Actions** lists all workflow runs. Failed runs are visible here; you can click for logs.
- **Commit status:** The commit (and thus the PR) shows a green check or red X for the required check. This is what branch protection uses to allow or block merge.
- **No built-in Slack/Teams/PagerDuty:** Out of the box, GitHub Actions does not send notifications to Slack, Microsoft Teams, or PagerDuty. To get notifications there you must add steps (e.g. Slack workflow, webhook, or a dedicated action) in the workflow or use GitHub’s integrations (e.g. Slack app for GitHub).

### 3.3 What You Can Add Later

- **Slack/Teams:** A step that runs on failure (or on success) and calls an incoming webhook or API to post to a channel.
- **Email digests:** Scheduled or event-driven emails summarizing failed runs (e.g. via a cron job or third-party tool).
- **Status badges:** Add a workflow status badge to the README so the default branch’s CI status is visible at a glance.

---

## 4. Stakeholders to Involve as the Process Evolves

| Stakeholder | Role in evolving the process |
|-------------|------------------------------|
| **QA / Test engineers** | Own and extend automated checks (more linters, more test suites, coverage gates). Define what “quality” means for this repo and how CI should enforce it. |
| **Developers** | Consume CI feedback daily; should have input on which rules and tests run, how fast they need to be, and how to fix or relax rules that are too noisy. |
| **DevOps / Platform / SRE** | Own CI runtime (GitHub Actions or migration to Circle CI, Buildkite, etc.), secrets, runners, and integration with deployment pipelines. Involved when adding CD or environment-specific jobs. |
| **Engineering / Tech leads** | Decide branch strategy (e.g. `master` vs `main`), who can approve PRs, and whether to require code owners or multiple approvals as the team grows. |
| **Security / Compliance** | Involved when adding dependency scanning, license checks, or security gates that can block merges; may drive notification and audit requirements. |
| **Product / Project** | Need to know that “merge” implies “checks passed + reviewed”; any change that adds new required checks or blocks (e.g. E2E) can affect delivery timelines and should be communicated. |

As you add more checks, environments, or integrations (e.g. Slack, Jira), bring in the right subset of these stakeholders so the process stays aligned with quality, security, and delivery goals.

---

## 5. Quick Reference

- **CI tool:** GitHub Actions  
- **Workflow file:** `.github/workflows/ci.yml`  
- **Checks:** Checkstyle (linter), JUnit 5 (unit tests)  
- **Branch:** `master` (protection and push events)  
- **Merge rules:** All checks pass + 1 approval + squash merge only  
- **Branch protection setup:** See `docs/BRANCH_PROTECTION.md`  
- **Repo and credentials:** See deliverables (link to repo; for GitHub Actions, use the GitHub account that owns the repo; no separate CI “credentials” unless you add secrets).

---

*End of documentation. Export this file to PDF for submission.*
