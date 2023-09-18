### Table of Contents

1. [Getting Started](#getting-started)
2. [Code Style](#code-style)
3. [Logging Style](#logging-style)
3. [Submitting Changes](#submitting-changes)
4. [Branching Strategy](#branching-strategy)
5. [Commit Message Format](#commit-message-format)
6. [Code Reviews](#code-reviews)
7. [Issue Tracking](#issue-tracking)
8. [Testing](#testing)


### Getting Started

1. Fork the repository on GitHub.
2. Clone your fork locally.
3. Add the original repository as an upstream remote.


### Code Style

- [Java Code Style Guide](java-code-style-guide.md).


### Logging Style
- [Logging Style](logging-style.md)


### Submitting Changes

1. Create a new branch.
2. Make your changes.
3. Commit your changes following our [commit message format](#commit-message-format)
4. Push your changes to your fork.
5. Open a pull request.


### Branching Strategy

- I use a feature branching model.
- The `main` branch contains the latest stable version of the project.

- Feature branches: `feature/your-feature-name`
- Bugfix branches: `fix/your-fix-name`
- ...


### Commit Message Format

- Follow the conventional commit format:

```
<type>(<scope>): <description>
```
- Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
- Scope: Optional. Specifies the area of the project affected by the commit.
- Description: Brief description of changes.


### Code Reviews

- All pull requests are subject to code reviews.
- Code reviews are performed by maintainers and contributors with `write` access to the repository.
- Follow the code review suggestions and requirements before merging.


### Issue Tracking

- I use GitHub Issues for tracking.
- Before submitting a new issue, please check to see if a similar issue already exists.


### Testing

- Make sure to write unit tests for new features and to update tests for any modified existing features.
- All tests should pass before submitting a pull request.