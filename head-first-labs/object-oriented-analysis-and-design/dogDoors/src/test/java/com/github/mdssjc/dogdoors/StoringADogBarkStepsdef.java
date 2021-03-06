package com.github.mdssjc.dogdoors;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

public class StoringADogBarkStepsdef {

  private BarkManager manager;
  private List<Bark> barks;

  @Given("^The owner’s dog barks\\$ into the dog door\\.$")
  public void the_owner_s_dog_barks$_into_the_dog_door(
      final List<String> barks) {
    this.manager = new BarkManager();
    this.barks = barks.stream()
                      .map(Bark::new)
                      .collect(toList());
  }

  @When("^The dog door stores the owner’s dog’s bark\\.$")
  public void the_dog_door_stores_the_owner_s_dog_s_bark() {
    this.barks.forEach(this.manager::addAllowedBark);
  }

  @Then("^The barks\\$ are stored\\.$")
  public void the_barks$_are_stored(final List<String> barks) {
    this.manager.getAllowedBarks()
                .forEachRemaining(
                    b -> assertTrue(barks.contains(b.getSound())));
  }
}
