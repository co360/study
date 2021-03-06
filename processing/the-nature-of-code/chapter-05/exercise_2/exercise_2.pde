import shiffman.box2d.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

ArrayList<Box> boxes;
Box2DProcessing box2d;

void setup() {
  size(640, 360);
  boxes = new ArrayList<Box>();
  box2d = new Box2DProcessing(this);
  box2d.createWorld();
}

void draw() {
  background(255);

  if (mousePressed) {
    Box p = new Box();
    boxes.add(p);
  }

  for (Box b : boxes) {
    b.display();
  }

  box2d.step();
}