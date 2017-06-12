
# クラスパス設定
CLASSPATH=.:../lib/batik-awt-util.jar:../lib/batik-dom.jar:../lib/batik-ext.jar:../lib/batik-extension.jar:../lib/batik-svggen.jar:../lib/batik-util.jar:../lib/batik-xml.jar:../lib/commons-cli.jar
export CLASSPATH

# ターゲットディレクトリ
[ -d bin ] || mkdir -p bin
pushd src

# JAVAコンパイル
JAVAC_OPTIONS="-d ../bin -Xlint:unchecked"
for f in `find . -name '*.java'`
do
    echo $f
    javac $JAVAC_OPTIONS $f
done
for f in `find . -name '*.png'`
do
    echo $f
    d="../bin/`dirname $f`"
    [ -d $d ] || mkdir -p $d
    cp -p $f $d
done

# ターゲットディレクトリ
popd
pushd bin

# JARファイル生成
jar cfm ../lib/padtools.jar ../main-class.mf `find . -name '*.class'` `find . -name '*.png'`

popd
exit 0

