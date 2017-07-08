#!/bin/sh

sudo find / -name "jni.h"
sudo find / -name "jni_md.h"

echo $JAVA_HOME

svn co svn://scm.gforge.inria.fr/svnroot/apron/apron/trunk apron
python modify-makefile.py
cd apron
make
make install

