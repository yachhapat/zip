# CI Pipeline Demo

A minimal Maven-based Java repository demonstrating a CI pipeline with **one linter** (Checkstyle) and **one set of unit tests** (JUnit 5), suitable for take-home exercises and branch protection with required checks and reviews.

## Requirements

- Java 17+
- Maven 3.6+

## Setup

```bash
mvn verify
```

## Commands

| Command | Description |
|---------|-------------|
| `mvn checkstyle:check` | Run Checkstyle linter on `src/main/java` |
| `mvn test` | Run JUnit 5 unit tests |
| `mvn verify` | Run both (validate + test phase) |
| `./ci-local.sh` | Run same checks as CI (Checkstyle + tests) |

## CI

- **Tool:** GitHub Actions
- **Triggers:** Every pull request targeting `master`, and every push to `master`
- **Checks:** Checkstyle (linter) and JUnit 5 (unit tests)

Branch protection (require status checks, 1 approval, squash merge) is configured in the repository settings. See [DOCUMENTATION.md](./DOCUMENTATION.md) and [docs/BRANCH_PROTECTION.md](./docs/BRANCH_PROTECTION.md) for details.
