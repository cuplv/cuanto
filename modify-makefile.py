import os

JAVA_HOME = os.environ['JAVA_HOME']

target = open("apron/Makefile.config", 'w')

with open ("apron/Makefile.config.model", "r") as myfile:
	for line in myfile:
		if ("# HAS_JAVA = 1" in line):
			target.write("HAS_JAVA = 1")
		elif ("JNIINC    = -I/java/include" in line):
			l1 = os.path.join("-I" + JAVA_HOME, "include")
			l2 = os.path.join(l1, "linux")
			target.write("JNIINC = " + l1 + " " + l2)
		elif ("GMP_PREFIX = /usr" in line):
			target.write("GMP_PREFIX = /usr/lib")
		elif ("MPFR_PREFIX = /usr" in line):
			target.write("MPFR_PREFIX = /usr/lib")
		elif ("HAS_OCAML = 1" in line):
			target.write("#HAS_OCAML = 1")
		else:
			target.write(line)

target.close()