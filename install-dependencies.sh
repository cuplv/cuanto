#!/bin/sh

#find /usr/lib/jvm/java-8-oracle -name "jni.h"
#find /usr/lib/jvm/java-8-oracle -name "jni_md.h"

#echo $JAVA_HOME

svn co svn://scm.gforge.inria.fr/svnroot/apron/apron/trunk apron
python modify-makefile.py
cd apron
make
#make install
cd ..
mkdir lib/
mv apron/japron/libjapron.so lib/
mv apron/japron/libjgmp.so lib/
mv apron/japron/apron.jar lib/
mv apron/japron/gmp.jar lib/
mv apron/box/libboxD.so lib/
mv apron/octagons/liboctD.so lib/
mv apron/apron/libapron.so lib/
mv apron/newpolka/libpolkaMPQ.so lib/

#ls -al lib/