# Deliverables Checklist

Use this when submitting the take-home exercise.

## 1. Documentation (PDF)

- **Source:** [DOCUMENTATION.md](../DOCUMENTATION.md) in the repo root.
- **Action:** Export to PDF (e.g. open in VS Code → Print / Export as PDF, or use Pandoc / browser Print to PDF).
- **Content:** Process description, failure modes, notifications, and stakeholders.

## 2. Git Repository and CI Credentials

- **Repository:** Create a new repo on GitHub (e.g. `ci-pipeline-demo`), push this code, and submit the **link** to the repo.
- **CI provider:** This project uses **GitHub Actions** (built into GitHub).
  - **Credentials:** No separate CI account is needed. Use the **GitHub account** that owns the repository. For private repos or extra integrations, use **Settings → Secrets and variables → Actions** for any secrets (this repo does not require any by default).
  - If you use a different CI (e.g. Circle CI, Buildkite): add the workflow config for that tool and provide the **link to the CI project/dashboard** and any **login or API token** instructions as required by the evaluator.

## 3. Video Recording (Walkthrough)

- **Format:** Loom or similar (as requested).
- **Suggested content:**
  1. Show the repo structure and the CI workflow file (`.github/workflows/ci.yml`).
  2. Run `mvn checkstyle:check` and `mvn test` locally.
  3. Show branch protection and merge settings (or describe where they are).
  4. Optionally: open a PR, show CI running, and show that merge is blocked until checks pass and there is an approval.
