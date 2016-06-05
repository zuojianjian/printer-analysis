package com.github.openthos.printer.localprint.task;

import com.github.openthos.printer.localprint.model.JobItem;

import java.util.List;

/**
 * 取消打印任务 C3
 * Created by bboxh on 2016/6/5.
 */
public class JobCancelTask<Progress> extends CommandTask<JobItem, Progress, Boolean> {
    @Override
    protected String[] setCmd(JobItem... params) {

        JobItem item = params[0];

        return new String[]{"sh", "proot.sh", "lp", "-i", String.valueOf(item.getJobId()), "-H", "cancel"};
    }

    @Override
    protected Boolean handleCommand(List<String> stdOut, List<String> stdErr) {

        // TODO: 2016/6/5  取消打印任务 C3
        
        return true;
    }

    @Override
    protected String bindTAG() {
        return "JobCancelTask";
    }
}
