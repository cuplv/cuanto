import os

JAVA_HOME = os.environ['JAVA_HOME']

target = open("apron/Makefile.config", 'w')

with open ("apron/Makefile.config.model", "r") as myfile:
	for line in myfile:
		if ("# HAS_JAVA = 1" in line):
			target.write("HAS_JAVA = 1")
		elif ("JNIINC    = -I/java/include" in line):
			target.write(os.path.join("JNIINC = -I" + JAVA_HOME, "include"))
		elif ("GMP_PREFIX = /usr" in line):
			target.write("GMP_PREFIX = /usr/lib")
		elif ("MPFR_PREFIX = /usr" in line):
			target.write("MPFR_PREFIX = /usr/lib")
		else:
			target.write(line)

target.close()