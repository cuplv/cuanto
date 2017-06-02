# cuanto [![Develop Branch Build Status](https://travis-ci.org/cuplv/cuanto.svg?branch=develop)](https://travis-ci.org/cuplv/cuanto)

CU Suite of Program Analysis Tools

# Compiling Dependencies
*soot*
git clone https://github.com/Sable/jasmin 
cd jasmin cp ant.settings.template ant.settings 
ant publish-local 
cd ..

git clone https://github.com/Sable/heros 
cd heros cp ant.settings.template ant.settings 
ant publish-local 
cd ..

git clone https://github.com/cuplv/soot 
cd soot 
cp ant.settings.template ant.settings

Comment the following line in ant.settings: jastaddfrontend.loc=../JastAddExtensions/SootJastAddJ
Set the path to jasmin, heros and guava
jasmin.jar=<yourhome>/.ivy2/local/ca.mcgill.sable/jasmin/2.2.1/jars/jasmin.jar

heros.jar=<yourhome>/.ivy2/local/ca.mcgill.sable/heros/trunk/jars/heros.jar

guava.jar=<heros-git-repository-path>/guava-18.0.jar

ant publish-local

cd ..

## Contributing

Please see [CONTRIBUTING](CONTRIBUTING.md) for information on our contribution processes and setting up a development environment.


