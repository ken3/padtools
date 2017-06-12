
CLASSPATH=./bin:./lib/batik-awt-util.jar:./lib/batik-dom.jar:./lib/batik-ext.jar:./lib/batik-extension.jar:./lib/batik-svggen.jar:./lib/batik-util.jar:./lib/batik-xml.jar:./lib/commons-cli.jar
export CLASSPATH

java padtools/Main $*

