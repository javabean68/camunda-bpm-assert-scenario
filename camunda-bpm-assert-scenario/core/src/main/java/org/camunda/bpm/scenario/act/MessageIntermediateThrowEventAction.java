package org.camunda.bpm.scenario.act;

import org.camunda.bpm.scenario.delegate.ExternalTaskDelegate;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public interface MessageIntermediateThrowEventAction extends Action<ExternalTaskDelegate> {

  @Override
  void execute(final ExternalTaskDelegate externalTask) throws Exception;

}
