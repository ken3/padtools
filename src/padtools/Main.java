package padtools;

import padtools.converter.Converter;
import padtools.editor.Editor;
import padtools.util.PathUtil;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * エントリポイントクラス
 */
public class Main {
    private static Setting setting = null;
    public static Setting getSetting () {
        if(setting == null) throw new RuntimeException("Setting is not set");
        return setting;
    }

    /**
     * エントリポイント
     * @param args 引数
     */
    public static void main(String[] args) throws IOException{
        //TODO: IO例外を適切に扱ってユーザに分かる形で出力する

        //設定ファイルを読み込む
        Setting setting;
        File setting_file = new File(PathUtil.getBasePath(), "settings.xml");
        if(setting_file.exists()){
            setting = Setting.loadFromFile(setting_file);
        } else {
            setting = new Setting();
            setting.setDisableToolbar(true);
            setting.setDisableSaveMenu(true);
            setting.saveToFile(setting_file);
        }
        Main.setting = setting;

        //オプション定義
        Options opts = new Options();
        opts.addOption("i", "input",  true,  "Open spd file.");
        opts.addOption("o", "output", true,  "Save to result_file.");
        opts.addOption("s", "scale",  true,  "Image scale(available when result_file is set).");
        opts.addOption("h", "help",   false, "Show this help.");
        BasicParser parser = new BasicParser();
        HelpFormatter help = new HelpFormatter();

        try{
            CommandLine cl = parser.parse(opts, args);
            if (cl.hasOption("-h")) {
                help.printHelp("PadTools", opts);
                System.exit(1);
            }
            File file_in = null;
            if (cl.hasOption("-i")) {
                file_in = new File(cl.getOptionValue("i"));
                System.err.printf("file_in: %s\n", file_in.getPath());
            }
            File file_out = null;
            if (cl.hasOption("-o")) {
                file_out = new File(cl.getOptionValue("o"));
                System.err.printf("file_out: %s\n", file_out.getPath());
            }
            Double scale = null;
            if (cl.hasOption("-s")) {
                try {
                    scale = Double.parseDouble(cl.getOptionValue("s"));
                    System.err.printf("scale: %f\n", scale);
                } catch(NumberFormatException ex) {
                    System.err.println("不正なフォーマットのscale値が指定されました。");
                    System.exit(1);
                }
            }
            if( file_out == null ) {
                // file_out が指定されていない場合はエディタを起動
                Editor.openEditor(file_in);
            } else {
                // file_out が指定された場合はエディタを起動せず、変換のみを行う
                Converter.convert(file_in, file_out, scale);
            }
        } catch (ParseException e) {
            help.printHelp("PadTools", opts);
            System.exit(1);
        }
    }

}
