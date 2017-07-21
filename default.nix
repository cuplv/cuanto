{ stdenv, sbt, z3, japron, jdk }:

stdenv.mkDerivation rec {
  name = "cuanto-${version}";
  version = "dev";
  src = ./.;
  buildInputs = [ sbt z3 japron jdk ];
}
