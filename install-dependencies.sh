#!/bin/sh

find /usr/lib/jvm/java-8-oracle -name "jni.h"
find /usr/lib/jvm/java-8-oracle -name "jni_md.h"

echo $JAVA_HOME

svn co svn://scm.gforge.inria.fr/svnroot/apron/apron/trunk apron
python modify-makefile.py
cd apron
make
make install

