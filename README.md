# quarkus-rest-service

![contributors](https://img.shields.io/github/contributors/oren-ji/quarkus-rest-service)
![commits](https://img.shields.io/github/commit-activity/m/oren-ji/quarkus-rest-service)
![last commit](https://img.shields.io/github/last-commit/oren-ji/quarkus-rest-service)
![license](https://img.shields.io/github/license/oren-ji/quarkus-rest-service)

![language count](https://img.shields.io/github/languages/count/oren-ji/quarkus-rest-service)
![languages](https://img.shields.io/github/languages/top/oren-ji/quarkus-rest-service)
![repo size](https://img.shields.io/github/repo-size/oren-ji/quarkus-rest-service)

![stars](https://img.shields.io/github/stars/oren-ji/quarkus-rest-service?style=social)
![forks](https://img.shields.io/github/forks/oren-ji/quarkus-rest-service?style=social)

A sample source code to get started with Kotlin and Quarkus framework.

## Features

- OAuth Authentication
- REST Implementation

## Goal

To help developers onboard with Kotlin and Quarkus Framework.

## Getting Started

1. Install GraalVM Community 22.1 (Java)
   1. Linux - https://www.graalvm.org/22.1/docs/getting-started/linux/
   3. Windows - https://www.graalvm.org/22.1/docs/getting-started/windows/
   4. MacOS - https://www.graalvm.org/22.1/docs/getting-started/macos/
2. Install Docker
   1. Linux - https://docs.docker.com/desktop/linux/install/
   2. Windows - https://docs.docker.com/desktop/windows/install/
   3. MacOS - https://docs.docker.com/desktop/mac/install/
3. Install IntelliJ IDEA Community
   1. Linux - https://www.jetbrains.com/idea/download/#section=linux
   2. Windows - https://www.jetbrains.com/idea/download/#section=windows
   3. MacOS - https://www.jetbrains.com/idea/download/#section=mac

## Running the application

- You can run your application in dev mode that enables live coding using:

```
$ ./gradlew quarkusDev
# or
$ gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Refreshing dependencies

- You can refresh Gradle dependencies using:

```
$ ./gradlew build --refresh-dependencies
# or
$ gradlew build --refresh-dependencies
```

## Other References

You can also check the following references for more details:

- [Kotlin Docs](https://kotlinlang.org/docs/home.html)
- [Quarkus Guides](https://quarkus.io/guides/)
- [OAuth Tools](https://oauth.tools/)

## About

### Author

**Eagan Martin**
- [Github](https://github.com/oren-ji)

## Contributing

Pull requests and stars are always welcome. For bugs and feature requests, please [create an issue](https://github.com/oren-ji/quarkus-rest-service/issues/new).

1. Fork this repository and don't forget to star

2. Clone the repository

```
$ git clone https://github.com/<your-username>/quarkus-rest-service.git
```

3. To keep your forked repository in sync with the upstream repository:

```
$ git remote add upstream https://github.com/oren-ji/quarkus-rest-service.git
$ git fetch upstream
$ git pull upstream main
$ git push
```

4. Checkout to a new branch, preferred format would be:
    - For resolving issues
        - issue-<#>-<description>
    - For introducing new feature
        - feat-<description>
    - For minor fixes such as typos
        - chore-<description>

```
# Branches are only a sample
$ git checkout -b issue-1-memory-leak-fix
$ git checkout -b feat-backend-implementation
$ git checkout -b chore-minor-typo-fix
```

5. After changes has been made, add it to the staging area:

```
$ git add -A
$ git add <file>
$ git add .
```

6. Commit the staged changes, and push it the branch created earlier:

```
$ git commit -m <message>
$ git push origin <branch-name>
```

7. Issue a pull request from forked repo to this repo by clicking on New Pull Request.

8. Fill in the title and provide a concise description.

9. Wait for respose on the Pull Request issued.

10. Congratulations you just contributed to opensource!
