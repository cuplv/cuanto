{ stdenv, sbt, z3, jdk, japron }:

let

# If we're using Linux openjdk, there is an option that removes lots
# of unneeded extra dependencies.
jdk' =
  if stdenv.isLinux
  then jdk.override {minimal = true;}
  else jdk;

jarsPath = varName: pkgs: stdenv.mkDerivation {
  name = "jarsPath-${varName}";
  src = ./.;
  buildInputs = [jdk'] ++ pkgs;
  propagatedBuildInputs = pkgs;

  installPhase = ''
    # Create CLASSPATH at build-time
    mkdir $out
    echo $CLASSPATH > $out/${varName}

    # Export build-time CLASSPATH at run-time
    mkdir -p $out/nix-support
    cat <<EOF > $out/nix-support/setup-hook
    export ${varName}=`cat $out/${varName}`
    EOF
  '';
};

# This function takes a list of packages and produces a new package
# which sets $CUANTO_CLASSPATH to include the jars of all the input
# packages.  In this project, `sbt` is configured to load external
# unmanaged jars from $CUANTO_CLASSPATH only, so system-installed jars
# must use this function to be found.
#
# Example usage:
#     ...
#     buildInputs = [
#       sbt z3 (cuantoClasspath [japron])
#     ];
#     ...
#
# Note that the packages given to `cuantoClasspath` are *propagated*,
# so in the above example, the non-classpath parts of `japron` are
# available to outer build just as they would have if
# `cuantoClasspath` was not used.  You don't have to repeat it in the
# "top level" of `buildInputs`.
cuantoClasspath = jarsPath "CUANTO_CLASSPATH";

in

stdenv.mkDerivation rec {
  name = "cuanto-${version}";
  version = "dev";
  src = ./.;
  buildInputs = [
	sbt z3 (cuantoClasspath [japron])
  ];
}
