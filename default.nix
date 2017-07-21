{ stdenv, sbt, z3, jdk, japron }:

# If we're using Linux openjdk, there is an option that removes lots
# of unneeded extra dependencies.
let jdk' =
      if stdenv.isDarwin
      then jdk
      else jdk.override {minimal = true;};

in

stdenv.mkDerivation rec {
  name = "cuanto-${version}";
  version = "dev";
  src = ./.;
  buildInputs = [ sbt z3 jdk japron ];
}
