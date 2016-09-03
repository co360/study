package com.github.mdssjc.gsf.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnitTest {

  private static final int ID = 1000;
  private Unit             unit;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void initialize() {
    this.unit = new Unit(UnitTest.ID);
  }

  @Test
  public void settingAndGettingACommonProperty() {
    final String property = "type";

    this.unit.setProperty(property, "infantry");
    final Object output = this.unit.getProperty(property);

    assertEquals("infantry", output);
  }

  @Test
  public void settingAndGettingAUnitSpecificProperty() {
    final String property = "hitPoints";

    this.unit.setProperty(property, "25");
    final Object output = this.unit.getProperty(property);

    assertEquals("25", output);
  }

  @Test
  public void changingAnExistingPropertiesValue() {
    final String property = "hitPoints";
    this.unit.setProperty(property, "25");

    this.unit.setProperty(property, "15");
    final Object output = this.unit.getProperty(property);

    assertEquals("15", output);
  }

  @Test
  public void gettingANonExistentPropertiesValue() {
    this.thrown.expect(IllegalArgumentException.class);
    this.thrown.expectMessage("No properties for this Unit.");
    final String property = "strength";

    this.unit.getProperty(property);
  }

  @Test
  public void gettingANonExistentPropertyValue() {
    this.thrown.expect(IllegalArgumentException.class);
    this.thrown.expectMessage("Request for non-existent property.");
    this.unit.setProperty("hitPoints", "25");
    final String property = "strength";

    this.unit.getProperty(property);
  }

  @Test
  public void gettingTheIdProperty() {
    final int id = this.unit.getId();

    assertEquals(UnitTest.ID, id);
  }

  @Test
  public void settingGettingTheNameProperty() {
    final String name = "Damon";

    this.unit.setName(name);
    final String nameOutput = this.unit.getName();

    assertEquals(name, nameOutput);
  }

  @Test
  public void addingGettingWeapons() {
    final Weapon axe = new Weapon();

    this.unit.addWeapon(axe);
    final List<Weapon> weapons = this.unit.getWeapons();

    assertSame(axe, weapons.get(0));
  }
}
