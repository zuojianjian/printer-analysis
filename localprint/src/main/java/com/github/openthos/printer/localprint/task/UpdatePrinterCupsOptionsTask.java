package com.github.openthos.printer.localprint.task;

import com.github.openthos.printer.localprint.model.PrinterCupsOptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bboxh on 2016/5/31.
 */
public abstract class UpdatePrinterCupsOptionsTask<Progress> extends CommandTask<List<PrinterCupsOptionItem>, Progress, Boolean>{
    @Override
    protected String[] setCmd(List<PrinterCupsOptionItem>... params) {

        // TODO: 2016/5/31 修改打印机高级配置 B5
        List<String> list = new ArrayList<String>();
        list.add("sh");
        list.add("proot.sh");
        list.add("lpoptions");
        list.add("-p");
        list.add(getPrinter());

        for(PrinterCupsOptionItem item : params[0]){
            if(item.getDef() != item.getDef2()){
                list.add("-o");
                list.add(item.getOption_id() + "=" + item.getOption().get(item.getDef2()));
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * 取得打印机的名称
     * @return
     */
    protected abstract String getPrinter();

    @Override
    protected Boolean handleCommand(List<String> stdOut, List<String> stdErr) {

        for (String line : stdErr) {

            if (line.startsWith("WARNING"))
                continue;
            else if (line.contains("Bad file descriptor")) {
                if (startCups()) {
                    runCommandAgain();      //再次运行命令
                    return null;
                } else {
                    ERROR = "Cups start failed.";
                    return null;
                }
            }
        }
        return null;
    }


    @Override
    protected String bindTAG() {
        return "UpdatePrinterCupsOptionsTask";
    }
}