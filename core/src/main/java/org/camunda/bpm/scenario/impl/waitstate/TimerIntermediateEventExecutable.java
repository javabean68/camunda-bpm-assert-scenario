package org.camunda.bpm.scenario.impl.waitstate;


import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.act.Action;
import org.camunda.bpm.scenario.impl.ProcessRunnerImpl;
import org.camunda.bpm.scenario.impl.delegate.AbstractProcessInstanceDelegate;
import org.camunda.bpm.scenario.impl.util.Log;
import org.camunda.bpm.scenario.impl.util.Time;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TimerIntermediateEventExecutable extends AbstractProcessInstanceDelegate {

  public TimerIntermediateEventExecutable(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
  }

  @Override
  protected ProcessInstance getDelegate() {
    return getRuntimeService().createProcessInstanceQuery().processInstanceId(getProcessInstance().getId()).singleResult();
  }

  @Override
  protected Action action(ProcessScenario scenario) {
    return scenario.waitsAtTimerIntermediateEvent(getActivityId());
  }

  @SuppressWarnings("unchecked")
  public void execute() {
    Action action = action();
    Time.set(isExecutableAt());
    try {
      if (action != null) {
        Log.log(Log.Action.ActingOn, historicDelegate);
        action.execute(this);
      }
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
    runner.setExecuted(this);
  }

}
