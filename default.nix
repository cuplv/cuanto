{ stdenv, sbt, z3, jdk }:

stdenv.mkDerivation rec {
  name = "cuanto-${version}";
  version = "dev";
  src = ./.;
  buildInputs = [ sbt z3 (jdk.override {minimal = true;}) ];
}
