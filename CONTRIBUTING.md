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

### Getting an environment with `nix-shell`

The simplest way to set up the build environment if you are running
Linux, MacOS, or Windows (with a Linux subsystem) is with
the [Nix package manager](http://nixos.org/nix/).  With the Nix
package manager installed (see [Nix project](http://nixos.org/nix/)),
add the [`cuplv-nixpkgs`](https://github.com/cuplv/cuplv-nixpkgs)
overlay and then use `nix-shell` to open a shell with all dependencies
available.

    # Add the cuplv-nixpkgs overlay
    $ git clone https://github.com/cuplv/cuplv-nixpkgs
    $ cuplv-nixpkgs/install
    
    # Clone the cuanto project
    $ git clone https://github.com/cuplv/cuanto
    
    # Enter the cuanto build environment
    $ cd cuanto
    $ nix-shell
    
    # Test the build environment
    [nix-shell:~/cuanto]$ sbt test

#### Troubleshooting

On some Linux and MacOS systems, the system-installed version of bash
is too old for `nix-shell` to use.  If this is the case, you'll see
this error:

```
$ nix-shell

bash: local: -n: invalid option
local: usage: local name[=value] ...
bash: local: -n: invalid option
local: usage: local name[=value] ...
bash: failureHooks: command not found
```
To fix it, you'll need a newer version of bash.  The MACOS bash can be updated using homebrew. Be careful while changing shells, it may be a good idea to test the new shell first by running it.  When run, a properly configured shell should look very similar to the one it was run from and be interactive.
```
brew install bash
sudo -s
echo /usr/local/bin/bash >> /etc/shells
chsh -s /usr/local/bin/bash
```

If it is inconvenient
to update your system, you can install a suitable version using Nix
itself:

```$ nix-env -iA nixpkgs.bashInteractive```

(source: https://github.com/NixOS/nixpkgs/issues/27493)

### Manually setting up an environment

If you would like to set up a build environment using your own package
manager, you will need to find and install the following tools:

- JDK 8
- `sbt`
- `z3`

### Setting up an environment on MacOS with Homebrew

Using [Homebrew](https://brew.sh/), install dependencies as follows:
```
$ brew cask install java
$ brew install sbt
$ brew install z3
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

### Running on windows

We recommend using the Ubuntu Bash on windows for development under linux. (https://msdn.microsoft.com/en-us/commandline/wsl/install_guide) . This is an almost fully functional implementation of the linux environment from which you can run bash.  After following these instructions open a bash terminal and follow the instructions for linux.  We currently do not have a good way to make intellij work in this environment but command line is fully functional.

#Debugging:
If nix shell emits a disk I/O error then it may be neccessary to apply the following fix: https://github.com/NixOS/nix/issues/1203

Within the /etc/nix/nix.conf add the line
```
use-sqlite-wal = false
```
