package xyz.funnyboy.process.bean;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener implements TaskListener
{
    private static final long serialVersionUID = -8512525824990002897L;

    @Override
    public void notify(DelegateTask delegateTask) {
        if (delegateTask.getName()
                        .equals("经理审批")) {
            delegateTask.setAssignee("zhangsan");
        }
        else if (delegateTask.getName()
                             .equals("人事审批")) {
            delegateTask.setAssignee("sili");
        }
    }
}
