package com.hooxi.data.model.workflow;

import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.event.HooxiEvent;
import java.io.Serializable;
import java.util.List;

public class WorkflowParam implements Serializable {
  HooxiEvent hooxiEvent;
  List<Destination> eventDestinations;

  public WorkflowParam() {}

  public WorkflowParam(HooxiEvent hooxiEvent, List<Destination> eventDestinations) {
    this.hooxiEvent = hooxiEvent;
    this.eventDestinations = eventDestinations;
  }

  public HooxiEvent getHooxiEvent() {
    return hooxiEvent;
  }

  public void setHooxiEvent(HooxiEvent hooxiEvent) {
    this.hooxiEvent = hooxiEvent;
  }

  public List<Destination> getEventDestinations() {
    return eventDestinations;
  }

  public void setEventDestinations(List<Destination> eventDestinations) {
    this.eventDestinations = eventDestinations;
  }
}
