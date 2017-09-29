# Contributing

## Workflow

We follow the common Gitflow Workflow. A quick [tutorial][Atlassian. Gitflow Workflow] on the Gitflow Workflow is available from Atlassian.

In brief, the core of the workflow consists of the following:

- Basing feature branches off of the `develop` branch.

  `$ git checkout -b some-feature develop`

- Merging into the `develop` branch via pull requests.
- Reserving the `master` branch for releases.

Overall, we follow common [git best practices][Bao. Gitflow and Pull Request]. Please read over this document before making contributions, particularly about Gitflow, commit messages, pull requests (including [types of pull requests][Balter. Types of Pull Requests]), and code reviews.

[Atlassian. Gitflow Workflow]: https://www.atlassian.com/git/tutorials/comparing-workflows#gitflow-workflow
[Bao. Gitflow and Pull Request]: https://devblog.dwarvesf.com/post/git-best-practices/
[Balter. Types of Pull Requests]: http://ben.balter.com/2015/12/08/types-of-pull-requests/

### Commits and Commit Messages

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

This project is primarily a Scala project using sbt for building with a few external dependencies.

### Dependencies

If you would like to set up a build environment using your own package manager, you will need to find and install the following tools:

- JDK 8
- `sbt`
- `z3`

### With the Nix Package Manager

The simplest way to set up the build environment if you are running Linux,
macOS, or Windows (with a Linux subsystem) is with the portable [Nix Package
Manager][Nix].

#### macOS: Installing Nix with Homebrew

```bash
$ brew cask install nix
```

#### Installing the CUPLV Nix Overlay 

With the Nix Package Manager installed, we need to add the [cuplv-nixpkgs] overlay (globally once).

```bash
# From a desired install location, add the cuplv-nixpkgs overlay.
$ git clone https://github.com/cuplv/cuplv-nixpkgs

# This symlinks cuplv-nixpkgs to ~/.config/nixpkgs/overlays/cuplv-nixpkgs
$ cuplv-nixpkgs/install
```

#### Entering the Cuanto Build Environment

Then, use `nix-shell` to open a shell with all dependencies available.

```bash
# Enter the cuanto build environment
$ cd cuanto
$ nix-shell
    
# Test the build environment
[nix-shell:~/cuanto]$ sbt test
```  

[Nix]: https://nixos.org/nix/
[cuplv-nixpkgs]: https://github.com/cuplv/cuplv-nixpkgs

### Setting up a native environment

We offer some instructions on setting up a native build environment. Most dependencies are JVM-based, so this should work for most things.

### macOS with Homebrew

Using [Homebrew], install dependencies as follows:

```bash
$ brew cask install java
$ brew install sbt
$ brew install z3
```

[Homebrew]: https://brew.sh/

### Building and Testing 

See documentation on [sbt](http://www.scala-sbt.org/) for details. In brief, it is most convenient to start an `sbt` shell
```bash
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

### Troubleshooting

#### Nix Shell

On some Linux and macOS systems, the system-installed version of bash is too old for `nix-shell` to use.  You will see this error if you have this [issue][Nix Bash Issue]:

```bash
$ nix-shell

bash: local: -n: invalid option
local: usage: local name[=value] ...
bash: local: -n: invalid option
local: usage: local name[=value] ...
bash: failureHooks: command not found
```

To fix this issue, you will need a newer version of bash. For example, on macOS, you can get a newer version of bash via [Homebrew].

If it is inconvenient to update your system, you can install a suitable version using Nix itself:

```bash
$ nix-env -iA nixpkgs.bashInteractive
```

[Nix Bash Issue]: https://github.com/NixOS/nixpkgs/issues/27493

### Running on Windows

We recommend using the [Bash on Windows] for development. This is an almost fully functional implementation of the Linux environment from which you can run bash.  After following these instructions, open a bash terminal and follow the instructions for Linux. We currently do not have a good way to make IntelliJ work in this environment, but the command line is fully functional.

[Bash on Windows]: https://msdn.microsoft.com/en-us/commandline/wsl/install_guide

If `nix-shell` emits a disk I/O error then it may be necessary to apply the following [fix][Windows sqlite. Within the `/etc/nix/nix.conf` add the line
```
use-sqlite-wal = false
```

[Windows sqlite]: https://github.com/NixOS/nix/issues/1203
