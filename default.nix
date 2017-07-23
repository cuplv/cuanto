{ stdenv, sbt, z3, jdk }:

# If we're using Linux openjdk, there is an option that removes lots
# of unneeded extra dependencies.
let jdk' =
      if stdenv.isLinux
      then jdk.override {minimal = true;}
      else jdk;

in

stdenv.mkDerivation rec {
  name = "cuanto-${version}";
  version = "dev";
  src = ./.;
  buildInputs = [ sbt z3 jdk' ];
}
