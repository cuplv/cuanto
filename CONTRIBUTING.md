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

### Manually setting up an environment

If you would like to set up a build environment using your own package
manager, you will need to find and install the following tools:

- JDK 8
- `sbt`
- `z3`
- `gmp`
- `mpfr`

After installing the tools listed above, you need to download Apron source code,
compile it and configure the environment on your machine (assume you are using linux):
1. Clone source code: `svn co svn://scm.gforge.inria.fr/svnroot/apron/apron/trunk apron`
2. Enter Apron's root directory: `cd apron`
3. Configure: `./configure -prefix $out -no-cxx -absolute-dylibs`
4. Compile: `make`
5. Test: `make test`
6. Enter cuanto's root directory: `cd cuanto`
7. Create a lib folder in cuanto: `mkdir lib`
8. Move the followings files from `apron/` to `cuanto/lib/`: apron.jar, gmp.jar, libapron.so, libboxD.so, libjapron.so, libjgmp.so, liboctD.so, libpolkaMPQ.so (Note that these files are not necessarily in Apron's root directory. They might be under some sub-directories.)
9. Open a shell and type: `export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$CUANTO/lib` (replace $CUANTO with the absolute path of cuanto)
10. Done

If you are using MacOS, things will be slightly different when setting up Apron:
1. Replace step 8 above with: 
Move the followings files from `apron/` to `cuanto/lib/`: apron.jar, gmp.jar, libapron.so, libboxD.so, libjapron.dylib (renamed from libjapron.so), libjgmp.dylib (renamed from libjgmp.so), libjgmp.so, liboctD.so, libpolkaMPQ.so (Note that these files are not necessarily in Apron's root directory. They might be under some sub-directories.)
2. Replace step 9 above with:
Open a shell and type: `export DYLD_LIBRARY_PATH=$DYLD_LIBRARY_PATH:$CUANTO/lib` (replace $CUANTO with the absolute path of cuanto)

### Setting up an environment on MacOS with Homebrew

Using [Homebrew](https://brew.sh/), install dependencies as follows:
```
$ brew cask install java
$ brew install sbt
$ brew install z3
$ brew install gmp
$ brew install mpfr
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
