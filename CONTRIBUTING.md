# Contributing

## Workflow

We follow the common Gitflow Workflow. A quick [tutorial][Atlassian. Gitflow Workflow] on the Gitflow Workflow is available from Atlassian.

In brief, the core of the workflow consists of the following:

- Basing feature branches off of the `develop` branch.

  `$ git checkout -b some-feature develop`

- Merging into the `develop` branch via pull requests.
- Reserving the `master` branch for releases.

[Atlassian. Gitflow Workflow]: https://www.atlassian.com/git/tutorials/comparing-workflows#gitflow-workflow

## Commits and Commit Messages

We follow standard conventions for making commits and writing effective Git commit messages. First read the brief [How to Write a Git Commit Message] and then [Contributing to a Project][ProGit: Contributing to a Project] from the [ProGit] book. In short, make logically incremental commits and write substantitive messages that convey _why_ something was changed versus simply saying _what_ was changed. 

[How to Write a Git Commit Message]: https://chris.beams.io/posts/git-commit/
[ProGit: Contributing to a Project]: https://git-scm.com/book/en/v2/Distributed-Git-Contributing-to-a-Project "Scott Chacon and Ben Straub. ProGit: Distributed Git: Contributing to a Project."
[ProGit]: https://git-scm.com/book/

## Coding Style

We follow the standard [Scala Style Guide] and [Effective Scala] with respect to coding style. Please read over these guides before contributing.

[Scala Style Guide]: http://docs.scala-lang.org/style/
[Effective Scala]: http://twitter.github.io/effectivescala/

## API Documentation

We use Scaladoc and follow the standard guidelines from [Scaladoc for Library Authors].

[Scaladoc for Library Authors]: http://docs.scala-lang.org/overviews/scaladoc/for-library-authors.html

## Development Environment

### Dependencies

- JDK 8
- `sbt`

#### macOS: Install Dependencies

Using [Homebrew](https://brew.sh/), install dependencies as follows:
```
$ brew cask install java
$ brew install sbt
```

### Building and Testing 

See documentation on [sbt](http://www.scala-sbt.org/) for details. In brief, it is most convenient to start an `sbt` shell
```
$ sbt
```
and then

- Build

  `> compile`

- Test

  `> test`

- Generate API documentation

  `> doc`

- Start a Scala console

  `> console`  

- Prefix any command with `~` to re-execute the command when any file in the project is updated; for example,

  `> ~test`

### IntelliJ Import

If you want to use [IntelliJ IDEA](https://www.jetbrains.com/idea/) as your IDE, use the [`sbt` import wizard](https://www.jetbrains.com/help/idea/2016.3/getting-started-with-sbt.html#import_project). In brief,

- Make sure the Scala plugin is installed.
- Import via `File > New > Project from Existing Sources > Import project from external model > SBT`.
- We recommend checking `Use auto-import` to automaticaly stay in sync with `build.sbt`.
- Make sure the `Project SDK` is 1.8.

It is particularly convenient to run [ScalaTest via IntelliJ](http://www.scalatest.org/user_guide/using_scalatest_with_intellij). Get started by right-clicking on a ScalaTest `Spec` class.
