package org.camunda.bpm.scenario.impl.waitstate;


import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.action.ScenarioAction;
import org.camunda.bpm.scenario.delegate.EventSubscriptionDelegate;
import org.camunda.bpm.scenario.impl.ProcessRunnerImpl;
import org.camunda.bpm.scenario.impl.delegate.AbstractEventSubscriptionDelegate;

import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class MessageIntermediateCatchEventWaitstate extends AbstractEventSubscriptionDelegate {

  public MessageIntermediateCatchEventWaitstate(ProcessRunnerImpl runner, HistoricActivityInstance instance, String duration) {
    super(runner, instance, duration);
  }

  @Override
  protected EventSubscription getDelegate() {
    return getRuntimeService().createEventSubscriptionQuery().eventType("message").executionId(getExecutionId()).singleResult();
  }

  @Override
  protected ScenarioAction<EventSubscriptionDelegate> action(Scenario.Process scenario) {
    return scenario.actsOnMessageIntermediateCatchEvent(getActivityId());
  }

  protected void leave() {
    getRuntimeService().messageEventReceived(getDelegate().getEventName(), getDelegate().getExecutionId());
  }

  protected void leave(Map<String, Object> variables) {
    getRuntimeService().messageEventReceived(getDelegate().getEventName(), getDelegate().getExecutionId(), variables);
  }

  @Override
  public void receive() {
    leave();
  }

  @Override
  public void receive(Map<String, Object> variables) {
    leave(variables);
  }

}
